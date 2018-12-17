package cn.evake.app.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.evake.app.dao.model.AppInfo;
import cn.evake.app.dao.model.AppInfoExample;

public interface AppInfoMapper {
    int countByExample(AppInfoExample example);

    int deleteByExample(AppInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppInfo record);

    int insertSelective(AppInfo record);

    List<AppInfo> selectByExample(AppInfoExample example);

    AppInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppInfo record,
                                 @Param("example") AppInfoExample example);

    int updateByExample(@Param("record") AppInfo record, @Param("example") AppInfoExample example);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);
}