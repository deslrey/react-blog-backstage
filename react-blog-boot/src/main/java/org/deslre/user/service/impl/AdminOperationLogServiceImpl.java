package org.deslre.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.deslre.user.entity.po.AdminOperationLog;
import org.deslre.user.mapper.AdminOperationLogMapper;
import org.deslre.user.service.AdminOperationLogService;
import org.springframework.stereotype.Service;

/**
 * ClassName: AdminOperationLogServiceImpl
 * Description: 后台操作日志接口实现类
 * Author: Deslrey
 * Date: 2025-06-11 9:14
 * Version: 1.0
 */
@Service
public class AdminOperationLogServiceImpl extends ServiceImpl<AdminOperationLogMapper, AdminOperationLog> implements AdminOperationLogService {

}
