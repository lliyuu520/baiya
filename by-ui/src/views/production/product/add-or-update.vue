<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" :title="!dataForm.id ? '新增' : '修改'" width="800px">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
			<el-form-item label="编码" prop="code">
				<el-input v-model="dataForm.code" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="名称" prop="name">
				<el-input v-model="dataForm.name" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="产品类型" prop="categoryCode">
				<el-select v-model="dataForm.categoryCode" class="form-input">
					<el-option v-for="item in productCategoryList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="单位" prop="unit">
				<el-input v-model="dataForm.unit" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="一件片数" prop="oneBoxPackageNum">
				<el-input v-model="dataForm.oneBoxPackageNum" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="货号" prop="goodsCode">
				<el-input v-model="dataForm.goodsCode" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="条形码" prop="barCode">
				<el-input v-model="dataForm.barCode" class="form-input"></el-input>
			</el-form-item>
			<el-form-item label="规格" prop="specification">
				<el-input v-model="dataForm.specification" class="form-input"></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import { useProductApi, useProductSubmitApi } from "@/api/production/product/api";
import { useProductCategoryListAllApi } from "@/api/production/productCategory/api";
import { ElMessage } from "element-plus/es";
import { reactive, ref } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	code: '',
	name: '',
	categoryCode: '',
  unit: '',
  validPeriod: '',
  oneStackBoxNum: '',
  oneBoxPackageNum: '',
  goodsCode: '',
  barCode: '',
  specification: ''

})

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

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	initProductCategoryList()

	if (id) {
		getProduct(id)
	}
}

const getProduct = (id: number) => {
	useProductApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({
	code: [
		{ required: true, message: '编码不能为空', trigger: 'blur' }
	],
	name: [
		{ required: true, message: '名称不能为空', trigger: 'blur' }
	],
  categoryCode: [
		{ required: true, message: '产品类型不能为空', trigger: 'blur' }
	],
	unit: [
		{ required: true, message: '单位不能为空', trigger: 'blur' }
	],
	validPeriod: [
		{ required: true, message: '有效期不能为空', trigger: 'blur' }
	],
	oneStackBoxNum: [
		{ required: true, message: '一垛箱数不能为空', trigger: 'blur' }
	],
	oneBoxPackageNum: [
		{ required: true, message: '一箱包数不能为空', trigger: 'blur' }
	],
	goodsCode: [
		{ required: true, message: '货号不能为空', trigger: 'blur' }
	],
	barCode: [
		{ required: true, message: '条形码不能为空', trigger: 'blur' }
	],
	specification: [
		{ required: true, message: '规格不能为空', trigger: 'blur' }
	]
})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useProductSubmitApi(dataForm).then(() => {
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
	max-width: 400px;
}
</style>
