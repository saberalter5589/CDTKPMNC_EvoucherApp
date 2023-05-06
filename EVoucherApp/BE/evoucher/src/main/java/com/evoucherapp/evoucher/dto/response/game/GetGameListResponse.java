package com.evoucherapp.evoucher.dto.response.game;

import com.evoucherapp.evoucher.entity.Game;
import lombok.Data;

import java.util.List;

@Data
public class GetGameListResponse {
    private List<Game> gameList;
}
