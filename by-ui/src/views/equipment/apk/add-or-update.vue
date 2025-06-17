<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
			<el-form-item label="版本号" prop="versionNo">
				<el-input-number v-model="dataForm.versionNo" :controls="false"></el-input-number>
			</el-form-item>
			<el-form-item label="版本名称" prop="versionName">
				<el-input v-model="dataForm.versionName"></el-input>
			</el-form-item>
			<el-form-item label="版本描述" prop="versionDesc">
				<el-input v-model="dataForm.versionDesc" type="textarea" :rows="3"></el-input>
			</el-form-item>
			<el-form-item label="APK文件" prop="apkUrl">
				<el-upload
					:action="uploadUrl"
					:on-success="handleUploadSuccess"
					:on-error="handleUploadError"
					:show-file-list="false"
					>
					<el-button type="primary">点击上传</el-button>
				</el-upload>
			</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
import {useApkApi, useApkSubmitApi} from '@/api/equipement/apk/api'
import {ElMessage} from 'element-plus/es'
import {reactive, ref} from 'vue'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	versionNo: 0,
	versionName: '',
	versionDesc: '',
	apkUrl:''

})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	if (id) {
		getApk(id)
	}
}

const getApk = (id: number) => {
	useApkApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useApkSubmitApi(dataForm).then(() => {
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

const uploadUrl = ref("/sys/apk/apkUpload")


const handleUploadSuccess = (response: any) => {
	console.log(response)
}
const handleUploadError = (error: any) => {
	console.log(error)
}


defineExpose({
	init
})
</script>
