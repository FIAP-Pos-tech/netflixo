package br.com.postech.netflixo.recommender.data;

import java.util.HashMap;
import java.util.Map;

public class RecommenderCategoryDataHashMap {

	
	public static String category(String n) {

		Map<String, String> categoryNames = new HashMap<>();
	        categoryNames.put("1.0", "Romance");
	        categoryNames.put("2.0", "Aventura");
	        categoryNames.put("3.0", "Policial");
	        categoryNames.put("4.0", "Guerra");
	        categoryNames.put("5.0", "Cinência");
	        categoryNames.put("6.0", "Ação");
	        categoryNames.put("7.0", "Drama");
	        categoryNames.put("8.0", "Desenho");
	        categoryNames.put("9.0", "Ficção");
	        categoryNames.put("10.0", "Terror");
	        String cat = categoryNames.get(n);
	        return cat;
	}
	
	
}
