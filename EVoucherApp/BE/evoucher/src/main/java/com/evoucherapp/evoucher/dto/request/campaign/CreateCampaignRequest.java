package com.evoucherapp.evoucher.dto.request.campaign;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCampaignRequest extends BaseRequest {
    private String campainCode;
    private String campainName;
    private String dateStart;
    private String dateEnd;
    private String description;
    private String note;
    private List<Long> gameIds = new ArrayList<>();
    private Long status;
}
