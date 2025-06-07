<template>
	<el-dialog v-model="visible" :close-on-click-modal="false" :title="!dataForm.id ? '新增' : '修改'" class="code-rule-dialog" width="1200px">
		<div class="dialog-content">
			<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="140px" @keyup.enter="submitHandle()">
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="编码" prop="code">
							<el-input v-model="dataForm.code" class="form-input input-value"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :span="24">
						<el-form-item label="名称" prop="name">
							<el-input v-model="dataForm.name" class="form-input input-value"></el-input>
						</el-form-item>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="24">
						<el-tabs v-model="activeTab" class="rule-tabs">
							<el-tab-pane label="箱码规则" name="boxCode">
								<rule-table
									v-model:ruleList="dataForm.boxCodeRuleList"
									:isEditMode="isEditMode"
									:sourceFieldList="boxCodeSourceFieldList"
									type="boxCode"
									@drag-end="onDragEnd"
								/>
							</el-tab-pane>
							<el-tab-pane label="袋码规则" name="bagCode">
								<rule-table
									v-model:ruleList="dataForm.bagCodeRuleList"
									:isEditMode="isEditMode"
									:sourceFieldList="bagCodeSourceFieldList"
									type="bagCode"
									@drag-end="onDragEnd"
								/>
							</el-tab-pane>
							<el-tab-pane label="万用码规则" name="universalCode">
								<rule-table
									v-model:ruleList="dataForm.universalCodeRuleList"
									:isEditMode="isEditMode"
									:sourceFieldList="universalCodeSourceFieldList"
									type="universalCode"
									@drag-end="onDragEnd"
								/>
							</el-tab-pane>
						</el-tabs>
					</el-col>
				</el-row>
			</el-form>
		</div>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import { useCodeRuleApi, useCodeRuleSubmitApi } from "@/api/sys/codeRule/api";
import type { CheckboxValueType } from "element-plus";
import { ElMessage } from "element-plus/es";
import Sortable from "sortablejs";
import { computed, nextTick, reactive, ref, watch } from "vue";
import RuleTable from "./components/RuleTable.vue";
import type { RuleDetail } from "./config/ruleTypes";
import { bagCodeSourceFieldList, boxCodeSourceFieldList, universalCodeSourceFieldList } from "./config/ruleTypes";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()
let sortableInstance: any = null

const dataForm = reactive({
	id: 0,
	code: '',
	domain: '',
	domainEnabled: false,
	name: '',
	boxCodeRuleList: [] as RuleDetail[],
	bagCodeRuleList: [] as RuleDetail[],
	universalCodeRuleList: [] as RuleDetail[]
})

const resetDataForm = () => {
	dataForm.id = 0
	dataForm.code = ''
	dataForm.domain = ''
	dataForm.domainEnabled = false
	dataForm.name = ''
	dataForm.boxCodeRuleList = []
	dataForm.bagCodeRuleList = []
	dataForm.universalCodeRuleList = []
}

const dataRules = ref({
	code: [
		{ required: true, message: '编码不能为空', trigger: 'blur' }
	],
	name: [
		{ required: true, message: '名称不能为空', trigger: 'blur' }
	]
})

const onDragEnd = (evt: any) => {
	const { newIndex, oldIndex } = evt
	if (newIndex !== undefined && oldIndex !== undefined && newIndex !== oldIndex) {
		let currRow
		switch (activeTab.value) {
			case 'boxCode':
				currRow = dataForm.boxCodeRuleList.splice(oldIndex, 1)[0]
				dataForm.boxCodeRuleList.splice(newIndex, 0, currRow)
				dataForm.boxCodeRuleList.forEach((item, index) => {
					item.weight = index
				})
				break
			case 'bagCode':
				currRow = dataForm.bagCodeRuleList.splice(oldIndex, 1)[0]
				dataForm.bagCodeRuleList.splice(newIndex, 0, currRow)
				dataForm.bagCodeRuleList.forEach((item, index) => {
					item.weight = index
				})
				break
			case 'universalCode':
				currRow = dataForm.universalCodeRuleList.splice(oldIndex, 1)[0]
				dataForm.universalCodeRuleList.splice(newIndex, 0, currRow)
				dataForm.universalCodeRuleList.forEach((item, index) => {
					item.weight = index
				})
				break
		}
	}
}

