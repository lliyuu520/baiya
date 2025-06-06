<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="编码">
				<el-input v-model="state.queryForm.code" clearable></el-input>
			</el-form-item>
			<el-form-item label="名称">
				<el-input v-model="state.queryForm.name" clearable></el-input>
			</el-form-item>
			<el-form-item label="产品类型">
				<el-select v-model="state.queryForm.productType" clearable filterable>
					<el-option v-for="item in productTypeList" :key="item.code" :label="item.name" :value="item.code"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column align="center" header-align="center" label="编码" prop="code"></el-table-column>
			<el-table-column align="center" header-align="center" label="名称" prop="name"></el-table-column>
			<el-table-column align="center" header-align="center" label="产品类型" prop="productType">
				<template #default="scope">
					{{ filterProductTypeName(scope.row.productType) }}
				</template>
			</el-table-column>
      <el-table-column align="center" header-align="center" label="单位" prop="unit"></el-table-column>
      <el-table-column align="center" header-align="center" label="一件片数" prop="oneBoxPackageNum"></el-table-column>
      <el-table-column align="center" header-align="center" label="货号" prop="goodsCode"></el-table-column>
      <el-table-column align="center" header-align="center" label="条形码" prop="barCode"></el-table-column>
      <el-table-column align="center" header-align="center" label="规格" prop="specification"></el-table-column>

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
import { onMounted, reactive, ref } from "vue";

const state: IHooksOptions = reactive({
	dataListUrl: '/production/product/page',
	deleteUrl: '/production/product/delete',
	queryForm: {
		code: '',
		name: '',
    	productType: ''
	}
})




const filterProductTypeName = (code: string) => {
	const productType = productTypeList.value.find(item => item.code === code)
	return productType ? productType.name : ''
}


interface ProductType{
	code: string,
	name:string
}

const productTypeList = ref<ProductType[]>([{
code:"FINISHED_PRODUCT",name:"成品"
},{
	code:"SEMI_FINISHED_PRODUCT",name:"半成品"
}])



const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
