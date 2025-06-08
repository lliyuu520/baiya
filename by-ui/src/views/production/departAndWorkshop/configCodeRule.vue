<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" title="配置编码规则" width="800px" v-loading="loading">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
			<el-form-item label="编码规则" prop="codeRuleId">
				<el-select v-model="dataForm.codeRuleId" class="form-input" placeholder="请选择编码规则" filterable>
					<el-option v-for="item in codeRuleList" :key="item.id" :label="item.name" :value="item.id"></el-option>
				</el-select>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import { useDepartAndWorkshopApi, useConfigCodeRuleApi } from "@/api/production/departAndWorkshop/api";
import { useCodeRuleListAllApi } from "@/api/sys/codeRule/api";
import { ElMessage } from "element-plus/es";
import { reactive, ref, watch } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()



interface CodeRule{
	id:number
	name:string
}

const codeRuleList = ref<CodeRule[]>([])

const initCodeRuleList = () => {
	useCodeRuleListAllApi().then(res => {
		codeRuleList.value = res.data
	})
}	




const dataForm = reactive({
	id: '',
	codeRuleId: '',
})

// 数据加载状态
const loading = ref(false)
// 数据是否已加载
const dataLoaded = ref(false)

// 初始化列表数据
const initList = async () => {
	// 如果数据已加载，直接返回
	if (dataLoaded.value) {
		return Promise.resolve()
	}

	loading.value = true
	try {
		initCodeRuleList();
		dataLoaded.value = true;
	} finally {
		loading.value = false;
	}
}



const dataRules = ref({
	
})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	// 先加载列表数据，再获取详情
	initList().then(() => {
		if (id) {
			getInfo(id)
		}
	})
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

		useConfigCodeRuleApi(dataForm).then(() => {
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
