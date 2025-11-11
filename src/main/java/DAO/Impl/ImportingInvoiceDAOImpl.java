package DAO.Impl;

import DAO.DAO;
import DAO.ImportingInvoiceDAO;
import Entity.ImportingInvoice;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImportingInvoiceDAOImpl extends DAO implements ImportingInvoiceDAO {

    @Override
    public List<ImportingInvoice> getImportingInvoiceByTimeStamp(Date timeStart, Date timeEnd) {
        String sql = "Select * from tblimportinginvoice as i where dateOfPayment between ? and ?";
        List<ImportingInvoice> importingInvoices = new ArrayList<ImportingInvoice>();
        try(PreparedStatement ps = con.prepareStatement(sql);){
            ps.setDate(1, timeStart);
            ps.setDate(2, timeEnd);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ImportingInvoice i = new ImportingInvoice();
                i.setId(rs.getInt("id"));
                i.setTotalQuantity(rs.getInt("totalQuantity"));
                i.setTotalPrice(rs.getInt("totalPrice"));
                i.setDateOfPayment(rs.getDate("dateOfPayment"));
                i.setSupplierId(rs.getInt("tblSupplierId"));
                i.setMemberId(rs.getInt("tblMemberId"));
                importingInvoices.add(i);
            }
            return importingInvoices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return importingInvoices;
    }

    @Override
    public List<ImportingInvoice> getImportingInvoiceByTimeStampAndSupplier(int supplierId, Date timeStart, Date timeEnd) {
        String sql = "select * from tblimportinginvoice as i where tblSupplierId = ? and  dateOfPayment between ? and ?";
        List<ImportingInvoice> importingInvoices = new ArrayList<>();
        try(PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, supplierId);
            ps.setDate(2, timeStart);
            ps.setDate(3, timeEnd);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ImportingInvoice i = new ImportingInvoice();
                i.setId(rs.getInt("id"));
                i.setTotalQuantity(rs.getInt("totalQuantity"));
                i.setTotalPrice(rs.getInt("totalPrice"));
                i.setDateOfPayment(rs.getDate("dateOfPayment"));
                i.setSupplierId(rs.getInt("tblSupplierId"));
                i.setMemberId(rs.getInt("tblMemberId"));
                importingInvoices.add(i);
            }
            return importingInvoices;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return importingInvoices;
    }

    @Override
    public ImportingInvoice getImportingInvoiceById(int id) {
        String sql = "select * from tblimportinginvoice where id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            ImportingInvoice i = new ImportingInvoice();
            while(rs.next()){
                i.setId(rs.getInt("id"));
                i.setTotalQuantity(rs.getInt("totalQuantity"));
                i.setTotalPrice(rs.getInt("totalPrice"));
                i.setDateOfPayment(rs.getDate("dateOfPayment"));
                i.setSupplierId(rs.getInt("tblSupplierId"));
                i.setMemberId(rs.getInt("tblMemberId"));
            }
            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
