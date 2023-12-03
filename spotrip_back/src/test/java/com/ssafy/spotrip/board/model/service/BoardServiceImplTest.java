package com.ssafy.spotrip.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(
        properties = {"spring.config.location=classpath:application.properties"}
)
public class BoardServiceImplTest {
    @Autowired
    BoardService boardService;

//    @Test
//    @DisplayName("게시글 상세조회")
//    void testGetMember() throws Exception {
//        assertThat(boardService.getArticle(7).getTitle())
//                .isEqualTo("다시 관통하자");
//    }

}
