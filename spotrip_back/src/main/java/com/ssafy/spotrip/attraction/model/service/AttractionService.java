package com.ssafy.spotrip.attraction.model.service;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.board.dto.Plan;
import java.util.List;
import java.util.Map;

public interface AttractionService {
    List<Attraction> listAttraction(String gugunCode, int contentTypeId, String contentSubType);
}
