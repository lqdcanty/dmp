package com.efida.sports.dmp.web.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.efida.sport.admin.facade.model.SpMatchModel;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: MatchVo.java, v 0.1 2018年8月31日 下午10:16:56 zengbo Exp $
 */
public class MatchVo {

    @ApiModelProperty(value = "赛事编号")
    private String  matchCode;

    @ApiModelProperty(value = "赛事名称")
    private String  matchName;

    @ApiModelProperty(value = "是不是top数据")
    private boolean isTop = false;

    public static List<MatchVo> coverVos(List<SpMatchModel> spMatchModels,
                                         List<String> matchCodes) {
        List<MatchVo> vos = new ArrayList<MatchVo>();
        if (CollectionUtils.isEmpty(spMatchModels)) {
            return null;
        }
        for (SpMatchModel spMatchModel : spMatchModels) {
            vos.add(coverVo(spMatchModel, matchCodes));
        }
        return vos;
    }

    public static MatchVo coverVo(SpMatchModel matchEntity, List<String> matchCodes) {
        if (matchEntity == null) {
            return null;
        }
        MatchVo matchVo = new MatchVo();
        BeanUtils.copyProperties(matchEntity, matchVo);
        if (matchCodes != null && matchCodes.size() > 0) {
            matchVo.setTop(matchCodes.contains(matchEntity.getMatchCode()));

        }
        return matchVo;
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

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean isTop) {
        this.isTop = isTop;
    }

}
