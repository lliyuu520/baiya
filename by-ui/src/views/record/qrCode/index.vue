<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="二维码">
				<el-input v-model="state.queryForm.code" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column align="center" header-align="center" label="二维码" prop="code"></el-table-column>
			<el-table-column align="center" header-align="center" label="上传时间" prop="uploadDateTime" width="180">
				<template #default="scope">
					{{ scope.row.uploadDateTime ? scope.row.uploadDateTime.replace('T', ' ') : '' }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="拉码时间" prop="pullDateTime" width="180">
				<template #default="scope">
					{{ scope.row.pullDateTime ? scope.row.pullDateTime.replace('T', ' ') : '' }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="成品订单号" prop="finishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="半成品订单号" prop="semiFinishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="箱码" prop="boxCode"></el-table-column>
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
import { IHooksOptions } from "@/hooks/interface";
import { onMounted, reactive } from "vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/record/qrCode/page',
	queryForm: {
		code: ''
	}
})

onMounted(() => {
	getDataList()
})

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle } = useCrud(state)
</script> 