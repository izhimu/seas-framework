create table seas_mqt_user
(
    id         bigint not null
        constraint seas_mqt_user_pk
            primary key,
    username   varchar(64),
    password   varchar(60),
    topic_auth text
);

comment on table seas_mqt_user is 'MQTT用户表';

comment on column seas_mqt_user.id is 'ID';

comment on column seas_mqt_user.username is '用户名';

comment on column seas_mqt_user.password is '密码';

comment on column seas_mqt_user.topic_auth is '允许订阅的主题（,分割）';

alter table seas_mqt_user
    owner to postgres;

INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1754034868963057666, 1503940219005603842, '2024-02-04 14:51:15.809072', 1503940219005603842, '2024-02-04 14:51:15.809072', 1, 0, 1754034684329795586, 0, '修改', 1, null, null, null, 1, null, 'mqt.user.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1754034917516320770, 1503940219005603842, '2024-02-04 14:51:27.385972', 1503940219005603842, '2024-02-04 14:51:27.385972', 1, 0, 1754034684329795586, 0, '删除', 1, null, null, null, 1, null, 'mqt.user.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1754034763509866497, 1503940219005603842, '2024-02-04 14:50:50.667117', 1503940219005603842, '2024-02-04 14:50:50.667117', 1, 0, 1754034684329795586, 0, '新增', 1, null, null, null, 1, null, 'mqt.user.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1754034684329795586, 1503940219005603842, '2024-02-04 14:50:31.790518', 1503940219005603842, '2024-02-04 14:50:31.790518', 1, 0, 1754034488233500673, 0, '鉴权管理', 0, null, null, null, 1, null, 'mqtt.user');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1754034488233500673, 1503940219005603842, '2024-02-04 14:49:45.037894', 1503940219005603842, '2024-02-04 14:49:54.908738', 1, 0, 1, 105, 'MQTT服务', 0, null, null, null, 1, null, 'mqtt');
