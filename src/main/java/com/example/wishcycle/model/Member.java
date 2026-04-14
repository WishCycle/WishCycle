package com.example.wishcycle.model;

public class Member {

    private Long memberId;
    private String name;

    public Member(Long memberId, String name) {

        this.memberId = memberId;
        this.name = name;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
