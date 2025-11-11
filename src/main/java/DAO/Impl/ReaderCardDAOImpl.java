package DAO.Impl;

import DAO.DAO;
import DAO.ReaderCardDAO;
import Entity.Reader;
import Entity.ReaderCard;

import java.sql.*;

public class ReaderCardDAOImpl extends DAO implements ReaderCardDAO {

    @Override
    public void createReaderCard(ReaderCard readerCard) {
        String sql = "INSERT INTO tblreadercard (cardNumber, dateOfCreate, tblReaderId) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, readerCard.getCardNumber());
            ps.setDate(2, (Date) readerCard.getDateOfCard());
            ps.setInt(3, readerCard.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int createReader(Reader reader) throws SQLException {
        String sql = "INSERT INTO tblReader (fullName, dateOfBirth, email, phoneNumber, tblAddressId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reader.getFullname());
            ps.setDate(2, (Date) reader.getDateOfBirth());
            ps.setString(3, reader.getEmail());
            ps.setString(4, reader.getPhoneNumber());
            ps.setInt(5, reader.getAddressId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public boolean checkEmailExist(String email) {
        String sql = "SELECT COUNT(*) FROM tblReader WHERE email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
