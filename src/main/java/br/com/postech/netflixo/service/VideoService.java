package br.com.postech.netflixo.service;

import br.com.postech.netflixo.component.StorageComponent;
import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.domain.repository.VideoRepository;
import com.google.cloud.storage.Blob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);
    private static final int MAX_CHUNK_SIZE = 1024 * 1024 * 100;
    private final VideoRepository videoRepository;
    private StorageComponent storageComponent;
    private ReactiveMongoTemplate mongoTemplate;

    public VideoService(VideoRepository videoRepository, ReactiveMongoTemplate mongoTemplate) {
        this.videoRepository = videoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    private StorageComponent getStorageComponent() {
        if (storageComponent == null) {
            storageComponent = new StorageComponent("bucketName", "projectId");
        }
        return storageComponent;
    }

    public Flux<Video> getVideos(Pageable pageable) {
        Query query = new Query().with(pageable);
        return mongoTemplate.find(query, Video.class);
    }

    public Mono<Video> createVideo(Video video) {
        video.setId(UUID.randomUUID().toString());
        if (Objects.isNull(video.getUrl())) {
            video.setUrl(String.format("https://storage.googleapis.com/netflixo-videos/%s/%s.mp4", video.getCategory(), video.getId()));
        }
        if (Objects.isNull(video.getPublicationDate())) {
            video.setPublicationDate(LocalDateTime.now());
        }
        try {
            return videoRepository.save(video);
        } catch (Exception e) {
            log.error("Failed to save video", e);
            return Mono.error(e);
        }
    }

    public Flux<Video> findVideoByCategory(String category, int page, int size) {
        return videoRepository.findByCategory(category, PageRequest.of(page, size));
    }

    public Mono<byte[]> getVideoContentById(String id) {
        Mono<Video> videoMono = videoRepository.findById(id);
        Video video = videoMono.block();
        Blob blob = getStorageComponent().getVideoContent(video);
        try {
            File tempVideoFile = File.createTempFile("video", ".mp4");
            blob.downloadTo(tempVideoFile.toPath());
            return Mono.just(Files.readAllBytes(tempVideoFile.toPath()));
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

    public void uploadVideoContent(String id, byte[] content) {
        Mono<Video> videoMono = videoRepository.findById(id);
        Video video = videoMono.block();
        try {
            File file = File.createTempFile("video", ".mp4");
            Files.write(file.toPath(), content);
            getStorageComponent()
                    .uploadFileChunked(file, String.format("%s/%s.mp4", video.getCategory(), video.getId()), MAX_CHUNK_SIZE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}