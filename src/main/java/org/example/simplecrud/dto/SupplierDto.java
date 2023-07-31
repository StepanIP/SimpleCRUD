package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SupplierDto {
    String id;
    String companyName;
    String contactPerson;
    String address;
    String email;
}
