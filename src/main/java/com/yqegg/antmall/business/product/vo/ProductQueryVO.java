package com.yqegg.antmall.business.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductQueryVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "商品名字")
    private String name;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品单价")
    private BigDecimal unitPrice;

    @Schema(description = "库存数量")
    private Integer stockQuantity;

    @Schema(description = "创建时间", example = "2025-12-02 10:30:00")
    private LocalDateTime createTime;

    @Schema(description = "修改时间", example = "2025-12-02 15:45:00")
    private LocalDateTime updateTime;
}
