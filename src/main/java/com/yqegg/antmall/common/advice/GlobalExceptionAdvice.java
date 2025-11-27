package com.yqegg.antmall.common.advice;

import com.yqegg.antmall.common.entity.ResultWrapper;
import com.yqegg.antmall.common.exception.BusinessException;
import com.yqegg.antmall.common.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理增强器（公共组件核心类）
 * 作用：统一捕获整个Spring Boot项目中Controller层抛出的所有异常，
 * 避免直接向客户端返回原始错误信息，统一封装返回格式，同时记录异常日志
 *
 * 关键注解说明：
 * 1. @Slf4j：Lombok提供的注解，自动生成日志对象log，用于打印异常日志
 * 2. @RestControllerAdvice：Spring MVC的核心注解，是@ControllerAdvice + @ResponseBody的组合
 *    - @ControllerAdvice：声明这是一个全局增强器，会拦截所有标注了@Controller/@RestController的类
 *    - @ResponseBody：确保所有异常处理方法的返回值会自动转为JSON格式返回给前端
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 通用异常处理器：捕获所有未被特殊处理的Exception（最顶层异常）
     * 适用场景：除了自定义业务异常、空指针异常之外的所有异常（如IO异常、数据库异常等）
     *
     * @param e 捕获到的异常对象
     * @return 统一封装的错误响应结果（ResultWrapper）
     * @throws Exception 如果异常类上有@ResponseStatus注解，会继续抛出，让Spring处理HTTP状态码
     */
    @ExceptionHandler(Exception.class)
    public ResultWrapper<Object> handleException(Exception e) throws Exception {
        // 打印异常日志（包含错误信息和堆栈跟踪，方便开发人员排查问题）
        log.error(e.getMessage(), e);

        // 检查异常类上是否有@ResponseStatus注解（该注解用于指定HTTP响应状态码）
        // 如果有，说明需要Spring自动处理状态码，因此将异常继续抛出
         if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // 注：实际生产环境中，不要返回e.getMessage()（可能包含敏感信息）
        // 应返回统一的用户友好提示，例如：return ResultWrapper.error().message("操作失败，请联系管理员");
        // 此处返回异常信息仅为开发测试用
        return ResultWrapper.error().message(e.getMessage());
    }



    /**
     * 业务异常处理器：专门捕获自定义的BusinessException
     * 适用场景：业务逻辑错误（如参数校验失败、权限不足、数据不存在等主动抛出的异常）
     *
     * @param e 捕获到的业务异常对象
     * @return 统一封装的错误响应结果
     * @throws Exception 如果异常类上有@ResponseStatus注解，继续抛出
     */
    @ExceptionHandler(BusinessException.class)
    public ResultWrapper<Object> handleBusinessException(Exception e) throws Exception {
        // 打印空指针异常日志（NPE是常见bug，需要详细堆栈信息排查）
        log.error(e.getMessage(), e);

        // 同样检查是否有@ResponseStatus注解，有则继续抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // 业务异常的message通常是用户友好的提示（如"用户名已存在"），可直接返回
        return ResultWrapper.error().message(e.getMessage());
    }




    /**
     * 空指针异常处理器：专门捕获NullPointerException（NPE）
     * 适用场景：代码中出现空对象调用（如String str = null; str.length()）
     * 特殊处理：通过ThrowableUtil工具类获取异常堆栈的最后一条信息，更精准定位错误位置
     *
     * @param e 捕获到的空指针异常对象
     * @return 统一封装的错误响应结果
     */
    @ExceptionHandler(NullPointerException.class)   // 只处理空指针异常
    public ResultWrapper<?> handleNullPointerException(NullPointerException e) {
        // 打印空指针异常日志（NPE是常见bug，需要详细堆栈信息排查）
        log.error(e.getMessage(), e);

        // 检查是否有@ResponseStatus注解，有则继续抛出（空指针异常一般不会加该注解，此处为兼容）
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        // 调用工具类获取异常堆栈的最后一条详细信息（比e.getMessage()更详细，方便定位代码行）
        String message = ThrowableUtil.getLastStackTrace(e, null);
        // 返回包含详细错误位置的提示（生产环境可简化为"系统异常，请联系管理员"）
        return ResultWrapper.error().message(message);
    }
}