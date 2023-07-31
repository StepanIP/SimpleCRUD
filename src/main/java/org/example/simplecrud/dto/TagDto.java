package org.example.simplecrud.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TagDto {
    String id;
    String tag;
}
