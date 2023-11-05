<template>
  <a-card :bordered="false">
    <a-form ref="searchFormRef" :model="searchFormState" class="ant-advanced-search-form" name="advanced_search">
      <a-row :gutter="24">
        <a-col :span="6">
          <a-form-item label="内容原地址" name="originalAddress">
            <a-input v-model:value="searchFormState.originalAddress" placeholder="请输入内容原地址"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="内容md5" name="contentMd5">
            <a-input v-model:value="searchFormState.contentMd5" placeholder="请输入内容md5"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-form-item label="摘要信息" name="synopsisInfo">
            <a-input v-model:value="searchFormState.synopsisInfo" placeholder="请输入摘要信息"/>
          </a-form-item>
        </a-col>
        <a-col v-show="advanced" :span="6">
          <a-form-item label="媒体类型（文本、图像、音频、视频）" name="mediumType">
            <a-input v-model:value="searchFormState.mediumType" placeholder="请输入媒体类型（文本、图像、音频、视频）"/>
          </a-form-item>
        </a-col>
        <a-col v-show="advanced" :span="6">
          <a-form-item label="用户ID" name="userId">
            <a-input v-model:value="searchFormState.userId" placeholder="请输入用户ID"/>
          </a-form-item>
        </a-col>
        <a-col :span="6">
          <a-button type="primary" @click="table.refresh(true)">查询</a-button>
          <a-button style="margin: 0 8px" @click="reset">重置</a-button>
          <a style="margin-left: 8px" @click="toggleAdvanced">
            {{ advanced ? '收起' : '展开' }}
            <component :is="advanced ? 'up-outlined' : 'down-outlined'"/>
          </a>
        </a-col>
      </a-row>
    </a-form>
    <s-table
        ref="table"
        :alert="options.alert.show"
        :columns="columns"
        :data="loadData"
        :row-key="(record) => record.id"
        :row-selection="options.rowSelection"
        :tool-config="toolConfig"
        bordered
    >
      <template #operator class="table-operator">
        <a-space>
          <a-button v-if="hasPerm('aiContentSynopsisAdd')" type="primary" @click="formRef.onOpen()">
            <template #icon>
              <plus-outlined/>
            </template>
            新增
          </a-button>
          <xn-batch-delete
              v-if="hasPerm('aiContentSynopsisBatchDelete')"
              :selectedRowKeys="selectedRowKeys"
              @batchDelete="deleteBatchAiContentSynopsis"
          />
        </a-space>
      </template>
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'action'">
          <a-space>
            <a v-if="hasPerm('aiContentSynopsisEdit')" @click="formRef.onOpen(record)">编辑</a>
            <a-divider v-if="hasPerm(['aiContentSynopsisEdit', 'aiContentSynopsisDelete'], 'and')" type="vertical"/>
            <a-popconfirm title="确定要删除吗？" @confirm="deleteAiContentSynopsis(record)">
              <a-button v-if="hasPerm('aiContentSynopsisDelete')" danger size="small" type="link">删除</a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </s-table>
  </a-card>
  <Form ref="formRef" @successful="table.refresh(true)"/>
</template>

<script name="aicontentsynopsis" setup>
import Form from './form.vue'
import aiContentSynopsisApi from '@/api/app/aiContentSynopsisApi'

let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = {refresh: true, height: true, columnSetting: true, striped: false}
// 查询区域显示更多控制
const advanced = ref(false)
const toggleAdvanced = () => {
  advanced.value = !advanced.value
}
const columns = [
  {
    title: '内容原地址',
    dataIndex: 'originalAddress'
  },
  {
    title: '内容md5',
    dataIndex: 'contentMd5'
  },
  {
    title: '摘要信息',
    dataIndex: 'synopsisInfo'
  },
  {
    title: '摘要思维导读',
    dataIndex: 'synopsisMindMap'
  },
  {
    title: '目标内容',
    dataIndex: 'content'
  },
  {
    title: '使用的 promptTemplate',
    dataIndex: 'prompttemplate'
  },
  {
    title: '是否为首次解析',
    dataIndex: 'hasFirstSynopsis'
  },
  {
    title: '内容字数',
    dataIndex: 'contentWords'
  },
  {
    title: '标签，多标签逗号分隔',
    dataIndex: 'tags'
  },
  {
    title: '逻辑谬误解析',
    dataIndex: 'logicalFallacy'
  },
  {
    title: '方法论解析',
    dataIndex: 'methodology'
  },
  {
    title: '媒体类型（文本、图像、音频、视频）',
    dataIndex: 'mediumType'
  },
  {
    title: '用户ID',
    dataIndex: 'userId'
  },
]
// 操作栏通过权限判断是否显示
if (hasPerm(['aiContentSynopsisEdit', 'aiContentSynopsisDelete'])) {
  columns.push({
    title: '操作',
    dataIndex: 'action',
    align: 'center',
    width: '150px'
  })
}
const selectedRowKeys = ref([])
// 列表选择配置
const options = {
  // columns数字类型字段加入 needTotal: true 可以勾选自动算账
  alert: {
    show: true,
    clear: () => {
      selectedRowKeys.value = ref([])
    }
  },
  rowSelection: {
    onChange: (selectedRowKey, selectedRows) => {
      selectedRowKeys.value = selectedRowKey
    }
  }
}
const loadData = (parameter) => {
  const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
  return aiContentSynopsisApi.aiContentSynopsisPage(Object.assign(parameter, searchFormParam)).then((data) => {
    return data
  })
}
// 重置
const reset = () => {
  searchFormRef.value.resetFields()
  table.value.refresh(true)
}
// 删除
const deleteAiContentSynopsis = (record) => {
  let params = [
    {
      id: record.id
    }
  ]
  aiContentSynopsisApi.aiContentSynopsisDelete(params).then(() => {
    table.value.refresh(true)
  })
}
// 批量删除
const deleteBatchAiContentSynopsis = (params) => {
  aiContentSynopsisApi.aiContentSynopsisDelete(params).then(() => {
    table.value.clearRefreshSelected()
  })
}
</script>
