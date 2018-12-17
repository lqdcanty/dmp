package com.efida.sports.dmp.service.score;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.efida.sport.dmp.facade.model.ScoreGridData;
import com.efida.sport.dmp.facade.model.ScoreHeader;
import com.efida.sport.dmp.facade.model.SoreExtPro;
import com.efida.sports.dmp.dao.entity.CompetitionEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreEntity;
import com.efida.sports.dmp.dao.entity.OpenScoreRankEntity;

import cn.evake.auth.usermodel.PagingResult;
import cn.evake.auth.usermodel.UserInfoModel;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang yi
 * @since 2018-07-28
 */
public interface CompetitionService extends IService<CompetitionEntity> {
    /**
     * 根据比赛赛事编号和电话号码获取比赛列表
     * 电话号码不为空 ：查询当前号码参加比赛
     * 
     * @param matchCode
     * @param phoneNum
     * @return
     */
    List<CompetitionEntity> selectCompetitions(String matchCode, String phoneNum);

    /**
     * 根据赛事编号获取比赛列表
     * 
     * @param matchCode
     * @return
     */
    List<CompetitionEntity> selectCompetitionByMatchCode(String matchCode);

    /**
     * 根据赛事code分页查询比赛
     */
    PagingResult<CompetitionEntity> selectPageCompetitionByMatchCode(String matchCode,
                                                                     int currentPage, int pageSize);

    /**
     * 
     * 根据比赛编号和电话号码查询比赛时间
     * @param competitionCode
     * @param phoneNum
     * @return
     */
    List<Date> queyCompetitionDate(String competitionCode, String phoneNum);

    /**
     * 查询我的比赛排名
     * 
     * @param phoneNum
     * @param competitionDate
     * @param competitionCode
     * @return
     */
    List<OpenScoreRankEntity> getMyRanks(String phoneNum, Date competitionDate,
                                         String competitionCode);

    /**
     * 分页查询某个比赛项排名数据
     * 
     * @param competitionCode
     * @param competitionDate
     * @param phoneNum
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<OpenScoreRankEntity> queryRankByCompetition(String competitionCode,
                                                             Date competitionDate, String phoneNum,
                                                             int currentPage, int pageSize);

    /**
     * 
     * 获取扩展属性
     * @param rankCode    排名编号
     * @param competitionCode  比赛编号
     * @return
     */
    @Deprecated
    List<SoreExtPro> getExtPro(String rankCode, String competitionCode);

    CompetitionEntity getCompetitionByCode(String competitionCode);

    void editCompetition(CompetitionEntity entity, UserInfoModel userInfo);

    List<ScoreHeader> getHeaderInfo(String rankCode, String competitionCode);

    ScoreGridData getScoreGridData(String rankCode, String competitionCode);

    void deleteByCode(String competitionCode);

    /**
     * 分页查询比赛配置中赛事编号
     * 
     * @param gameCode
     * @param phoneNum
     * @param currentPage
     * @param pageSize
     * @return
     */
    PagingResult<String> queryMatchCodes(String gameCode, String phoneNum, int currentPage,
                                         int pageSize);

    /**
     * 查询我最好的成绩
     * rank表中查询我排名最好的成绩 ，如果查询返回结果为空
     * 从成绩表中查询，按score到序查询一条记录，如果score_type 为big 表示到序第一条为最好成绩
     * 如果score_type 不为big按score正序查询一条记录，改记录为最好成绩
     * @param phoneNum 电话号码 （必传）
     * @param competitionDate   比赛时间
     * @param competitionCode   比赛编号（必传）
     * @return
     */
    OpenScoreEntity getRegisterBestScore(String phoneNum, Date competitionDate,
                                         String competitionCode);

    /**
     * 获取用户成绩数据
     * 
     * @param phoneNum
     * @param competitionDate
     * @param competitionCode
     * @return
     */
    List<OpenScoreEntity> queryRegisterScores(String phoneNum, Date competitionDate,
                                              String competitionCode);

    /**
     * 分页获取用户成绩数据
     * 
     * @param phoneNum 电话号码 
     * @param competitionDate   比赛时间
     * @param competitionCode   比赛编号
     * @param currentPage       当前页数
     * @param pageSize          每页大小
     * @return
     */
    PagingResult<OpenScoreEntity> queryRegisterScores(String phoneNum, Date competitionDate,
                                                      String competitionCode, int currentPage,
                                                      int pageSize);

    /**
    * 根据比赛名称查询赛事编码
    */
    PagingResult<String> queryMatchCodesByName(String keyword,int currentPage,
                                               int pageSize);

    /**
     * 锁定比赛配置
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param isShow
     * @return
     */
    boolean lockCompetition(String competitionCode, Integer isShow);

    /**
     * 查询比赛成绩分页
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param competitionCode
     * @param matchCode
     * @param groups
     * @param areas
     * @param events
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PagingResult<OpenScoreRankEntity> queryCompetitionScoreRank(String competitionCode,
                                                                String matchCode,
                                                                List<String> groups,
                                                                List<String> areas,
                                                                List<String> events,
                                                                Integer pageNumber,
                                                                Integer pageSize);

    /**
     * 刷新比赛排名数据
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param competitionCode
     * @return
     */
    boolean refreshRankCompetition(String competitionCode);

    /**
     * 查询需要排名的比赛 
     * 
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<CompetitionEntity> queryNeedRankingCompetition(Integer pageNumber, Integer pageSize);

    /**
     * 更新最近一次排名时间
     * 
     * @param cmp
     * @return 
     */
    boolean updateLastRankingTime(CompetitionEntity cmp);

    PagingResult<OpenScoreRankEntity> queryPageRankByCompetition(String competitionCode,
                                                                 String phoneNum, int currentPage,
                                                                 int pageSize);

    /**
     * 根据排名编号查询比赛
     *
     * @param scoreRankCode
     * @return
     */
    String selectNameByRankCode(String scoreRankCode);
}
