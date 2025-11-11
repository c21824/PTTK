package Servlet;

import DAO.Impl.ImportingInvoiceDAOImpl;
import DAO.Impl.SupplierStatisticDAOImpl;
import DAO.ImportingInvoiceDAO;
import DAO.SupplierStatisticDAO;
import Entity.ImportingInvoice;
import Entity.Supplier;
import Entity.SupplierStatistic;
import com.mysql.cj.Session;
import utils.ConvertDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(name="StatisticSupplier", value = "/LibraryManager/StatisticSupplier")
public class SupplierStatisticServlet extends HttpServlet {
    private SupplierStatisticDAO supplierStatisticDAO = new SupplierStatisticDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timeStart = request.getParameter("timeStart");
        String timeEnd = request.getParameter("timeEnd");
        System.out.println("timeStart: " + timeStart + " timeEnd: " + timeEnd);

        HttpSession session = request.getSession();

        // If timeStart/timeEnd not provided in this request (e.g. paging links), try to get from session
        if ((timeStart == null || timeEnd == null) && session.getAttribute("timeStart") != null && session.getAttribute("timeEnd") != null) {
            timeStart = (String) session.getAttribute("timeStart");
            timeEnd = (String) session.getAttribute("timeEnd");
        }

        if(timeStart == null || timeEnd == null){
            request.setAttribute("error", "Please select all require field");
            request.getRequestDispatcher("/SelectTimeStampView.jsp").forward(request, response);
            return;
        }

        try{
            Date start = ConvertDate.parseDmyToSqlDate(timeStart);
            Date end = ConvertDate.parseDmyToSqlDate(timeEnd);

            List<SupplierStatistic> supplierStatistics = supplierStatisticDAO.getSupplierStatisticsByTimeStamp(start,end);
            int totalQuantity = 0;
            double totalPrice = 0;
            for(SupplierStatistic supplierStatistic : supplierStatistics){
                totalPrice +=  supplierStatistic.getTotalPrice();
                totalQuantity += supplierStatistic.getTotalQuantity();
            }
            supplierStatistics.sort(
                    Comparator.comparingDouble(SupplierStatistic::getTotalPrice).reversed()
            );

            // Pagination: read page and pageSize from request, with defaults
            int page = 1;
            int pageSize = 10;
            try {
                String pageParam = request.getParameter("page");
                String pageSizeParam = request.getParameter("pageSize");
                if (pageParam != null) page = Math.max(1, Integer.parseInt(pageParam));
                if (pageSizeParam != null) pageSize = Math.max(1, Integer.parseInt(pageSizeParam));
            } catch (NumberFormatException ignored) {}

            int totalItems = supplierStatistics.size();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, totalItems);
            List<SupplierStatistic> pageList = supplierStatistics.subList(fromIndex, toIndex);

            // store time range in session as before (keep DMY formatted)
            String strTimeStart = ConvertDate.toDmyFromObject(timeStart);
            String strTimeEnd = ConvertDate.toDmyFromObject(timeEnd);
            session.setAttribute("timeStart", strTimeStart);
            session.setAttribute("timeEnd", strTimeEnd);

            request.setAttribute("totalQuantity", totalQuantity);
            request.setAttribute("totalPrice", totalPrice);
            // set paginated list into request so JSP renders only current page
            request.setAttribute("supplierStatistics", pageList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);

            request.getRequestDispatcher("/LibraryManager/SupplierStatisticView.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
