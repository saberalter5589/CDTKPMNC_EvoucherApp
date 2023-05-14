package com.evoucherapp.evoucher.controller;

import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.request.BaseRequest;
import com.evoucherapp.evoucher.dto.request.branch.GetBranchListRequest;
import com.evoucherapp.evoucher.dto.response.branch.GetBranchListResponse;
import com.evoucherapp.evoucher.dto.response.game.GetGameListResponse;
import com.evoucherapp.evoucher.service.AuthenticationService;
import com.evoucherapp.evoucher.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Arrays;

@Controller
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    GameService gameService;

    @GetMapping("/game/search")
    public ResponseEntity<GetGameListResponse> searchGame(@RequestHeader("userId") Long userId,
                                                                      @RequestHeader("password") String password){
        BaseRequest request = new BaseRequest();
        request.getAuthentication().setUserId(userId);
        request.getAuthentication().setPassword(password);
        authenticationService.validateUser(request, Arrays.asList(UserType.ADMIN, UserType.PARTNER, UserType.CUSTOMER));
        GetGameListResponse response = gameService.getGameList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
