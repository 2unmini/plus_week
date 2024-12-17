package com.example.demo.dto;

import com.example.demo.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {
    private Long id;
    private String name;
    private String description;


    public static ItemResponseDto toDto(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .build();

    }
}

