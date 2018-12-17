/**
 * 
 */
package com.efida.sports.dmp.biz.open;

import java.util.List;

import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;

/**
 * @author zhiyang
 *
 */
public interface EnrollSyncService {

    /**
     * 
     * @param app
     */
    void syncOneEnrollInfo(OpenApplyInfoEntity app);
    /**
     * 
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<OpenApplyInfoEntity> selectUnSyncRecord(int pageNumber, int pageSize);

    public void multTreadSync(int maxNumber);
    /**
     * 
     * @param unitCode
     * @param pageNumber
     * @param pageSize
     * @return
     */
    long multTreadSync2(String unitCode, int pageNumber, int pageSize);
}
