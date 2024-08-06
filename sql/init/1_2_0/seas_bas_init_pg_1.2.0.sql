create table seas_bas_account
(
    id               bigint not null
        constraint seas_sys_account_pkey
            primary key,
    created_by       bigint,
    created_time     timestamp(6),
    updated_by       bigint,
    updated_time     timestamp(6),
    version          integer default 1,
    logic_del        integer default 0,
    user_id          bigint,
    user_account     varchar(64),
    user_certificate varchar(60),
    type_code        integer,
    status           integer,
    last_id          bigint
);

comment on table seas_bas_account is '用户账号表';

comment on column seas_bas_account.id is '主键ID';

comment on column seas_bas_account.created_by is '创建人';

comment on column seas_bas_account.created_time is '创建时间';

comment on column seas_bas_account.updated_by is '更新人';

comment on column seas_bas_account.updated_time is '更新时间';

comment on column seas_bas_account.version is '版本';

comment on column seas_bas_account.logic_del is '逻辑删除';

comment on column seas_bas_account.user_id is '用户ID';

comment on column seas_bas_account.user_account is '用户账号';

comment on column seas_bas_account.user_certificate is '用户密码';

comment on column seas_bas_account.type_code is '用户类型';

comment on column seas_bas_account.status is '账号状态';

comment on column seas_bas_account.last_id is '最后登录信息ID';

alter table seas_bas_account
    owner to postgres;

create table seas_bas_account_log
(
    id               bigint not null
        constraint seas_sys_account_log_pkey
            primary key,
    login_time       timestamp(6),
    login_device_id  bigint,
    login_device     varchar(255),
    login_ip         varchar(39),
    login_address    varchar(512),
    login_version    varchar(128),
    login_os_version varchar(255),
    user_id          bigint,
    account_id       bigint,
    status           integer
);

comment on table seas_bas_account_log is '登录日志表';

comment on column seas_bas_account_log.id is '主键ID';

comment on column seas_bas_account_log.login_time is '登录时间';

comment on column seas_bas_account_log.login_device_id is '登录设备ID';

comment on column seas_bas_account_log.login_device is '登录设备名称';

comment on column seas_bas_account_log.login_ip is '登录IP';

comment on column seas_bas_account_log.login_address is '登录地址';

comment on column seas_bas_account_log.login_version is '登录版本';

comment on column seas_bas_account_log.login_os_version is '系统版本';

comment on column seas_bas_account_log.user_id is '用户ID';

comment on column seas_bas_account_log.account_id is '账号ID';

comment on column seas_bas_account_log.status is '状态：0.登录，1.退出，2.密码错误';

alter table seas_bas_account_log
    owner to postgres;

create table seas_bas_auth_menu
(
    id         bigint not null
        constraint seas_sys_auth_menu_pkey
            primary key,
    menu_id    bigint,
    role_id    bigint,
    is_checked smallint
);

comment on table seas_bas_auth_menu is '菜单权限表';

comment on column seas_bas_auth_menu.id is '主键ID';

comment on column seas_bas_auth_menu.menu_id is '菜单ID';

comment on column seas_bas_auth_menu.role_id is '角色ID';

comment on column seas_bas_auth_menu.is_checked is '选中的，0否，1是';

alter table seas_bas_auth_menu
    owner to postgres;

create table seas_bas_auth_org
(
    id      bigint not null
        constraint seas_sys_auth_org_pkey
            primary key,
    org_id  bigint,
    role_id bigint
);

comment on table seas_bas_auth_org is '组织权限表';

comment on column seas_bas_auth_org.id is '主键ID';

comment on column seas_bas_auth_org.org_id is '组织ID';

comment on column seas_bas_auth_org.role_id is '角色ID';

alter table seas_bas_auth_org
    owner to postgres;

create table seas_bas_conf
(
    id           bigint not null
        primary key,
    conf_name    varchar(255),
    conf_key     varchar(255),
    conf_value   text,
    conf_info    varchar(255),
    created_by   bigint,
    created_time timestamp(6),
    updated_by   bigint,
    updated_time timestamp(6),
    version      integer default 1,
    logic_del    integer default 0
);

comment on table seas_bas_conf is '配置信息表';

