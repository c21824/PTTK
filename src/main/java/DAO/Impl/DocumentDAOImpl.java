package DAO.Impl;

import DAO.DAO;
import DAO.DocumentDAO;
import Entity.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DocumentDAOImpl extends DAO implements DocumentDAO {

    @Override
    public Document getDocumentById(int id) {
        String sql = "select * from tbldocument where id = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            Document d =  new Document();
            while(rs.next()){
                d.setId(rs.getInt("id"));
                d.setAuthor(rs.getString("author"));
                d.setTitle(rs.getString("title"));
                d.setNumberOfPages(rs.getInt("numberOfPages"));
                d.setPublisher(rs.getString("publisher"));
                d.setYearOfPublication(rs.getInt("yearOfPublication"));
                d.setType(rs.getString("type"));
                d.setCategory(rs.getString("category"));
                d.setQuantity(rs.getInt("quantity"));
            }
            return d;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
