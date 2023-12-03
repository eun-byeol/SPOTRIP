package com.ssafy.spotrip.member.model.dao;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

//import com.ssafy.spotrip.member.dto.LoginDto;
import com.ssafy.spotrip.member.dto.Member;

@Mapper
public interface MemberDao {
    Member getMember(String id) throws SQLException;
    int insertMember(Member member)throws SQLException;
    int updateMember(Member member);
    int deleteMember(String id);
    Member login(Member member) throws SQLException;
    void saveRefreshToken(Map<String, String> map) throws SQLException;
    Object getRefreshToken(String id) throws SQLException;
    void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
