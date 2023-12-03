package com.ssafy.spotrip.attraction.controller;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.board.dto.Plan;
import com.ssafy.spotrip.attraction.model.service.AttractionService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/attraction")
@Slf4j
public class AttractionController {
    private AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        super();
        this.attractionService = attractionService;
    }


    //@GetMapping(value = "/{gugunCode}/{contentTypeId}/{contentSubType}")
//public ResponseEntity<?> searchAttraction(@PathVariable String gugunCode,
//                                          @PathVariable int contentTypeId,
//                                          @PathVariable String contentSubType) {
@GetMapping
public ResponseEntity<?> searchAttraction(@RequestParam String gugunCode,
                                          @RequestParam int contentTypeId,
                                          @RequestParam String contentSubType) {
        log.debug("searchAttractions call - {}, {}, {}", gugunCode, contentTypeId, contentSubType);
        try {
            List<Attraction> list = attractionService.listAttraction(gugunCode, contentTypeId, contentSubType);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(header).body(list);
        } catch(Exception e){
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
