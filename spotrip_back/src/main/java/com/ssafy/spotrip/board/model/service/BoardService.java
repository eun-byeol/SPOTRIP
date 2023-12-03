package com.ssafy.spotrip.board.model.service;
import java.util.Map;

import com.ssafy.spotrip.board.dto.*;

public interface BoardService {
    void writeArticle(BoardCreate boardCreate) throws Exception;
    BoardList listArticle(Map<String, String> map) throws Exception;
    void updateViews(int id) throws Exception;
    BoardDetail getArticle(int id) throws Exception;
    void modifyArticle(BoardCreate boardCreate) throws Exception;
    void deleteArticle(int id) throws Exception;
}
