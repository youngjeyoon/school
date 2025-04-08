package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.MemoDto;

@Mapper
public interface MemoMapper {

	ArrayList<MemoDto> send(String userid);
	
	public ArrayList<HashMap<String,String>> getUserid();

	public void memoWriteOk(MemoDto mdto);

	ArrayList<MemoDto> receive(String userid);

	void setState(String id);

	String viewContent(String id);

	String cntMemo(String userid);
	
}
