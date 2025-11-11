package Entity;

public class SupplierStatistic extends Supplier{
    private int totalQuantity;
    private double totalPrice;

    public SupplierStatistic() {
    }


    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity (int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
