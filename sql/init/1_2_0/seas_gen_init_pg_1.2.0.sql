create table seas_gen_datasource
(
    id      bigint not null
        constraint seas_gen_datasource_pk
            primary key,
    ds_name varchar(255),
    ds_type varchar(64),
    ds_url  varchar(1024),
    ds_user varchar(1024),
    ds_pwd  varchar(1024),
    remark  varchar(1024),
    db_name varchar(512)
);

comment on table seas_gen_datasource is '数据源表';

comment on column seas_gen_datasource.id is 'ID';

comment on column seas_gen_datasource.ds_name is '数据源名称';

comment on column seas_gen_datasource.ds_type is '数据源类型';

comment on column seas_gen_datasource.ds_url is '数据源连接地址';

comment on column seas_gen_datasource.ds_user is '数据源用户名';

comment on column seas_gen_datasource.ds_pwd is '数据源密码';

comment on column seas_gen_datasource.remark is '备注';

comment on column seas_gen_datasource.db_name is '数据库名';

alter table seas_gen_datasource
    owner to postgres;

create table seas_gen_template
(
    id               bigint not null
        constraint seas_gen_template_pk
            primary key,
    template_name    varchar(255),
    template_version varchar(64),
    remark           varchar(1024)
);

comment on table seas_gen_template is '模板表';

comment on column seas_gen_template.id is 'ID';

comment on column seas_gen_template.template_name is '模板名称';

comment on column seas_gen_template.template_version is '模板版本';

alter table seas_gen_template
    owner to postgres;

create table seas_gen_template_assets
(
    id          bigint,
    template_id bigint,
    assets_name varchar(255),
    assets_data bytea,
    out_path    varchar(255),
    assets_type varchar(255)
);

comment on table seas_gen_template_assets is '模板资源表';

comment on column seas_gen_template_assets.id is 'ID';

comment on column seas_gen_template_assets.template_id is '模板ID';

comment on column seas_gen_template_assets.assets_name is '资源名称';

comment on column seas_gen_template_assets.assets_data is '资源数据';

comment on column seas_gen_template_assets.out_path is '相对输出路径';

comment on column seas_gen_template_assets.assets_type is '资源类型';

alter table seas_gen_template_assets
    owner to postgres;

INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743201947645874177, 1503940219005603842, '2024-01-05 17:25:06.101257', 1503940219005603842, '2024-01-05 17:25:06.101257', 1, 0, 1743200963020427266, 0, '修改', 1, null, null, null, 1, null, 'gen.template.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743202024628129794, 1503940219005603842, '2024-01-05 17:25:24.457635', 1503940219005603842, '2024-01-05 17:25:24.457635', 1, 0, 1743200963020427266, 0, '删除', 1, null, null, null, 1, null, 'gen.template.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743201986594181122, 1503940219005603842, '2024-01-05 17:25:15.389918', 1503940219005603842, '2024-01-05 17:25:15.389918', 1, 0, 1743200963020427266, 0, '模板资源', 1, null, null, null, 1, null, 'gen.template.assets');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743201890104217601, 1503940219005603842, '2024-01-05 17:24:52.383331', 1503940219005603842, '2024-01-05 17:24:52.383331', 1, 0, 1743200963020427266, 0, '新增', 1, null, null, null, 1, null, 'gen.template.add');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743200963020427266, 1503940219005603842, '2024-01-05 17:21:11.349102', 1503940219005603842, '2024-01-05 17:21:11.349102', 1, 0, 1743200716999331842, 1, '模板管理', 0, null, null, null, 1, null, 'generate.template');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743201226225586177, 1503940219005603842, '2024-01-05 17:22:14.102702', 1503940219005603842, '2024-01-05 17:22:14.102702', 1, 0, 1743200716999331842, 0, '代码生成', 0, null, null, null, 1, null, 'generate.info');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743200877544706050, 1503940219005603842, '2024-01-05 17:20:50.970905', 1503940219005603842, '2024-01-05 17:21:21.262236', 1, 0, 1743200716999331842, 2, '数据源管理', 0, null, null, null, 1, null, 'generate.datasource');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743200716999331842, 1503940219005603842, '2024-01-05 17:20:12.693892', 1503940219005603842, '2024-01-13 15:12:09.721248', 1, 0, 1, 104, '代码生成', 0, '@vicons/ionicons5/Copy', null, null, 1, null, 'generate');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743202159135264769, 1503940219005603842, '2024-01-05 17:25:56.524077', 1503940219005603842, '2024-01-05 17:25:56.524077', 1, 0, 1743200877544706050, 0, '修改', 1, null, null, null, 1, null, 'gen.datasource.update');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743202222284705793, 1503940219005603842, '2024-01-05 17:26:11.580936', 1503940219005603842, '2024-01-05 17:26:11.580936', 1, 0, 1743200877544706050, 0, '测试连接', 1, null, null, null, 1, null, 'gen.datasource.test');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743202264240328706, 1503940219005603842, '2024-01-05 17:26:21.584340', 1503940219005603842, '2024-01-05 17:26:21.584340', 1, 0, 1743200877544706050, 0, '删除', 1, null, null, null, 1, null, 'gen.datasource.delete');
INSERT INTO seas_bas_menu (id, created_by, created_time, updated_by, updated_time, version, logic_del, parent_id, sort, menu_name, menu_type, menu_icon, menu_url, remarks, display, menu_component, menu_code) VALUES (1743202106572247041, 1503940219005603842, '2024-01-05 17:25:43.992296', 1503940219005603842, '2024-01-05 17:25:43.992296', 1, 0, 1743200877544706050, 0, '新增', 1, null, null, null, 1, null, 'gen.datasource.add');


