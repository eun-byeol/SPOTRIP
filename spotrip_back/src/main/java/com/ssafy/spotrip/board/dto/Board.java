package com.ssafy.spotrip.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private int id;
    private String memberId;
    private String title;
    private String content;
    private int recruits;
    private String category;
    private String image;
    private String departureDate;
    private String arrivalDate;
    private int views;
    private String createdTime;
    private String updatedTime;
    private String sidoCode;
}
