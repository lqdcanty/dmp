package com.efida.sports.dmp.service.dubbo.intergration;

import com.efida.sport.facade.model.DefaultResult;
import com.efida.sport.facade.model.PagingResult;
import com.efida.sport.facade.model.SocialInfoCountModel;
import com.efida.sport.facade.model.SocialInfoModel;

import java.util.Map;

public interface SocialInfoFacadeClient {

    PagingResult<SocialInfoModel> findPage(Map<String, String> params);

    PagingResult<SocialInfoModel> findPageByStick(Map<String, String> params);

    void updateStatus(SocialInfoModel bean);

    void saveStickInfo(String infoId);

    /**
     * 取消置顶
     * @param id  置顶id
     * @return
     */
    void removeStickInfo(String id );

    SocialInfoModel getSocialInfo(String id);

    SocialInfoModel getSocialStick(String id);

    SocialInfoCountModel getInfoCount(Map<String, String> params);

    void stickSort(Map<String, String> params);

    void infoAuthCheck(Map<String, String> params);
}
