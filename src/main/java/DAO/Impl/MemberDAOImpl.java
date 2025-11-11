package DAO.Impl;

import DAO.DAO;
import DAO.MemberDAO;
import Entity.Member;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAOImpl extends DAO implements MemberDAO {

    @Override
    public Member checkLogin(String username, String password) throws SQLException {
        String sql = "select * from tblmember where username = ? and password = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Member mem = new Member(rs.getInt("id"),
                                        rs.getString("fullname"),
                                        rs.getString("username"),
                                        rs.getString("password"),
                                        rs.getString("email"),
                                        rs.getString("phoneNumber"),
                                        rs.getDate("dateOfBirth"),
                                        rs.getString("role"),
                                        rs.getInt("tblAddressId"));
                return mem;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
