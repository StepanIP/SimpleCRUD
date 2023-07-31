package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PromotionDto {
    String id;
    String startDate;
    String endDate;
    String productId;
    String discount;
}
