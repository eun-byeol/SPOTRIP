package com.ssafy.spotrip.member.controller;
import com.ssafy.spotrip.member.dto.Member;
import com.ssafy.spotrip.member.model.service.MemberService;
import com.ssafy.spotrip.util.JWTUtil;

import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.*;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private MemberService memberService;
    private JWTUtil jwtUtil;

    @Autowired
    public MemberController(MemberService memberService, JWTUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Boolean> userRegister(@RequestBody Member member) {
        log.debug("userRegister member : {}", member);
        try {
            if (memberService.registerMember(member) > 0) {
                return ResponseEntity.ok().body(true);
            }
            else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/info/{id}")
    public ResponseEntity<Member> userInfo(@PathVariable("id") String id, HttpServletRequest request) {
        log.debug("userInfo id : {}", id);
        try {

            HttpStatus status = HttpStatus.ACCEPTED;

            if(jwtUtil.checkToken(request.getHeader("Authorization"))){
                log.info("사용 가능 토큰");
                    Member member = memberService.getMember(id);
                    return ResponseEntity.ok().body(member);
            } else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping(value = "/password/{id}")
    public ResponseEntity<String> findPassword(@PathVariable("id") String id) {
        log.debug("userInfo id : {}", id);
        try {
            Member member = memberService.getMember(id);
            if(member != null) {
                return ResponseEntity.ok().body(member.getPassword());
            }
            else
                return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @PutMapping
    public ResponseEntity<Boolean> userModify(@RequestBody Member member) {
        log.debug("userModify member : {}", member);
        try {
            if (memberService.updateMember(member) > 0) {
                return ResponseEntity.ok().body(true);
            }
            else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> userDelete(@PathVariable("id") String id) {
        log.debug("userDelete id : {}", id);
        try {
            if (memberService.deleteMember(id) > 0) {
                return ResponseEntity.ok().body(true);
            }
            else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Member member) {
        log.debug("login member : {}", member);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            Member loginUser = memberService.login(member);
            if(loginUser != null) {
                String accessToken = jwtUtil.createAccessToken(loginUser.getId());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getId());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);

//				발급받은 refresh token을 DB에 저장.
                memberService.saveRefreshToken(loginUser.getId(), refreshToken);

//				JSON으로 token 전달.
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);

                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<?> removeToken(@PathVariable ("id") String id) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            memberService.deleRefreshToken(id);
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Member member, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, member : {}", token, member);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(member.getId()))) {
                String accessToken = jwtUtil.createAccessToken(member.getId());
                log.debug("token : {}", accessToken);
                log.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                status = HttpStatus.CREATED;
            }
        } else {
            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
