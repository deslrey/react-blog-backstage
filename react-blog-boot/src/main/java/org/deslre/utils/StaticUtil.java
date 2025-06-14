package org.deslre.utils;

import java.io.File;

/**
 * ClassName: StaticUtil
 * Description: 状态工具类
 * Author: Deslrey
 * Date: 2025-04-16 11:17
 * Version: 1.0
 */
public class StaticUtil {

    public static final Boolean TRUE = true;
    public static final Boolean FALSE = false;

    public static final String SUPER_ADMIN = "super_admin";
    public static final String ADMIN = "admin";
    public static final String USER = "user";


    //    文件映射路径
//    public static final String FILE_PATH = "/opt/staticSource";
//    @Value("${custom.static-source-path}")
    public static String FILE_PATH = "E:\\staticSource\\";

    //    静态资源路径
    public static final String RESOURCE_URL = "http://localhost:8080/deslre/staticSource/";
    //    静态资源路径
    public static final String RESOURCE_DEFAULT = "http://localhost:8080/deslre/staticSource/default/";
    //    图片url
    public static final String RESOURCE_URL_IMAGE = "http://localhost:8080/deslre/staticSource/image/";
    //    图片存储路径
    public static final String RESOURCE_IMAGE = FILE_PATH + "image" + File.separator;
    //    草稿图片url
    public static final String RESOURCE_DRAFT = RESOURCE_URL + "draft" + File.separator;
    //    草稿封面存储路径
    public static final String RESOURCE_DRAFT_PATH = FILE_PATH + "draft";
    //    头像保存路径
    public static final String RESOURCE_AVATAR = FILE_PATH + "avatar" + File.separator;
    //    头像url
    public static final String RESOURCE_AVATAR_URL = RESOURCE_URL + "avatar/";

    //    md文件的url
    public static final String RESOURCE_URL_MD = RESOURCE_URL + "md/";
    //    md文件存储路径
    public static final String RESOURCE_MD = FILE_PATH + "md" + File.separator;
    //    默认封面
    public static final String DEFAULT_COVER = RESOURCE_DEFAULT + "cover.webp";
    //    默认头像
    public static final String DEFAULT_AVATAR = RESOURCE_URL + "avatar.jpg";
    //    默认标题
    public static final String DEFAULT_TITLE = "Deslrey的博客";
    //    默认作者
    public static final String DEFAULT_AUTHOR = "Deslrey";
    //    默认描述
    public static final String DEFAULT_DESCRIPTION = "Deslrey的个人博客，记录生活中的点滴，分享编程的乐趣。";

    /*              Redis 相关                */

    private static final String ARTICLE = "article:";
    //    访客量
    public static final String UNIQUE_VISITORS = ARTICLE + "uniqueVisitors:";

    //    访问量
    public static final String PAGE_VIEWS = ARTICLE + "pageViews:";

}