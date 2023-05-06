package com.evoucherapp.evoucher.mapper;

import com.evoucherapp.evoucher.entity.BaseEntity;

import java.time.LocalDateTime;

public class EntityDxo {
    public static void preUpdate(Long userId, BaseEntity entity) {
        entity.setUpdatedUserId(userId);
        entity.setUpdatedAt(LocalDateTime.now());
    };

    public static void preCreate(Long userId, BaseEntity entity){
        entity.setCreatedUserId(userId);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedUserId(userId);
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
