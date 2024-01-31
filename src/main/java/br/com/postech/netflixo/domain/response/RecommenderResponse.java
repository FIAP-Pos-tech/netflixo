package br.com.postech.netflixo.domain.response;

import java.util.List;

import br.com.postech.netflixo.domain.entity.RecommenderFilm;

public record RecommenderResponse(List<RecommenderFilm> recommender) {
}



