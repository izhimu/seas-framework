-- ----------------------------
-- Table structure for seas_job_timer
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_job_timer";
CREATE TABLE "public"."seas_job_timer" (
                                           "id" int8 NOT NULL,
                                           "name" varchar(255) COLLATE "pg_catalog"."default",
                                           "key" varchar(255) COLLATE "pg_catalog"."default",
                                           "type" int4,
                                           "expression" varchar(255) COLLATE "pg_catalog"."default",
                                           "class_path" varchar(512) COLLATE "pg_catalog"."default",
                                           "param" text COLLATE "pg_catalog"."default",
                                           "start_time" timestamp(6),
                                           "end_time" timestamp(6),
                                           "status" int4
)
;
COMMENT ON COLUMN "public"."seas_job_timer"."id" IS 'ID';
COMMENT ON COLUMN "public"."seas_job_timer"."name" IS '定时器名称';
COMMENT ON COLUMN "public"."seas_job_timer"."key" IS '定时器标识';
COMMENT ON COLUMN "public"."seas_job_timer"."type" IS '定时器类型：0.CRON 1.定时执行 2.间隔时间';
COMMENT ON COLUMN "public"."seas_job_timer"."expression" IS '定时表达式';
COMMENT ON COLUMN "public"."seas_job_timer"."class_path" IS '定时器类路径';
COMMENT ON COLUMN "public"."seas_job_timer"."param" IS '参数';
COMMENT ON COLUMN "public"."seas_job_timer"."start_time" IS '开始时间';
COMMENT ON COLUMN "public"."seas_job_timer"."end_time" IS '结束时间';
COMMENT ON COLUMN "public"."seas_job_timer"."status" IS '状态：0.就绪 1.运行 2.完成';
COMMENT ON TABLE "public"."seas_job_timer" IS '定时器表';

-- ----------------------------
-- Primary Key structure for table seas_job_timer
-- ----------------------------
ALTER TABLE "public"."seas_job_timer" ADD CONSTRAINT "seas_sys_timer_pkey" PRIMARY KEY ("id");


INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663020544474177538, 1503940219005603842, '2023-05-29 11:12:49.891434', 1503940219005603842, '2023-05-29 11:13:03.816503', 1, 0, 1, 4, '任务中心', 0, '', '', '', 1, '', 'job');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663020814591549441, 1503940219005603842, '2023-05-29 11:13:54.297419', NULL, NULL, 1, 0, 1663020544474177538, 1, '定时任务', 0, '', 'job/timer', '', 1, '../view/job/TimerPage.vue', 'job.timer');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663056613777301505, 1503940219005603842, '2023-05-29 13:36:09.488781', NULL, NULL, 1, 0, 1663020814591549441, 0, '新增', 1, '', '', '', 1, '', 'job.timer.add');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663056718324523010, 1503940219005603842, '2023-05-29 13:36:34.406926', NULL, NULL, 1, 0, 1663020814591549441, 0, '删除', 1, '', '', '', 1, '', 'job.timer.delete');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663783863085699074, 1503940219005603842, '2023-05-31 13:45:59.227596', NULL, NULL, 1, 0, 1663020814591549441, 0, '执行', 1, '', '', '', 1, '', 'job.timer.exec');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663783819993419777, 1503940219005603842, '2023-05-31 13:45:48.956793', NULL, NULL, 1, 0, 1663020814591549441, 0, '暂停', 1, '', '', '', 1, '', 'job.timer.pause');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663783770697764866, 1503940219005603842, '2023-05-31 13:45:37.207989', NULL, NULL, 1, 0, 1663020814591549441, 0, '运行', 1, '', '', '', 1, '', 'job.timer.run');
INSERT INTO "public"."seas_bas_menu" ("id", "created_by", "created_time", "updated_by", "updated_time", "version", "logic_del", "parent_id", "sort", "menu_name", "menu_type", "menu_icon", "menu_url", "remarks", "display", "menu_component", "menu_code") VALUES (1663056671180546049, 1503940219005603842, '2023-05-29 13:36:23.169225', NULL, NULL, 1, 0, 1663020814591549441, 0, '修改', 1, '', '', '', 1, '', 'job.timer.update');

