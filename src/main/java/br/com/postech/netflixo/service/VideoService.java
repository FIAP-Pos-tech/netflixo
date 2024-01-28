package br.com.postech.netflixo.service;

import br.com.postech.netflixo.component.StorageComponent;
import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.domain.repository.VideoRepository;
import com.google.cloud.storage.Blob;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private StorageComponent storageComponent;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Flux<Video> getVideos() {
       return videoRepository.findAll();
    }

    public Mono<Video> createVideo(Video video) {
        return videoRepository.save(video);
    }

    public Flux<Video> findVideoByTitle(String title) {
        return videoRepository.findByTitle(title);
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

    private StorageComponent getStorageComponent() {
        if (storageComponent == null) {
            storageComponent = new StorageComponent("bucketName", "projectId");
        }
        return storageComponent;
    }
}