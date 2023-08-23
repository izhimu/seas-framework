ALTER TABLE "public"."seas_bas_role"
    ADD COLUMN "enable" int2,
  ADD COLUMN "sort" int4,
  ADD COLUMN "auth_type" int2,
  ADD COLUMN "dept_cascade" int2;

COMMENT ON COLUMN "public"."seas_bas_role"."enable" IS '是否启用：0否、1是';

COMMENT ON COLUMN "public"."seas_bas_role"."sort" IS '优先级';

COMMENT ON COLUMN "public"."seas_bas_role"."auth_type" IS '权限模式：0全部数据权限、1自定义数据权限、2本部门及下级部门数据权限、3本部门数据权限、4仅自己数据权限';

COMMENT ON COLUMN "public"."seas_bas_role"."dept_cascade" IS '部门级联选择：0否、1是';

INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1687331937087143938, 1503940219005603842, '2023-08-04 13:17:37.573303', 1503940219005603842, '2023-08-04 13:17:37.573303', 1, 0, 1504275251215638530, 0, '分配用户', 1, NULL, NULL, NULL, 1, NULL, 'system.role.user');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1687340940445892609, 1503940219005603842, '2023-08-04 13:53:24.13988', 1503940219005603842, '2023-08-04 13:53:24.13988', 1, 0, 1504275251215638530, 0, '菜单权限', 1, NULL, NULL, NULL, 1, NULL, 'system.role.menu');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1687340992534953986, 1503940219005603842, '2023-08-04 13:53:36.557268', 1503940219005603842, '2023-08-04 13:53:36.557268', 1, 0, 1504275251215638530, 0, '数据权限', 1, NULL, NULL, NULL, 1, NULL, 'system.role.dept');

ALTER TABLE "public"."seas_bas_auth_menu" 
  ADD COLUMN "is_checked" int2;

COMMENT ON COLUMN "public"."seas_bas_auth_menu"."role_id" IS '角色ID';

COMMENT ON COLUMN "public"."seas_bas_auth_menu"."is_checked" IS '选中的，0否，1是';

DROP TABLE IF EXISTS "public"."seas_bas_user_org";

ALTER TABLE "public"."seas_bas_user"
    ADD COLUMN "org_id" int8;

COMMENT ON COLUMN "public"."seas_bas_user"."org_id" IS '组织ID';

INSERT INTO "public"."seas_bas_org" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "org_name", "org_code", "org_type", "org_level", "org_address", "manager_name", "manager_mobile", "remarks", "sort") VALUES (1, NULL, NULL, NULL, NULL, 1, 0, 0, '顶级部门', '001', NULL, 1, NULL, NULL, NULL, NULL, 1);

INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1680777632112754690, 1503940219005603842, '2023-07-17 11:13:09.50717', 1503940219005603842, '2023-07-17 11:13:09.50717', 1, 0, 1504021318394232834, 3, '组织管理', 0, NULL, 'system/org', NULL, 1, '../../view/base/OrgPage.vue', 'system.org');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1680778011915370498, 1503940219005603842, '2023-07-17 11:14:40.048097', 1503940219005603842, '2023-07-17 11:14:40.048097', 1, 0, 1680777632112754690, 0, '新增', 1, NULL, NULL, NULL, 1, NULL, 'system.org.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1680778160590864386, 1503940219005603842, '2023-07-17 11:15:15.491598', 1503940219005603842, '2023-07-17 11:15:15.491598', 1, 0, 1680777632112754690, 0, '删除', 1, NULL, NULL, NULL, 1, NULL, 'system.org.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1680778264626380801, 1503940219005603842, '2023-07-17 11:15:40.307749', 1503940219005603842, '2023-07-17 11:15:40.307749', 1, 0, 1680777632112754690, 0, '页面', 1, NULL, NULL, NULL, 1, NULL, 'system.org.page');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1680778113706934273, 1503940219005603842, '2023-07-17 11:15:04.324819', 1503940219005603842, '2023-07-17 11:15:04.324819', 1, 0, 1680777632112754690, 0, '修改', 1, NULL, NULL, NULL, 1, NULL, 'system.org.update');


