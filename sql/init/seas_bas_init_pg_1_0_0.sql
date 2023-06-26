-- ----------------------------
-- Table structure for seas_bas_account
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_account";
CREATE TABLE "public"."seas_bas_account" (
                                             "id" int8 NOT NULL,
                                             "created_by" int8,
                                             "created_time" timestamp(6),
                                             "updated_by" int8,
                                             "updated_time" timestamp(6),
                                             "version" int4 DEFAULT 1,
                                             "logic_del" int4 DEFAULT 0,
                                             "user_id" int8,
                                             "user_account" varchar(64) COLLATE "pg_catalog"."default",
                                             "user_certificate" varchar(60) COLLATE "pg_catalog"."default",
                                             "type_code" int4,
                                             "status" int4,
                                             "last_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_account"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_account"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_account"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_account"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_account"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_account"."version" IS '版本';
COMMENT ON COLUMN "public"."seas_bas_account"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."seas_bas_account"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_account"."user_account" IS '用户账号';
COMMENT ON COLUMN "public"."seas_bas_account"."user_certificate" IS '用户密码';
COMMENT ON COLUMN "public"."seas_bas_account"."type_code" IS '用户类型';
COMMENT ON COLUMN "public"."seas_bas_account"."status" IS '账号状态';
COMMENT ON COLUMN "public"."seas_bas_account"."last_id" IS '最后登录信息ID';
COMMENT ON TABLE "public"."seas_bas_account" IS '用户账号表';

-- ----------------------------
-- Records of seas_bas_account
-- ----------------------------
INSERT INTO "public"."seas_bas_account" VALUES (1503940219529891842, 0, '2022-03-16 11:44:24.851225', 1503940219005603842, '2023-05-25 16:31:43.116491', 271, 0, 1503940219005603842, 'admin', '$2a$10$0sSR7YUV.aEuS/yczspyuuYqsYf1BSdlafu1Z4o3NeidOkjFVGGeW', 0, 0, 1666318673851219968);

-- ----------------------------
-- Table structure for seas_bas_account_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_account_log";
CREATE TABLE "public"."seas_bas_account_log" (
                                                 "id" int8 NOT NULL,
                                                 "login_time" timestamp(6),
                                                 "login_device_id" int8,
                                                 "login_device" varchar(255) COLLATE "pg_catalog"."default",
                                                 "login_ip" varchar(39) COLLATE "pg_catalog"."default",
                                                 "login_address" varchar(512) COLLATE "pg_catalog"."default",
                                                 "login_version" varchar(128) COLLATE "pg_catalog"."default",
                                                 "login_os_version" varchar(255) COLLATE "pg_catalog"."default",
                                                 "user_id" int8,
                                                 "account_id" int8,
                                                 "status" int4
)
;
COMMENT ON COLUMN "public"."seas_bas_account_log"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_time" IS '登录时间';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_device_id" IS '登录设备ID';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_device" IS '登录设备名称';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_ip" IS '登录IP';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_address" IS '登录地址';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_version" IS '登录版本';
COMMENT ON COLUMN "public"."seas_bas_account_log"."login_os_version" IS '系统版本';
COMMENT ON COLUMN "public"."seas_bas_account_log"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_account_log"."account_id" IS '账号ID';
COMMENT ON COLUMN "public"."seas_bas_account_log"."status" IS '状态：0.登录，1.退出，2.密码错误';
COMMENT ON TABLE "public"."seas_bas_account_log" IS '登录日志表';

-- ----------------------------
-- Records of seas_bas_account_log
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_auth_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_auth_menu";
CREATE TABLE "public"."seas_bas_auth_menu" (
                                               "id" int8 NOT NULL,
                                               "menu_id" int8,
                                               "role_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_auth_menu"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_auth_menu"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "public"."seas_bas_auth_menu"."role_id" IS '菜单ID';
COMMENT ON TABLE "public"."seas_bas_auth_menu" IS '菜单权限表';

-- ----------------------------
-- Records of seas_bas_auth_menu
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_auth_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_auth_org";
CREATE TABLE "public"."seas_bas_auth_org" (
                                              "id" int8 NOT NULL,
                                              "org_id" int8,
                                              "role_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_auth_org"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_auth_org"."org_id" IS '组织ID';
COMMENT ON COLUMN "public"."seas_bas_auth_org"."role_id" IS '角色ID';
COMMENT ON TABLE "public"."seas_bas_auth_org" IS '组织权限表';

-- ----------------------------
-- Records of seas_bas_auth_org
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_device
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_device";
CREATE TABLE "public"."seas_bas_device" (
                                            "id" int8 NOT NULL,
                                            "created_by" int8,
                                            "created_time" timestamp(6),
                                            "updated_by" int8,
                                            "updated_time" timestamp(6),
                                            "user_id" int8,
                                            "device_name" varchar(128) COLLATE "pg_catalog"."default",
                                            "device_code" varchar(255) COLLATE "pg_catalog"."default",
                                            "system_version" varchar(255) COLLATE "pg_catalog"."default",
                                            "last_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_device"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_device"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_device"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_device"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_device"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_device"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_device"."device_name" IS '设备名称';
COMMENT ON COLUMN "public"."seas_bas_device"."device_code" IS '设备编号';
COMMENT ON COLUMN "public"."seas_bas_device"."system_version" IS '系统版本';
COMMENT ON COLUMN "public"."seas_bas_device"."last_id" IS '最后登录信息ID';
COMMENT ON TABLE "public"."seas_bas_device" IS '用户设备表';

-- ----------------------------
-- Records of seas_bas_device
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_dict
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_dict";
CREATE TABLE "public"."seas_bas_dict" (
                                          "id" int8 NOT NULL,
                                          "parent_id" int8,
                                          "dict_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "dict_code" varchar(255) COLLATE "pg_catalog"."default",
                                          "fixed" int2,
                                          "has_children" int2,
                                          "sort" int4
)
;
COMMENT ON COLUMN "public"."seas_bas_dict"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_dict"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."seas_bas_dict"."dict_name" IS '字典名称';
COMMENT ON COLUMN "public"."seas_bas_dict"."dict_code" IS '字典值';
COMMENT ON COLUMN "public"."seas_bas_dict"."fixed" IS '是否固定';
COMMENT ON COLUMN "public"."seas_bas_dict"."has_children" IS '是否有子项';
COMMENT ON COLUMN "public"."seas_bas_dict"."sort" IS '排序';
COMMENT ON TABLE "public"."seas_bas_dict" IS '字典表';

-- ----------------------------
-- Records of seas_bas_dict
-- ----------------------------
INSERT INTO "public"."seas_bas_dict" VALUES (1, 0, '根节点', 'root', 1, 1, 0);
INSERT INTO "public"."seas_bas_dict" VALUES (1503298210886459393, 1, '用户性别', 'user.sex', 1, 0, 0);
INSERT INTO "public"."seas_bas_dict" VALUES (1503299007149907970, 1503298210886459393, '其他', '0', 1, 0, 0);
INSERT INTO "public"."seas_bas_dict" VALUES (1503298977521344514, 1503298210886459393, '女', '2', 1, 0, 2);
INSERT INTO "public"."seas_bas_dict" VALUES (1503298887343808514, 1503298210886459393, '男', '1', 1, 0, 1);

-- ----------------------------
-- Table structure for seas_bas_log
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_log";
CREATE TABLE "public"."seas_bas_log" (
                                         "id" int8 NOT NULL,
                                         "request_url" varchar(255) COLLATE "pg_catalog"."default",
                                         "method" varchar(32) COLLATE "pg_catalog"."default",
                                         "params" json,
                                         "result" json,
                                         "request_date" timestamp(6),
                                         "user_id" int8,
                                         "user_name" varchar(255) COLLATE "pg_catalog"."default",
                                         "log_name" varchar(255) COLLATE "pg_catalog"."default",
                                         "log_type" varchar(64) COLLATE "pg_catalog"."default",
                                         "ip" varchar(39) COLLATE "pg_catalog"."default",
                                         "status" varchar(3) COLLATE "pg_catalog"."default",
                                         "runtime" int8,
                                         "account" varchar(64) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."seas_bas_log"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_log"."request_url" IS '请求地址';
COMMENT ON COLUMN "public"."seas_bas_log"."method" IS '请求方法';
COMMENT ON COLUMN "public"."seas_bas_log"."params" IS '请求参数';
COMMENT ON COLUMN "public"."seas_bas_log"."result" IS '响应结果';
COMMENT ON COLUMN "public"."seas_bas_log"."request_date" IS '请求时间';
COMMENT ON COLUMN "public"."seas_bas_log"."user_id" IS '操作用户';
COMMENT ON COLUMN "public"."seas_bas_log"."user_name" IS '用户名';
COMMENT ON COLUMN "public"."seas_bas_log"."log_name" IS '日志名称';
COMMENT ON COLUMN "public"."seas_bas_log"."log_type" IS '日志类型';
COMMENT ON COLUMN "public"."seas_bas_log"."ip" IS 'IP';
COMMENT ON COLUMN "public"."seas_bas_log"."status" IS '请求状态';
COMMENT ON COLUMN "public"."seas_bas_log"."runtime" IS '执行用时';
COMMENT ON COLUMN "public"."seas_bas_log"."account" IS '账号';
COMMENT ON TABLE "public"."seas_bas_log" IS '操作日志表';

-- ----------------------------
-- Records of seas_bas_log
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_menu
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_menu";
CREATE TABLE "public"."seas_bas_menu" (
                                          "id" int8 NOT NULL,
                                          "created_by" int8,
                                          "created_time" timestamp(6),
                                          "updated_by" int8,
                                          "updated_time" timestamp(6),
                                          "version" int4 DEFAULT 1,
                                          "logic_del" int2 DEFAULT 0,
                                          "parent_id" int8,
                                          "sort" int4,
                                          "menu_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "menu_type" int8,
                                          "menu_icon" varchar(255) COLLATE "pg_catalog"."default",
                                          "menu_url" varchar(900) COLLATE "pg_catalog"."default",
                                          "remarks" varchar(900) COLLATE "pg_catalog"."default",
                                          "display" int2,
                                          "menu_component" varchar(255) COLLATE "pg_catalog"."default",
                                          "menu_code" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."seas_bas_menu"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_menu"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_menu"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_menu"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_menu"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_menu"."version" IS '版本';
COMMENT ON COLUMN "public"."seas_bas_menu"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."seas_bas_menu"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."seas_bas_menu"."sort" IS '排序';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_name" IS '菜单名称';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_type" IS '菜单类型';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_icon" IS '菜单图标';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_url" IS '菜单地址';
COMMENT ON COLUMN "public"."seas_bas_menu"."remarks" IS '备注';
COMMENT ON COLUMN "public"."seas_bas_menu"."display" IS '是否显示';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_component" IS '菜单组件';
COMMENT ON COLUMN "public"."seas_bas_menu"."menu_code" IS '菜单标识';
COMMENT ON TABLE "public"."seas_bas_menu" IS '菜单表';

-- ----------------------------
-- Records of seas_bas_menu
-- ----------------------------
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618435337153945602, 1503940219005603842, '2023-01-26 10:27:08.12013', 1503940219005603842, '2023-06-16 13:36:45.939286', 1, 0, 1, 102, '监控中心', 0, '', '', '', 1, '', 'healthy');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618436991194505218, 1503940219005603842, '2023-01-26 10:33:42.472463', NULL, NULL, 1, 0, 1618435337153945602, 1, '系统信息', 0, '', 'healthy/os', '', 1, '../view/healthy/OsPage.vue', 'healthy.os');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618435280912523265, 1503940219005603842, '2023-01-26 10:26:54.701865', 1503940219005603842, '2023-06-16 13:36:40.945125', 1, 0, 1, 101, '日志中心', 0, '', '', '', 1, '', 'log');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618438381765664769, 1503940219005603842, '2023-01-26 10:39:14.010153', NULL, NULL, 1, 0, 1618435280912523265, 2, '操作日志', 0, '', 'log/log', '', 1, '../view/system/LogPage.vue', 'log.log');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618597930271272962, 1503940219005603842, '2023-01-26 21:13:13.336251', 1503940219005603842, '2023-01-26 21:13:38.568204', 1, 0, 1618438381765664769, 0, '详情', 1, '', '', '', 1, '', 'log.log.see');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618436852602118146, 1503940219005603842, '2023-01-26 10:33:09.430108', 1503940219005603842, '2023-01-26 10:37:58.778294', 1, 0, 1618435280912523265, 1, '登录日志', 0, '', 'log/login/log', '', 1, '../view/system/LoginLogPage.vue', 'log.login.log');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1618597874075987969, 1503940219005603842, '2023-01-26 21:12:59.946782', NULL, NULL, 1, 0, 1618436852602118146, 0, '详情', 1, '', '', '', 1, '', 'log.login.log.see');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1504021318394232834, 0, '2022-03-16 17:06:40.327935', 1503940219005603842, '2023-06-16 13:36:36.307428', 1, 0, 1, 100, '系统管理', 0, '@vicons/material/SettingsFilled', '', '', 1, '', 'system');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1504281160557961218, 0, '2022-03-17 10:19:11.526878', 0, '2022-05-08 10:10:46.039405', 1, 0, 1504021318394232834, 3, '字典管理', 0, '@vicons/material/LibraryBooksFilled', 'system/dict', '', 1, '../view/system/DictPage.vue', 'system.dict');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122530271911937, 0, '2022-05-08 10:08:04.1301', NULL, NULL, 1, 0, 1504281160557961218, 0, '新增', 1, '', '', '', 1, '', 'system.dict.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122658332401666, 0, '2022-05-08 10:08:34.662625', 0, '2022-05-08 10:08:41.625905', 1, 0, 1504281160557961218, 0, '删除', 1, '', '', '', 1, '', 'system.dict.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1672902289961746434, 1503940219005603842, '2023-06-25 17:39:21.607159', 1503940219005603842, '2023-06-25 17:39:21.607253', 1, 0, 1504281160557961218, 0, '页面', 1, NULL, NULL, NULL, 1, NULL, 'system.dict.page');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122608155942914, 0, '2022-05-08 10:08:22.692929', NULL, NULL, 1, 0, 1504281160557961218, 0, '修改', 1, '', '', '', 1, '', 'system.dict.update');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1555753033859805186, 0, '2022-08-06 11:10:02.423087', 0, '2022-08-06 11:10:26.575806', 1, 0, 1523123079205642241, 0, '详情', 1, '', '', '', 1, '', 'system.login.log.see');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1504280947042721794, 0, '2022-03-17 10:18:20.620298', 0, '2022-05-08 10:10:53.720292', 1, 0, 1504021318394232834, 2, '菜单管理', 0, '@vicons/material/MenuFilled', 'system/menu', '', 1, '../view/system/MenuPage.vue', 'system.menu');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523119481986002945, 0, '2022-05-08 09:55:57.355407', NULL, NULL, 1, 0, 1504280947042721794, 0, '新增', 1, '', '', '', 1, '', 'system.menu.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523121850035494914, 0, '2022-05-08 10:05:21.951854', NULL, NULL, 1, 0, 1504280947042721794, 0, '删除', 1, '', '', '', 1, '', 'system.menu.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1672902238283726849, 1503940219005603842, '2023-06-25 17:39:09.286062', 1503940219005603842, '2023-06-25 17:39:09.286137', 1, 0, 1504280947042721794, 0, '页面', 1, NULL, NULL, NULL, 1, NULL, 'system.menu.page');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523121789578797057, 0, '2022-05-08 10:05:07.528882', NULL, NULL, 1, 0, 1504280947042721794, 0, '修改', 1, '', '', '', 1, '', 'system.menu.update');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1504275251215638530, 0, '2022-03-17 09:55:42.630279', 0, '2022-05-08 10:11:02.000108', 1, 0, 1504021318394232834, 1, '角色管理', 0, '@vicons/material/AdminPanelSettingsFilled', 'system/role', '', 1, '../view/system/RolePage.vue', 'system.role');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122172602638337, 0, '2022-05-08 10:06:38.850155', NULL, NULL, 1, 0, 1504275251215638530, 0, '新增', 1, '', '', '', 1, '', 'system.role.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122350214635521, 0, '2022-05-08 10:07:21.19802', NULL, NULL, 1, 0, 1504275251215638530, 0, '授权', 1, '', '', '', 1, '', 'system.role.auth');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122404509900801, 0, '2022-05-08 10:07:34.147649', NULL, NULL, 1, 0, 1504275251215638530, 0, '删除', 1, '', '', '', 1, '', 'system.role.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1672902171363606530, 1503940219005603842, '2023-06-25 17:38:53.330109', 1503940219005603842, '2023-06-25 17:38:53.330209', 1, 0, 1504275251215638530, 0, '页面', 1, NULL, NULL, NULL, 1, NULL, 'system.role.page');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122229762613250, 0, '2022-05-08 10:06:52.47957', 0, '2022-05-08 10:07:10.030886', 1, 0, 1504275251215638530, 0, '修改', 1, '', '', '', 1, '', 'system.role.update');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1504024693223505921, 0, '2022-03-16 17:20:04.949264', 0, '2022-05-08 10:11:09.290568', 1, 0, 1504021318394232834, 0, '用户管理', 0, '@vicons/material/AccountCircleFilled', 'system/user', '', 1, '../view/system/UserPage.vue', 'system.user');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523121966846861313, 0, '2022-05-08 10:05:49.802942', NULL, NULL, 1, 0, 1504024693223505921, 0, '新增', 1, '', '', '', 1, '', 'system.user.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122080667688962, 0, '2022-05-08 10:06:16.938683', NULL, NULL, 1, 0, 1504024693223505921, 0, '删除', 1, '', '', '', 1, '', 'system.user.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1672902105336872961, 1503940219005603842, '2023-06-25 17:38:37.58823', 1503940219005603842, '2023-06-25 17:38:37.588319', 1, 0, 1504024693223505921, 0, '页面', 1, NULL, NULL, NULL, 1, NULL, 'system.user.page');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1523122032395444225, 0, '2022-05-08 10:06:05.421572', NULL, NULL, 1, 0, 1504024693223505921, 0, '修改', 1, '', '', '', 1, '', 'system.user.update');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1, 0, '2022-03-16 16:52:10', NULL, NULL, 1, 0, 0, 0, '根节点', 0, NULL, '/', NULL, 1, NULL, NULL);


