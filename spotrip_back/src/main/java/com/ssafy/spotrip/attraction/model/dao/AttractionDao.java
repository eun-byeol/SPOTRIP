package com.ssafy.spotrip.attraction.model.dao;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.board.dto.Plan;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttractionDao {
    List<Attraction> listAttraction(Map<String, Object> option);
    List<Attraction> listAttractionPlanByBoardId(int boardId);
}
