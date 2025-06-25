<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item label="父级名称">
				<el-input v-model="state.queryForm.parentName" clearable></el-input>
			</el-form-item>
			<el-form-item  label="名称">
				<el-input v-model="state.queryForm.name" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column  align="center" header-align="center" label="父级名称" prop="parentName"></el-table-column>
			<el-table-column  align="center" header-align="center" label="父级编码" prop="parentCode"></el-table-column>
			<el-table-column  align="center" header-align="center" label="名称" prop="name"></el-table-column>
			<el-table-column  align="center" header-align="center" label="编码" prop="code"></el-table-column>
			<el-table-column  align="center" header-align="center" label="别名" prop="alias"></el-table-column>
			<el-table-column  align="center" header-align="center" label="编码规则" prop="codeRuleId">
				<template #default="scope">
					{{ filterCodeRule(scope.row.codeRuleId)?.name }}
				</template>
			</el-table-column>
			<el-table-column  align="center" header-align="center" label="操作" width="400">
				<template #default="scope">
					<el-button type="primary" @click="configCodeRuleHandle(scope.row.id)">配置编码规则</el-button>
					<el-button type="primary" @click="configAliasHandle(scope.row.id)">配置别名</el-button>
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
		<ConfigCodeRule ref="configCodeRuleRef" @refreshDataList="getDataList" />
		<ConfigAlias ref="configAliasRef" @refreshDataList="getDataList" />

		
	</el-card>
</template>

<script lang="ts" setup>
import {useCrud} from "@/hooks";
import {IHooksOptions} from "@/hooks/interface";
import {onMounted, reactive, ref} from "vue";
import ConfigCodeRule from "./configCodeRule.vue";
import ConfigAlias from "./configAlias.vue";
import {useCodeRuleListAllApi} from "@/api/sys/codeRule/api";
import {useDepartAndWorkshopListAllApi} from "@/api/production/departAndWorkshop/api";

const state: IHooksOptions = reactive({
	dataListUrl: '/production/departAndWorkshop/page',
	deleteUrl: '/production/departAndWorkshop/delete',
	queryForm: {
		name: '',
		parentName:''
	}
})

const configCodeRuleRef = ref()
const configAliasRef = ref()

const configCodeRuleHandle = (id: number) => {
	configCodeRuleRef.value.init(id)
}

const configAliasHandle = (id: number) => {
	configAliasRef.value.init(id)
}
interface CodeRule{
	id:number
	name:string
}

const codeRuleList = ref<CodeRule[]>([])

const initCodeRuleList = () => {
	useCodeRuleListAllApi().then(res => {
		codeRuleList.value = res.data
	})
}
interface DepartAndWorkshop {
	code: string
	name:string
}	

const departAndWorkshopList = ref<DepartAndWorkshop[]>([])
const initDepartAndWorkshopList = () => {
	useDepartAndWorkshopListAllApi().then(res => {
		departAndWorkshopList.value=res.data
	})
}


onMounted(() => {
	initCodeRuleList()
	initDepartAndWorkshopList()
})

const filterCodeRule = (id: number) => {
	return codeRuleList.value.find(item => item.id === id)
}

const filterDepartAndWorkshop = (code: string) => {
	return departAndWorkshopList.value.find(item => item.code === code)
}


const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
