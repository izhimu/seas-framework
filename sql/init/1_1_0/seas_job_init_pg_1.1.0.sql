create table seas_job_timer
(
    id         bigint not null
        constraint seas_sys_timer_pkey
            primary key,
    name       varchar(255),
    key        varchar(255),
    type       integer,
    expression varchar(255),
    class_path varchar(512),
    param      text,
    start_time timestamp(6),
    end_time   timestamp(6),
    status     integer
);

comment on table seas_job_timer is '定时器表';

comment on column seas_job_timer.id is 'ID';

comment on column seas_job_timer.name is '定时器名称';

comment on column seas_job_timer.key is '定时器标识';

comment on column seas_job_timer.type is '定时器类型：0.CRON 1.定时执行 2.间隔时间';

comment on column seas_job_timer.expression is '定时表达式';

comment on column seas_job_timer.class_path is '定时器类路径';

comment on column seas_job_timer.param is '参数';

comment on column seas_job_timer.start_time is '开始时间';

comment on column seas_job_timer.end_time is '结束时间';

comment on column seas_job_timer.status is '状态：0.就绪 1.运行 2.完成';

alter table seas_job_timer
    owner to postgres;

INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663056671180546049, 1503940219005603842, '2023-05-29 13:36:23.169225', null, null, 1, 0, 1663020814591549441, 0, '修改', 1, '', '', '', 1, '', 'job.timer.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663783770697764866, 1503940219005603842, '2023-05-31 13:45:37.207989', null, null, 1, 0, 1663020814591549441, 0, '运行', 1, '', '', '', 1, '', 'job.timer.run');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663783819993419777, 1503940219005603842, '2023-05-31 13:45:48.956793', null, null, 1, 0, 1663020814591549441, 0, '暂停', 1, '', '', '', 1, '', 'job.timer.pause');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1672902403732242434, 1503940219005603842, '2023-06-25 17:39:48.731195', 1503940219005603842, '2023-06-25 17:39:48.731290', 1, 0, 1663020814591549441, 0, '页面', 1, null, null, null, 1, null, 'job.timer.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663783863085699074, 1503940219005603842, '2023-05-31 13:45:59.227596', null, null, 1, 0, 1663020814591549441, 0, '执行', 1, '', '', '', 1, '', 'job.timer.exec');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663056718324523010, 1503940219005603842, '2023-05-29 13:36:34.406926', null, null, 1, 0, 1663020814591549441, 0, '删除', 1, '', '', '', 1, '', 'job.timer.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663056613777301505, 1503940219005603842, '2023-05-29 13:36:09.488781', null, null, 1, 0, 1663020814591549441, 0, '新增', 1, '', '', '', 1, '', 'job.timer.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663020814591549441, 1503940219005603842, '2023-05-29 11:13:54.297419', null, null, 1, 0, 1663020544474177538, 1, '定时任务', 0, '', 'job/timer', '', 1, '../view/job/TimerPage.vue', 'job.timer');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1663020544474177538, 1503940219005603842, '2023-05-29 11:12:49.891434', 1503940219005603842, '2023-06-16 13:36:50.207830', 1, 0, 1, 103, '任务中心', 0, '', '', '', 1, '', 'job');
