package com.miguoma.by.common.utils;

import com.miguoma.by.modules.client.dto.MachineLoginDTO;
import com.miguoma.by.modules.equipment.entity.EquipmentClient;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端上下文持有者
 * 用于存储客户端相关的 ThreadLocal 变量，包括工厂代码和车间代码
 * 
 * 使用场景：
 * 1. 在拦截器中存储从 token 解析出的工厂代码和车间代码
 * 2. 在业务代码中获取当前请求的工厂代码和车间代码
 * 
 * 注意事项：
 * 1. 这些值只在当前线程中有效
 * 2. 必须在请求处理完成后调用 clear() 方法清除，否则可能造成内存泄漏
 * 3. 如果获取不到值，会返回 null，使用时需要注意空值判断
 * 
 * @author AI Assistant
 */
@Slf4j
public class ClientContextHolder {

    /**
     * 涩北登录信息
     */
    private static final ThreadLocal<EquipmentClient> MACHINE_LOGIN_DTO_THREAD_LOCAL = new ThreadLocal<>();

    /**
     *  设置机器登录信息
     * @param equipmentClient
     */
    public static void setEquipmentClient(EquipmentClient equipmentClient) {
        MACHINE_LOGIN_DTO_THREAD_LOCAL.set(equipmentClient);
    }

    /**
     *  获取机器登录信息
     * @return
     */
    public static EquipmentClient getEquipmentClient() {
        return MACHINE_LOGIN_DTO_THREAD_LOCAL.get();
    }






    /**
     * 清除所有 ThreadLocal 变量
     * 必须在请求处理完成后调用，以防止内存泄漏
     * 通常在拦截器的 afterCompletion 方法中调用
     */
    public static void clear() {
        MACHINE_LOGIN_DTO_THREAD_LOCAL.remove();

    }
}