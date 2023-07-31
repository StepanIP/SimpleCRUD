package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DetailInformationDto {
    String id;
    String age;
    String country;
    String deliveryAddress;
    String cartId;
}
