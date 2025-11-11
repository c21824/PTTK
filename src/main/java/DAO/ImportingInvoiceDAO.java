package DAO;

import Entity.ImportingInvoice;


import java.sql.Date;
import java.util.List;

public interface ImportingInvoiceDAO {
    List<ImportingInvoice> getImportingInvoiceByTimeStamp(Date timeStart, Date timeEnd);
    List<ImportingInvoice> getImportingInvoiceByTimeStampAndSupplier(int supplierId, Date timeStart, Date timeEnd);
    ImportingInvoice getImportingInvoiceById(int id);
}
