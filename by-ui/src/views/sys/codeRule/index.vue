<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="编码">
				<el-input v-model="state.queryForm.code" clearable></el-input>
			</el-form-item>
			<el-form-item label="名称">
				<el-input v-model="state.queryForm.name" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'sys:codeRule:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="编码" prop="code"></el-table-column>
			<el-table-column align="center" header-align="center" label="名称" prop="name"></el-table-column>
			<el-table-column align="center" header-align="center" label="是否当前" prop="enabled">
				<template #default="scope">
					<el-tag v-if="scope.row.enabled" type="success">是</el-tag>
					<el-tag v-else type="danger">否</el-tag>
				</template>
			</el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button v-auth="'sys:codeRule:update'" link type="primary" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'sys:codeRule:update'" link type="primary" @click="setCurrentHandle(scope.row.id)">设置为当前</el-button>
					<el-button v-auth="'sys:codeRule:delete'" link type="primary" @click="deleteHandle(scope.row.id)">删除</el-button>
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
import { useCodeRuleSetCurrentApi } from "@/api/sys/codeRule/api";
import { useCrud } from "@/hooks";
import { IHooksOptions } from "@/hooks/interface";
import { ElMessage } from "element-plus";
import { onMounted, reactive, ref } from "vue";
import AddOrUpdate from "./add-or-update.vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/sys/codeRule/page',
	deleteUrl: '/sys/codeRule/delete',
	queryForm: {
		code: '',
		name: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const setCurrentHandle = (id: number) => {
	useCodeRuleSetCurrentApi(id).then((res) => {

			ElMessage.success('设置成功')
			getDataList()

	})
}

onMounted(() => {
	getDataList()
})

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
