package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dto.StudentDto;
import com.example.demo.mapper.StudentMapper;

import jakarta.servlet.http.HttpSession;

@Service
public class StudentService {
    @Autowired
    private StudentMapper mapper;

	public String studentList(int ban, Model model, HttpSession session) 
	{
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/main/login";
		}
		else  
		{
			int ban2=Integer.parseInt(session.getAttribute("ban").toString());
			if(ban==ban2 || session.getAttribute("userid").equals("admin"))
			{
				ArrayList<StudentDto> slist=mapper.studentList(ban);
				model.addAttribute("slist",slist);
				model.addAttribute("ban",ban);
				
				return "/student/studentList";
			}
			else
			{
				return "redirect:/main/main";
			}
		}
		
		
	}

	public String studentAdd(int ban, Model model,HttpSession session) 
	{
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/main/login";
		}
		else
		{
			model.addAttribute("ban",ban);
			return "/student/studentAdd";
		}
		
	}
	
	public String studentAddOk(StudentDto sdto,HttpSession session) 
	{
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/main/login";
		}
		else
		{
			// 다른 값은 그대로 전달하면 되지만 bunho는 다른 값을 가져야 되므로
			// 입력하는 순서에 따라서 1씩 증가되게 한다 
			// 쿼리문 처리 : 가장 높은 번호를 가져와서 1을 더한 값을 sdto에 setter한다.
			int bunho=mapper.getBunho(sdto.getBan());
			sdto.setBunho(bunho);
			
			mapper.studentAddOk(sdto);
			
			return "redirect:/student/studentList?ban="+sdto.getBan();
		}
		
	}

	public String studentUpdateOk(StudentDto sdto, HttpSession session)
    {
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/main/login";
		}
		else
		{
			mapper.studentUpdateOk(sdto);
			
			return "redirect:/student/studentList?ban="+sdto.getBan();
		}
	}

	public String studentDel(int id,int ban, HttpSession session) 
	{
		if(session.getAttribute("userid")==null)
		{
			return "redirect:/main/login";
		}
		else
		{
			mapper.studentDel(id);
			return "redirect:/student/studentList?ban="+ban;
		}
			
		
	}
}






