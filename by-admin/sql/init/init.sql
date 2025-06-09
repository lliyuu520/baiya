-- =============================================
-- 系统相关表
-- =============================================

-- 系统
create table SYS_MENU
(
    ID          NUMBER(20) not null
        constraint PK_SYS_MENU
            primary key,
    PARENT_ID   NUMBER(20),
    NAME        VARCHAR2(50),
    URL         VARCHAR2(200),
    PERMS       VARCHAR2(500),
    TYPE        NUMBER(1),
    ICON        VARCHAR2(50),
    WEIGHT      NUMBER(4),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1,
    OPEN_STYLE  NUMBER
)
/

comment on table SYS_MENU is '系统菜单表'
/

-- 系统角色表
create table SYS_ROLE
(
    ID          NUMBER(20) not null
        constraint PK_SYS_ROLE
            primary key,
    NAME        VARCHAR2(100),
    REMARKS     VARCHAR2(500),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1
)
/

comment on table SYS_ROLE is '系统角色表'
/

-- 系统角色字段权限表
create table SYS_ROLE_FIELD
(
    ID             NUMBER(20) not null
        constraint PK_SYS_ROLE_FIELD
            primary key,
    ROLE_ID        NUMBER(20),
    TABLE_FIELD_ID NUMBER,
    CREATE_TIME    TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME    TIMESTAMP(6) default SYSDATE,
    CREATOR        NUMBER(20),
    UPDATER        NUMBER(20),
    DELETED        NUMBER(1)    default 0,
    VERSION        NUMBER(10)   default 1
)
/

comment on table SYS_ROLE_FIELD is '系统角色字段权限表'
/

-- 系统角色菜单关联表
create table SYS_ROLE_MENU
(
    ID          NUMBER(20) not null
        constraint PK_SYS_ROLE_MENU
            primary key,
    ROLE_ID     NUMBER(20),
    MENU_ID     NUMBER(20),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1
)
/

comment on table SYS_ROLE_MENU is '系统角色菜单关联表'
/

-- 系统用户表
create table SYS_USER
(
    ID          NUMBER(20) not null
        constraint PK_SYS_USER
            primary key,
    USERNAME    VARCHAR2(50),
    PASSWORD    VARCHAR2(100),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1,
    USER_TYPE   VARCHAR2(200)
)
/

comment on table SYS_USER is '系统用户表'
/

comment on column SYS_USER.USER_TYPE is '用户类型'
/

-- 系统用户角色关联表
create table SYS_USER_ROLE
(
    ID          NUMBER(20) not null
        constraint PK_SYS_USER_ROLE
            primary key,
    USER_ID     NUMBER(20),
    ROLE_ID     NUMBER(20),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1
)
/

comment on table SYS_USER_ROLE is '系统用户角色关联表'
/

-- 表配置表
create table SYS_TABLE_CONFIG
(
    ID            NUMBER(20) not null
        constraint PK_TABLE_CONFIG
            primary key,
    TABLE_NAME    VARCHAR2(100),
    TABLE_COMMENT VARCHAR2(500),
    CREATE_TIME   TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME   TIMESTAMP(6) default SYSDATE,
    CREATOR       NUMBER(20),
    UPDATER       NUMBER(20),
    DELETED       NUMBER(1)    default 0,
    VERSION       NUMBER(10)   default 1
)
/

comment on table SYS_TABLE_CONFIG is '表配置表'
/

-- 表字段配置表
create table SYS_TABLE_FIELD
(
    ID              NUMBER(20) not null
        constraint PK_TABLE_FIELD
            primary key,
    FIELD_NAME      VARCHAR2(100),
    TABLE_CONFIG_ID NUMBER,
    FIELD_COMMENT   VARCHAR2(500),
    CREATE_TIME     TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME     TIMESTAMP(6) default SYSDATE,
    CREATOR         NUMBER(20),
    UPDATER         NUMBER(20),
    DELETED         NUMBER(1)    default 0,
    VERSION         NUMBER(10)   default 1,
    PERMISSION      VARCHAR2(200)
)
/

comment on table SYS_TABLE_FIELD is '表字段配置表'
/

