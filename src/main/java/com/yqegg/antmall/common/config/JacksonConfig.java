package com.yqegg.antmall.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilderCustomizer;

import java.math.BigInteger;

@Configuration
public class JacksonConfig {

    @Bean
//    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

    public ObjectMapper jacksonObjectMapper() { // 去掉参数 Jackson2ObjectMapperBuilder builder
        // 原逻辑：ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        // 替换为直接创建 ObjectMapper，等效于 createXmlMapper(false)（不支持 XML 映射）
        ObjectMapper objectMapper = new ObjectMapper();
        // 全局配置序列化
        SimpleModule simpleModule = new SimpleModule();
        // 使用String来序列化Long包装类
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        // 使用String来序列化long基本类型
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 使用String来序列化BigInteger包装类类型。（有人用BigInteger表示大的整数）
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}