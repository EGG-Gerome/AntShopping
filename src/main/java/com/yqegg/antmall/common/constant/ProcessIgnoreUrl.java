package com.yqegg.antmall.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ProcessIgnoreUrl {
    List<String> KNIFE4J = Arrays.asList(
            // 这些是不处理的一些URL（因为这几个是Knife4jConfig.java的URL）
            // 项目启动后这个是作为文档来展示的）
            "/doc.html",
            "/swagger-resources",
            "/swagger-resources/configuration",
            "/v3/api-docs/**",
            "/v2/api-docs/**",
            "/webjars/**");

    List<String> ALL = new ArrayList<>(KNIFE4J);
}
