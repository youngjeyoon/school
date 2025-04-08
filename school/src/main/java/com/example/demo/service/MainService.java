package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TeacherDto;
import com.example.demo.mapper.MainMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class MainService {
	@Autowired
	private MainMapper mapper;
	public String main;

	public String home() {
		return "redirect:/main/login";
	}

	public String login() {
		return "/main/login";
	}

	public String loginOk(TeacherDto tdto, HttpSession session) {
		if(mapper.isTeacher(tdto)) {
			tdto = mapper.getTeacher(tdto.getUserid());
			// 세션변수 할당
			session.setAttribute("userid", tdto.getUserid());
			session.setAttribute("level", tdto.getLevel());
			session.setAttribute("name", tdto.getName());
			
			return "redirect:/main/main";
		} else {
			return "redirect:/main/login?err=1";
		}
	}
	
	public String main() {
		return "/main/main";
	}

	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/main/login";
	}


}
