package DAO.Impl;

import DAO.DAO;
import DAO.ImportingDocumentDAO;
import Entity.ImportingDocument;
import Entity.ImportingInvoice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImportingDocumentDAOImpl extends DAO implements ImportingDocumentDAO {
    @Override
    public List<ImportingDocument> getImportingDocumentByImportingInvoiceId(Integer importingId) {
        String sql = "select * from tblimportingdocument where tblImportingInvoiceId=? ";
        List<ImportingDocument> importingDocuments = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, importingId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ImportingDocument i = new ImportingDocument();
                i.setId(rs.getInt("id"));
                i.setDocumentId(rs.getInt("tblDocumentId"));
                i.setImportingDate(rs.getDate("importingDate"));
                i.setImportingInvoiceId(rs.getInt("tblImportingInvoiceId"));
                i.setQuantity(rs.getInt("quantity"));
                i.setPrice(rs.getDouble("price"));
                importingDocuments.add(i);
            }
            return importingDocuments;
        }catch(Exception e){
            e.printStackTrace();
        }
        return importingDocuments;
    }
}
