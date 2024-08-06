create table seas_mes_info
(
    id           bigint not null
        constraint seas_mes_info_pk
            primary key,
    title        varchar(1024),
    preview      varchar(4096),
    content      text,
    type         varchar(64),
    created_by   bigint,
    created_time timestamp
);

comment on table seas_mes_info is '消息信息表';

comment on column seas_mes_info.id is 'ID';

comment on column seas_mes_info.title is '消息标题';

comment on column seas_mes_info.preview is '预览图';

comment on column seas_mes_info.content is '消息内容';

comment on column seas_mes_info.type is '消息类型';

comment on column seas_mes_info.created_by is '创建人';

comment on column seas_mes_info.created_time is '创建时间';

alter table seas_mes_info
    owner to postgres;

create table seas_mes_template
(
    id           bigint not null
        constraint seas_mes_template_pk
            primary key,
    name         varchar(255),
    key          varchar(255),
    type         varchar(64),
    title        varchar(1024),
    preview      varchar(4096),
    content      text,
    remark       varchar(1024),
    created_by   bigint,
    created_time timestamp,
    updated_by   bigint,
    updated_time timestamp,
    version      integer  default 1,
    logic_del    smallint default 1
);

comment on table seas_mes_template is '消息模板表';

comment on column seas_mes_template.id is 'ID';

comment on column seas_mes_template.name is '模板名称';

comment on column seas_mes_template.key is '模板标识';

comment on column seas_mes_template.type is '模板类型';

comment on column seas_mes_template.title is '模板消息标题';

comment on column seas_mes_template.preview is '模板消息预览图';

comment on column seas_mes_template.content is '模板消息内容';

comment on column seas_mes_template.remark is '备注';

comment on column seas_mes_template.created_by is '创建人';

comment on column seas_mes_template.created_time is '创建时间';

comment on column seas_mes_template.updated_by is '修改人';

comment on column seas_mes_template.updated_time is '修改时间';

comment on column seas_mes_template.version is '版本';

comment on column seas_mes_template.logic_del is '逻辑删除';

alter table seas_mes_template
    owner to postgres;

create table seas_mes_target
(
    id          bigint not null
        constraint seas_mes_target_pk
            primary key,
    user_id     bigint,
    msg_id      bigint,
    type        varchar(64),
    status      smallint,
    push_status smallint
);

comment on table seas_mes_target is '消息目标表';

comment on column seas_mes_target.id is 'ID';

comment on column seas_mes_target.user_id is '用户ID';

comment on column seas_mes_target.msg_id is '消息ID';

comment on column seas_mes_target.type is '目标客户端类型';

comment on column seas_mes_target.status is '状态：0未读、1已读';

comment on column seas_mes_target.push_status is '推送状态：0默认、1未推送、2已推送、3推送失败';

alter table seas_mes_target
    owner to postgres;