const getCodeRule = async (id: number) => {
	const res = await useCodeRuleApi(id)
	if (res.data && Array.isArray(res.data.ruleDetailList)) {
		res.data.ruleDetailList.forEach((item: any) => {
			if (!item.id || typeof item.id !== 'number') item.id = Date.now()
		})
	}
	Object.assign(dataForm, res.data)
}

const handleDomainEnabledChange = (val: CheckboxValueType) => {
	if (!val) {
		dataForm.domain = ''
	}
}

const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useCodeRuleSubmitApi(dataForm).then(() => {
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

const initDragSort = () => {
	// 销毁之前的实例
	if (sortableInstance) {
		sortableInstance.destroy()
		sortableInstance = null
	}
	
	// 延迟执行，确保DOM完全渲染
	setTimeout(() => {
		// 查找所有规则表格，然后找到当前可见的那个
		const ruleTables = document.querySelectorAll('.rule-table .el-table__body-wrapper tbody')
		let visibleTable: HTMLElement | null = null
		
		// 找到当前可见的表格
		ruleTables.forEach(table => {
			const tableElement = table as HTMLElement
			// 检查表格是否可见（不是display:none且有父元素可见）
			if (tableElement.offsetParent !== null) {
				visibleTable = tableElement
			}
		})
		
		console.log('Found tables:', ruleTables.length, 'Visible table:', visibleTable)
		
		if (visibleTable) {
			sortableInstance = Sortable.create(visibleTable, {
				handle: '.drag-handle',
				animation: 150,
				onEnd: onDragEnd
			})
			console.log('Sortable instance created for active tab:', activeTab.value)
		} else {
			console.warn('No visible table found for tab:', activeTab.value)
		}
	}, 300)
}

const destroySortable = () => {
	if (sortableInstance) {
		sortableInstance.destroy()
		sortableInstance = null
	}
}

const init = (id?: number) => {
	visible.value = true
	dataForm.id = 0

	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}
	resetDataForm()

	if (id) {
		getCodeRule(id)
	}

	nextTick(() => {
		initDragSort()
	})
}

// 监听对话框关闭，清理拖拽实例
watch(visible, (newVal) => {
	if (!newVal) {
		destroySortable()
	}
})

const isEditMode = computed(() => !!dataForm.id)

const activeTab = ref('boxCode')

// 监听tab切换，重新初始化拖拽功能
watch(activeTab, () => {
	if (visible.value) {
		nextTick(() => {
			initDragSort()
		})
	}
})

defineExpose({
	init
})
</script>

<style scoped>
.code-rule-dialog :deep(.el-dialog__body) {
	padding: 20px;
	max-height: calc(90vh - 120px);
	overflow-y: auto;
}

.dialog-content {
	padding: 0 20px;
}

.domain-input-group {
	display: flex;
	align-items: center;
	gap: 12px;
}

.domain-input-group .input-value {
	width: 240px;
	max-width: 240px;
}

.form-input {
	width: 120px;
	max-width: 120px;
	height: 36px;
	border-radius: 8px;
}

.input-value {
	width: 120px;
	max-width: 120px;
	height: 36px;
	border-radius: 8px;
}

.rule-tabs {
	margin-top: 20px;
}

.rule-tabs :deep(.el-tabs__content) {
	padding: 20px 0;
}

.rule-tabs :deep(.el-tabs__nav) {
	width: 100%;
	display: flex;
	background-color: #f5f7fa;
	border-radius: 8px;
	padding: 4px;
}

.rule-tabs :deep(.el-tabs__item) {
	flex: 1;
	text-align: center;
	height: 40px;
	line-height: 40px;
	border-radius: 6px;
	margin: 0 4px;
	transition: all 0.3s ease;
}

.rule-tabs :deep(.el-tabs__item.is-active) {
	background-color: #409eff;
	color: #fff;
	font-weight: 500;
}

.rule-tabs :deep(.el-tabs__item:not(.is-active):hover) {
	color: #409eff;
	background-color: rgba(64, 158, 255, 0.1);
}

.rule-tabs :deep(.el-tabs__active-bar) {
	display: none;
}
</style>
