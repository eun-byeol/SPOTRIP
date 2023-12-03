package com.ssafy.spotrip.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.ssafy.spotrip.board.controller.BoardController;
import com.ssafy.spotrip.board.model.service.BoardService;
import com.ssafy.spotrip.config.DataBaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(
        properties = {"spring.config.location=classpath:application.properties"},
        classes = { BoardController.class, DataBaseConfiguration.class}
)
@Slf4j
@ComponentScan(basePackages = {"com.ssafy.spotrip"})
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardService BoardService;

    @Test
    @DisplayName("게시글 상세 조회하기")
    void testGetArticle() throws Exception {
        int id = 7;
        mockMvc.perform(get("/board/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }
}
