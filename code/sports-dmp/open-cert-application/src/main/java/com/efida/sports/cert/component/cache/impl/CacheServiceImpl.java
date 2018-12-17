package com.efida.sports.cert.component.cache.impl;

/**
 * 
 * Copyright (c) 2004-2018 All Rights Reserved.
 */

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.efida.sports.cert.component.cache.CacheService;
import com.efida.sports.cert.utils.RedisUtil;

/**
 * 
 * 缓存服务
 * @author wang yi
 * @desc 
 * @version $Id: CacheServiceImpl.java, v 0.1 2018年7月26日 下午7:02:24 wang yi Exp $
 */
@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Logger                        log = LoggerFactory.getLogger(getClass());

    @Override
    public String get(final String k) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] data = connection.get(RedisUtil.convert(k));
                if (data == null) {
                    return null;
                }
                return new String(data);
            }
        });
    }

    /** 
     * @see cn.evake.auth.service.common.efida.dci.module.cache.CacheService#getObj(java.lang.String)
     */
    @Override
    public Object getObj(final String k) {
        Object data = null;
        try {
            data = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] data = connection.get(RedisUtil.convert(k));
                    if (data == null) {
                        return null;
                    }
                    try {
                        return RedisUtil.deSeialize(data);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("get data from redis encounter an error ", e);
        }
        return data;
    }

    /** 
     * @see cn.evake.auth.service.common.efida.common.udfs.module.cache.CacheService#remove(java.lang.String)
     */
    @Override
    public void remove(final String k) {
        redisTemplate.expire(k, -1, TimeUnit.MILLISECONDS);
    }

    /** 
     * @see cn.evake.auth.service.common.efida.common.udfs.module.cache.CacheService#put(java.lang.String, java.lang.String, int)
     */
    @Override
    public boolean put(final String k, final String v, final int expireTime) {
        boolean res = false;
        try {
            res = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.pSetEx(RedisUtil.convert(k), expireTime * 1000,
                        RedisUtil.convert(v));
                    return true;
                }
            });
        } catch (Exception e) {
            log.error("save data to redis encounter an error ", e);
        }
        return res;
    }

    /** 
     * @see cn.evake.auth.service.common.efida.dci.module.cache.CacheService#putObj(java.lang.String, java.lang.String, int)
     */
    @Override
    public boolean putObj(final String k, final Object v, final int expireTime) {
        boolean res = false;
        try {
            res = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        connection.pSetEx(RedisUtil.convert(k), expireTime * 1000,
                            RedisUtil.serialize(v));
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            log.error("save data to redis encounter an error ", e);
        }
        return res;
    }

    /**
     * 
     * @see cn.evake.auth.service.common.CacheService#putObjEmptyWipe(java.lang.String, java.lang.Object, int, int)
     */
    @Override
    public boolean putObjEmptyWipe(String k, Object v, int expireTime, int wipe) {
        boolean res = false;
        try {
            res = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    if (v == null) {
                        connection.pSetEx(RedisUtil.convert(k), wipe, RedisUtil.serialize(v));
                    } else {
                        connection.pSetEx(RedisUtil.convert(k), expireTime, RedisUtil.serialize(v));
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            log.error("save data to redis encounter an error ", e);
        }
        return res;
    }

    /** 
     * @see cn.evake.auth.service.common.efida.common.udfs.module.cache.CacheService#put(java.lang.String, java.lang.String)
     */
    @Override
    public void put(String k, String v) {
        put(k, v, 1000);
    }

    @Override
    public boolean expire(final String key, final int expireTime) {
        boolean res = false;
        try {
            res = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        connection.expire(RedisUtil.convert(key), expireTime);
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            log.error("save data to redis encounter an error ", e);
        }
        return res;
    }

    @Override
    public boolean putByte(String k, byte[] v, int expireTime) {
        boolean res = false;
        try {
            res = redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    try {
                        connection.pSetEx(RedisUtil.convert(k), expireTime * 1000, v);
                        return true;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        } catch (Exception e) {
            log.error("save data to redis encounter an error ", e);
        }
        return res;
    }

    /**
     * 
     * @see cn.evake.auth.service.common.CacheService#getByte(java.lang.String)
     */
    @Override
    public byte[] getByte(String k) {
        byte[] data = null;
        try {
            data = redisTemplate.execute(new RedisCallback<byte[]>() {
                @Override
                public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                    byte[] datare = connection.get(RedisUtil.convert(k));
                    if (datare == null) {
                        return null;
                    }
                    try {
                        return datare;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (Exception e) {
            log.error("get data from redis encounter an error ", e);
        }
        return data;
    }
}
