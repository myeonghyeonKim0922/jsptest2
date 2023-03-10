package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.MemberDAO;

@WebServlet("/")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request,response);
	}

	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getContextPath();
		String command = request.getServletPath();
		String site = null;
		
		System.out.println(request.getParameter("custno"));
		
		
		MemberDAO member = new MemberDAO();
		
		switch (command) {
		case "/home":
			site = "index.jsp";
			break;
		case "/insert":
			site=member.insert(request, response);
			break;
		case "/list":
			site = member.selectAll(request, response);
			break;
		case "/add":
			site = member.nextCustno(request, response);
			break;
		case "/modify":
			site = member.modify(request, response);
			break;
		case "/update":
			int result1 = member.update(request, response);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result1 == 1) { //업데이트 성공
				out.println("<script>");
				out.println("alert('회원수정이 완료 되었습니다!'); location.href='" + context +"';");
				out.println("</script>");
				out.flush();
			}else {
				out.println("<script>");
				out.println("alert('수정실패!'); location.href='" + context +"';");
				out.println("</script>");
				out.flush();
			}
				
			break;	
		}
		getServletContext().getRequestDispatcher("/"+site).forward(request, response);
		
	}
}
