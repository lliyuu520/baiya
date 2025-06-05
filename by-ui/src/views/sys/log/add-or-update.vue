<template>
	<el-dialog v-model="visible" destroy-on-close title="日志详情" width="700px">
		<el-descriptions :column="2" border>
			<el-descriptions-item label="操作人">{{ dataForm.operator }}</el-descriptions-item>
			<el-descriptions-item label="操作类型">{{ dataForm.operateType }}</el-descriptions-item>
			<el-descriptions-item label="操作模块">{{ dataForm.operateModule }}</el-descriptions-item>
			<el-descriptions-item label="操作IP">{{ dataForm.operateIp }}</el-descriptions-item>
			<el-descriptions-item label="请求方法">{{ dataForm.requestMethod }}</el-descriptions-item>
			<el-descriptions-item label="请求URL">{{ dataForm.requestUrl }}</el-descriptions-item>
			<el-descriptions-item :span="2" label="请求参数">
				<pre>{{ dataForm.requestParams }}</pre>
			</el-descriptions-item>
			<el-descriptions-item :span="2" label="请求体">
				<pre>{{ dataForm.requestBody }}</pre>
			</el-descriptions-item>
			<el-descriptions-item label="响应状态">{{ dataForm.responseStatus }}</el-descriptions-item>
			<el-descriptions-item label="耗时(ms)">{{ dataForm.costTime }}</el-descriptions-item>
			<el-descriptions-item label="操作时间">{{ dataForm.createTime }}</el-descriptions-item>
		</el-descriptions>
		<template #footer>
			<el-button @click="visible = false">关闭</el-button>
		</template>
	</el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useLogApi } from "@/api/sys/log/api";

const visible = ref(false)
const dataForm = reactive({
	id: '',
	operator: '',
	operatorId: '',
	operateType: '',
	operateModule: '',
	operateIp: '',
	requestMethod: '',
	requestUrl: '',
	requestParams: '',
	requestBody: '',
	responseStatus: '',
	costTime: '',
	createTime: ''
})




const init = (id?: string) => {
	visible.value = true
	if (id) {
		useLogApi(id).then((res: any) => {
			Object.assign(dataForm, res.data)
		})
	}
}

defineExpose({
	init
})
</script>

<style scoped>
pre {
	margin: 0;
	white-space: pre-wrap;
	word-wrap: break-word;
}
</style>
