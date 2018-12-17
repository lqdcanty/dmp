package com.efida.sport.open.model;

import com.efida.sport.open.OpenException;

/**
 * 模型通用接口
 * @author zhiyang
 * @version $Id: ICommonModel.java, v 0.1 2016年5月19日 下午8:43:56 zhiyang Exp $
 */
public interface ICommonModel {

    /**
     * 判定模型是否相同, 不相同则跑出异常
     * 
     * @param commonModel
     * @return
     * @throws OpenException
     */
    public void assertEqual(ICommonModel commonModel) throws OpenException;

    /**
     * 验证数据是否有效
     */
    public void validate() throws OpenException;
}
