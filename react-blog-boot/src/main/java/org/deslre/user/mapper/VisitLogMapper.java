package org.deslre.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.deslre.desk.entity.dto.ArticleViewDTO;
import org.deslre.user.entity.po.VisitCount;
import org.deslre.user.entity.po.VisitLog;

import java.util.List;

/**
 * ClassName: VisitLogMapper
 * Description: 访客日志
 * Author: Deslrey
 * Date: 2025-06-02 22:37
 * Version: 1.0
 */
public interface VisitLogMapper extends BaseMapper<VisitLog> {

    //    @Select("""
//               SELECT
//                                                        	title,
//                                                        	pageViews as count\s
//                                                        FROM
//                                                        	(
//                                                        	SELECT
//                                                        		a.title,
//                                                        		DATE ( v.visit_date ) AS visit_date,
//                                                        		COUNT(*) AS pageViews,
//                                                        		@rn :=
//                                                        	IF
//                                                        		( @CURRENT_DATE = DATE ( v.visit_date ), @rn + 1, 1 ) AS rn,
//                                                        		@CURRENT_DATE := DATE ( v.visit_date )\s
//                                                        	FROM
//                                                        		visit_log v
//                                                        		JOIN article a ON v.article_id = a.id,
//                                                        		( SELECT @rn := 0, @CURRENT_DATE := NULL ) vars\s
//                                                        	WHERE
//                                                        		v.article_id IS NOT NULL\s
//                                                        		AND v.exist = 1\s
//                                                        	GROUP BY
//                                                        		DATE ( v.visit_date ),
//                                                        		v.article_id\s
//                                                        	ORDER BY
//                                                        		DATE ( v.visit_date ),
//                                                        		pageViews DESC\s
//                                                        	) AS ranked\s
//                                                        WHERE
//                                                        	rn <= 5\s
//                                                        ORDER BY
//                                                        	visit_date DESC,
//                                                        	pageViews DESC;
//            """)
//    List<VisitCount> getDailyTop5Articles();
    @Select("""
            SELECT
                a.title,
                COUNT(*) AS count
            FROM
                visit_log v
                JOIN article a ON v.article_id = a.id
            WHERE
                v.exist = 1
                AND v.article_id IS NOT NULL
                AND v.visit_time >= NOW() - INTERVAL 1 DAY
            GROUP BY
                v.article_id
            ORDER BY
                count DESC
            LIMIT 5;
                                    """)
    List<VisitCount> getDailyTop5Articles();

    /**
     * 查询最近5天每天的访问量统计
     */
    @Select("SELECT DATE(visit_date) as date, COUNT(*) as count " +
            "FROM visit_log " +
            "WHERE visit_date >= DATE_SUB(CURDATE(), INTERVAL 4 DAY) " +
            "GROUP BY DATE(visit_date) " +
            "ORDER BY date ASC")
    List<VisitCount> selectLast5DaysVisitCount();

    @Select("SELECT province AS title, COUNT(*) AS count FROM visit_log WHERE exist = 1 GROUP BY province")
    List<VisitCount> getVisitCountByProvince();


}