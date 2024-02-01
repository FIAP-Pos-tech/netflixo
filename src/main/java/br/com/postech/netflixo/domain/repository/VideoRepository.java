package br.com.postech.netflixo.domain.repository;

import br.com.postech.netflixo.domain.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface VideoRepository extends ReactiveMongoRepository<Video, String> {
    Flux<Video> findByTitle(String title);

    Flux<Video> findByCategory(String category, PageRequest of);
}
