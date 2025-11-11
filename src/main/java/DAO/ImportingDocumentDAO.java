package DAO;

import Entity.ImportingDocument;


import java.util.List;

public interface ImportingDocumentDAO {
    List<ImportingDocument> getImportingDocumentByImportingInvoiceId(Integer importingId);
}
