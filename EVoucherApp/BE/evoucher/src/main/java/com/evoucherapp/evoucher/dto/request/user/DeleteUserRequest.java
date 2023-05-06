package com.evoucherapp.evoucher.dto.request.user;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class DeleteUserRequest extends BaseRequest {
    @JsonIgnore
    Long userTypeId;
}
