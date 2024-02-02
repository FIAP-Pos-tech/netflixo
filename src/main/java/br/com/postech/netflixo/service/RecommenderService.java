package br.com.postech.netflixo.service;

import java.io.IOException;
import java.util.List;

import br.com.postech.netflixo.component.recomendador.RecommenderData;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Service;
import br.com.postech.netflixo.domain.entity.RecommenderFilm;
import br.com.postech.netflixo.domain.response.RecommenderResponse;
import br.com.postech.netflixo.component.recomendador.RecomendaFactory;


@Service
public class RecommenderService {

    private RecommenderData recommenderData;

    public RecommenderService(RecommenderData recommenderData) {
        this.recommenderData = recommenderData;
    }

    public RecommenderResponse createRecommenderVideo(Long id) throws IOException, TasteException {
        return new RecommenderResponse(
                buscarPorId(id)
        );
    }
	
	public List<RecommenderFilm> buscarPorId(Long id) throws IOException, TasteException {
	    return  RecomendaFactory.factoryFilms(id, recommenderData.getModeloDeFilmes());
    }
	

}
