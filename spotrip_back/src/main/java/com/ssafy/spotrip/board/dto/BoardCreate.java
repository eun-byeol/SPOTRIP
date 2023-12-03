package com.ssafy.spotrip.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BoardCreate {
    private Board board;
    private List<Integer> attractionIds;
}
