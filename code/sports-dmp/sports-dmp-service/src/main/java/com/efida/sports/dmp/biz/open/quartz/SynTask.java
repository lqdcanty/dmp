/**
 * 
 */
package com.efida.sports.dmp.biz.open.quartz;

import com.efida.sports.dmp.biz.open.EnrollSyncService;
import com.efida.sports.dmp.dao.entity.OpenApplyInfoEntity;

/**
 * @author zhiyang
 *
 */
public class SynTask implements Runnable{

    private OpenApplyInfoEntity data=null;
    private EnrollSyncService syncService;

    public SynTask(OpenApplyInfoEntity item) {
        
        this.data = item;
    }

    @Override
    public void run() {
        
        this.syncService.syncOneEnrollInfo(this.data);
        
    }

    public void setSyncService(EnrollSyncService syncService) {
        
        this.syncService= syncService;
    }

}
