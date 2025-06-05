<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">

			<el-form-item label="单据编号">
				<el-input v-model="state.queryForm.orderNo" clearable></el-input>
			</el-form-item>
			<el-form-item label="返工">
				<el-select v-model="state.queryForm.reworkFlag" clearable>
					<el-option :value="true" label="是"></el-option>
					<el-option :value="false" label="否"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'production:order:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">

			<el-table-column align="center" header-align="center" label="单据编号" prop="orderNo"></el-table-column>
			<el-table-column align="center" header-align="center" label="单据日期" prop="orderDate"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产工厂" prop="productionFactoryName"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产日期" prop="productionDate"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产部门" prop="productionDepartName"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产车间" prop="productionWorkshopName"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产班次" prop="productionShiftName"></el-table-column>
			<el-table-column align="center" header-align="center" label="生产班组" prop="productionTeamName"></el-table-column>
			<el-table-column align="center" header-align="center" label="产品" prop="productName"></el-table-column>
			<el-table-column align="center" header-align="center" label="件数" prop="boxNum"></el-table-column>
			<el-table-column align="center" header-align="center" label="片数" prop="bagNum"></el-table-column>
			<el-table-column align="center" header-align="center" label="最大件数" prop="boxNumMaxLimited"></el-table-column>
			<el-table-column align="center" header-align="center" label="最大片数" prop="bagNumMaxLimited"></el-table-column>
			<el-table-column align="center" header-align="center" label="返工" prop="reworkFlag">
				<template #default="scope">
					{{ scope.row.reworkFlag ? '是' : '否' }}
				</template>
			</el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="200">
				<template #default="scope">
					<el-button v-if="!scope.row.reworkFlag" v-auth="'production:order:rework'" link type="primary" @click="reworkHandle(scope.row.id)">返工</el-button>
					<el-button v-if="scope.row.reworkFlag" v-auth="'production:order:cancelRework'" link type="primary" @click="cancelReworkHandle(scope.row.id)">撤销返工</el-button>
					<el-button v-auth="'production:order:update'" link type="primary" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'production:order:delete'" link type="primary" @click="deleteHandle(scope.row.id)">删除</el-button>
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
</template>

<script lang="ts" setup>
import { useCancelReworkApi, useReworkApi } from "@/api/production/order/api";
import { useCrud } from "@/hooks";
import { IHooksOptions } from "@/hooks/interface";
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue";
import AddOrUpdate from "./add-or-update.vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/production/order/page',
	deleteUrl: '/production/order/delete',
	queryForm: {
		orderNo: '',
		reworkFlag: null
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

interface BaseItem {
	code: string
	name: string
}




const reworkHandle = (id: number) => {
	useReworkApi(id).then(() => {
		ElMessage.success('返工成功')
		getDataList()
	})
}

const cancelReworkHandle = (id: number) => {
	useCancelReworkApi(id).then(() => {
		ElMessage.success('撤销返工成功')
		getDataList()
	})
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
