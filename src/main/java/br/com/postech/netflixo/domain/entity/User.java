package br.com.postech.netflixo.domain.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document
public class User {

	@Id
	@Field
	private String id;
	@Field
	private String name;
	@Field
	private String email;
	@Field
	private String password;
	@Field
	private List<String> favoriteVideos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getFavoriteVideos() {
		return favoriteVideos;
	}

	public void setFavoriteVideos(List<String> favoriteVideos) {
		this.favoriteVideos = favoriteVideos;
	}

	
}
