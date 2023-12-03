package com.ssafy.spotrip.board.dto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardList {
    private List<Board> articles;
    private int currentPage;
    private int totalPageCount;
}
