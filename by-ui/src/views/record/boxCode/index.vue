<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="箱码">
				<el-input v-model="state.queryForm.code" clearable></el-input>
			</el-form-item>
			<el-form-item label="拉码类型">
				<el-select v-model="state.queryForm.pullType" clearable>
					<el-option v-for="item in pullTypeList" :key="item.code" :label="item.desc" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="成品订单号">
				<el-input v-model="state.queryForm.finishedOrderNo" clearable></el-input>
			</el-form-item>
			<el-form-item label="半成品订单号">
				<el-input v-model="state.queryForm.semiFinishedOrderNo" clearable></el-input>
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
			<el-form-item label="是否组垛">
				<el-select v-model="state.queryForm.cribFlag" clearable>
					<el-option label="是" value="true"></el-option>
					<el-option label="否" value="false"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="组垛时间" v-if="state.queryForm.cribFlag">
				<el-date-picker v-model="state.queryForm.cribDateTimeRange" value-format="YYYY-MM-DD HH:mm:ss" format="YYYY-MM-DD HH:mm:ss" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
			</el-form-item>
			<el-form-item label="垛码" v-if="state.queryForm.cribFlag">
				<el-input v-model="state.queryForm.cribCode" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="箱码" prop="code"></el-table-column>
			
			<el-table-column align="center" header-align="center" label="拉取时间" prop="pullDateTime">
				<template #default="scope">
					{{ formatDateTime(scope.row.pullDateTime) }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="拉码类型" prop="pullType">
				<template #default="scope">
					{{ filterPullType(scope.row.pullType) }}
				</template>
			</el-table-column>

			<el-table-column align="center" header-align="center" label="成品订单号" prop="finishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="半成品订单号" prop="semiFinishedOrderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="上传时间" prop="uploadDateTime">
				<template #default="scope">
					{{ formatDateTime(scope.row.uploadDateTime) }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="垛码" prop="cribCode"></el-table-column>
			<el-table-column align="center" header-align="center" label="入库时间" prop="cribDateTime">
				<template #default="scope">
					{{ formatDateTime(scope.row.cribDateTime) }}
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
import { useCrud } from "@/hooks";
import { IHooksOptions } from "@/hooks/interface";
import { formatDateTime } from "@/utils/tool";
import { reactive } from "vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/record/boxCode/page',
	queryForm: {
		code: '',
		uploadFlag: '',
		cribFlag: '',
		finishedOrderNo: '',
		semiFinishedOrderNo: '',
		cribCode: '',
		uploadDateTimeRange: [],
		cribDateTimeRange: [],
		pullType: '',
	}
})



const pullTypeList = [
	{
		desc: '二维码',
		code: 'QR_CODE'
	},
	{
		desc: '物流码',
		code: 'LOGISTICS_CODE'
	}
	
]

const filterPullType = (code: string) => {
	return pullTypeList.find(item => item.code === code)?.desc
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle } = useCrud(state)
</script>
