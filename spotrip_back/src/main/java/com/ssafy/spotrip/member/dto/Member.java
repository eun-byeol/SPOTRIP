package com.ssafy.spotrip.member.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
    private String id;
    private String password;
    private String name;
    private String email;
    private String image;
    private String content;
    private String token;
}
