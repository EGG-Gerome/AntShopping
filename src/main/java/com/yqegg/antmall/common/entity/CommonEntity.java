package com.yqegg.antmall.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据库公共实体类
 * 业务类只需要继承CommonEntity一下就好了，这些字段都不用写
 * 并且这样可以保证id,创建时间，修改时间，删除时间都是一样的，便于维护
 */
@Data
public class CommonEntity {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic(delval = "id")
    private Long deleteFlag;
}
