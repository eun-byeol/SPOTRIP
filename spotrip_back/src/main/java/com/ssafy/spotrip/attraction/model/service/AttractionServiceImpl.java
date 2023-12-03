package com.ssafy.spotrip.attraction.model.service;

import com.ssafy.spotrip.attraction.dto.Attraction;
import com.ssafy.spotrip.board.dto.Plan;
import com.ssafy.spotrip.attraction.model.dao.AttractionDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttractionServiceImpl implements AttractionService {
    private AttractionDao attractionDao;

    @Autowired
    public AttractionServiceImpl(AttractionDao attractionDao) {
        this.attractionDao = attractionDao;
    }

    @Override
    public List<Attraction> listAttraction(String gugunCode, int contentTypeId, String contentSubType) {
        Map<String, Object> option = new HashMap<>();
        option.put("gugunCode", gugunCode);
        option.put("contentTypeId", contentTypeId);
        option.put("contentSubType", contentSubType);
        if (gugunCode.isEmpty())
            option.put("gugunCode", null);
        if (contentSubType.isEmpty())
            option.put("contentSubType", null);
        return attractionDao.listAttraction(option);
    }
}
