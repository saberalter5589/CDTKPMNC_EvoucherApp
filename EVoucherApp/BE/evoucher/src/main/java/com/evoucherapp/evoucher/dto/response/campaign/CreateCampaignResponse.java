package com.evoucherapp.evoucher.dto.response.campaign;

import com.evoucherapp.evoucher.dto.obj.CampaignDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateCampaignResponse {
    private CampaignDto campaign;
}