-- ----------------------------
-- Table structure for seas_bas_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_org";
CREATE TABLE "public"."seas_bas_org" (
                                         "id" int8 NOT NULL,
                                         "created_by" int8,
                                         "created_time" timestamp(6),
                                         "updated_by" int8,
                                         "updated_time" timestamp(6),
                                         "version" int4 DEFAULT 1,
                                         "logic_del" int2 DEFAULT 0,
                                         "parent_id" int8,
                                         "org_name" varchar(255) COLLATE "pg_catalog"."default",
                                         "org_code" varchar(255) COLLATE "pg_catalog"."default",
                                         "org_type" int8,
                                         "org_level" int4,
                                         "org_address" varchar(255) COLLATE "pg_catalog"."default",
                                         "manager_name" varchar(255) COLLATE "pg_catalog"."default",
                                         "manager_mobile" varchar(255) COLLATE "pg_catalog"."default",
                                         "remarks" varchar(900) COLLATE "pg_catalog"."default",
                                         "sort" int4
)
;
COMMENT ON COLUMN "public"."seas_bas_org"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_org"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_org"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_org"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_org"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_org"."version" IS '版本';
COMMENT ON COLUMN "public"."seas_bas_org"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."seas_bas_org"."parent_id" IS '父级ID';
COMMENT ON COLUMN "public"."seas_bas_org"."org_name" IS '组织名称';
COMMENT ON COLUMN "public"."seas_bas_org"."org_code" IS '组织代码';
COMMENT ON COLUMN "public"."seas_bas_org"."org_type" IS '组织类型';
COMMENT ON COLUMN "public"."seas_bas_org"."org_level" IS '级别';
COMMENT ON COLUMN "public"."seas_bas_org"."org_address" IS '组织地址';
COMMENT ON COLUMN "public"."seas_bas_org"."manager_name" IS '组织负责人';
COMMENT ON COLUMN "public"."seas_bas_org"."manager_mobile" IS '组织负责人联系方式';
COMMENT ON COLUMN "public"."seas_bas_org"."remarks" IS '备注';
COMMENT ON COLUMN "public"."seas_bas_org"."sort" IS '排序';
COMMENT ON TABLE "public"."seas_bas_org" IS '组织架构表';

