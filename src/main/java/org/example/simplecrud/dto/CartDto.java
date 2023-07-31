package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartDto {
     String id;
     String totalCost;
     String totalAmount;
}
