package Servlet;

import DAO.Impl.MemberDAOImpl;
import DAO.MemberDAO;
import Entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="LoginServlet", value = "/LibraryManager/login")
public class LoginServlet extends HttpServlet {
    private MemberDAO  memberDAO = new MemberDAOImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("username:"+username + " -- password:"+password);
        try {
            Member member = memberDAO.checkLogin(username, password);
            System.out.println(member);
            if( member != null ) {
                HttpSession  session = req.getSession();
                session.setAttribute("fullname", member.getFullname());
                resp.sendRedirect(req.getContextPath() + "/LibraryManager/LibraryManagerMainView.jsp");
            }
            else{
                req.setAttribute("errorMessage", "Wrong username or password");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
