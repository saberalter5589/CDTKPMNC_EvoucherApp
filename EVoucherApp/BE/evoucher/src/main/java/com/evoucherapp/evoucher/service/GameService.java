package com.evoucherapp.evoucher.service;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.response.game.GetGameListResponse;
import com.evoucherapp.evoucher.entity.Game;

import java.util.List;

public interface GameService {
    GetGameListResponse getGameList();
}
