package com.example.demo.dto;

import lombok.Data;

@Data
public class MemoDto {
    private int id,state;
    private String title,content,ofname,sfname;
    private String writeday,seUserid;
    private String reUserid;
}
