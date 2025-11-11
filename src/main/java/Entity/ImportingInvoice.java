package Entity;

import java.sql.Date;

public class ImportingInvoice {
    private int id;
    private int totalQuantity;
    private double totalPrice;
    private Date dateOfPayment;
    private int supplierId;
    private int memberId;

    public ImportingInvoice() {
    }

    public ImportingInvoice(int totalQuantity, double totalPrice, Date dateOfPayment, int supplierId, int memberId) {
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.dateOfPayment = dateOfPayment;
        this.supplierId = supplierId;
        this.memberId = memberId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "ImportingInvoice{" +
                "id=" + id +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                ", dateOfPayment=" + dateOfPayment +
                ", supplierId=" + supplierId +
                ", memberId=" + memberId +
                '}';
    }
}
