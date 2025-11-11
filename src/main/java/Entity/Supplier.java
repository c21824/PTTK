package Entity;

public class Supplier {
    private int id;
    private String supplierName;
    private String email;
    private String taxCode;
    private String bankName;
    private String bankAccountNumber;
    private String status;
    private int addressId;

    public Supplier() {
    }

    public Supplier(String supplierName, String email, String taxCode, String bankName, String bankAccountNumber, String status, int addressId) {
        this.supplierName = supplierName;
        this.email = email;
        this.taxCode = taxCode;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.status = status;
        this.addressId = addressId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
