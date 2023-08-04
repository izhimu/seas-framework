-- ----------------------------
-- Table structure for seas_sto_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."seas_sto_file";
CREATE TABLE "public"."seas_sto_file" (
                                          "id" int8 NOT NULL,
                                          "file_name" varchar(255) COLLATE "pg_catalog"."default",
                                          "content_type" varchar(255) COLLATE "pg_catalog"."default",
                                          "file_path" varchar(900) COLLATE "pg_catalog"."default",
                                          "file_size" int8,
                                          "file_suffix" varchar(90) COLLATE "pg_catalog"."default",
                                          "storage_type" varchar(90) COLLATE "pg_catalog"."default",
                                          "del_tag" int2 DEFAULT 0,
                                          "created_by" int8,
                                          "created_time" timestamp(6),
                                          "bind_id" int8
)
;
COMMENT ON COLUMN "public"."seas_sto_file"."id" IS '主键ID';
COMMENT ON COLUMN "public"."seas_sto_file"."file_name" IS '文件名称';
COMMENT ON COLUMN "public"."seas_sto_file"."content_type" IS '文件类型';
COMMENT ON COLUMN "public"."seas_sto_file"."file_path" IS '文件路径';
COMMENT ON COLUMN "public"."seas_sto_file"."file_size" IS '文件大小';
COMMENT ON COLUMN "public"."seas_sto_file"."file_suffix" IS '文件后缀';
COMMENT ON COLUMN "public"."seas_sto_file"."storage_type" IS '存储类型';
COMMENT ON COLUMN "public"."seas_sto_file"."del_tag" IS '删除标记;0，非删除，1，已删除';
COMMENT ON COLUMN "public"."seas_sto_file"."created_by" IS '创建人';
COMMENT ON COLUMN "public"."seas_sto_file"."created_time" IS '创建时间';
COMMENT ON COLUMN "public"."seas_sto_file"."bind_id" IS '绑定ID';
COMMENT ON TABLE "public"."seas_sto_file" IS '文件信息表';

-- ----------------------------
-- Primary Key structure for table seas_sto_file
-- ----------------------------
ALTER TABLE "public"."seas_sto_file" ADD CONSTRAINT "seas_sys_file_pkey" PRIMARY KEY ("id");
