package com.evoucherapp.evoucher.service.impl;

import com.evoucherapp.evoucher.common.constant.CampaignStatus;
import com.evoucherapp.evoucher.common.constant.DateTimeFormat;
import com.evoucherapp.evoucher.common.constant.UserType;
import com.evoucherapp.evoucher.dto.MessageInfo;
import com.evoucherapp.evoucher.dto.obj.CampaignDto;
import com.evoucherapp.evoucher.dto.request.campaign.CreateCampaignRequest;
import com.evoucherapp.evoucher.dto.request.campaign.SearchCampaignRequest;
import com.evoucherapp.evoucher.dto.response.campaign.CreateCampaignResponse;
import com.evoucherapp.evoucher.dto.response.campaign.SearchCampaignResponse;
import com.evoucherapp.evoucher.entity.Campaign;
import com.evoucherapp.evoucher.entity.CampaignGame;
import com.evoucherapp.evoucher.entity.EUser;
import com.evoucherapp.evoucher.exception.DataExistException;
import com.evoucherapp.evoucher.exception.NoDataFoundException;
import com.evoucherapp.evoucher.exception.UnAuthorizationException;
import com.evoucherapp.evoucher.mapper.CampaignDxo;
import com.evoucherapp.evoucher.mapper.EntityDxo;
import com.evoucherapp.evoucher.repository.CampaignGameRepository;
import com.evoucherapp.evoucher.repository.CampaignRepository;
import com.evoucherapp.evoucher.repository.EUserRepository;
import com.evoucherapp.evoucher.service.CampaignService;
import com.evoucherapp.evoucher.util.CommonUtil;
import com.evoucherapp.evoucher.util.DateTimeUtil;
import com.evoucherapp.evoucher.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CampaignServiceImpl implements CampaignService {
    @Autowired
    CampaignRepository campaignRepository;
    @Autowired
    CampaignGameRepository campaignGameRepository;
    @Autowired
    EUserRepository userRepository;

    @Override
    @Transactional
    public CreateCampaignResponse createCampaign(CreateCampaignRequest request) {
        Long partnerId = request.getAuthentication().getUserId();
        Campaign campaign = campaignRepository.findByCode(request.getCampainCode());
        if(campaign != null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "campain_code");
            throw new DataExistException(messageInfo);
        }
        if(request.getGameIds().size() == 0){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "gameIds");
            throw new NoDataFoundException(messageInfo);
        }

        LocalDate dateStart = DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE);
        LocalDate dateEnd = DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE);

        Campaign newCampaign = new Campaign();
        newCampaign.setPartnerId(partnerId);
        newCampaign.setCampainCode(request.getCampainCode());
        newCampaign.setCampainName(request.getCampainName());
        newCampaign.setDateStart(dateStart);
        newCampaign.setDateEnd(dateEnd);
        newCampaign.setDescription(request.getDescription());
        newCampaign.setNote(request.getNote());
        newCampaign.setStatus(CampaignStatus.NOT_START);
        newCampaign.setIsDeleted(false);
        EntityDxo.preCreate(partnerId, newCampaign);
        campaignRepository.save(newCampaign);

        createNewCampaignGame(partnerId, newCampaign.getCampainId(), request);

        CampaignDto dto = new CampaignDto();
        dto.setCampainId(newCampaign.getCampainId());
        CreateCampaignResponse response = new CreateCampaignResponse();
        response.setCampaign(dto);
        return response;
    }

    @Override
    @Transactional
    public void updateCampaign(Long campaignId, CreateCampaignRequest request) {
        Long partnerId = request.getAuthentication().getUserId();
        Campaign campaign = campaignRepository.findByCampaignId(campaignId);
        if(campaign == null){
            MessageInfo messageInfo = MessageUtil.formatMessage(10001, "campain_id");
            throw new NoDataFoundException(messageInfo);
        }
        if(!campaign.getPartnerId().equals(partnerId)){
            MessageInfo messageInfo = MessageUtil.formatMessage(10003);
            throw new UnAuthorizationException(messageInfo);
        }

        String reqCode = request.getCampainCode();
        Campaign existCodeCampain = campaignRepository.findByCode(reqCode);
        if(existCodeCampain != null && existCodeCampain != campaign){
            MessageInfo messageInfo = MessageUtil.formatMessage(10002, "campain_code");
            throw new DataExistException(messageInfo);
        }

        LocalDate dateStart = DateTimeUtil.parseStringToDate(request.getDateStart(), DateTimeFormat.DATE);
        LocalDate dateEnd = DateTimeUtil.parseStringToDate(request.getDateEnd(), DateTimeFormat.DATE);

        campaign.setCampainCode(request.getCampainCode());
        campaign.setCampainName(request.getCampainName());
        campaign.setDateStart(dateStart);
        campaign.setDateEnd(dateEnd);
        campaign.setDescription(request.getDescription());
        campaign.setNote(request.getNote());
        campaign.setStatus(request.getStatus());
        EntityDxo.preCreate(partnerId, campaign);
        campaignRepository.save(campaign);

        createNewCampaignGame(partnerId, campaignId, request);
    }

    @Override
    public SearchCampaignResponse searchCampaign(SearchCampaignRequest request) {
        Long userId = request.getAuthentication().getUserId();
        EUser user = userRepository.findByUserId(userId);
        if(user.getUserTypeId().equals(UserType.PARTNER)){
            request.setPartnerId(userId);
        }

        List<Object[]> dbSearchResult = campaignRepository.searchCampaign(request);
        List<CampaignDto> dtoList = CampaignDxo.convertFromDbListToDtoList(dbSearchResult);
        SearchCampaignResponse response = new SearchCampaignResponse();
        response.setCampaignDtoList(dtoList);
        return response;
    }

    private void createNewCampaignGame(Long partnerId, Long campaignId, CreateCampaignRequest request){
        List<CampaignGame> existingCampaignGames = campaignGameRepository.findByCampainId(campaignId);
        if(!CommonUtil.isNullOrEmpty(existingCampaignGames)){
            for(CampaignGame existingGame : existingCampaignGames){
                existingGame.setIsDeleted(true);
                EntityDxo.preUpdate(partnerId, existingGame);
            }
            campaignGameRepository.saveAllAndFlush(existingCampaignGames);
        }

        List<Long> reqGameIds = request.getGameIds();
        List<CampaignGame> newCampainGames = new ArrayList<>();
        for(Long gameId : reqGameIds){
            CampaignGame campaignGame = new CampaignGame();
            campaignGame.setGameId(gameId);
            campaignGame.setCampainId(campaignId);
            campaignGame.setIsDeleted(false);
            EntityDxo.preCreate(partnerId, campaignGame);
            newCampainGames.add(campaignGame);
        }
        campaignGameRepository.saveAllAndFlush(newCampainGames);
    }
}
