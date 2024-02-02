package br.com.postech.netflixo.controller;

import br.com.postech.netflixo.component.StorageComponent;
import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.service.VideoService;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/videos")
public class VideoController {

	private final VideoService videoService;

	private static final Logger log = LoggerFactory.getLogger(VideoController.class);

	private final StorageComponent storageComponent;

	public VideoController(VideoService videoService) {
		this.storageComponent = new StorageComponent("netflixo-videos", "netflixo-410521");
		this.videoService = videoService;
	}

	@GetMapping("/category/{category}")
	public DeferredResult<ResponseEntity<?>> listVideosByCategory(@PathVariable String category,
																  @RequestParam(defaultValue = "0") int page,
																  @RequestParam(defaultValue = "5") int size) {
		DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
		output.setResult(ResponseEntity.ok(videoService.findVideoByCategory(category, page, size)));
		return output;
	}

	@GetMapping(value = "{uuid}", produces = "video/mp4")
	public Flux<DataBuffer> streamVideo(@PathVariable String uuid,
										@RequestHeader("Range") String range,
										ServerWebExchange exchange) {
		try {
			// Realiza o streaming do vídeo
			log.info("range: {}", range);
			return storageComponent.downloadFileStreaming(uuid, exchange.getResponse());
		} catch (Exception e) {
			// Trate a exceção conforme necessário
			log.error("Erro ao fazer streaming do vídeo", e);
			return Flux.error(e);
		}
	}


	@PostMapping
	public DeferredResult<ResponseEntity<Video>> createVideo(@RequestBody Video video) throws InterruptedException {
		log.info("Criando vídeo {}", video);
		DeferredResult<ResponseEntity<Video>> output = new DeferredResult<>();
		Mono<Video> monoVideo = videoService.createVideo(video);
		monoVideo.subscribe(v -> {
			log.info("Vídeo criado {}", v);
			output.setResult(ResponseEntity.ok(v));
		});
		Thread.sleep(1000);
		log.info("result criado {}", output.getResult());
		return output;
	}

	@PostMapping("/upload")
	public Mono<ResponseEntity<?>> uploadChunk(
			@RequestPart("file") FilePart file,
			@RequestPart("fileName") String fileName) throws Exception {

		log.info("Received file with name: {}", fileName);

		String tempDir = System.getProperty("java.io.tmpdir");
		File tempFile = new File(tempDir + File.separator + fileName);


		file.transferTo(tempFile).block();

		log.info("File saved in directory: {}", tempFile.getAbsolutePath());

		storageComponent.uploadFileChunked(tempFile, fileName, 100);
		return Mono.just(new ResponseEntity<>(HttpStatus.OK));
	}
}