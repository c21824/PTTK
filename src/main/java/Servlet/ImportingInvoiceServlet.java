package Servlet;

import DAO.Impl.ImportingInvoiceDAOImpl;
import DAO.Impl.SupplierStatisticDAOImpl;
import DAO.ImportingInvoiceDAO;
import DAO.SupplierStatisticDAO;
import Entity.ImportingInvoice;
import Entity.Supplier;
import Entity.SupplierStatistic;
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

@WebServlet(name="ImportingInvoice", value="/LibraryManager/ImportingInvoice")
public class ImportingInvoiceServlet extends HttpServlet {
    private ImportingInvoiceDAO importingInvoiceDAO = new ImportingInvoiceDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String timeStart = session.getAttribute("timeStart").toString();
        String timeEnd = session.getAttribute("timeEnd").toString();

        int supplierId = Integer.parseInt(req.getParameter("supplierId"));

        session.setAttribute("supplierName", req.getParameter("supplierName"));

        Date startDate = ConvertDate.parseDmyToSqlDate(timeStart);
        Date endDate = ConvertDate.parseDmyToSqlDate(timeEnd);

        int totalQuantity = 0;
        double totalPrice = 0;

        List<ImportingInvoice> importingInvoices = importingInvoiceDAO.getImportingInvoiceByTimeStampAndSupplier(supplierId, startDate, endDate);
        for(ImportingInvoice i : importingInvoices){
            totalQuantity += i.getTotalQuantity();
            totalPrice += i.getTotalPrice();
        }
        importingInvoices.sort(
                Comparator.comparingDouble(ImportingInvoice::getTotalPrice).reversed()
        );

        int page = 1;
        int pageSize = 10;
        try {
            String pageParam = req.getParameter("page");
            String pageSizeParam = req.getParameter("pageSize");
            if (pageParam != null) page = Math.max(1, Integer.parseInt(pageParam));
            if (pageSizeParam != null) pageSize = Math.max(1, Integer.parseInt(pageSizeParam));
        } catch (NumberFormatException ignored) {}

        int totalItems = importingInvoices.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<ImportingInvoice> pageList = importingInvoices.subList(fromIndex, toIndex);

        session.setAttribute("importingInvoices",pageList );
        req.setAttribute("totalQuantity", totalQuantity);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", pageSize);
        req.getRequestDispatcher(("/LibraryManager/ImportingDocumentBatchView.jsp")).forward(req, resp);
    }
}
