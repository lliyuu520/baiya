<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" :title="!dataForm.id ? '新增' : '修改'" width="800px">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
			<el-form-item label="单据编号" prop="orderNo">
				<el-input v-model="dataForm.orderNo" class="form-input" placeholder="请输入单据编号"></el-input>
			</el-form-item>
			<el-form-item label="单据日期" prop="orderDate">
				<el-date-picker v-model="dataForm.orderDate" class="form-input" placeholder="请选择单据日期" type="date" value-format="YYYY-MM-DD"></el-date-picker>
			</el-form-item>
			<el-form-item label="生产日期" prop="productionDate">
				<el-date-picker v-model="dataForm.productionDate" class="form-input" placeholder="请选择生产日期" type="date" value-format="YYYY-MM-DD"></el-date-picker>
			</el-form-item>
			<el-form-item label="生产部门" prop="productionDepartCode">
				<el-select v-model="dataForm.productionDepartCode" class="form-input" placeholder="请选择生产部门">
					<el-option v-for="item in departList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="生产车间" prop="productionWorkshopCode">
				<el-select v-model="dataForm.productionWorkshopCode" class="form-input" placeholder="请选择生产车间">
					<el-option v-for="item in workshopList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="生产班次" prop="productionShiftCode">
				<el-select v-model="dataForm.productionShiftCode" class="form-input" placeholder="请选择生产班次">
					<el-option v-for="item in shiftList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="生产班组" prop="productionTeamCode">
				<el-select v-model="dataForm.productionTeamCode" class="form-input" placeholder="请选择生产班组">
					<el-option v-for="item in teamList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="产品类型" prop="productType">
				<el-radio-group v-model="dataForm.productType" class="product-type-radio">
					<el-radio label="FINISHED_PRODUCT">成品</el-radio>
					<el-radio label="SEMI_FINISHED_PRODUCT">半成品</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item v-show="dataForm.productType === 'FINISHED_PRODUCT'" label="成品" prop="productCode">
				<el-select v-model="dataForm.productCode" class="form-input">
					<el-option v-for="item in finishedProductList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item v-show="dataForm.productType === 'SEMI_FINISHED_PRODUCT'" label="半成品" prop="productCode">
				<el-select v-model="dataForm.productCode" class="form-input">
					<el-option v-for="item in semiFinishedProductList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="件数" prop="boxNum">
				<el-input-number v-model="dataForm.boxNum" :controls="false" :min="1"  class="form-input"></el-input-number>
			</el-form-item>
			<el-form-item label="片数" prop="bagNum">
				<el-input-number v-model="dataForm.bagNum" :controls="false" :min="1"  class="form-input"></el-input-number>
			</el-form-item>
			<el-form-item label="最大件数" prop="boxNumMaxLimited">
				<el-input-number v-model="dataForm.boxNumMaxLimited" :controls="false" :min="1"  class="form-input"></el-input-number>
			</el-form-item>
			<el-form-item label="最大片数" prop="bagNumMaxLimited">
				<el-input-number v-model="dataForm.bagNumMaxLimited" :controls="false" :min="1"  class="form-input"></el-input-number>
			</el-form-item>

			<el-form-item label="返工标识" prop="reworkFlag">
				<el-switch v-model="dataForm.reworkFlag"></el-switch>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import { useDepartAndWorkshopListAllApi } from "@/api/production/departAndWorkshop/api";
import { useOrderApi, useOrderSubmitApi } from "@/api/production/order/api";
import { useProductListAllApi } from "@/api/production/product/api";
import { useShiftListAllApi } from "@/api/production/shift/api";
import { useTeamListAllApi } from "@/api/production/team/api";
import { ElMessage } from "element-plus/es";
import { reactive, ref, watch } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

interface BaseItem {
	code: string
	name: string
}

interface Product extends BaseItem {
	unit: string
}

interface Depart extends BaseItem {}
interface Workshop extends BaseItem {}
interface Shift extends BaseItem {}
interface Team extends BaseItem { }
interface Unit extends BaseItem { }

