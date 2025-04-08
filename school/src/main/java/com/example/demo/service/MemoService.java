package com.example.demo.service;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.MyUtil;
import com.example.demo.dto.MemoDto;
import com.example.demo.mapper.MemoMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class MemoService {
    @Autowired
    private MemoMapper mapper;
    
    public String send(HttpSession session,Model model)
    {
    	if(session.getAttribute("userid")==null)
    	{
    		return "redirect:/main/login";
    	}
    	else
    	{
    		String userid=session.getAttribute("userid").toString();
    		
    		ArrayList<MemoDto> mlist=mapper.send(userid);
    		model.addAttribute("mlist",mlist);
    		
    		return "memo/send";
    	}
    }

	public ArrayList<HashMap<String, String>> getUserid() {
		return mapper.getUserid();
	}

	public String moemoWriteOk(MemoDto mdto, HttpSession session, MultipartFile file) throws Exception {
		// memo 테이블에 저장
		if(file!=null && !file.isEmpty()) {
			String ofname=file.getOriginalFilename();
			
			String sfname=MyUtil.getRName(ofname);
			
			String imsi=ResourceUtils.getFile("classpath:static/memo").toString()+"/";
			
			Path path=Paths.get(imsi+sfname);
			
			Files.copy(file.getInputStream(), path,StandardCopyOption.REPLACE_EXISTING );
			
			mdto.setOfname(ofname);
			mdto.setSfname(sfname);
		}
		
		mdto.setSeUserid(session.getAttribute("userid").toString());
		
		mapper.memoWriteOk(mdto);
		
		return "redirect:/memo/send";
	}

    public String receive(HttpSession session,Model model)
    {
    	if(session.getAttribute("userid")==null)
    	{
    		return "redirect:/main/login";
    	}
    	else
    	{
    		String userid=session.getAttribute("userid").toString();
    		
    		ArrayList<MemoDto> mlist=mapper.receive(userid);
    		model.addAttribute("mlist",mlist);
    		return "memo/receive";
    	}
    }
    
    public String viewContent(String id,int ck)
    {
    	// 보낸메모의 content를 읽는다면 state 변경X  : ck=1
    	// 받은메모는 state를 1로변경               : ck=2
    	if(ck==2)
    	   mapper.setState(id); // state필드를 1로 변경
    	
    	String content=mapper.viewContent(id);
    	content=content.replace("\r\n","<br>");
    	return content;
    	//return mapper.viewContent(id).replace("\r\n", "<br>");
    }
    
    public void down(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		// 원래 이름으로 다운로드 => 원래파일이름, 저장되어 있는 파일이름
		String sfname=request.getParameter("sfname"); // 저장된 파일이름
		String ofname=request.getParameter("ofname"); // 원래 파일이름
		 
		String path=ResourceUtils.getFile("classpath:static/memo").toString();
		
		response.setHeader("Content-Type", "application/octet-stream"); // 바이너리 파일
		ofname=URLEncoder.encode(ofname,"utf-8");
		response.setHeader("Content-Disposition","attachment; filename="+ofname);
		response.setContentType("application/unknown"); // 파일의 유형을 알려준다
		              
		Path path2=Paths.get(path+"/"+sfname); // 시스템에 저장된 파일명
		
		Files.copy(path2, response.getOutputStream());
	}

	public String cntMemo(HttpSession session) {
		String userid=session.getAttribute("userid").toString();
    	String cnt=mapper.cntMemo(userid);
    	
    	return cnt;
	}
    
    
}













