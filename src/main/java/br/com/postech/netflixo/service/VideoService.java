package br.com.postech.netflixo.service;

import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.domain.repository.VideoRepository;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final ReactiveMongoTemplate mongoTemplate;

    public VideoService(VideoRepository videoRepository, ReactiveMongoTemplate mongoTemplate) {
        this.videoRepository = videoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Flux<Video> getVideos() {
       return videoRepository.findAll();
    }

    public Mono<Video> createVideo(Video video) {
        return videoRepository.save(video);
    }

    public Flux<Video> findVideoByTitle(String title) {
        Query query = new Query(Criteria.where("title").is(title));
        return mongoTemplate.find(query, Video.class);
    }
}