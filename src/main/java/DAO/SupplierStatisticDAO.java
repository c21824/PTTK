package DAO;

import Entity.Supplier;
import Entity.SupplierStatistic;

import java.sql.Date;
import java.util.List;

public interface SupplierStatisticDAO{
    List<SupplierStatistic>  getSupplierStatisticsByTimeStamp(Date timeStart, Date timeEnd);
    Supplier getSupplierById(int supplierId);
}
