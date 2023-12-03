package com.ssafy.spotrip.board.dto;

import com.ssafy.spotrip.attraction.dto.Attraction;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardDetail {
    private Board board;
    private List<Attraction> attractions;
}
