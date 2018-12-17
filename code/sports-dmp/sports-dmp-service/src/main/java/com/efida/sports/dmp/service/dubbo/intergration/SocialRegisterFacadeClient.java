package com.efida.sports.dmp.service.dubbo.intergration;

import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.MoRegisterModel;
import com.efida.sport.facade.model.PagingResult;

import java.util.Map;

public interface SocialRegisterFacadeClient {

    PagingResult<MoRegisterModel> findPage(Map<String, String> params);

    MoRegisterModel updateBannedstatuslong(MoRegisterModel bean);
}
