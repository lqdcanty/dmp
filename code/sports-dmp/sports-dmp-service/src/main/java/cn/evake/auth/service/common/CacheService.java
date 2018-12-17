package cn.evake.auth.service.common;

/**
 * 缓存
 * @author zoutao
 * @version $Id: CacheService.java, v 0.1 2018年1月21日 下午2:59:27 zoutao Exp $
 */
public interface CacheService {

    /**
     * 
     * 根据key 获取redis的值
     * 返回String
     * @param k
     * @return
     */
    public String get(final String k);

    /**
     * @title 获取Object
     * @methodName
     * @description
     * @param k
     * @return
     */
    public Object getObj(final String k);

    /**
     * 获取二进制文件
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param k
     * @return
     */
    public byte[] getByte(final String k);

    /**
     * 
     * @title 移除数据
     * @methodName
     * @description
     * @param k
     * @return
     */
    public void remove(final String k);

    /***
     * 
     * 缓存  expireTime 秒对象
     * @title
     * @methodName
     * @description
     * @param k
     * @param v
     * @param expireTime 活动时间 （单位秒）
     */
    public boolean put(final String k, final String v, int expireTime);

    /**
     * 缓存obj
     * @title
     * @methodName
     * @description
     * @param k
     * @param v
     * @param expireTime 活动时间
     */
    public boolean putObj(final String k, final Object v, int expireTime);

    /**
     * 如果空对象
     * 根据wipe时间缓存
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param k
     * @param v
     * @param expireTime 活动时间
     * @param wipe 空数据清除时间
     * @return
     */
    public boolean putObjEmptyWipe(final String k, final Object v, int expireTime, int wipe);

    /**
     * 缓存字节
     * @title
     * @methodName
     * @description
     * @param k
     * @param v
     * @param expireTime 活动时间
     */
    public boolean putByte(final String k, final byte[] v, int expireTime);

    /***
     * @title default expired time 60s
     * @methodName
     * @description
     * @param k
     * @param v
     */
    public void put(final String k, final String v);

    /**
     * 
     * 续存时间
     * @param key
     * @param userRedisTime
     */
    public boolean expire(final String k, final int expireTime);

}
