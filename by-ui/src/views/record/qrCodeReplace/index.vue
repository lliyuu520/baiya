<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="原始二维码">
				<el-input v-model="state.queryForm.originalQrCode" clearable></el-input>
			</el-form-item>
			<el-form-item label="替换二维码">
				<el-input v-model="state.queryForm.replaceCode" clearable></el-input>
			</el-form-item>
			<el-form-item label="上传时间">
				<el-date-picker v-model="state.queryForm.submitDatetimeRange" value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item label="是否执行">
				<el-select v-model="state.queryForm.handleFlag" clearable>
					<el-option v-for="item in handleFlagOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
				</el-select>
			</el-form-item>

			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="原始二维码" prop="originalQrCode"></el-table-column>
			<el-table-column align="center" header-align="center" label="替换二维码" prop="replaceQrCode"></el-table-column>
			<el-table-column align="center" header-align="center" label="上传用户" prop="submitUsername" ></el-table-column>
			<el-table-column align="center" header-align="center" label="上传时间" prop="submitDatetime">
				<template #default="scope">
					{{ formatDateTime(scope.row.submitDatetime) }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="执行时间" prop="handleDateTime">
				<template #default="scope">
					{{ formatDateTime(scope.row.handleDateTime) }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="状态" prop="handleFlag">
				<template #default="scope">
					{{ filterHandleFlag(scope.row.handleFlag) }}
				</template>	
			</el-table-column>
			<el-table-column align="center" header-align="center" label="失败原因" prop="failReason"></el-table-column>

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
	</el-card>
</template>

<script lang="ts" setup>
import { useCrud } from "@/hooks";
import { IHooksOptions } from "@/hooks/interface"
import { formatDateTime } from "@/utils/tool";
import { reactive } from "vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/record/qrCodeReplace/page',
	queryForm: {
		originalQrCode: '',
		replaceCode: '',
		handleFlag: '',
		submitDatetimeRange: []
	}
})

const handleFlagOptions = [
	{
		value: 'WAITING',
		label: '待处理'
	},
	{
		value: 'SUCCESS',
		label: '已处理'
	},
	{
		value: 'FAIL',
		label: '失败'
	}
]

const filterHandleFlag = (value: string) => {
	return handleFlagOptions.find(item => item.value === value)?.label
}



const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle } = useCrud(state)
</script> 