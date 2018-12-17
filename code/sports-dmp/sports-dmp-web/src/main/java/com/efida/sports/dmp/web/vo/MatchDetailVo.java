package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.efida.sport.admin.facade.model.open.MatchDetailModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 赛事信息Vo
 * @author wang yi
 * @desc 
 * @version $Id: MatchDetailVo.java, v 0.1 2018年7月18日 下午3:19:56 wang yi Exp $
 */
@ApiModel(value = "赛事详细信")
public class MatchDetailVo {

    @ApiModelProperty(value = "项目编号")
    private String            gameCode;

    @ApiModelProperty(value = "赛事编号")
    private String            matchCode;

    @ApiModelProperty(value = "赛事名称")
    private String            matchName;

    @ApiModelProperty(value = "赛事状态(0 未开始  1 进行中 2 已结束)")
    private int               matchStatus;

    @ApiModelProperty(value = "赛事项)")
    private List<MatchItemVo> matchItemVos;

    public static List<MatchDetailVo> coverToVos(List<MatchDetailModel> matchs) {
        List<MatchDetailVo> results = new ArrayList<MatchDetailVo>();
        if (CollectionUtils.isEmpty(matchs)) {
            return results;
        }
        for (MatchDetailModel matchDetailModel : matchs) {
            results.add(coverToVo(matchDetailModel));
        }
        return results;
    }

    public static MatchDetailVo coverToVo(MatchDetailModel matchDetailModel) {
        MatchDetailVo detailModel = new MatchDetailVo();
        detailModel.setGameCode(matchDetailModel.getGameCode());
        detailModel.setMatchCode(matchDetailModel.getMatchCode());
        detailModel.setMatchName(matchDetailModel.getMatchName());
        detailModel.setMatchStatus(matchDetailModel.getMatchStatus());
        detailModel.setMatchItemVos(MatchItemVo.toItemVos(matchDetailModel));
        return detailModel;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public int getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(int matchStatus) {
        this.matchStatus = matchStatus;
    }

    public List<MatchItemVo> getMatchItemVos() {
        return matchItemVos;
    }

    public void setMatchItemVos(List<MatchItemVo> matchItemVos) {
        this.matchItemVos = matchItemVos;
    }

}