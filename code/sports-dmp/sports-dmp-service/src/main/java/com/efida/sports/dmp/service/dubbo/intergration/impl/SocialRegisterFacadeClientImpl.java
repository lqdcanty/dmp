package com.efida.sports.dmp.service.dubbo.intergration.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.efida.sport.facade.MoRegisterFacade;
import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.MoRegisterModel;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sports.dmp.service.dubbo.intergration.SocialRegisterFacadeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class SocialRegisterFacadeClientImpl implements SocialRegisterFacadeClient {
    private static Logger logger = LoggerFactory
            .getLogger(SocialRegisterFacadeClientImpl.class);

    @Reference
    private MoRegisterFacade registerFacade;
    @Override
    public PagingResult<MoRegisterModel> findPage(Map<String, String> params) {

        return registerFacade.findPage(params);
    }

    @Override
    public MoRegisterModel updateBannedstatuslong(MoRegisterModel bean) {

        return registerFacade.updateBannedstatuslong(bean);
    }
}
