package com.ssafy.spotrip.member.model.service;

import com.ssafy.spotrip.member.model.dao.MemberDao;
//import com.ssafy.spotrip.member.dto.LoginDto;
import com.ssafy.spotrip.member.dto.Member;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    MemberDao memberdao;

    @Autowired
    public MemberServiceImpl(MemberDao memberdao) {
        super();
        this.memberdao = memberdao;
    }

    @Override
    public int registerMember(Member member) throws SQLException {
        return memberdao.insertMember(member);

    }

    @Override
    public int updateMember(Member member) throws SQLException {
        return memberdao.updateMember(member);
    }

    @Override
    public int deleteMember(String id) throws SQLException {
        return memberdao.deleteMember(id);
    }

    public Member getMember(String id) throws SQLException{
        return memberdao.getMember(id);
    }

    @Override
    public Member login(Member member) throws Exception {
        return memberdao.login(member);
    }

    @Override
    public void saveRefreshToken(String id, String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("token", token);
        memberdao.saveRefreshToken(map);
    }

    @Override
    public Object getRefreshToken(String id) throws Exception {
        return memberdao.getRefreshToken(id);
    }

    @Override
    public void deleRefreshToken(String id) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("token", null);
        memberdao.deleteRefreshToken(map);
    }
}
