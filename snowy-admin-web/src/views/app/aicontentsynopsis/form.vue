<template>
  <xn-form-container
      :destroy-on-close="true"
      :title="formData.id ? '编辑AI 内容摘要' : '增加AI 内容摘要'"
      :visible="visible"
      :width="700"
      @close="onClose"
  >
    <a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
      <a-row :gutter="16">
        <a-col :span="12">
          <a-form-item label="内容原地址：" name="originalAddress">
            <a-input v-model:value="formData.originalAddress" allow-clear placeholder="请输入内容原地址"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="内容md5：" name="contentMd5">
            <a-input v-model:value="formData.contentMd5" allow-clear placeholder="请输入内容md5"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="摘要信息：" name="synopsisInfo">
            <a-input v-model:value="formData.synopsisInfo" allow-clear placeholder="请输入摘要信息"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="摘要思维导读：" name="synopsisMindMap">
            <a-input v-model:value="formData.synopsisMindMap" allow-clear placeholder="请输入摘要思维导读"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="目标内容：" name="content">
            <a-input v-model:value="formData.content" allow-clear placeholder="请输入目标内容"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="使用的 promptTemplate：" name="prompttemplate">
            <a-input v-model:value="formData.prompttemplate" allow-clear placeholder="请输入使用的 promptTemplate"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="是否为首次解析：" name="hasFirstSynopsis">
            <a-input v-model:value="formData.hasFirstSynopsis" allow-clear placeholder="请输入是否为首次解析"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="内容字数：" name="contentWords">
            <a-input v-model:value="formData.contentWords" allow-clear placeholder="请输入内容字数"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="标签，多标签逗号分隔：" name="tags">
            <a-input v-model:value="formData.tags" allow-clear placeholder="请输入标签，多标签逗号分隔"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="逻辑谬误解析：" name="logicalFallacy">
            <a-input v-model:value="formData.logicalFallacy" allow-clear placeholder="请输入逻辑谬误解析"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="方法论解析：" name="methodology">
            <a-input v-model:value="formData.methodology" allow-clear placeholder="请输入方法论解析"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="媒体类型（文本、图像、音频、视频）：" name="mediumType">
            <a-input v-model:value="formData.mediumType" allow-clear placeholder="请输入媒体类型（文本、图像、音频、视频）"/>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="用户ID：" name="userId">
            <a-input v-model:value="formData.userId" allow-clear placeholder="请输入用户ID"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <template #footer>
      <a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
      <a-button :loading="submitLoading" type="primary" @click="onSubmit">保存</a-button>
    </template>
  </xn-form-container>
</template>

<script name="aiContentSynopsisForm" setup>
import {cloneDeep} from 'lodash-es'
import aiContentSynopsisApi from '@/api/app/aiContentSynopsisApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({successful: null})
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)

// 打开抽屉
const onOpen = (record) => {
  visible.value = true
  if (record) {
    let recordData = cloneDeep(record)
    formData.value = Object.assign({}, recordData)
  }
}
// 关闭抽屉
const onClose = () => {
  formRef.value.resetFields()
  formData.value = {}
  visible.value = false
}
// 默认要校验的
const formRules = {}
// 验证并提交数据
const onSubmit = () => {
  formRef.value.validate().then(() => {
    submitLoading.value = true
    const formDataParam = cloneDeep(formData.value)
    aiContentSynopsisApi
        .aiContentSynopsisSubmitForm(formDataParam, formDataParam.id)
        .then(() => {
          onClose()
          emit('successful')
        })
        .finally(() => {
          submitLoading.value = false
        })
  })
}
// 抛出函数
defineExpose({
  onOpen
})
</script>
