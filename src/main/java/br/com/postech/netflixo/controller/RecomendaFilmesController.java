package br.com.postech.netflixo.controller;


import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.postech.netflixo.domain.response.RecommenderResponse;
import br.com.postech.netflixo.service.RecommenderService;


@RestController
@RequestMapping("/recomenda")
public class RecomendaFilmesController {

	
	private final RecommenderService recommenderService;
	
	public RecomendaFilmesController(RecommenderService recommenderService) {
		this.recommenderService = recommenderService;
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<RecommenderResponse> buscarConsumoPorId(@PathVariable Long id) throws IOException, TasteException { //throws ConsumoNotFoundException {
		return ResponseEntity.ok(recommenderService.createRecommenderVideo(id));
	}
}
