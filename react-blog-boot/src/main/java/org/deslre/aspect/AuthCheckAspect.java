package org.deslre.aspect;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.deslre.annotation.AuthCheck;
import org.deslre.result.ResultCodeEnum;
import org.deslre.utils.StaticUtil;
import org.deslre.utils.StringUtils;
import org.deslre.exception.DeslreException;
import org.deslre.user.entity.po.AdminOperationLog;
import org.deslre.user.entity.po.Region;
import org.deslre.user.entity.po.User;
import org.deslre.user.entity.po.VisitorInfo;
import org.deslre.user.service.AdminOperationLogService;
import org.deslre.user.service.UsersService;
import org.deslre.utils.Constants;
import org.deslre.utils.RedisUtil;
import org.deslre.utils.RegexUtils;
import org.deslre.utils.VisitorUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: AuthCheckAspect
 * Description: 解析AuthCheck注解
 * Author: Deslrey
 * Date: 2025-06-11 8:49
 * Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class AuthCheckAspect {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UsersService usersService;
    @Resource
    private AdminOperationLogService adminOperationLogService;

    @Pointcut("@annotation(org.deslre.annotation.AuthCheck)")
    public void authCheckPointcut() {
    }

    @Around("authCheckPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuthCheck authCheck = method.getAnnotation(AuthCheck.class);
        return handleAuth(joinPoint, authCheck);
    }


    public Object handleAuth(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        HttpServletRequest request = null;
        String email = null;
        String userName = null;
        boolean admin;

        // 解析参数
        for (int i = 0; i < paramNames.length; i++) {
            Object arg = args[i];
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
        }

        if (request != null) {
            email = request.getHeader("x-deslre-email");
            String adminStr = request.getHeader("x-deslre-admin");
            userName = request.getHeader("x-deslre-userName");
            admin = "true".equalsIgnoreCase(adminStr) || "1".equals(adminStr);
//            System.out.println("前端传来的用户信息: email=" + email + ", admin=" + admin + ", userName=" + userName);
        } else {
            throw new DeslreException(ResultCodeEnum.CODE_500);
        }


        // 登录校验逻辑
        if (authCheck.checkLogin()) {
            String loginEmail = redisUtil.get(Constants.LOGIN_EMAIL + email);
            if (StringUtils.isEmpty(loginEmail))
                throw new DeslreException(ResultCodeEnum.LOGIN_AUTH);
            redisUtil.setEx(Constants.LOGIN_EMAIL + email, "login", Constants.LENGTH_30, TimeUnit.MINUTES);
        }

        // 管理员校验逻辑
        if (authCheck.admin()) {
            boolean isAdmin = checkAdmin(email);
            if (!isAdmin) {
                throw new DeslreException(ResultCodeEnum.PERMISSION);
            }
        }

        // 日志记录
        String log = authCheck.log();
        if (StringUtils.isNotEmpty(log)) {
            AdminOperationLog operationLog = new AdminOperationLog();
            VisitorInfo visitorInfo = VisitorUtil.buildVisitorInfo(request);
            Region region = visitorInfo.getRegion();
            operationLog.setOperatorEmail(email);
            operationLog.setOperatorUser(userName);
            operationLog.setOperatorName(authCheck.category());
            operationLog.setOperation(log);
            operationLog.setOperationIp(visitorInfo.getIp());
            operationLog.setPlatform(visitorInfo.getPlatform());
            operationLog.setDevice(visitorInfo.getDevice());
            operationLog.setBrowser(visitorInfo.getBrowser());
            operationLog.setCountry(region.getCountry() != null ? region.getCountry() : "未知");
            operationLog.setProvince(region.getProvince() != null ? region.getProvince() : "未知");
            operationLog.setCity(region.getCity() != null ? region.getCity() : "未知");
            operationLog.setOperationTime(LocalDateTime.now());
            operationLog.setExist(StaticUtil.TRUE);
//            System.out.println("operationLog ======>  " + operationLog);
            adminOperationLogService.save(operationLog);
        }
        return joinPoint.proceed();
    }

    private boolean checkAdmin(String email) {
        if (StringUtils.isEmpty(email) || !RegexUtils.checkEmail(email)) {
            log.error("验证管理员传入的邮箱异常 ======> {}", email);
            throw new DeslreException(ResultCodeEnum.CODE_500);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getEmail, email);
        User user = usersService.getOne(queryWrapper);
        if (user == null || !user.getExist()) {
            log.error("验证管理员身份不存在 ======> {}", email);
            throw new DeslreException(ResultCodeEnum.PERMISSION);
        }
        return user.getAdmin();
    }
}



