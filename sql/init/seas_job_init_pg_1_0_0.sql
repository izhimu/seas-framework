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
