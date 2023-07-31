package org.example.simplecrud.service.mappers;

import org.example.simplecrud.dto.SupplierDto;
import org.example.simplecrud.entity.Supplier;

public class SupplierMapper {

    public static Supplier mapToSupplier(SupplierDto supplierDto){
        return Supplier.builder()
                .id(Integer.parseInt(supplierDto.getId()))
                .companyName(supplierDto.getCompanyName())
                .contactPerson(supplierDto.getContactPerson())
                .address(supplierDto.getAddress())
                .email(supplierDto.getEmail())
                .build();
    }
}
