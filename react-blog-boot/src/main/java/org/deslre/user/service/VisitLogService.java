package org.deslre.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.deslre.commons.result.Results;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.user.entity.po.VisitCount;
import org.deslre.user.entity.po.VisitLog;
import org.deslre.user.entity.vo.VisitLogVO;

import java.util.List;

/**
 * ClassName: VisitLogService
 * Description: 访客日志服务类
 * Author: Deslrey
 * Date: 2025-06-02 22:38
 * Version: 1.0
 */
public interface VisitLogService extends IService<VisitLog> {

    Results<List<VisitLogVO>> visitLogList();

    Results<Boolean> updateExist(Integer id, Boolean exist);

    Results<List<ArticleViewDTO>> getDailyTop5Articles();

    Results<List<VisitCount>> getLast5DaysVisitCount();
}
