package com.ssafy.spotrip.map.controller;

import java.util.List;

import com.ssafy.spotrip.map.dto.SidoGugunCode;
import com.ssafy.spotrip.map.model.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/map")
@Slf4j
public class MapController {
	
	private MapService mapService;

	public MapController(MapService mapService) {
		super();
		this.mapService = mapService;
	}

	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCode>> sido() throws Exception {
		log.info("sido - 호출");
		return new ResponseEntity<>(mapService.getSido(), HttpStatus.OK);
	}

	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCode>> gugun(@RequestParam("sido") String sido) throws Exception {
		log.info("gugun - 호출");
		return new ResponseEntity<>(mapService.getGugunInSido(sido), HttpStatus.OK);
	}
	
}
