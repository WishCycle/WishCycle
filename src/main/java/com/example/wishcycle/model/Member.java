package com.example.wishcycle.model;

public class Member {

    private Long memberId;
    private String name;
    private String password;
    private String email;

    public Member(Long memberId, String name, String password, String email) {
        this.memberId = memberId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Member() {}

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
