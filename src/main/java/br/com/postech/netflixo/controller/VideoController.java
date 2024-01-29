package br.com.postech.netflixo.controller;

import br.com.postech.netflixo.domain.entity.Video;
import br.com.postech.netflixo.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping
    public DeferredResult<ResponseEntity<?>> createVideo(@RequestBody Video video) {
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
        output.setResult(ResponseEntity.ok(videoService.createVideo(video)));

        return output;
    }

    @GetMapping("/{title}")
    public DeferredResult<ResponseEntity<?>> listVideosByTitle(@PathVariable String title) {
        DeferredResult<ResponseEntity<?>> output = new DeferredResult<>();
        output.setResult(ResponseEntity.ok(videoService.findVideoByTitle(title)));

        return output;
    }

    @GetMapping("/{id}")
    public DeferredResult<ResponseEntity<Mono<byte[]>>> getVideoById(@PathVariable String id) {
        DeferredResult<ResponseEntity<Mono<byte[]>>> output = new DeferredResult<>();
        output.setResult(ResponseEntity.ok(videoService.getVideoContentById(id)));

        return output;
    }
}


