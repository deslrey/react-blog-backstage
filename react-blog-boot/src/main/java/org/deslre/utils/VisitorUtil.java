package org.deslre.utils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.deslre.user.entity.po.Region;
import org.deslre.user.entity.po.VisitorInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName: VisitorUtil
 * Description: 获取访客信息工具类
 * Author: Deslrey
 * Date: 2025-06-03 10:44
 * Version: 1.0
 */
public class VisitorUtil {

    /**
     * 获取封装的访客信息
     */
    public static VisitorInfo buildVisitorInfo(HttpServletRequest request) {
        String ip = getClientIp(request);
        String userAgentString = request.getHeader("User-Agent");

        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();

        String browserName = browser.getName();               // e.g., Chrome
        String platform = os.getName();                       // e.g., Windows 10
        String deviceType = os.getDeviceType().getName();     // e.g., Computer / Mobile

        VisitorInfo visitorInfo = new VisitorInfo();
        visitorInfo.setIp(ip);
        visitorInfo.setPlatform(platform);
        visitorInfo.setBrowser(browserName);
        visitorInfo.setDevice(deviceType);
        Region region = IpAddressUtil.getIpAddressByOnline(ip);
        visitorInfo.setRegion(region);
        return visitorInfo;
    }

    /**
     * 获取客户端 IP，支持代理场景
     */
    public static String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }
        }

        if (request.getRemoteAddr().equals("0:0:0:0:0:0:0:1"))
            return "127.0.0.1";
        else
            return request.getRemoteAddr();
    }

}