INSERT INTO seas_gen_template (id, template_name, template_version, remark) VALUES (1724710974355836929, 'Seas Framework 模板', 'v1.0.0', '适用于Seas Framework v1.1.7+');

INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747543343206936577, 1724710974355836929, '${fileName}.ts', e'import Qs from "qs";
import { Page, Result, api } from "@izhimu/seas-core";
import { ${className} } from "../entity/${fileName}";

const url = "${pathName}";

export const page = (
  // eslint-disable-next-line no-shadow
  page: Page<${className}>,
  search: ${className},
): Promise<Result<Page<${className}>>> => {
  return api().get(`${r\'${url}\'}/page`, {
    params: { ...page, ...search },
    paramsSerializer: {
      serialize(params) {
        return Qs.stringify(params, {
          arrayFormat: "indices",
          allowDots: true,
        });
      },
    },
  });
};

export const get = (id: string): Promise<Result<${className}>> => {
  return api().get(`${r\'${url}\'}/${r\'${id}\'}`);
};

export const save = (data: ${className}): Promise<Result<void>> => {
  return api().post(`${r\'${url}\'}`, data);
};

export const update = (data: ${className}): Promise<Result<void>> => {
  return api().put(`${r\'${url}\'}`, data);
};

export const del = (id: string): Promise<Result<void>> => {
  return api().delete(`${r\'${url}\'}/${r\'${id}\'}`);
};
', '/vue/request', 'typescript');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747549623996821506, 1724710974355836929, '${className}Page.vue', e'<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import { NCard, NInput, NButton, NDataTable, NSpace, NIcon } from "naive-ui";
import { usePage, useTableButton, SearchIcon } from "@izhimu/seas-core";
import { d${className} as defEntity } from "../entity/${fileName}";
import { page, del } from "../request/${fileName}";
import DataForm from "./${className}Form.vue";

