package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReviewsDto {
    String id;
    String userId;
    String productId;
    String text;
    String rate;
}
