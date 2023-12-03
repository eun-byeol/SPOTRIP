package com.ssafy.spotrip.map.model.dao;

import com.ssafy.spotrip.map.dto.SidoGugunCode;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface MapDao {

	List<SidoGugunCode> getSido() throws SQLException;
	List<SidoGugunCode> getGugunInSido(String sido) throws SQLException;
	
}
