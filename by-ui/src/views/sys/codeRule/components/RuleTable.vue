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
            <el-table-column align="center" label="序号" width="60">
                <template #default="{ $index }">
                    <span class="weight-field">{{ $index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" label="排序" width="60">
                <template #default>
                    <el-icon class="drag-handle"><Rank /></el-icon>
                </template>
            </el-table-column>
            <el-table-column label="来源字段" width="180">
                <template #default="{ row }">
                    <el-select v-model="row.sourceField" class="form-input" @change="handleSourceFieldChange(row)">
                        <el-option v-for="sourceField in sourceFieldList" :key="sourceField.code" :label="sourceField.name" :value="sourceField.code" />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column align="center" label="开始索引" width="90">
                <template #default="{ row }">
                    <el-input-number v-model="row.indexBegin" :controls="false" :min="-1" class="input-number-mini" 
                    :disabled=" row.sourceField === 'SPECIFY_BOX_NO' || row.sourceField === 'CONSTANT' || row.sourceField === 'BOX_NO' || row.sourceField === 'RANDOM_STRING' || row.sourceField === 'FINISHED_TEAM_CODE' || row.sourceField === 'SEMI_FINISHED_TEAM_CODE'" />
                </template>
            </el-table-column>
            <el-table-column align="center" label="结束索引" width="90">
                <template #default="{ row }">
                    <el-input-number v-model="row.indexEnd" :controls="false" :min="-1" class="input-number-mini" 
                    :disabled=" row.sourceField === 'SPECIFY_BOX_NO' || row.sourceField === 'CONSTANT' || row.sourceField === 'BOX_NO' || row.sourceField === 'RANDOM_STRING' || row.sourceField === 'FINISHED_TEAM_CODE' || row.sourceField === 'SEMI_FINISHED_TEAM_CODE'" />
                </template>
            </el-table-column>
            <el-table-column align="center" label="编码" width="180">
                <template #default="{ row }">
                    <el-select v-model="row.encodeType" class="form-input input-encode" 
                    :disabled=" row.sourceField === 'SPECIFY_BOX_NO' || row.sourceField === 'CONSTANT' || row.sourceField === 'BOX_NO' || row.sourceField === 'FINISHED_TEAM_CODE' || row.sourceField === 'SEMI_FINISHED_TEAM_CODE'" >

                        <el-option v-for="type in encodeTypeList" :key="type.code" :label="type.name" :value="type.code" />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column align="center" label="长度" width="80">
                <template #default="{ row }">
                    <el-input-number v-model="row.length" :controls="false" :min="0" class="input-number-mini" 
                      :disabled=" row.sourceField !== 'BOX_NO' && row.sourceField !== 'RANDOM_STRING'"
                    />
                </template>
            </el-table-column>

            <el-table-column align="center" label="取值" width="200">
                <template #default="{ row }">
                    <el-input v-if="row.sourceField === 'CONSTANT' || row.sourceField === 'SPECIFY_BOX_NO'"
                        v-model="row.constant" 
                        class="input-constant" />
                    <el-select v-else-if="row.sourceField === 'RANDOM_STRING'"
                        v-model="row.randomType"
                        class="form-input">
                        <el-option
                            v-for="type in randomTypeList"
                            :key="type.code"
                            :label="type.name"
                            :value="type.code"
                        />
                    </el-select>
                    <el-input v-else
                        v-model="row.constant"
                        class="input-constant"
                        disabled />
                </template>
            </el-table-column>
            <el-table-column align="center" label="偏移年份" width="100">
                <template #default="{ row }">
                    <el-input-number v-model="row.offsetYears" :controls="false" :min="0" class="input-number-mini" 
                    :disabled=" row.sourceField !== 'FINISHED_PRODUCTION_DATE' && row.sourceField !== 'SEMI_FINISHED_PRODUCTION_DATE'"
                    />
                </template>
            </el-table-column>

            <el-table-column :resizable="false" align="center" label="操作" width="160">
                <template #default="{ $index }">
                    <el-button b :icon="ArrowUp" circle title="在上方插入" type="primary" @click="insertRule($index, 'above')" />
                    <el-button b :icon="Delete" circle title="删除" type="danger" @click="removeRule($index)" />
                    <el-button b :icon="ArrowDown" circle title="在下方插入" type="primary" @click="insertRule($index, 'below')" />
                </template>
            </el-table-column>
        </el-table>
        <el-button  :icon="Plus" :title="addButtonTitle" circle class="add-rule-btn" type="primary" @click="addRule" />
    </div>
</template>

<script lang="ts" setup>
import { ArrowDown, ArrowUp, Delete, Plus, Rank } from "@element-plus/icons-vue";
import { computed } from "vue";
import type { RuleDetail, SourceFieldOption } from "../config/ruleTypes";
import { encodeTypeList, randomTypeList } from "../config/ruleTypes";

const props = defineProps<{
    ruleList: RuleDetail[]
    sourceFieldList: SourceFieldOption[]
    isEditMode: boolean
    type: 'boxCode' | 'innerBoxCode' | 'bagCode' | 'universalCode'
}>()

const emit = defineEmits<{
    (e: 'update:ruleList', value: RuleDetail[]): void
    (e: 'drag-end', evt: any): void
}>()

const addButtonTitle = computed(() => {
    switch (props.type) {
        case 'boxCode':
            return '新增箱码规则'
        case 'innerBoxCode':
            return '新增箱内码规则'
        case 'bagCode':
            return '新增袋码规则'
        case 'universalCode':
            return '新增万用码规则'
        default:
            return '新增规则'
    }
})

const handleSourceFieldChange = (row: RuleDetail) => {
    // 当选择箱号或指定箱号时，设置默认值
    row.indexBegin = 0
    row.indexEnd = -1
    row.encodeType = 'BASE_10'
    row.constant = ''
    
    // 当选择随机字符串时，设置默认随机类型
    if (row.sourceField === 'RANDOM_STRING') {
        row.randomType = 'NUMBER'
        // 设置 RANDOM_TYPE 为随机类型
        row.constant = row.randomType
    } else {
        row.randomType = ''
    }

    // 当选择班次时，设置结束索引为1
    if (row.sourceField === 'FINISHED_TEAM_CODE' || row.sourceField === 'SEMI_FINISHED_TEAM_CODE') {
        row.indexEnd = 1
    }
}

const addRule = () => {
    const newRule: RuleDetail = {
        id: Date.now(),
        sourceField: '',
        indexBegin: 0,
        indexEnd: -1,
        weight: props.ruleList.length,
        encodeType: 'BASE_10',
        constant: '',
        length: 0,
        randomType: '',
        offsetYears: 0
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

const insertRule = (index: number, position: 'above' | 'below') => {
    const newRule: RuleDetail = {
        id: Date.now(),
        sourceField: '',
        indexBegin: 0,
        indexEnd: 0,
        encodeType: 'BASE_10',
        constant: '',
        weight: 0,
        length: 0,
        randomType: '',
        offsetYears: 0
    }
    
    const insertIndex = position === 'above' ? index : index + 1
    props.ruleList.splice(insertIndex, 0, newRule)
    
    // 更新所有规则的weight
    props.ruleList.forEach((item, idx) => {
        item.weight = idx
    })
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
    width: 100%;
    max-width: 100%;
    height: 36px;
    border-radius: 8px;
}

.input-number-mini {
    width: 100%;
    max-width: 100%;
    height: 36px;
    border-radius: 8px;
}

.input-encode {
    width: 100%;
    max-width: 100%;
    height: 36px;
    border-radius: 8px;
}

.input-constant {
    width: 100%;
    max-width: 100%;
    height: 36px;
    border-radius: 8px;
}
</style>
