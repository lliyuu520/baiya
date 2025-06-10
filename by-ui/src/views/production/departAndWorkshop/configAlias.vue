<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" title="配置编码规则" width="800px" v-loading="loading">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
			<el-form-item label="别名" prop="alias">
				<el-input v-model="dataForm.alias" class="form-input" placeholder="请输入别名"></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import {useConfigAliasApi, useDepartAndWorkshopApi} from "@/api/production/departAndWorkshop/api";
import {ElMessage} from "element-plus/es";
import {reactive, ref} from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()







const dataForm = reactive({
	id: '',
	alias: '',
})

// 数据加载状态
const loading = ref(false)



const dataRules = ref({
	
})

const init = (id: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
		getInfo(id)		
}

const getInfo = (id: number) => {
	useDepartAndWorkshopApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useConfigAliasApi(dataForm).then(() => {
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

<style scoped>

</style>
