package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WishListDto {
    String id;
    String Id;
    String userId;
}
