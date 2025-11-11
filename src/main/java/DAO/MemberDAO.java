package DAO;

import Entity.Member;

import java.sql.SQLException;

public interface MemberDAO {
    Member checkLogin(String username, String password) throws SQLException;
}