const formRef = ref();
const { actionButton, confirmButton } = useTableButton();
const {
  columns,
  pagination,
  pageLoading,
  pageData,
  search,
  queryPage,
  handleSorter,
} = usePage(page, reactive(defEntity()), [
  {
    title: "序号",
    key: "no",
    render(_, rowIndex) {
      return rowIndex + 1;
    },
  },
<#list fieldList as field>
    <#if field.isPk == 0>
  {
    title: "${field.showName}",
    key: "${field.attrName}",
        <#if field.sortable == 1>
    sorter: true,
    sortOrder: false,
    sortKey: "${field.fieldName}",
        </#if>
  },
    </#if>
</#list>
  {
    title: "操作",
    key: "actions",
    render(rowData) {
      return [
        actionButton(
          "修改",
          "info",
          "${keyName}.update",
          () => {
            formRef.value.openModel(rowData.id);
          },
          {
            style: "margin-right: 8px;",
          },
        ),
        confirmButton(
          "删除",
          "确认删除数据？",
          "error",
          "${keyName}.delete",
          () => {
            if (rowData.id) {
              del(rowData.id).then(queryPage);
            }
          },
        ),
      ];
    },
  },
]);

const handleSearchClick = (e: MouseEvent) => {
  e.preventDefault();
  queryPage();
};

const handleAddClick = (e: MouseEvent) => {
  e.preventDefault();
  formRef.value.openModel();
};

onMounted(() => queryPage());
</script>

<template>
  <div>
    <div class="box-content">
      <n-space vertical size="large">
        <n-card>
          <n-space>
<#list fieldList as field>
    <#if field.isPk == 0>
        <#if field.searchable == 1>
            <n-input
              v-model:value="search.${field.attrName}"
              placeholder="${field.showName}"
              clearable
            />
        </#if>
    </#if>
</#list>
            <n-button type="info" @click="handleSearchClick">
              <template #icon>
                <n-icon>
                  <search-icon />
                </n-icon>
              </template>
              搜索
            </n-button>
          </n-space>
        </n-card>
        <n-card>
          <n-space vertical :size="12">
            <n-space>
              <n-button
                v-auth="\'${keyName}.add\'"
                strong
                secondary
                type="info"
                @click="handleAddClick"
              >新增
              </n-button>
            </n-space>
            <n-data-table
              remote
              :loading="pageLoading"
              :bordered="false"
              :columns="columns"
              :data="pageData"
              :pagination="pagination"
              @update:sorter="handleSorter"
              @update:page="queryPage"
            />
          </n-space>
        </n-card>
      </n-space>
    </div>
    <data-form ref="formRef" @success="queryPage" />
  </div>
</template>

<style scoped></style>
', '/vue/view', 'typescript');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747549624026181633, 1724710974355836929, '${className}Form.vue', e'<script setup lang="ts">
import { reactive, ref } from "vue";
import {
  FormInst,
  FormRules,
  NModal,
  NSpace,
  NButton,
  NForm,
  NFormItem,
  NInput,
  NSpin,
  useMessage,
} from "naive-ui";
import { useFormModel } from "@izhimu/seas-core";
import { get, save, update } from "../request/${fileName}";
import { d${className} as defEntity } from "../entity/${fileName}";

const message = useMessage();
const { btnLoading, dataLoading, showModel, addStatus } = useFormModel();

const emits = defineEmits(["success"]);

const formRef = ref<FormInst | null>(null);
const model = reactive(defEntity());

const rules = ref<FormRules>({});

const openModel = (id?: string) => {
  Object.assign(model, defEntity());
  showModel.value = true;
  addStatus.value = !id;
  if (id) {
    dataLoading.value = true;
    get(id)
      .then((res) => {
        Object.assign(model, res.data);
      })
      .finally(() => {
        dataLoading.value = false;
      });
  }
};

const closeModel = () => {
  showModel.value = false;
};

const onSuccess = () => {
  message.success("提交成功");
  emits("success");
};

const handleSubmit = (e: MouseEvent) => {
  e.preventDefault();
  formRef.value?.validate((errors) => {
    if (!errors) {
      btnLoading.value = true;
      if (addStatus.value) {
        save(model)
          .then(() => {
            onSuccess();
            closeModel();
          })
          .finally(() => {
            btnLoading.value = false;
          });
      } else {
        update(model)
          .then(() => {
            onSuccess();
            closeModel();
          })
          .finally(() => {
            btnLoading.value = false;
          });
      }
    }
  });
};

