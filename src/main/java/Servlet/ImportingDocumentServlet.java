package Servlet;

import DAO.DocumentDAO;
import DAO.Impl.DocumentDAOImpl;
import DAO.Impl.ImportingDocumentDAOImpl;
import DAO.Impl.ImportingInvoiceDAOImpl;
import DAO.Impl.SupplierStatisticDAOImpl;
import DAO.ImportingDocumentDAO;
import DAO.ImportingInvoiceDAO;
import DAO.SupplierStatisticDAO;
import Entity.Document;
import Entity.ImportingDocument;
import Entity.ImportingInvoice;
import Entity.Supplier;

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

@WebServlet(name = "ImportingDocument", value = "/LibraryManager/ImportingDocument")
public class ImportingDocumentServlet extends HttpServlet {
    private ImportingDocumentDAO  importingDocumentDAO = new ImportingDocumentDAOImpl();
    private DocumentDAO documentDAO = new DocumentDAOImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int importingInvoiceId =  Integer.parseInt(req.getParameter("importingInvoiceId"));
        int totalQuantity = Integer.parseInt(req.getParameter("totalQuantity"));
        double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));
        Date dateOfPayment = Date.valueOf(req.getParameter("dateOfPayment"));
        HttpSession session = req.getSession();
        List<ImportingDocument> importingDocuments = importingDocumentDAO.getImportingDocumentByImportingInvoiceId(importingInvoiceId);

        // sort full list first
        importingDocuments.sort(
                Comparator.comparingDouble(ImportingDocument::getQuantity).reversed()
        );

        // Pagination: read page and pageSize from request, defaults
        int page = 1;
        int pageSize = 10;
        try {
            String pageParam = req.getParameter("page");
            String pageSizeParam = req.getParameter("pageSize");
            if (pageParam != null) page = Math.max(1, Integer.parseInt(pageParam));
            if (pageSizeParam != null) pageSize = Math.max(1, Integer.parseInt(pageSizeParam));
        } catch (NumberFormatException ignored) {}

        int totalItems = importingDocuments.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        List<ImportingDocument> pageList = new ArrayList<>();
        if (totalItems > 0 && fromIndex < toIndex) {
            pageList = importingDocuments.subList(fromIndex, toIndex);
        }

        // Only fetch documents for current page to reduce DB calls
        List<Document> documents = new ArrayList<>();
        for(ImportingDocument d : pageList){
            Document document = documentDAO.getDocumentById(d.getDocumentId());
            documents.add(document);
        }

        req.setAttribute("dateOfPayment", dateOfPayment);
        req.setAttribute("totalQuantity", totalQuantity);
        req.setAttribute("totalPrice", totalPrice);
        req.setAttribute("importingDocuments", pageList);
        req.setAttribute("documents", documents);
        // pagination attributes
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", pageSize);
        req.setAttribute("totalItems", totalItems);
        // also expose importingInvoiceId for building links in JSP
        req.setAttribute("importingInvoiceId", importingInvoiceId);
        req.getRequestDispatcher("/LibraryManager/ImportingInvoiceDetailView.jsp").forward(req, resp);
    }

}
