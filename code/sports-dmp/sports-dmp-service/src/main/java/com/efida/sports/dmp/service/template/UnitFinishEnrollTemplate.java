package com.efida.sports.dmp.service.template;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.evake.excel.annotation.ExcelAttribute;

/**
 * 
 * 
 * @author zengbo
 * @version $Id: UnitFinishEnrollTemplate.java, v 0.1 2018年10月10日 下午7:38:01 zengbo Exp $
 */
public class UnitFinishEnrollTemplate {

    @ExcelAttribute(column = "A", name = "项目方名称")
    private String unitName;

    @ExcelAttribute(column = "B", name = "报名人次")
    private int    enrollTotal;

    @ExcelAttribute(column = "C", name = "完善人次")
    private int    finishTotal;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getEnrollTotal() {
        return enrollTotal;
    }

    public void setEnrollTotal(int enrollTotal) {
        this.enrollTotal = enrollTotal;
    }

    public int getFinishTotal() {
        return finishTotal;
    }

    public void setFinishTotal(int finishTotal) {
        this.finishTotal = finishTotal;
    }

    public static List<UnitFinishEnrollTemplate> increaseRegisters(List<Map<String, Object>> matchs) {
        ArrayList<UnitFinishEnrollTemplate> list = new ArrayList<UnitFinishEnrollTemplate>();
        if (matchs == null || matchs.size() < 1) {
            return list;
        }
        for (Map<String, Object> match : matchs) {
            UnitFinishEnrollTemplate temp = new UnitFinishEnrollTemplate();
            String unitName = (String) match.get("unitName");
            BigDecimal enrollTotal = (BigDecimal) match.get("enrollCount");
            int finishTotal = (int) match.get("finshCount");
            temp.setUnitName(unitName);
            temp.setEnrollTotal(enrollTotal.intValue());
            temp.setFinishTotal(finishTotal);
            list.add(temp);
        }
        return list;
    }

}
