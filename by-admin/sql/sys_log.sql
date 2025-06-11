-- 系统日志表
CREATE TABLE sys_log (
    -- BaseEntity字段
    id NUMBER(20) NOT NULL,
    create_time TIMESTAMP,
    update_time TIMESTAMP,
    creator NUMBER(20),
    updater NUMBER(20),
    deleted NUMBER(1) DEFAULT 0,
    
    -- SysLog特有字段
    module_name VARCHAR2(50),
    type_name VARCHAR2(50),
    request_params CLOB,
    response_result CLOB,
    operator_name VARCHAR2(50),
    operate_time TIMESTAMP,
    status NUMBER(1) DEFAULT 0,
    error_msg CLOB,
    
    -- 主键和索引
    CONSTRAINT pk_sys_log PRIMARY KEY (id)
);

-- 添加注释
COMMENT ON TABLE sys_log IS '系统日志表';
COMMENT ON COLUMN sys_log.id IS '主键ID';
COMMENT ON COLUMN sys_log.create_time IS '创建时间';
COMMENT ON COLUMN sys_log.update_time IS '更新时间';
COMMENT ON COLUMN sys_log.creator IS '创建者';
COMMENT ON COLUMN sys_log.updater IS '更新者';
COMMENT ON COLUMN sys_log.deleted IS '删除标记（0：未删除；1：已删除）';
COMMENT ON COLUMN sys_log.module_name IS '操作模块';
COMMENT ON COLUMN sys_log.type_name IS '操作类型';
COMMENT ON COLUMN sys_log.request_params IS '请求参数';
COMMENT ON COLUMN sys_log.response_result IS '响应结果';
COMMENT ON COLUMN sys_log.operator_name IS '操作人用户名';
COMMENT ON COLUMN sys_log.operate_time IS '操作时间';
COMMENT ON COLUMN sys_log.status IS '操作状态（0：正常；1：异常）';
COMMENT ON COLUMN sys_log.error_msg IS '错误信息';

-- 创建索引
CREATE INDEX idx_sys_log_operator_name ON sys_log(operator_name);
CREATE INDEX idx_sys_log_operate_time ON sys_log(operate_time);
CREATE INDEX idx_sys_log_module_name ON sys_log(module_name);
CREATE INDEX idx_sys_log_type_name ON sys_log(type_name); 