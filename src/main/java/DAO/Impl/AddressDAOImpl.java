package DAO.Impl;

import DAO.AddressDAO;
import DAO.DAO;
import Entity.Address;

import java.sql.*;

public class AddressDAOImpl extends DAO implements AddressDAO {

    @Override
    public int createAddress(Address address) throws SQLException{
        String sql = "INSERT INTO tblAddress (houseNumber, street, ward, city, country) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, address.getHouseNumber());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getWard());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getCountry());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    @Override
    public int checkExistOrCreateAddress(Address address) throws SQLException {
        String checkSql = "SELECT id FROM tblAddress WHERE houseNumber=? AND street=? AND ward=? AND city=? AND country=?";
        try (PreparedStatement psCheck = con.prepareStatement(checkSql)) {
            psCheck.setString(1, address.getHouseNumber());
            psCheck.setString(2, address.getStreet());
            psCheck.setString(3, address.getWard());
            psCheck.setString(4, address.getCity());
            psCheck.setString(5, address.getCountry());

            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return createAddress(address);
            }
        }

    }
}
