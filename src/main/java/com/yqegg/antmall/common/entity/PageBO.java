package com.yqegg.antmall.common.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

// 这个是请求的实体，分页请求都会指定这两个（Business Object）
// 在分页的业务类里面只需要去继承一下这个就可以了
@Data
@Schema(description = "分页请求基础BO（所有分页请求类需继承）") // 可选：添加类级别的描述
public class PageBO {
    @Schema(description = "当前页", defaultValue = "0", example = "1") // 对应原 @ApiModelProperty 的属性
    private Long currentPage = 0L;

    @Schema(description = "每页个数", defaultValue = "10", example = "20")
    private Long pageSize = 10L;

}
