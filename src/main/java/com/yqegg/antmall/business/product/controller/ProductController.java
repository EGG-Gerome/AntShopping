package com.yqegg.antmall.business.product.controller;

import com.yqegg.antmall.business.product.bo.ProductAddBO;
import com.yqegg.antmall.business.product.bo.ProductEditBO;
import com.yqegg.antmall.business.product.bo.ProductQueryBO;
import com.yqegg.antmall.business.product.service.ProductService;
import com.yqegg.antmall.business.product.vo.ProductQueryVO;
import com.yqegg.antmall.common.entity.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "商品管理")
@RestController
@Validated      // 因为delete方法需要校验idList是否为空，我们写的不是实体类，而是一个列表，所以需要用@Validated校验
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @Operation(summary = "添加")   // 基础说明
//    @PostMapping 是 Spring 的 “复合注解”，等价于 @RequestMapping(method = RequestMethod.POST)，专门用来处理 HTTP POST 请求。
    @PostMapping("add") // 保存和新增一般用 @PostMapping 方法
    // @RequestBody是接受前端给后端数据的，会把JSON转为类，而 @RequestParam 是接受前端给后端数据的，会把类转为JSON
    public void add(@Valid @RequestBody ProductAddBO addBO){    // @Valid 是 “校验触发开关
        // 不是 “Post 方法必须加”，而是 “需要接收 JSON 参数时才加”
        // 前端用 “表单格式”（application/x-www-form-urlencoded）传递参数（比如表单提交），就不加 @RequestBody，直接用参数接收即可
        // 比如public String add(String name, BigDecimal price, Integer stock) { // 无@RequestBody
        productService.add(addBO);
        // Controller的入口
    }

    @Operation(description = "修改")
    @PostMapping("edit")
    public void update(@Valid @RequestBody ProductEditBO editBO){
        productService.edit(editBO);

    }

    @Operation(description = "分页查询")
    @GetMapping("page")
    // 没有校验是否为空的东西，所以不用加@Valid
    // 不用加@RequestBody，因为分页查询是用GET方法，参数是在URL后面的
    public PageVO<ProductQueryVO> page(ProductQueryBO queryBO){
        //     PageVO写了private List<T> dataList;所以这里不用再一次规定泛型<List>
        return productService.page(queryBO);
    }

    @Operation(description = "删除")
    @PostMapping("delete")
    // @NotEmpty 是校验idList是否为空，如果没有这个注解，就算前端传了空列表，也不会报错，更不会起到数据校验的作用
    public void delete(@NotEmpty @RequestBody List<Long> idList){
        productService.delete(idList);
    }

}