comment on column seas_bas_conf.id is 'ID';

comment on column seas_bas_conf.conf_name is '配置名称';

comment on column seas_bas_conf.conf_key is '配置标识';

comment on column seas_bas_conf.conf_value is '配置值';

comment on column seas_bas_conf.conf_info is '备注信息';

comment on column seas_bas_conf.created_by is '创建者';

comment on column seas_bas_conf.created_time is '创建时间';

comment on column seas_bas_conf.updated_by is '修改者';

comment on column seas_bas_conf.updated_time is '修改时间';

comment on column seas_bas_conf.version is '版本';

comment on column seas_bas_conf.logic_del is '逻辑删除';

alter table seas_bas_conf
    owner to postgres;

create table seas_bas_device
(
    id             bigint not null
        constraint seas_sys_device_pkey
            primary key,
    created_by     bigint,
    created_time   timestamp(6),
    updated_by     bigint,
    updated_time   timestamp(6),
    user_id        bigint,
    device_name    varchar(128),
    device_code    varchar(255),
    system_version varchar(255),
    last_id        bigint
);

comment on table seas_bas_device is '用户设备表';

comment on column seas_bas_device.id is '主键ID';

comment on column seas_bas_device.created_by is '创建人';

comment on column seas_bas_device.created_time is '创建时间';

comment on column seas_bas_device.updated_by is '更新人';

comment on column seas_bas_device.updated_time is '更新时间';

comment on column seas_bas_device.user_id is '用户ID';

comment on column seas_bas_device.device_name is '设备名称';

comment on column seas_bas_device.device_code is '设备编号';

comment on column seas_bas_device.system_version is '系统版本';

comment on column seas_bas_device.last_id is '最后登录信息ID';

alter table seas_bas_device
    owner to postgres;

create table seas_bas_dict
(
    id           bigint not null
        constraint seas_sys_dict_pkey
            primary key,
    parent_id    bigint,
    dict_name    varchar(255),
    dict_code    varchar(255),
    fixed        smallint,
    has_children smallint,
    sort         integer
);

comment on table seas_bas_dict is '字典表';

comment on column seas_bas_dict.id is '主键ID';

comment on column seas_bas_dict.parent_id is '父级ID';

comment on column seas_bas_dict.dict_name is '字典名称';

comment on column seas_bas_dict.dict_code is '字典值';

comment on column seas_bas_dict.fixed is '是否固定';

comment on column seas_bas_dict.has_children is '是否有子项';

comment on column seas_bas_dict.sort is '排序';

alter table seas_bas_dict
    owner to postgres;

create table seas_bas_log
(
    id           bigint not null
        constraint seas_sys_log_pkey
            primary key,
    request_url  varchar(255),
    method       varchar(32),
    params       json,
    result       json,
    request_date timestamp(6),
    user_id      bigint,
    user_name    varchar(255),
    log_name     varchar(255),
    log_type     varchar(64),
    ip           varchar(39),
    status       varchar(3),
    runtime      bigint,
    account      varchar(64)
);

comment on table seas_bas_log is '操作日志表';

comment on column seas_bas_log.id is '主键ID';

comment on column seas_bas_log.request_url is '请求地址';

comment on column seas_bas_log.method is '请求方法';

comment on column seas_bas_log.params is '请求参数';

comment on column seas_bas_log.result is '响应结果';

comment on column seas_bas_log.request_date is '请求时间';

comment on column seas_bas_log.user_id is '操作用户';

comment on column seas_bas_log.user_name is '用户名';

comment on column seas_bas_log.log_name is '日志名称';

comment on column seas_bas_log.log_type is '日志类型';

comment on column seas_bas_log.ip is 'IP';

comment on column seas_bas_log.status is '请求状态';

comment on column seas_bas_log.runtime is '执行用时';

comment on column seas_bas_log.account is '账号';

alter table seas_bas_log
    owner to postgres;

create table seas_bas_menu
(
    id             bigint not null
        constraint seas_sys_menu_pkey
            primary key,
    created_by     bigint,
    created_time   timestamp(6),
    updated_by     bigint,
    updated_time   timestamp(6),
    version        integer  default 1,
    logic_del      smallint default 0,
    parent_id      bigint,
    sort           integer,
    menu_name      varchar(255),
    menu_type      bigint,
    menu_icon      varchar(255),
    menu_url       varchar(900),
    remarks        varchar(900),
    display        smallint,
    menu_component varchar(255),
    menu_code      varchar(255)
);

