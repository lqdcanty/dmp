package cn.evake.auth.dao.mapper;

import cn.evake.auth.dao.model.SysDocument;
import cn.evake.auth.dao.model.SysDocumentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDocumentMapper {
    int countByExample(SysDocumentExample example);

    int deleteByExample(SysDocumentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysDocument record);

    int insertSelective(SysDocument record);

    List<SysDocument> selectByExampleWithBLOBs(SysDocumentExample example);

    List<SysDocument> selectByExample(SysDocumentExample example);

    SysDocument selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysDocument record, @Param("example") SysDocumentExample example);

    int updateByExampleWithBLOBs(@Param("record") SysDocument record, @Param("example") SysDocumentExample example);

    int updateByExample(@Param("record") SysDocument record, @Param("example") SysDocumentExample example);

    int updateByPrimaryKeySelective(SysDocument record);

    int updateByPrimaryKeyWithBLOBs(SysDocument record);

    int updateByPrimaryKey(SysDocument record);
}