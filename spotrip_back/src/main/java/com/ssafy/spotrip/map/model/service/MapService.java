package com.ssafy.spotrip.map.model.service;

import java.util.List;

import com.ssafy.spotrip.map.dto.SidoGugunCode;

public interface MapService {

	List<SidoGugunCode> getSido() throws Exception;
	List<SidoGugunCode> getGugunInSido(String sido) throws Exception;
	
}
