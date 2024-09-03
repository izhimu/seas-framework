create table seas_bas_topic
(
    id           bigint not null
        constraint seas_bas_topic_pk
            primary key,
    created_by   bigint,
    created_time timestamp,
    updated_by   bigint,
    updated_time timestamp,
    version      integer  default 1,
    logic_del    smallint default 0,
    sort         integer,
    topic_name   varchar(255),
    topic_code   varchar(255),
    index_url    varchar(900),
    remarks      varchar(900)
);

comment on table seas_bas_topic is '主题表';

comment on column seas_bas_topic.id is 'ID';

comment on column seas_bas_topic.created_by is '创建人';

comment on column seas_bas_topic.updated_time is '创建时间';

comment on column seas_bas_topic.updated_by is '修改人';

comment on column seas_bas_topic.updated_by is '修改时间';

comment on column seas_bas_topic.version is '版本';

comment on column seas_bas_topic.logic_del is '逻辑删除标识';

comment on column seas_bas_topic.sort is '排序';

comment on column seas_bas_topic.topic_name is '主题名称';

comment on column seas_bas_topic.topic_code is '主题标识';

comment on column seas_bas_topic.index_url is '首页地址';

comment on column seas_bas_topic.remarks is '备注';


create table seas_bas_auth_topic
(
    id       bigint not null
        constraint seas_bas_auth_topic_pk
            primary key,
    topic_id bigint,
    role_id  bigint
);

comment on table seas_bas_auth_topic is '角色主题关联表';

comment on column seas_bas_auth_topic.id is 'ID';

comment on column seas_bas_auth_topic.topic_id is '主题ID';

comment on column seas_bas_auth_topic.role_id is '角色ID';

alter table seas_bas_auth_topic
    owner to postgres;


create table seas_bas_topic_menu
(
    id         bigint not null
        constraint seas_bas_topic_menu_pk
            primary key,
    menu_id    bigint,
    topic_id   bigint,
    is_checked smallint
);

comment on table seas_bas_topic_menu is '主题菜单关联表';

comment on column seas_bas_topic_menu.id is 'ID';

comment on column seas_bas_topic_menu.menu_id is '菜单ID';

comment on column seas_bas_topic_menu.topic_id is '主题ID';

comment on column seas_bas_topic_menu.is_checked is '选中的，0否，1是';

alter table public.seas_gen_template
    add ext text;

comment on column public.seas_gen_template.ext is '附加参数';



