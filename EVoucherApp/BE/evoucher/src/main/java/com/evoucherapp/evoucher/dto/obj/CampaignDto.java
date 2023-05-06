package com.evoucherapp.evoucher.dto.obj;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CampaignDto {
    private Long campainId;
    private Long partnerId;
    private String partnerName;
    private String campainCode;
    private String campainName;
    private String dateStart;
    private String dateEnd;
    private String description;
    private String note;
    private Long status;
    private List<GameDto> gameList = new ArrayList<>();
}
