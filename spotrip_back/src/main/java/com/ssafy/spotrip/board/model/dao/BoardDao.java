package com.ssafy.spotrip.board.model.dao;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.board.dto.Board;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.spotrip.board.dto.BoardDetail;
import com.ssafy.spotrip.board.dto.Plan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BoardDao {
    void addArticle(Board board) throws SQLException;
    List<Board> listArticle(Map<String, Object> param) throws SQLException;
    int getTotalArticleCount(Map<String, Object> param) throws SQLException;
    void updateViews(int id) throws SQLException;
    Board getArticle(int id) throws SQLException;
    void modifyArticle(Board board) throws SQLException;
    void deleteArticle(int id) throws SQLException;
    void addPlan(Plan plan) throws SQLException;
    void deletePlanByBoardId(int id) throws SQLException;
//    void registerFile(Board board) throws Exception;
//    int getTotalCommentCount(int id) throws SQLException;
}
