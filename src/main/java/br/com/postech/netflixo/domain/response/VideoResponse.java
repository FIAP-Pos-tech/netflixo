package br.com.postech.netflixo.domain.response;

public class VideoResponse {

	private String id;
	private String title;
	private String description;
	private String url;
	private String thumbnailUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	@Override
	public String toString() {
		return "VideoResponse{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", url='" + url + '\'' +
				", thumbnailUrl='" + thumbnailUrl + '\'' +
				'}';
	}
}
