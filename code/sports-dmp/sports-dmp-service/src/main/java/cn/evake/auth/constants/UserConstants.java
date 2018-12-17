/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.constants;

/**
 * 
 * @author Evance
 * @version $Id: UserConstants.java, v 0.1 2018年3月10日 下午5:15:04 Evance Exp $
 */
public class UserConstants {

    public static final String defaultAvatar   = "http://img.zcool.cn/community/0177b355ed01bc6ac7251df8f6be5a.png@1280w_1l_2o_100sh.jpg";

    public static final String USER_TOKEN_KEY  = "auth_token";

    public static final String USER_UUID       = "user_uuid";

    public static final String USER_NAME       = "user_name";

    public static final int    REDIS_TIMEOUT   = 2 * 60 * 60;

    public static final int    COOKIES_TIMEOUT = 2 * 24 * 60 * 60;

    public static final String USER_PREFIX     = "US";
}