defineExpose({
  openModel,
});
</script>

<template>
  <n-modal
    v-model:show="showModel"
    style="width: 500px"
    preset="card"
    :title="addStatus ? \'新增\' : \'修改\'"
    :bordered="false"
    :segmented="{ content: true, action: true }"
    :mask-closable="false"
    @close="closeModel"
  >
    <n-spin :show="dataLoading">
      <n-form ref="formRef" :model="model" :rules="rules">
<#list fieldList as field>
    <#if field.isPk == 0>
        <n-form-item path="${field.attrName}" label="${field.showName}">
          <n-input
            v-model:value="model.${field.attrName}"
            placeholder="请输入${field.showName}"
            @keydown.enter.prevent
          />
        </n-form-item>
    </#if>
</#list>
      </n-form>
    </n-spin>
    <template #action>
      <n-space justify="end">
        <n-button @click="closeModel">关闭</n-button>
        <n-button
          type="info"
          :loading="btnLoading"
          :disabled="dataLoading"
          @click="handleSubmit"
          >提交
        </n-button>
      </n-space>
    </template>
  </n-modal>
</template>

<style scoped></style>
', '/vue/view', 'typescript');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747537511048196097, 1724710974355836929, '${className}Controller.java', e'package ${packageName}.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${tableDesc}控制层
 *
 * @author ${author}
 * @version v1.0
 */
@RestController
@RequestMapping("${pathName}")
public class ${className}Controller extends AbsBaseController<${className}Service, ${className}> {
    @Override
    public String logPrefix() {
        return "${tableDesc}";
    }
}
', '/java/controller', 'java');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1725061117378736129, 1724710974355836929, '${className}.java', e'package ${packageName}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
<#if importSearch>
import com.izhimu.seas.data.annotation.Search;
</#if>
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

<#list importList as import>
import ${import};
</#list>

/**
 * ${tableDesc}实体
 *
 * @author ${author}
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("${tableName}")
public class ${className} extends IdEntity {

<#list fieldList as field>
    <#if field.isPk == 0>
    /**
     * ${field.showName}
     */
        <#if field.searchable == 1>
    @Search
        </#if>
    private ${field.javaType} ${field.attrName};
    </#if>
</#list>
}
', '/java/entity', 'java');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747537510830092290, 1724710974355836929, '${className}Mapper.java', e'package ${packageName}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${packageName}.entity.${className};
import org.springframework.stereotype.Repository;

/**
 * ${tableDesc}映射层
 *
 * @author ${author}
 * @version v1.0
 */
@Repository
public interface ${className}Mapper extends BaseMapper<${className}> {
}
', '/java/mapper', 'java');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747537510939144193, 1724710974355836929, '${className}Service.java', e'package ${packageName}.service;

import com.izhimu.seas.data.service.IBaseService;
import ${packageName}.entity.${className};

/**
 * ${tableDesc}服务层接口
 *
 * @author ${author}
 * @version v1.0
 */
public interface ${className}Service extends IBaseService<${className}> {
}
', '/java/service', 'java');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747537510985281537, 1724710974355836929, '${className}ServiceImpl.java', e'package ${packageName}.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import ${packageName}.entity.${className};
import ${packageName}.mapper.${className}Mapper;
import ${packageName}.service.${className}Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${tableDesc}服务层实现
 *
 * @author ${author}
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Mapper, ${className}> implements ${className}Service {
}
', '/java/service/impl', 'java');
INSERT INTO seas_gen_template_assets (id, template_id, assets_name, assets_data, out_path, assets_type) VALUES (1747543272906207233, 1724710974355836929, '${fileName}.ts', e'export interface ${className} {
<#list fieldList as field>
  ${field.attrName}: ${field.jsType} | null;
</#list>
}

export const d${className} = (): ${className} => ({
<#list fieldList as field>
  ${field.attrName}: null,
</#list>
});
', '/vue/entity', 'typescript');
