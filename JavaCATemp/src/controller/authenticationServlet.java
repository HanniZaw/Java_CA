package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOException;
import dao.PersonDAO;
import model.Person;

/**
 * Servlet implementation class authenticationServlet
 */
@WebServlet(name = "authenServlet", urlPatterns = { "/authenServlet" })

public class authenticationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public authenticationServlet() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			authentication(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			authentication(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void authentication(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		char firstChar = username.trim().charAt(0);
		
		//Identification
		if(firstChar == 'S' || firstChar == 's'){
			PersonDAO ad = dao.DAOFactory.getStudentDAO();
			ArrayList<Person> aList;
			try {
				aList = ad.findAllPerson();
				request.setAttribute("aList", aList);
				for (Person p : aList) {
					if(username.equals(p.getId()) && password.equals(p.getPw())){
						RequestDispatcher ra = request.getRequestDispatcher("/studentload");
						ra.forward(request, response);
						return;	
					}
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(firstChar == 'L' || firstChar == 'l' ) {
			PersonDAO ad = dao.DAOFactory.getLectureDAO();
			ArrayList<Person> aList;
			try {
				aList = ad.findAllPerson();
				request.setAttribute("aList", aList);
				for (Person p : aList) {
					if(username.equals(p.getId()) && password.equals(p.getPw())){
						//RequestDispatcher ra = request.getRequestDispatcher("/views/Lecture/LectureDefault.jsp");
						
						HttpSession session = request.getSession();
				    	session.setAttribute("profile", p);
				    	 
						RequestDispatcher ra = request.getRequestDispatcher("/views/Lecture/LectureDefault.jsp");
						ra.forward(request, response);
						return;	
					}
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (firstChar == 'A' || firstChar == 'a'){
			PersonDAO ad = dao.DAOFactory.getAdminDao();
			ArrayList<Person> aList;
			try {
				aList = ad.findAllPerson();
				request.setAttribute("aList", aList);
				for (Person p : aList) {
					if(username.equals(p.getId()) && password.equals(p.getPw())){
						RequestDispatcher ra = request.getRequestDispatcher("/views/Admin/AdminDefault.jsp");
						ra.forward(request, response);
						return;	
					}
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
