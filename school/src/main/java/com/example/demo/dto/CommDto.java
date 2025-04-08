package com.example.demo.dto;

import lombok.Data;

@Data
public class CommDto {
	private int id,cla,readnum;
	private String title,content,writeday;
	private String ofname,sfname,userid;
}
