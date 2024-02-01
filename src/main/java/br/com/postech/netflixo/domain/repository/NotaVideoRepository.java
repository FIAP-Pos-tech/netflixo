package br.com.postech.netflixo.domain.repository;

import br.com.postech.netflixo.domain.entity.NotaVideo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaVideoRepository extends MongoRepository<NotaVideo, String> {
}