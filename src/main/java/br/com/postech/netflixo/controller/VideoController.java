package br.com.postech.netflixo.controller;

import br.com.postech.netflixo.domain.response.VideoResponse;
import br.com.postech.netflixo.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

	private final VideoService videoService;

	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}

	@GetMapping
	public DeferredResult<ResponseEntity<List<VideoResponse>>> getVideos() {
		
		return null;
	}

}
