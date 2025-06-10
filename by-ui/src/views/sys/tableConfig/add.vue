<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" title="新增">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
			<el-table :data="tableConfigList" border style="width: 100%" @selection-change="selectionChangeHandle" >
        <el-table-column align="center" header-align="center" type="selection" width="50"></el-table-column>
        <el-table-column label="表名" prop="tableName" width="150px"></el-table-column>
        <el-table-column label="描述" prop="tableComment" width="150px"></el-table-column>
			</el-table>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import {useTableConfigListAllApi, useTableConfigSubmitApi} from "@/api/sys/tableConfig/api";
import {ElMessage} from "element-plus/es";
import {onMounted, reactive, ref} from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive<TableConfig[]>([])


const init = () => {
	visible.value = true

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
}

const dataRules = ref({})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useTableConfigSubmitApi(dataForm).then(() => {
			ElMessage.success({
				message: '操作成功',
				duration: 500,
				onClose: () => {
					visible.value = false
					emit('refreshDataList')
				}
			})
		})
	})
}

interface TableConfig {
	tableName: string
	tableComment: string
}

const tableConfigList = ref<TableConfig[]>([])

const initTableConfigList=()=>{
  useTableConfigListAllApi().then(res=>{
    tableConfigList.value = res.data
  })
}
// 多选
const selectionChangeHandle = (selections: any[]) => {
	Object.assign(dataForm, selections.map(item => ({
		tableName: item.tableName,
		tableComment: item.tableComment
	})))
}


onMounted(()=>{
  initTableConfigList()
})

defineExpose({
	init
})
</script>
