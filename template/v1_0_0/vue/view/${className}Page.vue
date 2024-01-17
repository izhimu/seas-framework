<script setup lang="ts">
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
    render(rowData, rowIndex) {
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
                  v-auth="'${keyName}.add'"
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