-- 编码规则表
create table SYS_CODE_RULE
(
    ID          NUMBER(20) not null
        constraint PK_CORE_CODE_RULE
            primary key,
    NAME        VARCHAR2(100),
    CODE        VARCHAR2(100),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1,
    ENABLED     NUMBER(1)    default 1
)
/

-- 编码规则明细表
create table SYS_CODE_RULE_DETAIL
(
    ID           NUMBER(20) not null
        constraint PK_CORE_CODE_RULE_DETAIL
            primary key,
    RULE_ID      NUMBER(20),
    ENCODE_TYPE  VARCHAR2(100),
    SOURCE_FIELD VARCHAR2(100),
    WEIGHT       NUMBER(10),
    CREATE_TIME  TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME  TIMESTAMP(6) default SYSDATE,
    CREATOR      NUMBER(20),
    UPDATER      NUMBER(20),
    DELETED      NUMBER(1)    default 0,
    VERSION      NUMBER(10)   default 1,
    CONSTANT     VARCHAR2(200),
    RULE_TYPE    VARCHAR2(200),
    INDEX_BEGIN  NUMBER,
    INDEX_END    NUMBER
)
/

comment on column SYS_CODE_RULE_DETAIL.CONSTANT is '值'
/

-- 系统日志表
create table SYS_LOG
(
    ID            NUMBER(20) not null
        constraint PK_SYS_LOG
            primary key,
    MODULE        VARCHAR2(50),
    TYPE          VARCHAR2(50),
    DESCRIPTION   VARCHAR2(500),
    METHOD        VARCHAR2(200),
    PARAMS        CLOB,
    IP            VARCHAR2(64),
    URL           VARCHAR2(500),
    OPERATOR_ID   NUMBER(20),
    OPERATOR_NAME VARCHAR2(50),
    OPERATE_TIME  TIMESTAMP(6),
    DURATION      NUMBER(20),
    STATUS        NUMBER(1),
    ERROR_MSG     CLOB,
    CREATE_TIME   TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME   TIMESTAMP(6) default SYSDATE,
    CREATOR       NUMBER(20),
    UPDATER       NUMBER(20),
    DELETED       NUMBER(1)    default 0,
    VERSION       NUMBER(10)   default 1
)
/

comment on table SYS_LOG is '系统日志表'
/

-- =============================================
-- 生产相关表
-- =============================================

-- 工厂表
create table PRODUCTION_FACTORY
(
    ID          NUMBER(20) not null
        constraint PK_PRODUCTION_FACTORY
            primary key,
    CODE        VARCHAR2(100),
    NAME        VARCHAR2(100),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1
)
/

comment on table PRODUCTION_FACTORY is '工厂表'
/

-- 产品表
create table PRODUCTION_PRODUCT
(
    ID                  NUMBER not null
        primary key,
    CODE                VARCHAR2(255),
    NAME                VARCHAR2(255),
    CATEGORY_CODE       VARCHAR2(255),
    UNIT                VARCHAR2(255),
    ONE_BOX_PACKAGE_NUM NUMBER,
    GOODS_CODE          VARCHAR2(255),
    BAR_CODE            VARCHAR2(255),
    SPECIFICATION       VARCHAR2(255),
    CREATE_TIME         TIMESTAMP(6),
    UPDATE_TIME         TIMESTAMP(6),
    CREATOR             NUMBER,
    UPDATER             NUMBER,
    DELETED             NUMBER default 0,
    PRODUCT_TYPE        VARCHAR2(200)
)
/

comment on table PRODUCTION_PRODUCT is '产品表'
/

create index PRODUCTION_PRODUCT_CODE_INDEX
    on PRODUCTION_PRODUCT (CODE)
/

create index PRODUCTION_PRODUCT_CATEGORY_CODE_INDEX
    on PRODUCTION_PRODUCT (CATEGORY_CODE)
/

create index PRODUCTION_PRODUCT_PRODUCT_TYPE_INDEX
    on PRODUCTION_PRODUCT (PRODUCT_TYPE)
/

-- 产品分类表
create table PRODUCTION_PRODUCT_CATEGORY
(
    ID          NUMBER(20) not null
        constraint PK_PRODUCTION_PRODUCT_TYPE
            primary key,
    CODE        VARCHAR2(100)
        constraint PRODUCTION_PRODUCT_CATEGORY_PK
            unique,
    NAME        VARCHAR2(100),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1,
    PARENT_CODE VARCHAR2(200)
)
/

