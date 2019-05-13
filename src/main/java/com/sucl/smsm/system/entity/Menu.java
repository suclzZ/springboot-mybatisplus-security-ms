package com.sucl.smsm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sucl.smsm.core.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author sucl
 * @since 2019-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
//@Accessors(chain = true)
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.UUID)
    private String menuId;

    private String menuName;

    private Menu parentMenu;

    private String path;

    private String style;

    private String leaf;


}
