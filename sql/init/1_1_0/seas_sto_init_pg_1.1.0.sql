create table seas_sto_file
(
    id           bigint not null
        constraint seas_sys_file_pkey
            primary key,
    file_name    varchar(255),
    content_type varchar(255),
    file_path    varchar(900),
    file_size    bigint,
    file_suffix  varchar(90),
    storage_type varchar(90),
    del_tag      smallint default 0,
    created_by   bigint,
    created_time timestamp(6),
    bind_id      bigint
);

comment on table seas_sto_file is '文件信息表';

comment on column seas_sto_file.id is '主键ID';

comment on column seas_sto_file.file_name is '文件名称';

comment on column seas_sto_file.content_type is '文件类型';

comment on column seas_sto_file.file_path is '文件路径';

comment on column seas_sto_file.file_size is '文件大小';

comment on column seas_sto_file.file_suffix is '文件后缀';

comment on column seas_sto_file.storage_type is '存储类型';

comment on column seas_sto_file.del_tag is '删除标记;0，非删除，1，已删除';

comment on column seas_sto_file.created_by is '创建人';

comment on column seas_sto_file.created_time is '创建时间';

comment on column seas_sto_file.bind_id is '绑定ID';

alter table seas_sto_file
    owner to postgres;

