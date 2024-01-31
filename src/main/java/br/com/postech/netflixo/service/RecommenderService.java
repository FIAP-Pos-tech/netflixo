package br.com.postech.netflixo.service;

import java.io.IOException;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.stereotype.Service;
import br.com.postech.netflixo.domain.entity.RecommenderFilm;
import br.com.postech.netflixo.domain.response.RecommenderResponse;
import br.com.postech.netflixo.recomendafilmes.RecomendaFactory;


@Service
public class RecommenderService {
	
		
	public RecommenderResponse createRecommenderVideo(Long id) throws IOException, TasteException {//throws ConsumoNotFoundException {
        return new RecommenderResponse(
                buscarPorId(id)
        );
    }
	
	public List<RecommenderFilm> buscarPorId(Long id) throws IOException, TasteException { //throws ConsumoNotFoundException {
	    return  RecomendaFactory.factoryFilms(id);
    }
	

}
