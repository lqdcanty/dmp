/**
 * evake.cn Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package cn.evake.auth.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

/**
 * 文件工具类
 * @author Evance
 * @version $Id: FileUtil.java, v 0.1 2018年1月1日 下午4:20:10 Evance Exp $
 */
public class FileUtil {

    /**
     *
     * 获取文件后缀名
     * @param fileFullName
     * @return
     */
    public static String getFileSuffix(String fileFullName) {
        String subffix = null;
        if (StringUtils.isBlank(fileFullName)) {
            return subffix;
        }
        if (!fileFullName.contains(".")) {
            return subffix;

        }
        //取到文件后缀
        subffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1);
        return subffix;
    }

    /**
     * 检查文件夹是否存在
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param filePath
     * @return
     */
    public static boolean isDirExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建一个文件夹(存在忽略)
     * @title
     * @methodName
     * @author wangyi
     * @description
     * @param filePath
     */
    public static void forceMkdir(String filePath) {
        if (!isDirExists(filePath)) {
            File file = new File(filePath);
            file.mkdir();
        }
    }

}
