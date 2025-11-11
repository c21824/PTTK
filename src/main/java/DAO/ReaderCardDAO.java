package DAO;

import Entity.Reader;
import Entity.ReaderCard;

import java.sql.SQLException;

public interface ReaderCardDAO {
    void createReaderCard(ReaderCard readerCard);
    boolean checkEmailExist(String email);
    int createReader(Reader reader) throws SQLException;
}