-- ----------------------------
-- Records of seas_bas_org
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_role";
CREATE TABLE "public"."seas_bas_role" (
                                          "id" int8 NOT NULL,
                                          "created_by" int8,
                                          "created_time" timestamp(6),
                                          "updated_by" int8,
                                          "updated_time" timestamp(6),
                                          "role_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "role_desc" varchar(512) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "public"."seas_bas_role"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_role"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_role"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_role"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_role"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."seas_bas_role"."role_desc" IS '角色描述';
COMMENT ON TABLE "public"."seas_bas_role" IS '用户角色表';

-- ----------------------------
-- Records of seas_bas_role
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_user";
CREATE TABLE "public"."seas_bas_user" (
                                          "id" int8 NOT NULL,
                                          "created_by" int8,
                                          "created_time" timestamp(6),
                                          "updated_by" int8,
                                          "updated_time" timestamp(6),
                                          "version" int4 DEFAULT 1,
                                          "logic_del" int2 DEFAULT 0,
                                          "user_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "user_sex" int4,
                                          "birthday" date,
                                          "signature" varchar(255) COLLATE "pg_catalog"."default",
                                          "mobile" varchar(64) COLLATE "pg_catalog"."default",
                                          "email" varchar(255) COLLATE "pg_catalog"."default",
                                          "face" int8,
                                          "face_small" int8,
                                          "status" int4
)
;
COMMENT ON COLUMN "public"."seas_bas_user"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_user"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_user"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_user"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_user"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_user"."version" IS '版本';
COMMENT ON COLUMN "public"."seas_bas_user"."logic_del" IS '逻辑删除';
COMMENT ON COLUMN "public"."seas_bas_user"."user_name" IS '用户昵称';
COMMENT ON COLUMN "public"."seas_bas_user"."user_sex" IS '用户性别;0.保密1.男2.女';
COMMENT ON COLUMN "public"."seas_bas_user"."birthday" IS '用户生日';
COMMENT ON COLUMN "public"."seas_bas_user"."signature" IS '个人签名';
COMMENT ON COLUMN "public"."seas_bas_user"."mobile" IS '手机号';
COMMENT ON COLUMN "public"."seas_bas_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."seas_bas_user"."face" IS '头像';
COMMENT ON COLUMN "public"."seas_bas_user"."face_small" IS '小头像';
COMMENT ON COLUMN "public"."seas_bas_user"."status" IS '状态';
COMMENT ON TABLE "public"."seas_bas_user" IS '用户表';

-- ----------------------------
-- Records of seas_bas_user
-- ----------------------------
INSERT INTO "public"."seas_bas_user" VALUES (1503940219005603842, 0, '2022-03-16 11:44:24.727624', 1503940219005603842, '2023-05-25 17:01:41.227029', 1, 0, '系统管理员', 1, null, '', null, null, 0, 0, 0);

-- ----------------------------
-- Table structure for seas_bas_user_location
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_user_location";
CREATE TABLE "public"."seas_bas_user_location" (
                                                   "id" int8 NOT NULL,
                                                   "created_by" int8,
                                                   "created_time" timestamp(6),
                                                   "updated_by" int8,
                                                   "updated_time" timestamp(6),
                                                   "user_id" int8,
                                                   "curr_nation" varchar(128) COLLATE "pg_catalog"."default",
                                                   "curr_province" varchar(128) COLLATE "pg_catalog"."default",
                                                   "curr_city" varchar(128) COLLATE "pg_catalog"."default",
                                                   "curr_district" varchar(128) COLLATE "pg_catalog"."default",
                                                   "area_number" varchar(128) COLLATE "pg_catalog"."default",
                                                   "location" varchar(512) COLLATE "pg_catalog"."default",
                                                   "lng" numeric(10,8),
                                                   "lat" numeric(10,8)
)
;
COMMENT ON COLUMN "public"."seas_bas_user_location"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_user_location"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_bas_user_location"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_user_location"."updated_by" IS '更新人';
COMMENT ON COLUMN "public"."seas_bas_user_location"."updated_time" IS '更新时间';
COMMENT ON COLUMN "public"."seas_bas_user_location"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_user_location"."curr_nation" IS '所在国家';
COMMENT ON COLUMN "public"."seas_bas_user_location"."curr_province" IS '所在省区';
COMMENT ON COLUMN "public"."seas_bas_user_location"."curr_city" IS '所在城市';
COMMENT ON COLUMN "public"."seas_bas_user_location"."curr_district" IS '所在区县';
COMMENT ON COLUMN "public"."seas_bas_user_location"."area_number" IS '行政代码';
COMMENT ON COLUMN "public"."seas_bas_user_location"."location" IS '详细地址';
COMMENT ON COLUMN "public"."seas_bas_user_location"."lng" IS '经度';
COMMENT ON COLUMN "public"."seas_bas_user_location"."lat" IS '纬度';
COMMENT ON TABLE "public"."seas_bas_user_location" IS '用户地址表';

-- ----------------------------
-- Records of seas_bas_user_location
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_user_org
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_user_org";
CREATE TABLE "public"."seas_bas_user_org" (
                                              "id" int8 NOT NULL,
                                              "user_id" int8,
                                              "org_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_user_org"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_user_org"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_user_org"."org_id" IS '组织ID';
COMMENT ON TABLE "public"."seas_bas_user_org" IS '用户组织中间表';

-- ----------------------------
-- Records of seas_bas_user_org
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_user_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_user_role";
CREATE TABLE "public"."seas_bas_user_role" (
                                               "id" int8 NOT NULL,
                                               "user_id" int8,
                                               "role_id" int8
)
;
COMMENT ON COLUMN "public"."seas_bas_user_role"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_bas_user_role"."user_id" IS '用户ID';
COMMENT ON COLUMN "public"."seas_bas_user_role"."role_id" IS '角色ID';
COMMENT ON TABLE "public"."seas_bas_user_role" IS '用户角色中间表';

-- ----------------------------
-- Records of seas_bas_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for seas_bas_conf
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_bas_conf";
CREATE TABLE "public"."seas_bas_conf" (
                                          "id" int8 NOT NULL,
                                          "conf_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "conf_key" varchar(255) COLLATE "pg_catalog"."default",
                                          "conf_value" text COLLATE "pg_catalog"."default",
                                          "conf_info" varchar(255) COLLATE "pg_catalog"."default",
                                          "created_by" int8,
                                          "created_time" timestamp(6),
                                          "updated_by" int8,
                                          "updated_time" timestamp(6),
                                          "version" int4 DEFAULT 1,
                                          "logic_del" int4 DEFAULT 0
)
;
COMMENT ON COLUMN "public"."seas_bas_conf"."id" IS 'ID';
COMMENT ON COLUMN "public"."seas_bas_conf"."conf_name" IS '配置名称';
COMMENT ON COLUMN "public"."seas_bas_conf"."conf_key" IS '配置标识';
COMMENT ON COLUMN "public"."seas_bas_conf"."conf_value" IS '配置值';
COMMENT ON COLUMN "public"."seas_bas_conf"."conf_info" IS '备注信息';
COMMENT ON COLUMN "public"."seas_bas_conf"."created_by" IS '创建者';
COMMENT ON COLUMN "public"."seas_bas_conf"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_bas_conf"."updated_by" IS '修改者';
COMMENT ON COLUMN "public"."seas_bas_conf"."updated_time" IS '修改时间';
COMMENT ON COLUMN "public"."seas_bas_conf"."version" IS '版本';
COMMENT ON COLUMN "public"."seas_bas_conf"."logic_del" IS '逻辑删除';
COMMENT ON TABLE "public"."seas_bas_conf" IS '配置信息表';

-- ----------------------------
-- Primary Key structure for table seas_bas_account
-- ----------------------------
ALTER TABLE "public"."seas_bas_account" ADD CONSTRAINT "seas_sys_account_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_account_log
-- ----------------------------
ALTER TABLE "public"."seas_bas_account_log" ADD CONSTRAINT "seas_sys_account_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_auth_menu
-- ----------------------------
ALTER TABLE "public"."seas_bas_auth_menu" ADD CONSTRAINT "seas_sys_auth_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_auth_org
-- ----------------------------
ALTER TABLE "public"."seas_bas_auth_org" ADD CONSTRAINT "seas_sys_auth_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_device
-- ----------------------------
ALTER TABLE "public"."seas_bas_device" ADD CONSTRAINT "seas_sys_device_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_dict
-- ----------------------------
ALTER TABLE "public"."seas_bas_dict" ADD CONSTRAINT "seas_sys_dict_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_log
-- ----------------------------
ALTER TABLE "public"."seas_bas_log" ADD CONSTRAINT "seas_sys_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_menu
-- ----------------------------
ALTER TABLE "public"."seas_bas_menu" ADD CONSTRAINT "seas_sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_org
-- ----------------------------
ALTER TABLE "public"."seas_bas_org" ADD CONSTRAINT "seas_sys_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_user
-- ----------------------------
ALTER TABLE "public"."seas_bas_user" ADD CONSTRAINT "seas_sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_user_location
-- ----------------------------
ALTER TABLE "public"."seas_bas_user_location" ADD CONSTRAINT "seas_sys_user_location_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_user_org
-- ----------------------------
ALTER TABLE "public"."seas_bas_user_org" ADD CONSTRAINT "seas_sys_user_org_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_user_role
-- ----------------------------
ALTER TABLE "public"."seas_bas_user_role" ADD CONSTRAINT "seas_sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table seas_bas_conf
-- ----------------------------
ALTER TABLE "public"."seas_bas_conf" ADD CONSTRAINT "seas_bas_conf_pkey" PRIMARY KEY ("id");
