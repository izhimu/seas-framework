create table seas_ai_history
(
    id           bigint not null
        constraint seas_ai_history_pkey
            primary key,
    chat_id      bigint,
    sort         integer,
    role         varchar(32),
    token        bigint,
    total_token  bigint,
    message      bytea,
    created_by   bigint,
    created_time timestamp(6)
);

comment on table seas_ai_history is 'AI聊天记录表';

comment on column seas_ai_history.id is '主键ID';

comment on column seas_ai_history.chat_id is '聊天ID';

comment on column seas_ai_history.sort is '排序';

comment on column seas_ai_history.role is '角色';

comment on column seas_ai_history.token is 'token';

comment on column seas_ai_history.total_token is '总token';

comment on column seas_ai_history.message is '消息';

comment on column seas_ai_history.created_by is '创建人';

comment on column seas_ai_history.created_time is '创建时间';

alter table seas_ai_history
    owner to postgres;

