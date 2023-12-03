package com.ssafy.spotrip.map.model.service;

import java.util.List;

import com.ssafy.spotrip.map.dto.SidoGugunCode;
import com.ssafy.spotrip.map.model.dao.MapDao;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {
	
	private MapDao mapDao;

	public MapServiceImpl(MapDao mapDao) {
		this.mapDao = mapDao;
	}

	@Override
	public List<SidoGugunCode> getSido() throws Exception {
		return mapDao.getSido();
	}

	@Override
	public List<SidoGugunCode> getGugunInSido(String sido) throws Exception {
		return mapDao.getGugunInSido(sido);
	}

}
