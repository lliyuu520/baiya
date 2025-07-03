<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item  label="车间">
				<el-input v-model="state.queryForm.departNo" clearable></el-input>
			</el-form-item>
			<el-form-item  label="产线">
				<el-input v-model="state.queryForm.workshopNo" clearable></el-input>
			</el-form-item>
			<el-form-item label="订单号">
				<el-input v-model="state.queryForm.orderNo" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column  align="center" header-align="center" label="车间名称" prop="departNo"></el-table-column>
			<el-table-column  align="center" header-align="center" label="产线标识" prop="workshopNo"></el-table-column>
			<el-table-column  align="center" header-align="center" label="生产日期" prop="orderProductionDate"></el-table-column>
			<el-table-column  align="center" header-align="center" label="工单号" prop="orderNo"></el-table-column>
			<el-table-column  align="center" header-align="center" label="步骤" prop="stepName"></el-table-column>
			<el-table-column  align="center" header-align="center" label="工控机箱数" prop="machineUploadBoxNum"></el-table-column>
			<el-table-column  align="center" header-align="center" label="工控机包数" prop="machineUploadBagNum"></el-table-column>
			<el-table-column  align="center" header-align="center" label="服务器箱数" prop="serverUploadBoxNum"></el-table-column>
			<el-table-column  align="center" header-align="center" label="服务器包数" prop="serverUploadBagNum"></el-table-column>
			<el-table-column  align="center" header-align="center" label="状态" prop="statusDesc"></el-table-column>
			<el-table-column  align="center" header-align="center" label="信息" prop="info"></el-table-column>
			<el-table-column  align="center" header-align="center" label="同步箱数" prop="syncBoxCodeNum"></el-table-column>
			<el-table-column  align="center" header-align="center" label="处理时间" prop="handleDateTime"></el-table-column>
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
import {reactive, ref} from "vue";


const state: IHooksOptions = reactive({
	dataListUrl: '/equipment/monitor/page',
	queryForm: {	
		departNo: '',
		workshopNo: '',
		orderNo: '',
	}
})

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
