<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item  label="版本号">
				<el-input v-model="state.queryForm.versionNo" clearable></el-input>
			</el-form-item>
			<el-form-item  label="版本名称">
				<el-input v-model="state.queryForm.versionName" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'production:factory:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column  align="center" header-align="center" label="编码" prop="versionNo"></el-table-column>
			<el-table-column  align="center" header-align="center" label="名称" prop="versionName"></el-table-column>
			<el-table-column  align="center" header-align="center" label="描述" prop="versionDesc"></el-table-column>
			<el-table-column  align="center" header-align="center" label="APK文件" prop="apkUrl">
				<template #default="scope">
					<el-button link type="primary" @click="downloadApkHandle(scope.row.apkUrl)">下载</el-button>
					<el-button link type="primary" @click="showQrCodeHandle(scope.row)">预览</el-button>
				</template>
			</el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button v-auth="'production:factory:delete'" link type="primary" @click="deleteHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			:current-page="state.page"
			:page-size="state.limit"
			:page-sizes="state.pageSizes"
			:total="state.total"
			layout="total, sizes, prev, pager, next, jumper"
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
		>
		</el-pagination>

		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update ref="addOrUpdateRef" @refreshDataList="getDataList"></add-or-update>
	</el-card>
	<el-dialog v-model="showQrcodeDialog" :title="qrCodeInfo.versionName+'-'+qrCodeInfo.versionNo+'.apk'" width="30%" center>
    <qrcode-vue :value="qrCodeInfo.apkUrl" :size="200" level="H"/>
    <template #footer>
			<span class="dialog-footer">
				<el-button @click="showQrcodeDialog = false">关闭</el-button>
			</span>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import {useCrud} from "@/hooks";
import {IHooksOptions} from "@/hooks/interface";
import {reactive, ref} from "vue";
import AddOrUpdate from "./add-or-update.vue";
import QrcodeVue from 'qrcode.vue'


const state: IHooksOptions = reactive({
	dataListUrl: '/equipment/apk/page',
	deleteUrl: '/equipment/apk/delete',
	queryForm: {
		versionNo: '',
		versionName: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const showQrcodeDialog = ref(false)
const qrCodeInfo = reactive({
  apkUrl: '',
  versionNo: '',
  versionName: '',
})

const showQrCodeHandle = (row: any) => {
  Object.assign(qrCodeInfo, {
    apkUrl: row.apkUrl,
    versionNo: row.versionNo,
    versionName: row.versionName,
  })

  showQrcodeDialog.value = true
}

const downloadApkHandle = (apkUrl: string) => {
	const link = document.createElement('a');
    link.href = apkUrl;
    link.setAttribute('download', ''); // 让浏览器使用服务器提供的文件名
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
