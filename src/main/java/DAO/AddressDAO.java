package DAO;

import Entity.Address;

import java.sql.SQLException;

public interface AddressDAO {
    int createAddress(Address address) throws SQLException;
    int checkExistOrCreateAddress(Address address) throws SQLException;
}
