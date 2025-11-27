package com.yqegg.antmall.common.advice;

import com.yqegg.antmall.common.constant.ProcessIgnoreUrl;
import com.yqegg.antmall.common.entity.ResultWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应体增强器（公共组件）
 * 作用：统一拦截整个项目中Controller层的「正常响应结果」，自动包装成标准格式（ResultWrapper）
 * 避免每个接口都手动写 ResultWrapper.success().data(xxx)，减少重复代码，保证响应格式统一
 *
 * 关键说明：
 * 1. @ControllerAdvice：和全局异常处理器一样，是Spring的全局增强注解，拦截所有Controller的响应
 * 2. 实现 ResponseBodyAdvice<Object> 接口：Spring提供的“响应体处理接口”，专门用于修改Controller的返回结果
 * 3. 与 GlobalExceptionAdvice 的区别：
 *    - 本类：处理「正常返回的结果」（比如接口成功返回数据），包装成统一格式
 *    - 全局异常处理器：处理「接口抛出的异常」，包装成统一错误格式
 * 两者配合，实现了“成功响应”和“失败响应”的全场景统一格式
 */
@Slf4j
@ControllerAdvice   // 全局增强，拦截所有Controller的响应
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 前置判断方法：决定当前请求的响应是否需要进入 beforeBodyWrite 方法处理
     * 简单说：返回true = 需要增强（包装结果），返回false = 跳过（不处理）
     *
     * @param methodParameter 当前请求的方法参数信息（比如Controller的方法签名）
     * @param converterType   用于处理响应的消息转换器类型（比如JSON转换器）
     * @return true=处理，false=不处理
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        // 注释掉的逻辑：如果Controller方法本身返回的就是ResultWrapper，就不需要再包装了（避免重复包装）
        // return !methodParameter.getParameterType().equals(ResultWrapper.class);

        // 当前逻辑：所有响应都进入处理（后续在beforeBodyWrite里再做细分判断）
        return true;
    }

    @Override
    @ResponseBody   // 确保返回值会被转为JSON格式（和@RestController里的@ResponseBody作用一致）
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // 1. 设置响应头：明确告诉前端，响应格式是JSON（避免前端解析格式混乱）
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        // 2. 特殊处理：如果原始返回值是String类型（直接转JSON会报错，需要手动序列化）
        if (body instanceof String) {
            // 若返回值为String类型，需要包装为String类型返回。否则会报错
            try {
                // 创建Jackson的JSON序列化工具
                ObjectMapper objectMapper = new ObjectMapper();
                // 把String包装成ResultWrapper（成功状态+数据）
                ResultWrapper<Object> resultWrapper = ResultWrapper.success().data(body);
                // 手动把ResultWrapper序列化成String返回（否则Spring会用字符串转换器，导致格式错误）
                return objectMapper.writeValueAsString(resultWrapper);
            } catch (JsonProcessingException e) {
                // 序列化失败时抛出运行时异常（实际项目可优化为返回统一错误提示）
                throw new RuntimeException("序列化String错误");
            }
            // 3. 如果原始返回值已经是ResultWrapper（比如某些接口手动包装过），直接返回，避免重复包装
        } else if (body instanceof ResultWrapper) {
            return body;
            // 4. 特殊场景：如果是接口文档（Knife4j）的请求地址，直接返回原始结果（不包装，否则文档无法正常显示）
        } else if (isKnife4jUrl(request.getURI().getPath())) {
            // 如果是接口文档uri，直接跳过
            return body;
        }
        // 5. 通用场景：把所有其他类型的返回值（User、List、Map等）包装成ResultWrapper成功格式
        // 最终返回给前端的JSON格式：{"code":200,"message":"成功","data": 原始数据}
        return ResultWrapper.success().data(body);
    }


    /**
     * 辅助方法：判断当前请求地址是否是Knife4j接口文档的地址
     * Knife4j是接口文档工具（类似Swagger），其自带的接口需要返回原始格式，不能包装
     *
     * @param uri 当前请求的路径（比如"/doc.html"、"/v3/api-docs"）
     * @return true=是接口文档地址，false=普通业务接口地址
     */
    private boolean isKnife4jUrl(String uri) {
        // AntPathMatcher：Spring提供的路径匹配工具，支持通配符（比如"/v3/api-docs/**"）
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String s : ProcessIgnoreUrl.KNIFE4J) {
            // ProcessIgnoreUrl.KNIFE4J：配置文件中定义的Knife4j相关路径集合（比如"/doc.html"、"/v3/api-docs/**"）
            if (pathMatcher.match(s, uri)) {
                return true;
            }
        }
        return false;
    }
}