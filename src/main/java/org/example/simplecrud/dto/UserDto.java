package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String id;
    String email;
    String phoneNumber;
    String password;
    String firstName;
    String surname;
    String detailInformationId;
}
