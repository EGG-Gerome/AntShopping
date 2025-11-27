package com.yqegg.antmall.common.entity;

//import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

// 这个是分页响应的实体类（VO: Value Object）
@Data
@Schema(description = "分页响应通用VO（封装分页元数据和实际数据列表）") // 类级别描述，可选但推荐
public class PageVO<T> {
    @Schema(description = "当前页", defaultValue = "0", example = "1")
    private Long currentPage = 0L;

    @Schema(description = "每页个数", defaultValue = "10", example = "20") // 替换@ApiModelProperty，同步默认值
    private Long pageSize = 10L;

    @Schema(description = "总个数", defaultValue = "0", example = "100") // 替换@ApiModelProperty，补充默认值和示例
    private Long totalSize = 0L;

    // 这个是实际的数据，用泛型来定
    @Schema(description = "分页查询的实际数据列表", example = "[]")
    private List<T> dataList;
}
