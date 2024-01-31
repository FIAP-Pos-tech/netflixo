package br.com.postech.netflixo.recomendafilmes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import br.com.postech.netflixo.domain.entity.RecommenderFilm;
import br.com.postech.netflixo.recomendadorbuilder.data.RecomendadorBuilder;
import br.com.postech.netflixo.recommender.data.RecommenderData;



public class RecomendaFactory {

	
	public static List<RecommenderFilm> factoryFilms(Long id ) throws IOException, TasteException {
		
		DataModel filmes = new RecommenderData().getModeloDeFilmes();
        Recommender recommender = new RecomendadorBuilder().buildRecommender(filmes);
        //TODO receber as informações de usuário e número de recomendações do usuário
        List<RecommendedItem> recommendations = recommender.recommend( id , 4);
        
        List<RecommenderFilm> recommenders = new ArrayList<>();
        System.out.println("\nUSER TO RECOMMEND ["+id+"]");
        for (RecommendedItem recommendation : recommendations) {
        	
            System.out.println("\nVoce pode gostar deste filme");
            System.out.println("ITEM ["+recommendation.getItemID()+"] "
            		        + "VALOR ["+recommendation.getValue()+"]");
            RecommenderFilm rec = new RecommenderFilm();
            rec.setId(id);
            rec.setItem( String.valueOf(recommendation.getItemID()));
            rec.setNota( String.valueOf(recommendation.getValue())  );
            recommenders.add(rec);
        }
        System.out.println("\nRecommenders ["+recommenders.size()+"]");
		return recommenders;
	}
}
