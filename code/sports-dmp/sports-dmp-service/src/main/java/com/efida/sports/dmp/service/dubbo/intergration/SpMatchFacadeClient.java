package com.efida.sports.dmp.service.dubbo.intergration;

import java.util.Date;
import java.util.List;

import com.efida.sport.admin.facade.model.MatchAreasModel;
import com.efida.sport.admin.facade.model.SpGroupItemModel;
import com.efida.sport.admin.facade.model.SpMatchAllInfoModel;
import com.efida.sport.admin.facade.model.SpMatchGroupAndItemInfoModel;
import com.efida.sport.admin.facade.model.SpMatchGroupModel;
import com.efida.sport.admin.facade.model.SpMatchInfoModel;
import com.efida.sport.admin.facade.model.SpMatchModel;
import com.efida.sport.admin.facade.model.SpPlayingAreaModel;
import com.efida.sport.admin.facade.model.open.MatchDetailModel;
import com.efida.sport.admin.facade.model.page.SportsAdminPageResult;

public interface SpMatchFacadeClient {
    /**
     * 获取赛事列表
     * @param gameCode   项目分类Code
     * @return
     */
    List<SpMatchModel> getMatchs(String gameCode);

    /**
     * 查询比赛组和比赛项数据 
     * 
     * @param playingAreaId  赛场Id
     * @return
     */
    List<SpMatchGroupAndItemInfoModel> getMatchTypeAndGroupInfo(String fileidCode);

    /**
     * 查询赛场列表数据
     * 
     * @param matchCode 赛事code
     * @return
     */
    List<SpPlayingAreaModel> getSpPlayingAreas(String matchCode);

    /**
     * 获取赛事详情
     * @param code
     * @return 
     */
    SpMatchInfoModel getMatchDetail(String code);

    /**
     * 获取赛事信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param matchCode
     * @return
     */
    SpMatchModel getEnableSpMatch(String matchCode);

    /**
     * 获取比赛项详细信息
     * 
     * @param itemId
     * @return
     */
    SpMatchAllInfoModel getItemDetail(String fileidCode, String itemCode);

    /**
     * 获取赛事信息
     * 
     * @param matchCode
     * @param matchStatus
     * @param beginTimeMin
     * @param beginTimeMax
     * @param endTimeMin
     * @param endTimeMax
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<MatchDetailModel> queryMatches(String matchCode, String matchStatus,
                                               Date beginTimeMin, Date beginTimeMax,
                                               Date endTimeMin, Date endTimeMax, int pageNumber,
                                               int pageSize);

    /**
     * 根据项目编号获取赛事列表
     * 
     * @param gameCode
     * @param pageNumber
     * @param pageSize
     * @return
     */
    SportsAdminPageResult<SpMatchModel> getPageMatchs(String gameCode, int pageNumber,
                                                      int pageSize);

    /**
     * 
     * 搜索赛事列表
     * @param keyword
     * @param pageNumber
     * @param pageSize
     * @return
     */
    SportsAdminPageResult<SpMatchModel> searchMatchs(String keyword, int pageNumber, int pageSize);

    /**
     * 获取承办方赛事
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    SportsAdminPageResult<SpMatchModel> getUnitMatchByKeyWord(String unitCode, String keyword,
                                                              int page, int size);

    /**
     * 获取赛事详细信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode 承办方编号
     * @param matchCode 赛事编号
     * @param pageNumber 页数
     * @param pageSize 每页数量
     * @return
     */
    public SportsAdminPageResult<MatchDetailModel> getUnitMatchInfos(String unitCode,
                                                                     String matchCode,
                                                                     int pageNumber, int pageSize);

    /**
     * 获取赛事详细信息
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param unitCode 承办方编号
     * @param matchCode 赛事编号
     * @param pageNumber 页数
     * @param pageSize 每页数量
     * @return
     */
    public MatchDetailModel getUnitMatchInfo(String unitCode, String matchCode);

    /**
     * 根据MatchCodes 查询赛事列表
     * 
     * @param matchCodes
     * @return
     */
    public List<SpMatchModel> getMatchsByMatchCodes(List<String> matchCodes);

    /**
     * 
     *   获取赛事信息
     * @param matchCode
     * @return
     */
    MatchDetailModel getMatchDetailModel(String matchCode);

    /**
    * 返回所有有效的赛事
    * 
    * @return
    */
    List<SpMatchModel> getEnableSpMatchs();

    /**
     *获取赛事项(模板)信息
     *注: 该接口不能获取到实体赛事项编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param eventCode 项(模板)编号
     * @return
     */
    public SpGroupItemModel getEventItem(String eventCode);

    /**
     * 获取赛事组(模板)信息
     *注: 该接口不能获取到实体分组编号
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param groupCode  组(模板)编号
     * @return
     */
    public SpMatchGroupModel getGroupModel(String groupCode);

    /**
     * 获取所有的赛场信息
     * 
     * @return
     */
    List<MatchAreasModel> getMatchAreas();

}
