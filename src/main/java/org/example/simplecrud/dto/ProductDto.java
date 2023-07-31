package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDto {
    String id;
    String name;
    String metal;
    String cost;
    String image;
    String supplierId;
}
