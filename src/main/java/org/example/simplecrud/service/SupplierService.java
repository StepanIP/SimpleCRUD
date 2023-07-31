package org.example.simplecrud.service;

import org.example.simplecrud.dao.SupplierDao;
import org.example.simplecrud.dto.SupplierDto;
import org.example.simplecrud.entity.Supplier;
import org.example.simplecrud.service.mappers.SupplierMapper;
import org.example.simplecrud.utill.ConnectionManager;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierService {

    private static final SupplierService INSTANCE = new SupplierService();
    private static final Connection CONNECTION = ConnectionManager.get();

    private SupplierService(){

    }
    public static SupplierService getInstance(){
        return INSTANCE;
    }

    public static void save(SupplierDto supplierDto){
        boolean supplier = new SupplierDao().save(SupplierMapper.mapToSupplier(supplierDto),CONNECTION);
    }

    public static void delete(String id){
        boolean supplier = new SupplierDao().delete(Integer.parseInt(id),CONNECTION);
    }

    public static Supplier findSupplier(String id){
        return new SupplierDao().findById(Integer.parseInt(id),CONNECTION).get();
    }

    public static void updateSupplier(SupplierDto supplierDto){
        new SupplierDao().update(SupplierMapper.mapToSupplier(supplierDto),CONNECTION);
    }

    public static List<Supplier> takeList(){
        List<Supplier> supplierList = new SupplierDao().findAll().stream().sorted().collect(Collectors.toList());
        return supplierList;
    }
}
