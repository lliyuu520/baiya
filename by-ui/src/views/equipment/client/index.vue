<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item  label="版本号">
				<el-input v-model="state.queryForm.versionNo" clearable></el-input>
			</el-form-item>
			<el-form-item  label="版本名称">
				<el-input v-model="state.queryForm.versionName" clearable></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>

		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column  align="center" header-align="center" label="MAC地址" prop="macAddress"></el-table-column>
			<el-table-column  align="center" header-align="center" label="IP" prop="IP"></el-table-column>
      <el-table-column  align="center" header-align="center" label="工厂" prop="factoryNo"></el-table-column>
      <el-table-column  align="center" header-align="center" label="车间" prop="departCode"></el-table-column>
      <el-table-column  align="center" header-align="center" label="车间" prop="workshopNo"></el-table-column>
      <el-table-column  align="center" header-align="center" label="机台" prop="machineNo"></el-table-column>
      <el-table-column align="center" fixed="right" header-align="center" label="操作" width="150">
				<template #default="scope">
					<el-button v-auth="'equipment:client:updatePassword'" link type="primary" @click="updatePasswordHandle(scope.row.id)">修改密码</el-button>
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
		<update-password ref="addOrUpdateRef" @refreshDataList="getDataList"></update-password>
	</el-card>
	
</template>

<script lang="ts" setup>
import {useCrud} from "@/hooks";
import {IHooksOptions} from "@/hooks/interface";
import {reactive, ref} from "vue";
import UpdatePassword from "./update-password.vue";
import QrcodeVue from 'qrcode.vue'


const state: IHooksOptions = reactive({
	dataListUrl: '/equipment/client/page',
	queryForm: {
		macAddress: '',
		ip: '',
		factoryNo: '',
		departCode: '',
		workshopNo: '',
		machineNo: '',
	}
})

const updatePasswordRef = ref()

const updatePasswordHandle = (id: number) => {
	updatePasswordRef.value.init(id)
}




const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteHandle } = useCrud(state)
</script>
