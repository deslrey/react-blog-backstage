package org.deslre.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.deslre.user.entity.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author author
 * @since 2025-04-24
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectBySql(@Param("userId") Integer userId);

}
