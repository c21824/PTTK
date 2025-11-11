package DAO.Impl;

import DAO.DAO;
import DAO.SupplierStatisticDAO;
import Entity.Supplier;
import Entity.SupplierStatistic;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SupplierStatisticDAOImpl extends DAO implements SupplierStatisticDAO {
    @Override
    public List<SupplierStatistic> getSupplierStatisticsByTimeStamp(Date timeStart, Date timeEnd) {
        String sql =
                "SELECT " +
                        "  s.id AS tblSupplierId, " +
                        "  s.supplierName as  supplierName, " +
                        "  SUM(ii.totalQuantity) AS totalQuantity, " +
                        "  SUM(ii.totalPrice) AS totalPrice " +
                        "FROM tblSupplier s " +
                        "JOIN tblImportingInvoice ii ON ii.tblSupplierId = s.id " +
                        "WHERE ii.dateOfPayment BETWEEN ? AND ? " +
                        "GROUP BY s.id, s.supplierName " +
                        "ORDER BY totalPrice DESC";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setDate(1, timeStart);
            ps.setDate(2, timeEnd);
            ResultSet rs = ps.executeQuery();
            List<SupplierStatistic> supplierStatistics = new ArrayList<>();
            while(rs.next()){
                SupplierStatistic s = new SupplierStatistic();
                s.setId(rs.getInt("tblSupplierId"));
                s.setSupplierName(rs.getString("supplierName"));
                s.setTotalQuantity(rs.getInt("totalQuantity"));
                s.setTotalPrice(rs.getInt("totalPrice"));
                supplierStatistics.add(s);
            }
            return supplierStatistics;
        }catch(SQLException e){
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public Supplier getSupplierById(int supplierId) {
        String sql = "select * from tblsupplier where id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, supplierId);
            ResultSet rs = ps.executeQuery();
            Supplier supplier = new Supplier();
            while (rs.next()) {
                supplier.setId(rs.getInt("id"));
                supplier.setSupplierName(rs.getString("supplierName"));
                supplier.setEmail(rs.getString("email"));
                supplier.setTaxCode(rs.getString("taxCode"));
                supplier.setBankName(rs.getString("bankName"));
                supplier.setBankAccountNumber(rs.getString("bankAccountNumber"));
                supplier.setStatus(rs.getString("status"));
                supplier.setAddressId(rs.getInt("tblAddressId"));
            }
            return supplier;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
