package cn.evake.auth.dao.mapper;

import cn.evake.auth.dao.model.SysBase;
import cn.evake.auth.dao.model.SysBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysBaseMapper {
	int countByExample(SysBaseExample example);

	int deleteByExample(SysBaseExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(SysBase record);

	int insertSelective(SysBase record);

	List<SysBase> selectByExampleWithBLOBs(SysBaseExample example);

	List<SysBase> selectByExample(SysBaseExample example);

	SysBase selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") SysBase record, @Param("example") SysBaseExample example);

	int updateByExampleWithBLOBs(@Param("record") SysBase record, @Param("example") SysBaseExample example);

	int updateByExample(@Param("record") SysBase record, @Param("example") SysBaseExample example);

	int updateByPrimaryKeySelective(SysBase record);

	int updateSelective(SysBase record);

	int updateByPrimaryKeyWithBLOBs(SysBase record);

	int updateByPrimaryKey(SysBase record);
}