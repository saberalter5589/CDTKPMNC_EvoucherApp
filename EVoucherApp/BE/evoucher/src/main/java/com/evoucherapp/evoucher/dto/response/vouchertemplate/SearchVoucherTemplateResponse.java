package com.evoucherapp.evoucher.dto.response.vouchertemplate;

import com.evoucherapp.evoucher.dto.obj.VoucherTemplateDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchVoucherTemplateResponse {
    private List<VoucherTemplateDto> voucherTemplateDtoList;
}
