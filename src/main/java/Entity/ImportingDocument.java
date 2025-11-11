package Entity;

import java.sql.Date;


public class ImportingDocument {
    private int id;
    private int quantity;
    private double price;
    private Date importingDate;
    private int documentId;
    private int importingInvoiceId;

    public ImportingDocument() {
    }

    public ImportingDocument(int quantity, double price, Date importingDate, int documentId, int importingInvoiceId) {
        this.quantity = quantity;
        this.price = price;
        this.importingDate = importingDate;
        this.documentId = documentId;
        this.importingInvoiceId = importingInvoiceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getImportingDate() {
        return importingDate;
    }

    public void setImportingDate(Date importingDate) {
        this.importingDate = importingDate;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getImportingInvoiceId() {
        return importingInvoiceId;
    }

    public void setImportingInvoiceId(int importingInvoiceId) {
        this.importingInvoiceId = importingInvoiceId;
    }
}
