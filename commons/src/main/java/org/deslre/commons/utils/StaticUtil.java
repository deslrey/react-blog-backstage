package org.deslre.commons.utils;

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

    //  文件映射路径
    public static final String FILE_PATH = "E:\\staticSource\\";

    //    静态资源路径
    public static final String RESOURCE_URL = "http://localhost:8080/deslre/staticSource/";
    //    静态资源路径
    public static final String RESOURCE_DEFAULT = "http://localhost:8080/deslre/staticSource/default/";
    //    图片url
    public static final String RESOURCE_URL_IMAGE = "http://localhost:8080/deslre/staticSource/image/";
    //    图片存储路径
    public static final String RESOURCE_IMAGE = FILE_PATH + "image\\";

    //    md文件的url
    public static final String RESOURCE_URL_MD = "http://localhost:8080/deslre/staticSource/md/";
    //    md文件存储路径
    public static final String RESOURCE_MD = FILE_PATH + "md\\";
    //  默认封面
    public static final String DEFAULT_COVER = RESOURCE_DEFAULT + "cover.webp";
    //默认头像
    public static final String DEFAULT_AVATAR = RESOURCE_URL + "avatar.jpg";
    //  默认标题
    public static final String DEFAULT_TITLE = "Deslrey的博客";
    //  默认作者
    public static final String DEFAULT_AUTHOR = "Deslrey";
    //  默认描述
    public static final String DEFAULT_DESCRIPTION = "Deslrey的个人博客，记录生活中的点滴，分享编程的乐趣。";


}