comment on table PRODUCTION_PRODUCT_CATEGORY is '产品分类表'
/

create index PRODUCTION_PRODUCT_CATEGORY_PARENT_CODE_INDEX
    on PRODUCTION_PRODUCT_CATEGORY (PARENT_CODE)
/

-- 生产部门和车间表
create table PRODUCTION_DEPART_AND_WORKSHOP
(
    UPDATER     NUMBER(20),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    DELETED     NUMBER(1)    default 0,
    ID          NUMBER(20) not null
        constraint PK_PRODUCTION_DEPART
            primary key,
    NAME        VARCHAR2(100),
    CODE        VARCHAR2(100)
        constraint PRODUCTION_DEPART_AND_WORKSHOP_PK
            unique,
    CREATOR     NUMBER(20),
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    VERSION     NUMBER(10)   default 1,
    PARENT_CODE VARCHAR2(200)
)
/

comment on table PRODUCTION_DEPART_AND_WORKSHOP is '生产部门和车间表'
/

-- 生产班组表
create table PRODUCTION_TEAM
(
    ID          NUMBER(20) not null
        constraint PK_PRODUCTION_TEAM
            primary key,
    CODE        VARCHAR2(100)
        constraint PRODUCTION_TEAM_PK
            unique,
    NAME        VARCHAR2(100),
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    CREATOR     NUMBER(20),
    UPDATER     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1
)
/

comment on table PRODUCTION_TEAM is '生产班组表'
/

-- 生产班次表
create table PRODUCTION_SHIFT
(
    CREATOR     NUMBER(20),
    DELETED     NUMBER(1)    default 0,
    VERSION     NUMBER(10)   default 1,
    CODE        VARCHAR2(100)
        constraint PRODUCTION_SHIFT_PK
            unique,
    CREATE_TIME TIMESTAMP(6) default SYSDATE,
    NAME        VARCHAR2(100),
    ID          NUMBER(20) not null
        constraint PK_PRODUCTION_SHIFT
            primary key,
    UPDATE_TIME TIMESTAMP(6) default SYSDATE,
    UPDATER     NUMBER(20)
)
/

comment on table PRODUCTION_SHIFT is '生产班次表'
/

-- 生产订单表
create table PRODUCTION_ORDER
(
    ID                       NUMBER(19) not null
        constraint PK_PRODUCTION_ORDER
            primary key,
    CREATE_TIME              TIMESTAMP(6),
    UPDATE_TIME              TIMESTAMP(6),
    CREATOR                  NUMBER(19),
    UPDATER                  NUMBER(19),
    DELETED                  NUMBER(1)  default 0,
    ORDER_NO                 VARCHAR2(50),
    ORDER_DATE               DATE,
    PRODUCTION_DATE          DATE,
    PRODUCTION_DEPART_CODE   VARCHAR2(50),
    PRODUCTION_WORKSHOP_CODE VARCHAR2(50),
    PRODUCTION_SHIFT_CODE    VARCHAR2(50),
    PRODUCTION_TEAM_CODE     VARCHAR2(50),
    PRODUCT_CODE             VARCHAR2(50),
    BAG_NUM                  NUMBER(10),
    BOX_NUM                  VARCHAR2(20),
    REWORK_FLAG              NUMBER(1)  default 0,
    PRODUCTION_FACTORY_CODE  VARCHAR2(200),
    BOX_NUM_MAX_LIMITED      NUMBER,
    BAG_NUM_MAX_LIMITED      NUMBER,
    BOX_NO                   NUMBER(20) default 0,
    PRODUCT_TYPE             VARCHAR2(200)
)
/

comment on table PRODUCTION_ORDER is '生产订单表'
/

-- =============================================
-- 记录相关表
-- =============================================