const dataForm = reactive({
	id: '',
	orderNo: '',
	orderDate: '',
	productionDate: '',
	productionDepartCode: '',
	productionWorkshopCode: '',
	productionShiftCode: '',
	productionTeamCode: '',
	productType: '',
	productCode: '',
	boxNum: 1,
	bagNum: 1,
  boxNumMaxLimited: 0,
  bagNumMaxLimited: 0,
	reworkFlag: false,
	length: 0
})

// 列表数据
const departList = ref<Depart[]>([])
const workshopList = ref<Workshop[]>([])
const shiftList = ref<Shift[]>([])
const teamList = ref<Team[]>([])
const finishedProductList = ref<Product[]>([])
const semiFinishedProductList = ref<Product[]>([])

// 初始化列表数据
const initList = () => {
	Promise.all([
		useDepartAndWorkshopListAllApi(),
    useDepartAndWorkshopListAllApi(),
		useShiftListAllApi(),
		useTeamListAllApi(),
		useProductListAllApi("FINISHED_PRODUCT"),
		useProductListAllApi("SEMI_FINISHED_PRODUCT"),
	]).then(([departRes, workshopRes, shiftRes, teamRes, finishedProductRes, semiFinishedProductRes]) => {
		departList.value = departRes.data
		workshopList.value = workshopRes.data
		shiftList.value = shiftRes.data
		teamList.value = teamRes.data
		finishedProductList.value = finishedProductRes.data
		semiFinishedProductList.value = semiFinishedProductRes.data
	})
}

interface ProductType {
	code: string
	value: string
}

const productTypeList=ref<ProductType[]>([{code:"FINISHED_PRODUCT",value:"成品"},{code:"SEMI_FINISHED_PRODUCT",value:"半成品"}])



const dataRules = ref({
	orderType: [
		{ required: true, message: '订单类型不能为空', trigger: 'change' }
	],
	orderNo: [
		{ required: true, message: '单据编号不能为空', trigger: 'blur' }
	],
	quantity: [
		{ required: true, message: '订单数量不能为空', trigger: 'blur' }
	],
	orderDate: [
		{ required: true, message: '单据日期不能为空', trigger: 'change' }
	],
	productionDate: [
		{ required: true, message: '生产日期不能为空', trigger: 'change' }
	],
	productionDepartCode: [
		{ required: true, message: '生产部门不能为空', trigger: 'change' }
	],
	productionWorkshopCode: [
		{ required: true, message: '生产车间不能为空', trigger: 'change' }
	],
	productionShiftCode: [
		{ required: true, message: '生产班次不能为空', trigger: 'change' }
	],
	productionTeamCode: [
		{ required: true, message: '生产班组不能为空', trigger: 'change' }
	],
	productCode: [
		{ required: true, message: '产品编码不能为空', trigger: 'change' }
	],
	boxNum: [
		{ required: true, message: '件数不能为空', trigger: 'blur' }
	],
	bagNum: [
		{ required: true, message: '片数不能为空', trigger: 'blur' }
	],
  boxNumMaxLimited: [
    { required: true, message: '最大件数不能为空', trigger: 'blur' }
  ],
  bagNumMaxLimited: [ { required: true, message: '最大片数不能为空', trigger: 'blur' }],
  reworkFlag: [
    { required: true, message: '返工标识不能为空', trigger: 'change' }
  ],
})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	initList()

	if (id) {
		getInfo(id)
	}
}

const getInfo = (id: number) => {
	useOrderApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useOrderSubmitApi(dataForm).then(() => {
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

// 监听产品类型变化，清空产品编码
watch(() => dataForm.productType, (newVal) => {
    dataForm.productCode = ''
})

defineExpose({
	init
})
</script>

<style scoped>
.form-input {
	width: 100%;
	max-width: 400px;
}

.product-type-radio {
	display: flex;
	gap: 20px;
}

.product-type-radio :deep(.el-radio) {
	margin-right: 0;
}
</style>
