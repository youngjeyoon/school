package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {
    @Autowired
    private StudentService service;
    
    @GetMapping("/student/studentList")
    public String studentList(@RequestParam int ban,
    		Model model,HttpSession session)
    {
    	return service.studentList(ban, model, session);    	
    }
    
    @GetMapping("/student/studentAdd")
    public String studentAdd(@RequestParam int ban,Model model,HttpSession session)
    {
        return service.studentAdd(ban,model,session);	
    }
    
    @PostMapping("/student/studentAddOk")
    public String studentAddOk(StudentDto sdto,HttpSession session)
    {
    	return service.studentAddOk(sdto, session);
    }
    
    @PostMapping("/student/studentUpdateOk")
    public String studentUpdateOk(StudentDto sdto,HttpSession session)
    {
    	return service.studentUpdateOk(sdto,session);
    }
    
    @GetMapping("/student/studentDel")
    public String studentDel(@RequestParam int id
    		,@RequestParam int ban
    		,HttpSession session)
    {
    	return service.studentDel(id,ban,session);
    }
}
