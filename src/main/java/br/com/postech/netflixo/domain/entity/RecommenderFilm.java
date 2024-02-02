package br.com.postech.netflixo.domain.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RecommenderFilm {
	
	private Long id;
	private String item;
	private String nota;
	
	public RecommenderFilm() {
	}
	
	public RecommenderFilm(Long id, String item, String category) {
		super();
		this.id = id;
		this.item = item;
		this.nota = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}


	
	

	 
	 
}
