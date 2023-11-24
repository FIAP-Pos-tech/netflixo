package br.com.postech.netflixo.domain.repository;

import br.com.postech.netflixo.domain.entity.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String> {
}
