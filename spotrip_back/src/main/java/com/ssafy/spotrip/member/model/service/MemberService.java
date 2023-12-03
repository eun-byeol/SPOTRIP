package com.ssafy.spotrip.member.model.service;
import java.sql.SQLException;
//import java.util.Map;

//import com.ssafy.spotrip.member.dto.LoginDto;
import com.ssafy.spotrip.member.dto.Member;
public interface MemberService{
    int registerMember(Member member) throws SQLException;

//    Member loginMember(LoginDto loginDto) throws SQLException;

    public int updateMember(Member member) throws SQLException;

    public int deleteMember(String id) throws SQLException;

    Member getMember(String id) throws SQLException;
    Member login(Member member) throws Exception;
    void saveRefreshToken(String id, String token) throws Exception;
    Object getRefreshToken(String id) throws Exception;
    void deleRefreshToken(String id) throws Exception;
}
