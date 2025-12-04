package com.yqegg.antmall.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableKnife4j // 保留 Knife4j 启用注解（核心）
public class Knife4jConfig {

    // 替换原来的 Docket，用 OpenAPI3 规范的配置类
    @Bean
    public OpenAPI defaultApi2() {

        return new OpenAPI()
                .info(new Info() // 绑定基本信息
                    .title("我的标题") // 文档标题
                    .description("我的描述") // 文档描述
                    .version("1.0") // 版本号
                    // 联系人信息（替换原来的 Contact）
                    .contact(new Contact()
                            .name("daoren") // 联系人姓名
                            .url("https://xxx.com") // 联系人链接
                            .email("xx@qq.com") // 联系人邮箱
                    )
            )
        // 2. 构建 OpenAPI 实例（对应原来的 Docket）

                .openapi("3.0.0"); // 明确指定 OpenAPI3 版本（必须）
    }

    // 3. （可选）如果需要扫描指定包的 Controller，添加以下配置
    // （Knife4j 默认会扫描所有 @RestController/@Controller 注解，若需精准控制可加）
// 配置包扫描（通过 GroupedOpenApi 实现）
    @Bean
    public GroupedOpenApi controllerGroup() {
        return GroupedOpenApi.builder()
                .group("all") // 分组名称，和原来的保持一致
                .packagesToScan("com.yqegg.antmall") // 你的 Controller 所在包路径
                .build();
    }
}