<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" draggable>
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="120px"
			@keyup.enter="submitHandle()">
			<template v-for="item in tableConfigVOList">
				<el-form-item :label="item.tableComment">
					<el-checkbox-group v-model="dataForm.tableFieldIdList">
						<el-checkbox v-for="field in item.tableFieldVOList" :key="field.id" :label="field.id">
							{{ field.fieldComment }}
						</el-checkbox>
					</el-checkbox-group>
				</el-form-item>
			</template>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
import {useGetFieldIdByRoleId, useRoleFieldSubmitApi} from '@/api/sys/role'
import {useTableConfigVOListApi} from '@/api/sys/tableConfig/api'
import {ElMessage} from 'element-plus/es'
import {reactive, ref} from 'vue'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)

interface TableFieldVO {
	id: number
	fieldName: string
	fieldComment: string
}

interface TableConfigVO {
	tableName: string
	tableComment: string
	tableFieldVOList: TableFieldVO[]
}
const tableConfigVOList = reactive<TableConfigVO[]>([])
const initTableConfigVOList = () => {
	useTableConfigVOListApi().then((res) => {
		Object.assign(tableConfigVOList, res.data)
	})
}

interface DataForm {
	id: number
	tableFieldIdList: number[]
}

const dataFormRef = ref()

const dataForm = reactive<DataForm>({
	id: 0,
	tableFieldIdList: []
})

const init = (id?: number) => {
	visible.value = true
	if (id) {
		dataForm.id = id
		initCheckedFiledIdList()
	}

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
	initTableConfigVOList()


}

const checkedFiledIdList = ref([])

const initCheckedFiledIdList = () => {
	useGetFieldIdByRoleId(dataForm.id).then((res) => {
		checkedFiledIdList.value = res.data
		dataForm.tableFieldIdList = [...res.data]
	})
}



const dataRules = ref({
})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}
		useRoleFieldSubmitApi(dataForm).then(() => {
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

defineExpose({
	init
})
</script>
