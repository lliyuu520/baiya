package com.miguoma.by.common.utils;

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
     * 工厂代码
     * 用于存储当前请求对应的工厂代码
     * 在拦截器中从 token 解析并设置
     * 在业务代码中可以通过 getFactoryCode() 方法获取
     */
    private static final ThreadLocal<String> FACTORY_CODE = new ThreadLocal<>();

    /**
     * 车间代码
     * 用于存储当前请求对应的车间代码
     * 在拦截器中从 token 解析并设置
     * 在业务代码中可以通过 getWorkshopCode() 方法获取
     */
    private static final ThreadLocal<String> WORKSHOP_NAME = new ThreadLocal<>();

    /**
     * 设置工厂代码
     * 通常在拦截器中调用，用于存储从 token 解析出的工厂代码
     *
     * @param factoryCode 工厂代码
     */
    public static void setFactoryCode(String factoryCode) {
        FACTORY_CODE.set(factoryCode);
    }

    /**
     * 获取工厂代码
     * 在业务代码中调用，获取当前请求对应的工厂代码
     * 如果未设置，则返回 null
     *
     * @return 工厂代码，如果未设置则返回 null
     */
    public static String getFactoryCode() {
        return FACTORY_CODE.get();
    }

    /**
     * 设置车间代码
     * 通常在拦截器中调用，用于存储从 token 解析出的车间代码
     *
     * @param workshopName 车间代码
     */
    public static void setWorkshopName(String workshopName) {
        WORKSHOP_NAME.set(workshopName);
    }

    /**
     * 获取车间代码
     * 在业务代码中调用，获取当前请求对应的车间代码
     * 如果未设置，则返回 null
     *
     * @return 车间代码，如果未设置则返回 null
     */
    public static String getWorkshopName() {
        return WORKSHOP_NAME.get();
    }

    /**
     * 清除所有 ThreadLocal 变量
     * 必须在请求处理完成后调用，以防止内存泄漏
     * 通常在拦截器的 afterCompletion 方法中调用
     */
    public static void clear() {
        FACTORY_CODE.remove();
        WORKSHOP_NAME.remove();
    }
}