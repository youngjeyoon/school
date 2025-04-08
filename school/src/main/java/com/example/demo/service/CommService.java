package com.example.demo.service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.MyUtil;
import com.example.demo.dto.CommDto;
import com.example.demo.mapper.CommMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class CommService {
	@Autowired
	private CommMapper mapper;

	public String write(String cla, Model model) {
		model.addAttribute("cla", cla);
		return "/comm/write";
	}

	public String writeOk(CommDto cdto, HttpSession session, MultipartFile file) throws Exception {
		if (file != null && !file.isEmpty()) { // 파일이 존재하면 파일복사, 파일명을 구하기
			String ofname = file.getOriginalFilename(); // 원래 파일명

			String sfname = MyUtil.getRName2(ofname);

			String imsi = ResourceUtils.getFile("classpath:static/file").toString() + "/";

			Path path = Paths.get(imsi + sfname);

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			cdto.setOfname(ofname);
			cdto.setSfname(sfname);
		}

		cdto.setUserid(session.getAttribute("userid").toString());

		mapper.writeOk(cdto);

		return "redirect:/comm/list?cla=" + cdto.getCla();
	}

	public String list(HttpServletRequest request, HttpSession session, Model model) {
		if (session.getAttribute("userid") == null) {

			return "redirect:/main/login";
		} else {
			String cla = request.getParameter("cla");

			int page = 1;
			if (request.getParameter("page") != null)
				page = Integer.parseInt(request.getParameter("page"));

			int index = (page - 1) * 10;

			ArrayList<CommDto> clist = mapper.list(index, cla);
			model.addAttribute("clist", clist);
			model.addAttribute("page", page);
			model.addAttribute("cla", cla);
			return "/comm/list";
		}
	}

	public String readnum(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("userid") == null) {
			return "/comm/login";
		} else {
			String id = request.getParameter("id");
			String page = request.getParameter("page");

			mapper.readnum(id);

			return "redirect:/comm/content?id=" + id + "&page=" + page;
		}
	}

	public String content(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("userid") == null) {
			return "/comm/login";
		} else {
			String id = request.getParameter("id");
			String page = request.getParameter("page");

			CommDto cdto = mapper.content(id);
			String content = cdto.getContent().replace("\r\n", "<br>");
			cdto.setContent(content);

			model.addAttribute("cdto", cdto);

			model.addAttribute("page", page);

			return "/comm/content";
		}

	}

	public String delete(HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("userid") == null) {
			return "/comm/login";
		} else {
			String id = request.getParameter("id");
			String page = request.getParameter("page");
			String cla = request.getParameter("cla");

			mapper.delete(id);

			return "redirect:/comm/list?page=" + page + "&cla=" + cla;
		}
	}

	public String update(HttpServletRequest request, Model model, HttpSession session) {
		if (session.getAttribute("userid") == null) {
			return "/comm/login";
		} else {
			String id = request.getParameter("id");
			String page = request.getParameter("page");

			CommDto cdto = mapper.content(id);
			model.addAttribute("cdto", cdto);
			model.addAttribute("page", page);

			return "/comm/update";
		}
	}

	public String updateOk(MultipartFile file,CommDto cdto,String page) throws Exception
    {
    	//System.out.println(file.isEmpty());
    	
    	if(file!=null && !file.isEmpty()) // 파일이 존재한다면 => 파일바꾸겠다
    	{
    		String imsi=ResourceUtils.getFile("classpath:static/file").toString()+"/";
    		// 기존 파일 삭제하기
    		File ff=new File(imsi+cdto.getSfname());
    		//System.out.println(ff.getAbsolutePath());
    		if(ff.exists())
    		{
    			System.out.println("del");
    			ff.delete();
    		}   		
    		
    		// cdto.ofname, cdto.sfname을 값을 입력
            String ofname=file.getOriginalFilename(); // 원래파일명
    		
    		String sfname=MyUtil.getRName2(ofname);
    		
    		
    		Path path=Paths.get(imsi+sfname);
    		
    		 
    		
    		
    		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    		
    		
    		
    		cdto.setOfname(ofname);
    		cdto.setSfname(sfname);
    		
    		
    		
    	}
 
    	
    	mapper.updateOk(cdto);
    	
    	return "redirect:/comm/content?id="+cdto.getId()+"&page="+page;
    }
    
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// 원래 이름으로 다운로드 => 원래파일이름, 저장되어 있는 파일이름
		String sfname=request.getParameter("sfname"); // 저장된 파일이름
		String ofname=request.getParameter("ofname"); // 원래 파일이름
		 
		String path=ResourceUtils.getFile("classpath:static/file").toString();
		
		response.setHeader("Content-Type", "application/octet-stream"); // 바이너리 파일
		ofname=URLEncoder.encode(ofname,"utf-8");
		response.setHeader("Content-Disposition","attachment; filename="+ofname);
		response.setContentType("application/unknown"); // 파일의 유형을 알려준다
		              
		Path path2=Paths.get(path+"/"+sfname); // 시스템에 저장된 파일명
		
		Files.copy(path2, response.getOutputStream());
	}

}