-- 包码记录表
create table RECORD_BAG_CODE
(
    ID                             NUMBER(20) not null
        primary key,
    CREATOR                        VARCHAR2(64),
    CREATE_TIME                    TIMESTAMP(6),
    UPDATER                        VARCHAR2(64),
    UPDATE_TIME                    TIMESTAMP(6),
    FINISHED_PRODUCT_ORDER_ID      NUMBER(20),
    SEMI_FINISHED_PRODUCT_ORDER_ID NUMBER(20),
    CODE                           VARCHAR2(100)
        constraint RECORD_BAG_CODE_PK
            unique,
    UPLOAD_DATE_TIME               TIMESTAMP(6),
    PULL_DATE_TIME                 TIMESTAMP(6),
    RULE_ID                        NUMBER(20),
    BOX_CODE                       VARCHAR2(100),
    DELETED                        NUMBER(1) default 0
)
/

comment on table RECORD_BAG_CODE is '包码记录表'
/

-- 箱码记录表
create table RECORD_BOX_CODE
(
    ID                             NUMBER(20) not null
        primary key,
    CREATOR                        VARCHAR2(64),
    CREATE_TIME                    TIMESTAMP(6),
    UPDATER                        VARCHAR2(64),
    UPDATE_TIME                    TIMESTAMP(6),
    FINISHED_PRODUCT_ORDER_ID      NUMBER(20),
    SEMI_FINISHED_PRODUCT_ORDER_ID NUMBER(20),
    CODE                           VARCHAR2(100)
        constraint RECORD_BOX_CODE_PK
            unique,
    UPLOAD_DATE_TIME               TIMESTAMP(6),
    PULL_DATE_TIME                 TIMESTAMP(6),
    PULL_TYPE                      VARCHAR2(50),
    RULE_ID                        NUMBER(20),
    CRIB_DATE_TIME                 TIMESTAMP(6),
    CRIB_CODE                      VARCHAR2(200),
    DELETED                        NUMBER default 0
)
/

comment on table RECORD_BOX_CODE is '箱码记录表'
/

-- 二维码记录表
create table RECORD_QR_CODE
(
    ID                             NUMBER(20) not null
        primary key,
    CREATOR                        VARCHAR2(64),
    CREATE_TIME                    TIMESTAMP(6),
    UPDATER                        VARCHAR2(64),
    UPDATE_TIME                    TIMESTAMP(6),
    FINISHED_PRODUCT_ORDER_ID      NUMBER(20),
    SEMI_FINISHED_PRODUCT_ORDER_ID NUMBER(20),
    CODE                           VARCHAR2(100)
        constraint RECORD_QR_CODE_PK
            unique,
    UPLOAD_DATE_TIME               TIMESTAMP(6),
    PULL_DATE_TIME                 TIMESTAMP(6),
    BOX_CODE                       VARCHAR2(100),
    DELETED                        NUMBER default 0
)
/

comment on table RECORD_QR_CODE is '二维码记录表'
/

-- =============================================
-- 表注释
-- =============================================

-- 生产订单表字段注释
comment on column PRODUCTION_ORDER.ID is '主键ID';
comment on column PRODUCTION_ORDER.CREATE_TIME is '创建时间';
comment on column PRODUCTION_ORDER.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_ORDER.CREATOR is '创建者';
comment on column PRODUCTION_ORDER.UPDATER is '更新者';
comment on column PRODUCTION_ORDER.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_ORDER.ORDER_NO is '单据编号';
comment on column PRODUCTION_ORDER.ORDER_DATE is '单据日期';
comment on column PRODUCTION_ORDER.PRODUCTION_DATE is '生产日期';
comment on column PRODUCTION_ORDER.PRODUCTION_DEPART_CODE is '生产部门编码';
comment on column PRODUCTION_ORDER.PRODUCTION_WORKSHOP_CODE is '生产车间编码';
comment on column PRODUCTION_ORDER.PRODUCTION_SHIFT_CODE is '生产班次编码';
comment on column PRODUCTION_ORDER.PRODUCTION_TEAM_CODE is '生产班组编码';
comment on column PRODUCTION_ORDER.PRODUCT_CODE is '产品编码';
comment on column PRODUCTION_ORDER.BAG_NUM is '片数';
comment on column PRODUCTION_ORDER.BOX_NUM is '件数';
comment on column PRODUCTION_ORDER.REWORK_FLAG is '返工标识(0:否 1:是)';
comment on column PRODUCTION_ORDER.PRODUCTION_FACTORY_CODE is '工厂编码';
comment on column PRODUCTION_ORDER.BOX_NUM_MAX_LIMITED is '最大件数';
comment on column PRODUCTION_ORDER.BAG_NUM_MAX_LIMITED is '最大片数';
comment on column PRODUCTION_ORDER.BOX_NO is '箱号';
comment on column PRODUCTION_ORDER.PRODUCT_TYPE is '产品类型';

