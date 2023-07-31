package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDto {
    String id;
    String state;
    String dateOrdered;
    String dateShifted;
    String productId;
    String userId;
}
