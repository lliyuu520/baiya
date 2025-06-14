<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="袋码">
				<el-input v-model="state.queryForm.code" clearable></el-input>
			</el-form-item>
			<el-form-item label="是否上传">
				<el-select v-model="state.queryForm.uploadFlag" clearable>
					<el-option label="是" value="true"></el-option>
					<el-option label="否" value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="上传时间" v-if="state.queryForm.uploadFlag">
				<el-date-picker v-model="state.queryForm.uploadDateTimeRange" value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item label="箱码" v-if="state.queryForm.uploadFlag">
				<el-input v-model="state.queryForm.boxCode" clearable></el-input>
			</el-form-item>
			<el-form-item label="成品订单号">
				<el-input v-model="state.queryForm.finishedOrderNo" clearable></el-input>
			</el-form-item>
			<el-form-item label="半成品订单号">
				<el-input v-model="state.queryForm.semiFinishedOrderNo" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="袋码" prop="code"></el-table-column>
			<el-table-column align="center" header-align="center" label="拉码时间" prop="pullDateTime" width="180">
				<template #default="scope">
					{{ scope.row.pullDateTime ? scope.row.pullDateTime.replace('T', ' ') : '' }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="成品订单号" prop="finishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="半成品订单号" prop="semiFinishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="箱码" prop="boxCode"></el-table-column>
			<el-table-column align="center" header-align="center" label="上传时间" prop="uploadDateTime" width="180">
				<template #default="scope">
					{{ scope.row.uploadDateTime ? scope.row.uploadDateTime.replace('T', ' ') : '' }}
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
	</el-card>
</template>

<script lang="ts" setup>
import {useCrud} from "@/hooks";
import {IHooksOptions} from "@/hooks/interface";
import {onMounted, reactive} from "vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/record/bagCode/page',
	queryForm: {
		code: '',
		uploadFlag: '',
		finishedOrderNo: '',
		semiFinishedOrderNo: '',
		boxCode: '',
		uploadDateTimeRange: []
	}
})



const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle } = useCrud(state)
</script> 