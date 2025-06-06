-- =============================================
-- 创建表空间
-- =============================================
-- 创建数据表空间
CREATE TABLESPACE by_admin_data
DATAFILE '/oracle/oradata/orcl/by_admin_data.dbf'
SIZE 1G
AUTOEXTEND ON
NEXT 100M
MAXSIZE UNLIMITED;

-- 创建索引表空间
CREATE TABLESPACE by_admin_idx
DATAFILE '/oracle/oradata/orcl/by_admin_idx.dbf'
SIZE 500M
AUTOEXTEND ON
NEXT 50M
MAXSIZE UNLIMITED;

-- =============================================
-- 创建用户
-- =============================================
-- 创建用户，使用默认表空间
CREATE USER by_admin
IDENTIFIED BY by_admin_123456
DEFAULT TABLESPACE USERS
TEMPORARY TABLESPACE TEMP;

-- =============================================
-- 赋予系统权限
-- =============================================
-- 基本权限
GRANT CREATE SESSION TO by_admin;
GRANT CREATE TABLE TO by_admin;
GRANT CREATE VIEW TO by_admin;
GRANT CREATE SEQUENCE TO by_admin;
GRANT CREATE PROCEDURE TO by_admin;
GRANT CREATE TRIGGER TO by_admin;
GRANT CREATE TYPE TO by_admin;
GRANT CREATE JOB TO by_admin;
GRANT CREATE MATERIALIZED VIEW TO by_admin;
GRANT CREATE SYNONYM TO by_admin;

-- 高级权限
GRANT UNLIMITED TABLESPACE TO by_admin;
GRANT CREATE ANY DIRECTORY TO by_admin;
GRANT DROP ANY DIRECTORY TO by_admin;
GRANT ALTER SESSION TO by_admin;
GRANT ALTER SYSTEM TO by_admin;

-- =============================================
-- 赋予对象权限
-- =============================================
-- 表操作权限
GRANT SELECT ANY TABLE TO by_admin;
GRANT INSERT ANY TABLE TO by_admin;
GRANT UPDATE ANY TABLE TO by_admin;
GRANT DELETE ANY TABLE TO by_admin;
GRANT ALTER ANY TABLE TO by_admin;

-- 视图操作权限
GRANT SELECT ANY VIEW TO by_admin;
GRANT CREATE ANY VIEW TO by_admin;
GRANT DROP ANY VIEW TO by_admin;

-- 序列操作权限
GRANT SELECT ANY SEQUENCE TO by_admin;
GRANT ALTER ANY SEQUENCE TO by_admin;

-- 存储过程操作权限
GRANT EXECUTE ANY PROCEDURE TO by_admin;
GRANT CREATE ANY PROCEDURE TO by_admin;
GRANT ALTER ANY PROCEDURE TO by_admin;
GRANT DROP ANY PROCEDURE TO by_admin;

-- 触发器操作权限
GRANT CREATE ANY TRIGGER TO by_admin;
GRANT ALTER ANY TRIGGER TO by_admin;
GRANT DROP ANY TRIGGER TO by_admin;

-- =============================================
-- 赋予角色
-- =============================================
GRANT CONNECT TO by_admin;
GRANT RESOURCE TO by_admin;
GRANT DBA TO by_admin; 