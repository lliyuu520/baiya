<template>
	<el-select :clearable="clearable" :model-value="modelValue + ''" :placeholder="placeholder" @change="$emit('update:modelValue', $event)">
		<el-option v-for="data in dataList" :key="data.dictValue" :label="data.dictLabel" :value="data.dictValue">{{ data.dictLabel }}</el-option>
	</el-select>
</template>

<script lang="ts" name="FastSelect" setup>
import store from "@/store";
import { getDictDataList } from "@/utils/tool";

const props = defineProps({
	modelValue: {
		type: [Number, String],
		required: true
	},
	dictType: {
		type: String,
		required: true
	},
	clearable: {
		type: Boolean,
		required: false,
		default: () => false
	},
	placeholder: {
		type: String,
		required: false,
		default: () => ''
	}
})

const dataList = getDictDataList(store.appStore.dictList, props.dictType)
</script>
