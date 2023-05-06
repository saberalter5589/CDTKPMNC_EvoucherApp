package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.response.game.GetGameListResponse;
import com.evoucherapp.evoucher.entity.Game;
import com.evoucherapp.evoucher.repository.GameRepository;
import com.evoucherapp.evoucher.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    GameRepository gameRepository;

    @Override
    public GetGameListResponse getGameList() {
        GetGameListResponse response = new GetGameListResponse();
        response.setGameList(gameRepository.findAllGames());
        return response;
    }
}
