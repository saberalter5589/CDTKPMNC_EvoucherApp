package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

@Data
public class GameDto {
    private Long id;
    private String code;
    private String name;
    private String description;
}
