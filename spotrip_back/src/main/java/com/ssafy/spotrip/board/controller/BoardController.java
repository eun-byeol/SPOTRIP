package com.ssafy.spotrip.board.controller;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.ssafy.spotrip.board.dto.BoardCreate;
import com.ssafy.spotrip.board.dto.BoardDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.spotrip.board.model.service.BoardService;
import com.ssafy.spotrip.board.dto.Board;
import com.ssafy.spotrip.board.dto.BoardList;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<?> writeArticle(@RequestBody BoardCreate boardCreate) {
        log.info("writeArticle - {}", boardCreate);
        try {
            boardService.writeArticle(boardCreate);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> listArticle(@RequestParam(required = false) Map<String, String> map) {
        log.info("listArticle map - {}", map);
        try {
            BoardList boardList = boardService.listArticle(map);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(header).body(boardList);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDetail> getArticle(@PathVariable int id) throws Exception {
        log.info("getArticle - 호출 : {}", id);
        boardService.updateViews(id);
        BoardDetail boardDetail = boardService.getArticle(id);
        return new ResponseEntity<>(boardDetail, HttpStatus.OK);
    }

    @GetMapping("/modify/{id}")
    public ResponseEntity<BoardDetail> getModifyArticle(@PathVariable int id) throws Exception {
        log.info("getModifyArticle - 호출 : " + id);
        return new ResponseEntity<>(boardService.getArticle(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> modifyArticle(@RequestBody BoardCreate boardCreate) throws Exception {
        log.info("modifyArticle - 호출 {}", boardCreate);
        boardService.modifyArticle(boardCreate);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable int id) throws Exception {
        log.info("deleteArticle - 호출");
        boardService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
