package com.ssafy.spotrip.attraction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Attraction {
    int id;
    int contentTypeId;
    String contentSubType;
    String title;
    String addr1;
    String addr2;
    String tel;
    String image;
    String sidoCode;
    String gugunCode;
    String latitude;
    String longitude;
}