package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CommDto;
import com.example.demo.service.CommService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class CommController {
	@Autowired
	private CommService service;
	
	@GetMapping("/comm/write")
	public String write(@RequestParam String cla, Model model) {
		return service.write(cla,model);
	}
	
	@PostMapping("/comm/writeOk")
	public String wirteOk(CommDto cdto, HttpSession session, MultipartFile file) throws Exception {
		return service.writeOk(cdto, session, file);
	}
	
	@GetMapping("/comm/list")
	public String list(HttpServletRequest request,HttpSession session, Model model) {
		return service.list(request,session, model);
	}
	
	@GetMapping("/comm/readnum")
	public String readnum(HttpServletRequest request, HttpSession session) {
		return service.readnum(request,session);
	}
	
	@GetMapping("/comm/content")
	public String content(HttpServletRequest request, Model model, HttpSession session) {
		return service.content(request,model,session);
	}
	
	@GetMapping("/comm/delete")
	public String delete(HttpServletRequest request,HttpSession session) {
		return service.delete(request,session);
	}
	
	@GetMapping("/comm/update")
	public String update(HttpServletRequest request,Model model, HttpSession session) {
		return service.update(request,model,session);
	}
	
	@PostMapping("/comm/updateOk")
	public String updateOk(MultipartFile file,CommDto cdto,@RequestParam String page) throws Exception {
		
		return service.updateOk(file,cdto,page);
	}
	
    @GetMapping("/comm/down")
	public void down(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		service.down(request, response);
	}
	
	
	
}
