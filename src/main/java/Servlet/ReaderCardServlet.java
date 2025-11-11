package Servlet;

import DAO.AddressDAO;
import DAO.Impl.AddressDAOImpl;
import DAO.Impl.ReaderCardDAOImpl;
import DAO.ReaderCardDAO;
import Entity.Address;
import Entity.Reader;
import Entity.ReaderCard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.text.ParseException;

@WebServlet(name="ReaderCard", value = "/Reader/ReaderCard")
public class ReaderCardServlet extends HttpServlet {
    private AddressDAO addressDAO = new AddressDAOImpl();
    private ReaderCardDAO readerCardDAO = new ReaderCardDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try{
            String fullname = request.getParameter("fullName");
            String dateOfBirth = request.getParameter("dateOfBirth");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String country = request.getParameter("country");
            String city = request.getParameter("city");
            String street = request.getParameter("street");
            String ward = request.getParameter("ward");
            String houseNumber = request.getParameter("houseNumber");
            System.out.println(fullname +"-" + dateOfBirth + "-" + email + "-" + phoneNumber + "-" + country + "-" + city + "-" + street + "-" + ward + "-" + houseNumber );

            System.out.println("email = " + email);

            try {
                boolean exists = readerCardDAO.checkEmailExist(email);
                System.out.println("Email exists? " + exists);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(readerCardDAO.checkEmailExist(email)){
                request.setAttribute("errorMessage","Email already exist!!!");
                request.getRequestDispatcher("/Reader/RegisterForReaderCardView.jsp").forward(request,response);
                return;
            }

            java.sql.Date birthDateSql = null;
            if (dateOfBirth != null && !dateOfBirth.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                try {
                    java.util.Date parsed = sdf.parse(dateOfBirth);
                    birthDateSql = new java.sql.Date(parsed.getTime());
                } catch (ParseException pe) {
                    // fallback: try yyyy-MM-dd
                    try {
                        birthDateSql = java.sql.Date.valueOf(dateOfBirth);
                    } catch (IllegalArgumentException iae) {
                        request.setAttribute("errorMessage", "Invalid date format. Please use dd/MM/yyyy");
                        request.getRequestDispatcher("/Reader/RegisterForReaderCardView.jsp").forward(request, response);
                        return;
                    }
                }
            } else {
                request.setAttribute("errorMessage", "Date of birth is required");
                request.getRequestDispatcher("/Reader/RegisterForReaderCardView.jsp").forward(request, response);
                return;
            }

             int addressId = addressDAO.checkExistOrCreateAddress(new Address(country, city, ward, street, houseNumber));
            Reader reader = new Reader(fullname, email, phoneNumber, birthDateSql, addressId);
            int readerId = readerCardDAO.createReader(reader);
            reader.setId(readerId);
             String cardNumber = String.format("RC%05d", reader.getId());
             String createDate = LocalDate.now().toString();
            readerCardDAO.createReaderCard(new ReaderCard(cardNumber, Date.valueOf(createDate), reader));

             request.setAttribute("successMessage", "Register complete! Card Number:" + cardNumber);
             request.getRequestDispatcher("/Reader/ReaderMainView.jsp").forward(request,response);
         }catch (Exception e){
             e.printStackTrace();
         }
     }
 }
