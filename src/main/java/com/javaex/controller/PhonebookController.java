package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;


@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	
	//필드
	private static final long serialVersionUID = 1L;
       
	//생성자 - 기본생성자 사용하면 되니까 생략함
 

	//메소드 -gs
    
    
    //메소드 -일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//****
		System.out.println("PhonebookController.doGet()");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("wform".equals(action)) {
			System.out.println("wform:등록폼");
			
			//jsp에서 html 그려줘 + 응답해줘 ==> 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp"/*담당자 지정*/);
			rd.forward(request, response); //포워드 문법
			
		}else if("insert".equals(action)) {
			System.out.println("insert:등록");
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			// vo 로 묶기
			PersonVo personVo = new PersonVo(name, hp, company);
			System.out.println(personVo.toString());
			
			//db관련 업무
			PhoneDao phoneDao = new PhoneDao();
			
			//db에 저장
			phoneDao.personInsert(personVo); //Dao 에서 만들어준 등록 personInsert 씀
			
			//db에서 전체 데이터 가져오기
			List<PersonVo> personList = phoneDao.personSelect();
			
			//request에 담기
			request.setAttribute("personList", personList); //문자열, 주소
			
			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			
		}
		
		
		
	}
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
