<script setup lang="ts">
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
import { FormItemRule } from "naive-ui/lib/form/src/interface";
import { useFormModel } from "@izhimu/seas-core";
import { get, save, update, usable } from "../request/${fileName}";
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
    :title="addStatus ? '新增' : '修改'"
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
