package com.sucl.smsm.system.mapper;

import com.sucl.smsm.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sucl
 * @since 2019-05-10
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectWithParent();

    Menu selectWithParentByPath(@Param("path") String path);

}
