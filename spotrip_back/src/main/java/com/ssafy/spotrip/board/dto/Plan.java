package com.ssafy.spotrip.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Plan {
    int id;
    int orderNumber;
    int attractionId;
    int boardId;
}
