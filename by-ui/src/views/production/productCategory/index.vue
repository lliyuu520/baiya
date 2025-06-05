<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="父级编码">
				<el-select v-model="state.queryForm.parentCode" clearable>
					<el-option v-for="item in productCategoryList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
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
				<el-button v-auth="'production:productCategory:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="父级编码" prop="parentCode">
				<template #default="scope">
					{{ filterProductCategoryName(scope.row.parentCode) }}
				</template>
			</el-table-column>
			<el-table-column align="center" header-align="center" label="编码" prop="code"></el-table-column>
			<el-table-column align="center" header-align="center" label="名称" prop="name"></el-table-column>
			<el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button v-auth="'production:productCategory:update'" link type="primary" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'production:productCategory:delete'" link type="primary" @click="deleteHandle(scope.row.id)">删除</el-button>
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
import { useProductCategoryListAllApi } from "@/api/production/productCategory/api";
import { useCrud } from "@/hooks";
import { IHooksOptions } from "@/hooks/interface";
import { onMounted, reactive, ref } from "vue";
import AddOrUpdate from "./add-or-update.vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/production/productCategory/page',
	deleteUrl: '/production/productCategory/delete',
	queryForm: {
		code: '',
		name: '',
		parentCode: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

interface ProductCategory {
	code: string,
	name: string,
	parentCode: string
}

const productCategoryList = ref<ProductCategory[]>([])

const initProductCategoryList = () => {
	useProductCategoryListAllApi().then(res => {
		productCategoryList.value = res.data
	})
}

const filterProductCategoryName = (code: string) => {
	const productCategory = productCategoryList.value.find(item => item.code === code)
	return productCategory ? productCategory.name : ''
}

onMounted(() => {
	initProductCategoryList()
})

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