comment on table seas_bas_menu is '菜单表';

comment on column seas_bas_menu.id is '主键ID';

comment on column seas_bas_menu.created_by is '创建人';

comment on column seas_bas_menu.created_time is '创建时间';

comment on column seas_bas_menu.updated_by is '更新人';

comment on column seas_bas_menu.updated_time is '更新时间';

comment on column seas_bas_menu.version is '版本';

comment on column seas_bas_menu.logic_del is '逻辑删除';

comment on column seas_bas_menu.parent_id is '父级ID';

comment on column seas_bas_menu.sort is '排序';

comment on column seas_bas_menu.menu_name is '菜单名称';

comment on column seas_bas_menu.menu_type is '菜单类型';

comment on column seas_bas_menu.menu_icon is '菜单图标';

comment on column seas_bas_menu.menu_url is '菜单地址';

comment on column seas_bas_menu.remarks is '备注';

comment on column seas_bas_menu.display is '是否显示';

comment on column seas_bas_menu.menu_component is '菜单组件';

comment on column seas_bas_menu.menu_code is '菜单标识';

alter table seas_bas_menu
    owner to postgres;

create table seas_bas_org
(
    id             bigint not null
        constraint seas_sys_org_pkey
            primary key,
    created_by     bigint,
    created_time   timestamp(6),
    updated_by     bigint,
    updated_time   timestamp(6),
    version        integer  default 1,
    logic_del      smallint default 0,
    parent_id      bigint,
    org_name       varchar(255),
    org_code       varchar(255),
    org_type       varchar(255),
    org_level      integer,
    org_address    varchar(255),
    manager_name   varchar(255),
    manager_mobile varchar(255),
    remarks        varchar(900),
    sort           integer
);

comment on table seas_bas_org is '组织架构表';

comment on column seas_bas_org.id is '主键ID';

comment on column seas_bas_org.created_by is '创建人';

comment on column seas_bas_org.created_time is '创建时间';

comment on column seas_bas_org.updated_by is '更新人';

comment on column seas_bas_org.updated_time is '更新时间';

comment on column seas_bas_org.version is '版本';

comment on column seas_bas_org.logic_del is '逻辑删除';

comment on column seas_bas_org.parent_id is '父级ID';

comment on column seas_bas_org.org_name is '组织名称';

comment on column seas_bas_org.org_code is '组织代码';

comment on column seas_bas_org.org_type is '组织类型';

comment on column seas_bas_org.org_level is '级别';

comment on column seas_bas_org.org_address is '组织地址';

comment on column seas_bas_org.manager_name is '组织负责人';

comment on column seas_bas_org.manager_mobile is '组织负责人联系方式';

comment on column seas_bas_org.remarks is '备注';

comment on column seas_bas_org.sort is '排序';

alter table seas_bas_org
    owner to postgres;

create table seas_bas_role
(
    id           bigint not null,
    created_by   bigint,
    created_time timestamp(6),
    updated_by   bigint,
    updated_time timestamp(6),
    role_name    varchar(255),
    role_desc    varchar(512),
    enable       smallint,
    sort         integer,
    auth_type    smallint,
    dept_cascade smallint
);

comment on table seas_bas_role is '用户角色表';

comment on column seas_bas_role.id is '主键ID';

comment on column seas_bas_role.created_by is '创建人';

comment on column seas_bas_role.created_time is '创建时间';

comment on column seas_bas_role.updated_by is '更新人';

comment on column seas_bas_role.updated_time is '更新时间';

comment on column seas_bas_role.role_name is '角色名称';

comment on column seas_bas_role.role_desc is '角色描述';

comment on column seas_bas_role.enable is '是否启用：0否、1是';

comment on column seas_bas_role.sort is '优先级';

comment on column seas_bas_role.auth_type is '权限模式：0全部数据权限、1自定义数据权限、2本部门及下级部门数据权限、3本部门数据权限、4仅自己数据权限';

