package com.efida.sports.dmp.service.dubbo.intergration.impl;

import com.efida.sport.facade.MoRegisterFacade;
import com.efida.sport.facade.SocialInfoFacade;
import com.efida.sport.facade.model.*;
import com.efida.sports.dmp.service.dubbo.intergration.SocialInfoFacadeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;

import java.util.Map;

@Service
public class SocialInfoFacadeClientImpl implements SocialInfoFacadeClient {

    private static Logger logger = LoggerFactory
            .getLogger(SocialInfoFacadeClientImpl.class);

    @Reference
    private SocialInfoFacade socialInfoFacade;


    @Override
    public PagingResult<SocialInfoModel> findPage(Map<String, String> params) {

        return socialInfoFacade.findPageByInfo(params);
    }

    @Override
    public PagingResult<SocialInfoModel> findPageByStick(Map<String, String> params) {

        return socialInfoFacade.findPageByStick(params);
    }

    @Override
    public void updateStatus(SocialInfoModel bean) {

       socialInfoFacade.updateStatus(bean);
    }

    @Override
    public void saveStickInfo(String infoId) {

        socialInfoFacade.saveStickInfo(infoId);
    }

    @Override
    public void removeStickInfo(String id) {

        socialInfoFacade.removeStickInfo(id);
    }

    @Override
    public SocialInfoModel getSocialInfo(String id) {

        return socialInfoFacade.getSocialInfo(id);
    }

    @Override
    public SocialInfoModel getSocialStick(String id) {

        return socialInfoFacade.getSocialStick(id);
    }

    @Override
    public SocialInfoCountModel getInfoCount(Map<String, String> params) {

        return socialInfoFacade.getInfoCount(params);
    }

    @Override
    public void stickSort(Map<String, String> params) {
        socialInfoFacade.stickSort(params);
    }

    @Override
    public void infoAuthCheck(Map<String, String> params) {

        socialInfoFacade.infoAuthCheck(params);
    }
}
