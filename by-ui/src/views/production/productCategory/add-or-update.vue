<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" :title="!dataForm.id ? '新增' : '修改'" width="800px">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
			<el-row :gutter="20">
				<el-col :span="12">
					<el-form-item label="父级编码" prop="parentCode">
						<el-select v-model="dataForm.parentCode" class="form-input" clearable>
							<el-option v-for="item in productCategoryList" :key="item.code" :label="item.name" :value="item.code"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<el-col :span="12">
					<el-form-item label="编码" prop="code">
						<el-input v-model="dataForm.code" class="form-input"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
			<el-row :gutter="20">
				<el-col :span="12">
					<el-form-item label="名称" prop="name">
						<el-input v-model="dataForm.name" class="form-input"></el-input>
					</el-form-item>
				</el-col>
			</el-row>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import {
  useProductCategoryApi,
  useProductCategoryListAllApi,
  useProductCategorySubmitApi
} from "@/api/production/productCategory/api";
import { ElMessage } from "element-plus/es";
import { reactive, ref } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	parentCode: '',
	code: '',
	name: ''
})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
	initProductCategoryList()

	if (id) {
		getProductCategory(id)
	}
}

interface ProductCategory {
	code: string,
	name: string
}

const productCategoryList = ref<ProductCategory[]>([])

const initProductCategoryList = () => {
	useProductCategoryListAllApi().then(res => {
		productCategoryList.value = res.data
	})
}

const getProductCategory = (id: number) => {
	useProductCategoryApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({
	code: [
		{ required: true, message: '编码不能为空', trigger: 'blur' }
	],
	name: [
		{ required: true, message: '名称不能为空', trigger: 'blur' }
	]
})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useProductCategorySubmitApi(dataForm).then(() => {
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
.form-input {
	width: 100%;
	max-width: 260px;
}
</style>
