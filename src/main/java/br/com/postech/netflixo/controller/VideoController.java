package br.com.postech.netflixo.controller;

import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/videos")
public class VideoController {

	private final VideoService videoService;

	private static final Logger log = LoggerFactory.getLogger(VideoController.class);

	private final StorageComponent storageComponent;

	public VideoController(VideoService videoService) throws IOException {
		this.storageComponent = new StorageComponent("netflixo-videos", "netflixo-410521");
		this.videoService = videoService;
	}

	@GetMapping("/{category}")
	public DeferredResult<ResponseEntity<?>> listVideosByCategory(@PathVariable String category,
																  @RequestParam(defaultValue = "0") int page,
																  @RequestParam("5") int size) {
		DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
		output.setResult(ResponseEntity.ok(videoService.findVideoByCategory(category, page, size)));
		return output;
	}

	@GetMapping(value = "{uuid}", produces = "video/mp4")
	public Flux<DataBuffer> streamVideo(@PathVariable String uuid,
										@RequestHeader("Range") String range,
										ServerHttpRequest request,
										ServerHttpResponse response) {
		try {
			// Realiza o streaming do vídeo
			log.info("range: {}", range);
			return storageComponent.downloadFileStreaming(uuid, response);
		} catch (Exception e) {
			// Trate a exceção conforme necessário
			log.error("Erro ao fazer streaming do vídeo", e);
			return Flux.error(e);
		}
	}

	@PostMapping
	public DeferredResult<ResponseEntity<?>> createVideo(@RequestBody Video video) {
		log.info("Criando vídeo {}", video);
		DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
		output.setResult(ResponseEntity.ok(videoService.createVideo(video)));

		return output;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadChunk(
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileName") String fileName) throws Exception {

		String tempDir = System.getProperty("java.io.tmpdir");
		File tempFile = new File(tempDir + File.separator + fileName);
		file.transferTo(tempFile);

		log.info("arquivo recebido {} ", fileName);

		storageComponent.uploadFileChunked(tempFile, fileName.replace(" ", ""), 100);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}