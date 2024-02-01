package br.com.postech.netflixo.component.recomendador;

import br.com.postech.netflixo.domain.entity.RecommenderFilm;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecomendaFactory {


	public static List<RecommenderFilm> factoryFilms(Long id, DataModel filmes) throws IOException, TasteException {

        Recommender recommender = new RecomendadorBuilder().buildRecommender(filmes);
        List<RecommendedItem> recommendations = recommender.recommend( id , 4);
        List<RecommenderFilm> recommenders = new ArrayList<>();

        for (RecommendedItem recommendation : recommendations) {

            RecommenderFilm rec = new RecommenderFilm();
            rec.setId(id);
            rec.setItem( String.valueOf(recommendation.getItemID()));
            rec.setNota( String.valueOf(recommendation.getValue())  );
            recommenders.add(rec);
        }

		return recommenders;
	}
}
