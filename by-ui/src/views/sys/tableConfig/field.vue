<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" title="表字段配置" width="600px">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
			<el-form-item label="字段列表" prop="tableFieldList">
				<el-checkbox-group v-model="dataForm.tableFieldList" class="field-checkbox-group">
					<el-checkbox v-for="item in tableFieldList" :key="item.fieldName" :label="item.fieldName">
						{{ item.fieldComment }}
					</el-checkbox>
				</el-checkbox-group>
			</el-form-item>
			<el-form-item>
				<el-button @click="visible = false">取消</el-button>
				<el-button type="primary" @click="submitHandle()">确定</el-button>
			</el-form-item>
		</el-form>
	</el-dialog>
</template>

<script lang="ts" setup>
import { useInfoByTableConfigIdApi, useListByTableConfigIdApi, useSubmitApi } from "@/api/sys/tableField/api";
import { ElMessage } from "element-plus/es";
import { reactive, ref } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

interface TableField {
  fieldName: string
  fieldComment: string
  permission:string
}

interface DataForm {
  tableConfigId: number
  tableFieldList: string[]
  tableFieldNameList: string[]
}

const dataForm = reactive<DataForm>({
	tableConfigId: 0,
	tableFieldList: [],
  tableFieldNameList: []
})

const dataRules = ref({
  tableFieldList: [
    { required: true, message: '请选择至少一个字段', trigger: 'change' }
  ]
})
const tableName = ref('')

const init = async (id: number,tableNameTmp:string) => {
  visible.value = true
  tableName.value = tableNameTmp

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
	dataForm.tableConfigId = id
  await initTableFieldListInfo(id)
  await initTableFieldNameList(id)
  // 设置已选择的字段
  if (dataForm.tableFieldNameList.length > 0) {
    dataForm.tableFieldList = dataForm.tableFieldNameList
  }
}

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate(async (valid: boolean) => {
		if (!valid) {
			return false
		}

    try {
      await useSubmitApi({
        tableConfigId: dataForm.tableConfigId,
        tableFieldList: dataForm.tableFieldList.map(fieldName => ({
          fieldName,
          fieldComment: tableFieldList.value.find(item => item.fieldName === fieldName)?.fieldComment || '',
          permission: `${tableName.value}:${fieldName}`
        }))
      })

      ElMessage.success({
        message: '操作成功',
        duration: 500,
        onClose: () => {
          visible.value = false
          emit('refreshDataList')
        }
      })
    } catch (error) {
      ElMessage.error('操作失败')
    }
	})
}

const tableFieldList = ref<TableField[]>([])

const initTableFieldNameList = async (id: number) => {
  try {
    const res = await useInfoByTableConfigIdApi(id)
    dataForm.tableFieldNameList = res.data
  } catch (error) {
    ElMessage.error('获取字段列表失败')
  }
}

const initTableFieldListInfo = async (id: number) => {
  try {
    const res = await useListByTableConfigIdApi(id)
    tableFieldList.value = res.data
  } catch (error) {
    ElMessage.error('获取字段信息失败')
  }
}

defineExpose({
	init
})
</script>

<style scoped>
.field-checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}
</style>