-- 产品表字段注释
comment on column PRODUCTION_PRODUCT.ID is '主键ID';
comment on column PRODUCTION_PRODUCT.CODE is '产品编码';
comment on column PRODUCTION_PRODUCT.NAME is '产品名称';
comment on column PRODUCTION_PRODUCT.CATEGORY_CODE is '产品类别编码';
comment on column PRODUCTION_PRODUCT.PRODUCT_TYPE is '产品类型';
comment on column PRODUCTION_PRODUCT.UNIT is '单位';
comment on column PRODUCTION_PRODUCT.ONE_BOX_PACKAGE_NUM is '一箱包数';
comment on column PRODUCTION_PRODUCT.GOODS_CODE is '货号';
comment on column PRODUCTION_PRODUCT.BAR_CODE is '条形码';
comment on column PRODUCTION_PRODUCT.SPECIFICATION is '规格';
comment on column PRODUCTION_PRODUCT.CREATE_TIME is '创建时间';
comment on column PRODUCTION_PRODUCT.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_PRODUCT.CREATOR is '创建者';
comment on column PRODUCTION_PRODUCT.UPDATER is '更新者';
comment on column PRODUCTION_PRODUCT.DELETED is '删除标记(0:未删除 1:已删除)';

-- 产品分类表字段注释
comment on column PRODUCTION_PRODUCT_CATEGORY.ID is '主键ID';
comment on column PRODUCTION_PRODUCT_CATEGORY.CODE is '分类编码';
comment on column PRODUCTION_PRODUCT_CATEGORY.NAME is '分类名称';
comment on column PRODUCTION_PRODUCT_CATEGORY.PARENT_CODE is '父级编码';
comment on column PRODUCTION_PRODUCT_CATEGORY.CREATE_TIME is '创建时间';
comment on column PRODUCTION_PRODUCT_CATEGORY.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_PRODUCT_CATEGORY.CREATOR is '创建者';
comment on column PRODUCTION_PRODUCT_CATEGORY.UPDATER is '更新者';
comment on column PRODUCTION_PRODUCT_CATEGORY.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_PRODUCT_CATEGORY.VERSION is '版本号';

-- 生产部门和车间表字段注释
comment on column PRODUCTION_DEPART_AND_WORKSHOP.ID is '主键ID';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.CODE is '编码';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.NAME is '名称';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.PARENT_CODE is '父级部门编码';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.CREATE_TIME is '创建时间';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.CREATOR is '创建者';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.UPDATER is '更新者';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_DEPART_AND_WORKSHOP.VERSION is '版本号';

-- 生产班组表字段注释
comment on column PRODUCTION_TEAM.ID is '主键ID';
comment on column PRODUCTION_TEAM.CODE is '编码';
comment on column PRODUCTION_TEAM.NAME is '名称';
comment on column PRODUCTION_TEAM.CREATE_TIME is '创建时间';
comment on column PRODUCTION_TEAM.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_TEAM.CREATOR is '创建者';
comment on column PRODUCTION_TEAM.UPDATER is '更新者';
comment on column PRODUCTION_TEAM.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_TEAM.VERSION is '版本号';

-- 生产班次表字段注释
comment on column PRODUCTION_SHIFT.ID is '主键ID';
comment on column PRODUCTION_SHIFT.CODE is '编码';
comment on column PRODUCTION_SHIFT.NAME is '名称';
comment on column PRODUCTION_SHIFT.CREATE_TIME is '创建时间';
comment on column PRODUCTION_SHIFT.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_SHIFT.CREATOR is '创建者';
comment on column PRODUCTION_SHIFT.UPDATER is '更新者';
comment on column PRODUCTION_SHIFT.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_SHIFT.VERSION is '版本号';

