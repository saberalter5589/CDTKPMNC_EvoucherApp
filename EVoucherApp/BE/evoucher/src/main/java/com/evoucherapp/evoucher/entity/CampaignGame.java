package com.evoucherapp.evoucher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "campain_game")
@Entity
public class CampaignGame extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campainGameId;
    private Long campainId;
    private Long gameId;
}
