package com.ssafy.spotrip.board.model.service;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.attraction.model.dao.AttractionDao;
import com.ssafy.spotrip.board.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.spotrip.board.model.dao.BoardDao;
@Service
@Transactional
public class BoardServiceImpl implements BoardService{
    private BoardDao boardDao;
    private AttractionDao attractionDao;

    @Autowired
    public BoardServiceImpl(BoardDao boardDao, AttractionDao attractionDao) {
        this.boardDao = boardDao;
        this.attractionDao = attractionDao;
    }

    @Override
    public void writeArticle(BoardCreate boardCreate) throws Exception {
        // board 등록
        if (boardCreate.getBoard() != null) {
            boardDao.addArticle(boardCreate.getBoard());
        }
        // 여행지 목록 등록
        writePlans(boardCreate);
    }

    private void writePlans(BoardCreate boardCreate) throws SQLException {
        if (boardCreate.getAttractionIds() != null && boardCreate.getBoard() != null) {
            List<Integer> attractionIds = boardCreate.getAttractionIds();
            int boardId = boardCreate.getBoard().getId();
            int number = 0;
            for (Integer attractionId : attractionIds) {
                Plan plan = new Plan();
                plan.setAttractionId(attractionId);
                plan.setBoardId(boardId);
                plan.setOrderNumber(++number);
                boardDao.addPlan(plan);
            }
        }
    }

    @Override
    public BoardList listArticle(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sidoCode", map.get("sidoCode"));
        param.put("category", map.get("category"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
        param.put("start", currentPage * sizePerPage - sizePerPage);
        param.put("listSize", sizePerPage);

        List<Board> list = boardDao.listArticle(param);

        int totalArticleCount = boardDao.getTotalArticleCount(param);
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

        BoardList boardList = new BoardList();
        boardList.setArticles(list);
        boardList.setCurrentPage(currentPage);
        boardList.setTotalPageCount(totalPageCount);

        return boardList;
    }

    @Override
    public void updateViews(int id) throws Exception {
        boardDao.updateViews(id);
    }

    @Override
    public BoardDetail getArticle(int id) throws Exception {
        Board board = boardDao.getArticle(id);
        List<Attraction> attractions = attractionDao.listAttractionPlanByBoardId(id);
        BoardDetail boardDetail = new BoardDetail();
        boardDetail.setBoard(board);
        boardDetail.setAttractions(attractions);
        return boardDetail;
    }

    @Override
    public void modifyArticle(BoardCreate boardCreate) throws Exception {
        // 게시글 수정
        boardDao.modifyArticle(boardCreate.getBoard());
        // 여행지 목록 수정(삭제 후 생성)
        int boardId = boardCreate.getBoard().getId();
        boardDao.deletePlanByBoardId(boardId);
        writePlans(boardCreate);
    }

    @Override
    public void deleteArticle(int id) throws Exception {
        boardDao.deletePlanByBoardId(id);
        boardDao.deleteArticle(id);
    }
}