-- 工厂表字段注释
comment on column PRODUCTION_FACTORY.ID is '主键ID';
comment on column PRODUCTION_FACTORY.CODE is '编码';
comment on column PRODUCTION_FACTORY.NAME is '名称';
comment on column PRODUCTION_FACTORY.CREATE_TIME is '创建时间';
comment on column PRODUCTION_FACTORY.UPDATE_TIME is '更新时间';
comment on column PRODUCTION_FACTORY.CREATOR is '创建者';
comment on column PRODUCTION_FACTORY.UPDATER is '更新者';
comment on column PRODUCTION_FACTORY.DELETED is '删除标记(0:未删除 1:已删除)';
comment on column PRODUCTION_FACTORY.VERSION is '版本号';

-- 包码记录表字段注释
comment on column RECORD_BAG_CODE.ID is '主键ID';
comment on column RECORD_BAG_CODE.CREATOR is '创建者';
comment on column RECORD_BAG_CODE.CREATE_TIME is '创建时间';
comment on column RECORD_BAG_CODE.UPDATER is '更新者';
comment on column RECORD_BAG_CODE.UPDATE_TIME is '更新时间';
comment on column RECORD_BAG_CODE.FINISHED_PRODUCT_ORDER_ID is '成品订单ID';
comment on column RECORD_BAG_CODE.SEMI_FINISHED_PRODUCT_ORDER_ID is '半成品订单ID';
comment on column RECORD_BAG_CODE.CODE is '包码';
comment on column RECORD_BAG_CODE.UPLOAD_DATE_TIME is '上传时间';
comment on column RECORD_BAG_CODE.PULL_DATE_TIME is '拉取时间';
comment on column RECORD_BAG_CODE.RULE_ID is '规则ID';
comment on column RECORD_BAG_CODE.BOX_CODE is '箱码';
comment on column RECORD_BAG_CODE.DELETED is '删除标记(0:未删除 1:已删除)';

-- 箱码记录表字段注释
comment on column RECORD_BOX_CODE.ID is '主键ID';
comment on column RECORD_BOX_CODE.CREATOR is '创建者';
comment on column RECORD_BOX_CODE.CREATE_TIME is '创建时间';
comment on column RECORD_BOX_CODE.UPDATER is '更新者';
comment on column RECORD_BOX_CODE.UPDATE_TIME is '更新时间';
comment on column RECORD_BOX_CODE.FINISHED_PRODUCT_ORDER_ID is '成品订单ID';
comment on column RECORD_BOX_CODE.SEMI_FINISHED_PRODUCT_ORDER_ID is '半成品订单ID';
comment on column RECORD_BOX_CODE.CODE is '箱码';
comment on column RECORD_BOX_CODE.UPLOAD_DATE_TIME is '上传时间';
comment on column RECORD_BOX_CODE.PULL_DATE_TIME is '拉取时间';
comment on column RECORD_BOX_CODE.PULL_TYPE is '拉取类型';
comment on column RECORD_BOX_CODE.RULE_ID is '规则ID';
comment on column RECORD_BOX_CODE.CRIB_DATE_TIME is '入库时间';
comment on column RECORD_BOX_CODE.CRIB_CODE is '入库编码';
comment on column RECORD_BOX_CODE.DELETED is '删除标记(0:未删除 1:已删除)';

-- 二维码记录表字段注释
comment on column RECORD_QR_CODE.ID is '主键ID';
comment on column RECORD_QR_CODE.CREATOR is '创建者';
comment on column RECORD_QR_CODE.CREATE_TIME is '创建时间';
comment on column RECORD_QR_CODE.UPDATER is '更新者';
comment on column RECORD_QR_CODE.UPDATE_TIME is '更新时间';
comment on column RECORD_QR_CODE.FINISHED_PRODUCT_ORDER_ID is '成品订单ID';
comment on column RECORD_QR_CODE.SEMI_FINISHED_PRODUCT_ORDER_ID is '半成品订单ID';
comment on column RECORD_QR_CODE.CODE is '二维码';
comment on column RECORD_QR_CODE.UPLOAD_DATE_TIME is '上传时间';
comment on column RECORD_QR_CODE.PULL_DATE_TIME is '拉取时间';
comment on column RECORD_QR_CODE.BOX_CODE is '箱码';
comment on column RECORD_QR_CODE.DELETED is '删除标记(0:未删除 1:已删除)'; 