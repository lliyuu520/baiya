<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="表名">
				<el-input v-model="state.queryForm.tableName" clearable></el-input>
			</el-form-item>
			<el-form-item label="描述">
				<el-input v-model="state.queryForm.tableComment" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button  type="primary" @click="addHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="表名" prop="tableName"></el-table-column>
			<el-table-column align="center" header-align="center" label="描述" prop="tableComment"></el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button link type="primary" @click="fieldHandle(scope.row.id,scope.row.tableName)">字段</el-button>
					<el-button  link type="primary" @click="deleteHandle(scope.row.id)">删除</el-button>
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
		<add ref="addRef" @refreshDataList="getDataList"></add>
    <field ref="fieldRef" @refreshDataList="getDataList"></field>
	</el-card>
</template>

<script lang="ts" setup>
import {useCrud} from "@/hooks";
import {reactive, ref} from "vue";
import {IHooksOptions} from "@/hooks/interface";
import Add from "./add.vue";
import Field from "./field.vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/sys/tableConfig/page',
	queryForm: {
		tableName: '',
		tableComment: ''
	}
})

const addRef = ref()
const addHandle = () => {
	addRef.value.init()
}

const fieldRef = ref()

const fieldHandle = (id: number,tableName:string) => {
  fieldRef.value.init(id,tableName)
}


const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
