package com.miguoma.by.common.interceptor;

import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import com.miguoma.by.modules.equipment.service.EquipmentClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.miguoma.by.common.utils.ClientContextHolder;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.client.dto.MachineLoginDTO;

import cn.hutool.core.codec.Base62;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端软件token拦截器
 * 用于验证客户端请求的token，并解析存储工厂代码和车间代码
 * 
 * 主要功能：
 * 1. 验证请求头中的token是否存在
 * 2. 解析token获取工厂代码和车间代码
 * 3. 将工厂代码和车间代码存储到ThreadLocal中
 * 4. 请求完成后清理ThreadLocal变量
 * 
 * 工作流程：
 * 1. 从请求头获取token
 * 2. 验证token是否存在
 * 3. 使用Base62解码token
 * 4. 将解码后的JSON转换为TeamLoginDTO对象
 * 5. 从TeamLoginDTO中获取工厂代码和车间代码
 * 6. 将代码存储到ThreadLocal中
 * 7. 请求完成后清理ThreadLocal变量
 * 
 * @author AI Assistant
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ClientTokenInterceptor implements HandlerInterceptor {

    private final EquipmentClientService equipmentClientService;

    /**
     * 请求处理前的拦截方法
     * 验证token并解析存储工厂代码和车间代码
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param handler  处理器
     * @return 如果验证通过返回true，否则返回false
     * @throws Exception 如果处理过程中发生异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求头中的token
        String macAddress = request.getHeader("Token");

        // 验证token是否存在
        if (StrUtil.isBlank(macAddress)) {
            log.warn("请求缺少token: {}", request.getRequestURI());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONUtil.toJsonStr(Result.error("请求缺乏Token")));
            return false;
        }
        final EquipmentClient equipmentClient = equipmentClientService.getByMacAddress(macAddress);
        if (equipmentClient == null) {
            log.warn("设备不存在: {}", macAddress);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONUtil.toJsonStr(Result.error("设备不存在")));
            return false;
        }

        // 存储工厂代码和车间代码到 ThreadLocal
        ClientContextHolder.setEquipmentClient(equipmentClient);
        // 验证通过，放行请求
        return true;
    }

    /**
     * 请求完成后的处理方法
     * 清理ThreadLocal变量，防止内存泄漏
     *
     * @param request  HTTP请求
     * @param response HTTP响应
     * @param handler  处理器
     * @param ex       处理过程中发生的异常，如果有的话
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        // 请求完成后清除 ThreadLocal 变量
        ClientContextHolder.clear();
    }
}
