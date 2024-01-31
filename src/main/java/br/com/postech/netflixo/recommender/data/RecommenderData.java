package br.com.postech.netflixo.recommender.data;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

public class RecommenderData {
	
	    private DataModel getModelo(String path) throws IOException {
	        File file = new File("src/main/resources/" + path);
	        return new FileDataModel(file);
	    }

	    public DataModel getModeloDeFilmes() throws IOException {
	       return getModelo("filmes.csv");
	    }
}
