<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="操作人">
				<el-input v-model="state.queryForm.operator" clearable></el-input>
			</el-form-item>
			<el-form-item label="操作类型">
				<el-input v-model="state.queryForm.operateType" clearable></el-input>
			</el-form-item>
			<el-form-item label="操作模块">
				<el-input v-model="state.queryForm.operateModule" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="操作人" prop="operator" width="120"></el-table-column>
			<el-table-column align="center" header-align="center" label="操作类型" prop="operateType" width="100"></el-table-column>
			<el-table-column align="center" header-align="center" label="操作模块" prop="operateModule" width="120"></el-table-column>
			<el-table-column align="center" header-align="center" label="请求方法" prop="requestMethod" width="100"></el-table-column>
			<el-table-column align="center" header-align="center" label="请求URL" prop="requestUrl" show-overflow-tooltip></el-table-column>
			<el-table-column align="center" header-align="center" label="请求参数" prop="requestParams" show-overflow-tooltip></el-table-column>
			<el-table-column align="center" header-align="center" label="响应状态" prop="responseStatus" width="100"></el-table-column>
			<el-table-column align="center" header-align="center" label="耗时(ms)" prop="costTime" width="100"></el-table-column>
			<el-table-column align="center" header-align="center" label="操作IP" prop="operateIp" width="120"></el-table-column>
			<el-table-column align="center" header-align="center" label="操作时间" prop="createTime" width="160"></el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button link type="primary" @click="addOrUpdateHandle(scope.row.id)">查看</el-button>
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
import {useCrud} from "@/hooks";
import {IHooksOptions} from "@/hooks/interface";
import {reactive, ref} from "vue";
import AddOrUpdate from "./add-or-update.vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/sys/log/page',
	queryForm: {
		operator: '',
		operateType: '',
		operateModule: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