comment on column seas_bas_role.dept_cascade is '部门级联选择：0否、1是';

alter table seas_bas_role
    owner to postgres;

create table seas_bas_user
(
    id           bigint not null
        constraint seas_sys_user_pkey
            primary key,
    created_by   bigint,
    created_time timestamp(6),
    updated_by   bigint,
    updated_time timestamp(6),
    version      integer  default 1,
    logic_del    smallint default 0,
    user_name    varchar(255),
    user_sex     integer,
    birthday     date,
    signature    varchar(255),
    mobile       varchar(64),
    email        varchar(255),
    face         bigint,
    face_small   bigint,
    status       integer,
    org_id       bigint
);

comment on table seas_bas_user is '用户表';

comment on column seas_bas_user.id is '主键ID';

comment on column seas_bas_user.created_by is '创建人';

comment on column seas_bas_user.created_time is '创建时间';

comment on column seas_bas_user.updated_by is '更新人';

comment on column seas_bas_user.updated_time is '更新时间';

comment on column seas_bas_user.version is '版本';

comment on column seas_bas_user.logic_del is '逻辑删除';

comment on column seas_bas_user.user_name is '用户昵称';

comment on column seas_bas_user.user_sex is '用户性别;0.保密1.男2.女';

comment on column seas_bas_user.birthday is '用户生日';

comment on column seas_bas_user.signature is '个人签名';

comment on column seas_bas_user.mobile is '手机号';

comment on column seas_bas_user.email is '邮箱';

comment on column seas_bas_user.face is '头像';

comment on column seas_bas_user.face_small is '小头像';

comment on column seas_bas_user.status is '状态';

comment on column seas_bas_user.org_id is '组织ID';

alter table seas_bas_user
    owner to postgres;

create table seas_bas_user_location
(
    id            bigint not null
        constraint seas_sys_user_location_pkey
            primary key,
    created_by    bigint,
    created_time  timestamp(6),
    updated_by    bigint,
    updated_time  timestamp(6),
    user_id       bigint,
    curr_nation   varchar(128),
    curr_province varchar(128),
    curr_city     varchar(128),
    curr_district varchar(128),
    area_number   varchar(128),
    location      varchar(512),
    lng           numeric(10, 8),
    lat           numeric(10, 8)
);

comment on table seas_bas_user_location is '用户地址表';

comment on column seas_bas_user_location.id is '主键ID';

comment on column seas_bas_user_location.created_by is '创建人';

comment on column seas_bas_user_location.created_time is '创建时间';

comment on column seas_bas_user_location.updated_by is '更新人';

comment on column seas_bas_user_location.updated_time is '更新时间';

comment on column seas_bas_user_location.user_id is '用户ID';

comment on column seas_bas_user_location.curr_nation is '所在国家';

comment on column seas_bas_user_location.curr_province is '所在省区';

comment on column seas_bas_user_location.curr_city is '所在城市';

comment on column seas_bas_user_location.curr_district is '所在区县';

comment on column seas_bas_user_location.area_number is '行政代码';

comment on column seas_bas_user_location.location is '详细地址';

comment on column seas_bas_user_location.lng is '经度';

comment on column seas_bas_user_location.lat is '纬度';

alter table seas_bas_user_location
    owner to postgres;

create table seas_bas_user_role
(
    id      bigint not null
        constraint seas_sys_user_role_pkey
            primary key,
    user_id bigint,
    role_id bigint
);

comment on table seas_bas_user_role is '用户角色中间表';

comment on column seas_bas_user_role.id is '主键ID';

comment on column seas_bas_user_role.user_id is '用户ID';

comment on column seas_bas_user_role.role_id is '角色ID';

alter table seas_bas_user_role
    owner to postgres;


INSERT INTO seas_bas_account (id, created_by, created_time, updated_by, updated_time, version, logic_del, user_id, user_account, user_certificate, type_code, status, last_id) VALUES (1503940219529891842, 0, '2022-03-16 11:44:24.851225', 1503940219005603842, '2023-08-18 13:27:00.695064', 597, 0, 1503940219005603842, 'admin', '$2a$10$0sSR7YUV.aEuS/yczspyuuYqsYf1BSdlafu1Z4o3NeidOkjFVGGeW', 0, 0, 1781128559575289857);

INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1, 0, '根节点', 'root', 1, 1, 0);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1503298977521344514, 1503298210886459393, '女', '2', 1, 0, 2);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1503298887343808514, 1503298210886459393, '男', '1', 1, 0, 1);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1680815178985308162, 1680814812847734785, '部门', 'dept', 1, null, 2);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1503299007149907970, 1503298210886459393, '保密', '0', 1, 0, 0);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1680815142687801345, 1680814812847734785, '企业', 'comp', 1, null, 1);
INSERT INTO seas_bas_dict (id, parent_id, dict_name, dict_code, fixed, has_children, sort) VALUES (1680814812847734785, 1, '组织类型', 'org.type', 1, null, 2);

INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1, 0, '2022-03-16 16:52:10.000000', null, null, 1, 0, 0, 0, '根节点', 0, null, '/', null, 1, null, null);
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122032395444225, 0, '2022-05-08 10:06:05.421572', null, null, 1, 0, 1504024693223505921, 0, '修改', 1, '', '', '', 1, '', 'system.user.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1729378831119319041, 1503940219005603842, '2023-11-28 13:56:58.301601', 1503940219005603842, '2023-11-28 13:56:58.302602', 1, 0, 1504024693223505921, 0, '解锁', 1, null, null, null, 1, null, 'system.user.unlock');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1672902105336872961, 1503940219005603842, '2023-06-25 17:38:37.588230', 1503940219005603842, '2023-06-25 17:38:37.588319', 1, 0, 1504024693223505921, 0, '页面', 1, null, null, null, 1, null, 'system.user.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122080667688962, 0, '2022-05-08 10:06:16.938683', null, null, 1, 0, 1504024693223505921, 0, '删除', 1, '', '', '', 1, '', 'system.user.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523121966846861313, 0, '2022-05-08 10:05:49.802942', null, null, 1, 0, 1504024693223505921, 0, '新增', 1, '', '', '', 1, '', 'system.user.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1504024693223505921, 0, '2022-03-16 17:20:04.949264', 1503940219005603842, '2023-07-17 11:13:26.444826', 1, 0, 1504021318394232834, 1, '用户管理', 0, '@vicons/material/AccountCircleFilled', 'system/user', '', 1, '../view/system/UserPage.vue', 'system.user');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1687331937087143938, 1503940219005603842, '2023-08-04 13:17:37.573303', 1503940219005603842, '2023-08-04 13:17:37.573303', 1, 0, 1504275251215638530, 0, '分配用户', 1, null, null, null, 1, null, 'system.role.user');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122229762613250, 0, '2022-05-08 10:06:52.479570', 0, '2022-05-08 10:07:10.030886', 1, 0, 1504275251215638530, 0, '修改', 1, '', '', '', 1, '', 'system.role.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1672902171363606530, 1503940219005603842, '2023-06-25 17:38:53.330109', 1503940219005603842, '2023-06-25 17:38:53.330209', 1, 0, 1504275251215638530, 0, '页面', 1, null, null, null, 1, null, 'system.role.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1687340940445892609, 1503940219005603842, '2023-08-04 13:53:24.139880', 1503940219005603842, '2023-08-04 13:53:24.139880', 1, 0, 1504275251215638530, 0, '菜单权限', 1, null, null, null, 1, null, 'system.role.menu');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1687340992534953986, 1503940219005603842, '2023-08-04 13:53:36.557268', 1503940219005603842, '2023-08-04 13:53:36.557268', 1, 0, 1504275251215638530, 0, '数据权限', 1, null, null, null, 1, null, 'system.role.dept');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122404509900801, 0, '2022-05-08 10:07:34.147649', null, null, 1, 0, 1504275251215638530, 0, '删除', 1, '', '', '', 1, '', 'system.role.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122350214635521, 0, '2022-05-08 10:07:21.198020', 1503940219005603842, '2023-08-04 13:53:02.268738', 1, 1, 1504275251215638530, 0, '授权', 1, '', '', '', 1, '', 'system.role.auth');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122172602638337, 0, '2022-05-08 10:06:38.850155', null, null, 1, 0, 1504275251215638530, 0, '新增', 1, '', '', '', 1, '', 'system.role.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1504275251215638530, 0, '2022-03-17 09:55:42.630279', 1503940219005603842, '2023-07-17 11:13:30.693688', 1, 0, 1504021318394232834, 2, '角色管理', 0, '@vicons/material/AdminPanelSettingsFilled', 'system/role', '', 1, '../view/system/RolePage.vue', 'system.role');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1680778113706934273, 1503940219005603842, '2023-07-17 11:15:04.324819', 1503940219005603842, '2023-07-17 11:15:04.324819', 1, 0, 1680777632112754690, 0, '修改', 1, null, null, null, 1, null, 'system.org.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1680778264626380801, 1503940219005603842, '2023-07-17 11:15:40.307749', 1503940219005603842, '2023-07-17 11:15:40.307749', 1, 0, 1680777632112754690, 0, '页面', 1, null, null, null, 1, null, 'system.org.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1680778160590864386, 1503940219005603842, '2023-07-17 11:15:15.491598', 1503940219005603842, '2023-07-17 11:15:15.491598', 1, 0, 1680777632112754690, 0, '删除', 1, null, null, null, 1, null, 'system.org.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1680778011915370498, 1503940219005603842, '2023-07-17 11:14:40.048097', 1503940219005603842, '2023-07-17 11:14:40.048097', 1, 0, 1680777632112754690, 0, '新增', 1, null, null, null, 1, null, 'system.org.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1680777632112754690, 1503940219005603842, '2023-07-17 11:13:09.507170', 1503940219005603842, '2023-07-17 11:13:09.507170', 1, 0, 1504021318394232834, 3, '组织管理', 0, null, 'system/org', null, 1, '../../view/base/OrgPage.vue', 'system.org');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523121789578797057, 0, '2022-05-08 10:05:07.528882', null, null, 1, 0, 1504280947042721794, 0, '修改', 1, '', '', '', 1, '', 'system.menu.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1672902238283726849, 1503940219005603842, '2023-06-25 17:39:09.286062', 1503940219005603842, '2023-06-25 17:39:09.286137', 1, 0, 1504280947042721794, 0, '页面', 1, null, null, null, 1, null, 'system.menu.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523121850035494914, 0, '2022-05-08 10:05:21.951854', null, null, 1, 0, 1504280947042721794, 0, '删除', 1, '', '', '', 1, '', 'system.menu.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523119481986002945, 0, '2022-05-08 09:55:57.355407', null, null, 1, 0, 1504280947042721794, 0, '新增', 1, '', '', '', 1, '', 'system.menu.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1504280947042721794, 0, '2022-03-17 10:18:20.620298', 1503940219005603842, '2023-07-17 11:13:18.146854', 1, 0, 1504021318394232834, 4, '菜单管理', 0, '@vicons/material/MenuFilled', 'system/menu', '', 1, '../view/system/MenuPage.vue', 'system.menu');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1555753033859805186, 0, '2022-08-06 11:10:02.423087', 0, '2022-08-06 11:10:26.575806', 1, 0, 1523123079205642241, 0, '详情', 1, '', '', '', 1, '', 'system.login.log.see');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122608155942914, 0, '2022-05-08 10:08:22.692929', null, null, 1, 0, 1504281160557961218, 0, '修改', 1, '', '', '', 1, '', 'system.dict.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1672902289961746434, 1503940219005603842, '2023-06-25 17:39:21.607159', 1503940219005603842, '2023-06-25 17:39:21.607253', 1, 0, 1504281160557961218, 0, '页面', 1, null, null, null, 1, null, 'system.dict.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122658332401666, 0, '2022-05-08 10:08:34.662625', 0, '2022-05-08 10:08:41.625905', 1, 0, 1504281160557961218, 0, '删除', 1, '', '', '', 1, '', 'system.dict.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1523122530271911937, 0, '2022-05-08 10:08:04.130100', null, null, 1, 0, 1504281160557961218, 0, '新增', 1, '', '', '', 1, '', 'system.dict.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1504281160557961218, 0, '2022-03-17 10:19:11.526878', 1503940219005603842, '2023-07-17 11:13:45.307548', 1, 0, 1504021318394232834, 5, '字典管理', 0, '@vicons/material/LibraryBooksFilled', 'system/dict', '', 1, '../view/system/DictPage.vue', 'system.dict');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1675325414257496066, 1503940219005603842, '2023-07-02 10:07:59.471791', 1503940219005603842, '2023-07-02 10:07:59.472797', 1, 0, 1675325108299796481, 0, '修改', 1, null, null, null, 1, null, 'system.conf.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1675325230689587202, 1503940219005603842, '2023-07-02 10:07:15.695918', 1503940219005603842, '2023-07-02 10:07:15.695918', 1, 0, 1675325108299796481, 0, '页面', 1, null, null, null, 1, null, 'system.conf.page');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1675325462932393985, 1503940219005603842, '2023-07-02 10:08:11.072562', 1503940219005603842, '2023-07-02 10:08:11.072562', 1, 0, 1675325108299796481, 0, '删除', 1, null, null, null, 1, null, 'system.conf.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1675325286675156993, 1503940219005603842, '2023-07-02 10:07:29.047948', 1503940219005603842, '2023-07-02 10:07:29.049516', 1, 0, 1675325108299796481, 0, '新增', 1, null, null, null, 1, null, 'system.conf.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1675325108299796481, 1503940219005603842, '2023-07-02 10:06:46.522868', 1503940219005603842, '2023-07-17 11:13:50.843241', 1, 0, 1504021318394232834, 6, '配置管理', 0, null, 'system/conf', null, 1, '../../view/base/ConfPage.vue', 'system.conf');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1504021318394232834, 0, '2022-03-16 17:06:40.327935', 1503940219005603842, '2023-08-14 17:12:30.380613', 1, 0, 1, 100, '系统管理', 0, '@vicons/material/SettingsFilled', '', '', 1, '', 'system');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1618597930271272962, 1503940219005603842, '2023-01-26 21:13:13.336251', 1503940219005603842, '2023-01-26 21:13:38.568204', 1, 0, 1618438381765664769, 0, '详情', 1, '', '', '', 1, '', 'log.log.see');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1618597874075987969, 1503940219005603842, '2023-01-26 21:12:59.946782', null, null, 1, 0, 1618436852602118146, 0, '详情', 1, '', '', '', 1, '', 'log.login.log.see');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1618436852602118146, 1503940219005603842, '2023-01-26 10:33:09.430108', 1503940219005603842, '2023-01-26 10:37:58.778294', 1, 0, 1618435280912523265, 1, '登录日志', 0, '', 'log/login/log', '', 1, '../view/system/LoginLogPage.vue', 'log.login.log');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1618438381765664769, 1503940219005603842, '2023-01-26 10:39:14.010153', null, null, 1, 0, 1618435280912523265, 2, '操作日志', 0, '', 'log/log', '', 1, '../view/system/LogPage.vue', 'log.log');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1618435280912523265, 1503940219005603842, '2023-01-26 10:26:54.701865', 1503940219005603842, '2023-06-16 13:36:40.945125', 1, 0, 1, 101, '日志中心', 0, '', '', '', 1, '', 'log');


INSERT INTO seas_bas_org (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, org_name, org_code, org_type, org_level, org_address, manager_name, manager_mobile, remarks, sort) VALUES (1, null, null, null, null, 1, 0, 0, '根节点', '001', null, 1, null, null, null, null, 1);

INSERT INTO seas_bas_role (id, created_by, created_time, updated_by, updated_time, role_name, role_desc, enable, sort, auth_type, dept_cascade) VALUES (1503190848421031938, 0, '2022-03-14 10:06:40.000000', 1503940219005603842, '2023-05-22 13:19:57.000000', '系统管理员', '系统最大权限角色', 1, 0, 2, 1);

INSERT INTO seas_bas_user (id, created_by, created_time, updated_by, updated_time, version, logic_del, user_name, user_sex, birthday, signature, mobile, email, face, face_small, status, org_id) VALUES (1503940219005603842, 0, '2022-03-16 11:44:24.727624', 1503940219005603842, '2023-08-18 13:27:00.638490', 1, 0, '系统管理员', 1, '1996-12-26', '', '17600000000', 'conghaoran1226@qq.com', 0, 0, 0, 1);

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