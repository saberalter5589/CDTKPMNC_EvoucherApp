package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;
import com.evoucherapp.evoucher.dto.response.game.GetGameListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Controller
public class GameController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    GameService gameService;

    @GetMapping("/game/search")
    public ResponseEntity<GetGameListResponse> searchGame(@RequestBody GetBranchListRequest request){
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER, UserType.CUSTOMER));
        GetGameListResponse response = gameService.getGameList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
