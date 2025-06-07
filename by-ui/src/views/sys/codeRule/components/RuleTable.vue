<template>
    <div class="rule-detail-list">
        <el-table
            :data="ruleList"
            :resizable="false"
            border
            class="rule-table"
            row-key="id"
            @row-drop="onDragEnd"
        >
            <el-table-column :resizable="false" align="center" label="序号" width="60">
                <template #default="{ $index }">
                    <span class="weight-field">{{ $index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column :resizable="false" align="center" label="排序" width="60">
                <template #default>
                    <el-icon class="drag-handle"><Rank /></el-icon>
                </template>
            </el-table-column>
            <el-table-column :resizable="false" label="来源字段">
                <template #default="{ row }">
                    <el-select v-model="row.sourceField" class="form-input" @change="handleSourceFieldChange(row)">
                        <el-option v-for="sourceField in sourceFieldList" :key="sourceField.code" :label="sourceField.name" :value="sourceField.code" />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column :resizable="false" align="center" label="开始索引">
                <template #default="{ row }">
                    <el-input-number v-model="row.indexBegin" :controls="false" :min="-1" class="input-number-mini" :disabled="row.sourceField === 'BOX_NO'" />
                </template>
            </el-table-column>
            <el-table-column :resizable="false" align="center" label="结束索引">
                <template #default="{ row }">
                    <el-input-number v-model="row.indexEnd" :controls="false" :min="-1" class="input-number-mini" :disabled="row.sourceField === 'BOX_NO'" />
                </template>
            </el-table-column>
            <el-table-column :resizable="false" align="center" label="编码类型">
                <template #default="{ row }">
                    <el-select v-model="row.encodeType" class="form-input input-encode" :disabled="row.sourceField === 'BOX_NO'">
                        <el-option v-for="type in encodeTypeList" :key="type.code" :label="type.name" :value="type.code" />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column :resizable="false" align="center" label="常量值">
                <template #default="{ row }">
                    <el-input v-model="row.constant" :controls="false" :disabled="row.sourceField !== 'CONSTANT'" />
                </template>
            </el-table-column>

            <el-table-column :resizable="false" align="center" label="操作" width="60">
                <template #default="{ $index }">
                    <el-button b :icon="Delete" circle title="删除" type="danger" @click="removeRule($index)" />
                </template>
            </el-table-column>
        </el-table>
        <el-button  :icon="Plus" :title="addButtonTitle" circle class="add-rule-btn" type="primary" @click="addRule" />
    </div>
</template>

<script lang="ts" setup>
import { Delete, Plus, Rank } from "@element-plus/icons-vue";
import { computed } from "vue";
import type { RuleDetail, SourceFieldOption } from "../config/ruleTypes";
import { encodeTypeList } from "../config/ruleTypes";

const props = defineProps<{
    ruleList: RuleDetail[]
    sourceFieldList: SourceFieldOption[]
    isEditMode: boolean
    type: 'boxCode' | 'bagCode' | 'universalCode'
}>()

const emit = defineEmits<{
    (e: 'update:ruleList', value: RuleDetail[]): void
    (e: 'drag-end', evt: any): void
}>()

const addButtonTitle = computed(() => {
    switch (props.type) {
        case 'boxCode':
            return '新增箱码规则'
        case 'bagCode':
            return '新增袋码规则'
        case 'universalCode':
            return '新增万用码规则'
        default:
            return '新增规则'
    }
})

const handleSourceFieldChange = (row: RuleDetail) => {
    if (row.sourceField === 'BOX_NO') {
        // 当选择箱号时，设置默认值
        row.indexBegin = 0
        row.indexEnd = -1
        row.encodeType = 'base10'
        row.constant = ''
    } else if (row.sourceField !== 'CONSTANT') {
        // 当选择非常量时，清空常量值
        row.constant = ''
    }
}

const addRule = () => {
    const newRule: RuleDetail = {
        id: Date.now(),
        sourceField: '',
        indexBegin: 0,
        indexEnd: -1,
        weight: props.ruleList.length,
        encodeType: 'base10',
        constant: ''
    }

    const newList = [...props.ruleList, newRule]
    emit('update:ruleList', newList)
}

const removeRule = (index: number) => {
    const newList = [...props.ruleList]
    newList.splice(index, 1)
    // 重新计算weight值，确保连续性
    newList.forEach((item, idx) => {
        item.weight = idx
    })
    emit('update:ruleList', newList)
}

const onDragEnd = (evt: any) => {
    emit('drag-end', evt)
}
</script>

<style scoped>
.rule-detail-list {
    margin-bottom: 20px;
}

.rule-table {
    margin-bottom: 10px;
}

.rule-table :deep(.el-table__cell) {
    padding: 8px 0;
}

.rule-table :deep(.el-table__header) {
    background-color: #f7f8fa;
}

.rule-table :deep(.el-table__header th) {
    background-color: #f7f8fa;
    font-weight: 600;
    color: #666;
    text-align: center;
}

.rule-table :deep(.el-table__cell) {
    padding: 8px 0;
    text-align: center;
}

.rule-table :deep(.el-table__row) {
    height: 60px;
}

.drag-handle {
    cursor: move;
    color: #909399;
    font-size: 20px;
}

.weight-field {
    color: #666;
    font-size: 15px;
    font-weight: 500;
}

.add-rule-btn {
    margin-top: 8px;
    margin-left: 0;
}

.form-input {
    width: 120px;
    max-width: 120px;
    height: 36px;
    border-radius: 8px;
}

.input-number-mini {
    width: 80px;
    max-width: 80px;
    height: 36px;
    border-radius: 8px;
}

.input-encode {
    width: 100px;
    max-width: 100px;
    height: 36px;
    border-radius: 8px;
}
</style>
