<template>
  <el-dialog v-model="visible" :close-on-click-modal="false" title="新增子级" draggable>
    <el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="120px" @keyup.enter="submitHandle()">
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="dataForm.type">
          <el-radio :value="0">菜单</el-radio>
          <el-radio :value="1">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="dataForm.name" placeholder="名称"></el-input>
      </el-form-item>
      <el-form-item label="上级菜单" prop="parentName">
        <el-input v-model="dataForm.parentName" disabled="disabled"></el-input>
      </el-form-item>
      <el-form-item v-if="dataForm.type === 0" label="路由" prop="url">
        <el-input v-model="dataForm.url" placeholder="路由"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="weight">
        <el-input-number v-model="dataForm.weight" :min="0" controls-position="right" label="排序"></el-input-number>
      </el-form-item>
      <el-form-item label="授权标识" prop="perms">
        <el-input
          v-model="permsInput"
          placeholder="输入后回车或逗号添加，多个用Tag显示"
          @keyup.enter.native="addPermTag"
          @blur="formatPermsInput"
          clearable
        />
        <div style="margin-top: 8px;">
          <el-tag
            v-for="(tag, idx) in permsTags"
            :key="String(tag)"
            closable
            @close="removePermTag(idx)"
            style="margin-right: 4px;"
          >
            {{ tag }}
          </el-tag>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="submitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { useMenuSubmitApi } from "@/api/sys/menu";
import { ElMessage } from "element-plus/es";
import { reactive, ref, watch } from "vue";

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
  type: 0,
  name: '',
  parentId: 0,
  parentName: '',
  url: '',
  perms: '',
  weight: 0,
  openStyle: 0
})

const permsInput = ref('')
const permsTags = ref<string[]>([])

watch(
  () => dataForm.perms,
  (val) => {
    if (typeof val === 'string' && val) {
      permsTags.value = val.split(',').map(s => s.trim()).filter(s => !!s)
    } else {
      permsTags.value = []
    }
  },
  { immediate: true }
)

const addPermTag = () => {
  if (!permsInput.value) return
  const tags = permsInput.value.split(/,|，/).map(s => s.trim()).filter(Boolean)
  tags.forEach(tag => {
    if (tag && !permsTags.value.includes(tag)) {
      permsTags.value.push(tag)
    }
  })
  permsInput.value = ''
}

const removePermTag = (idx: number) => {
  permsTags.value.splice(idx, 1)
}

const formatPermsInput = () => {
  addPermTag()
}

const init = (parentId: number, parentName: string) => {
  visible.value = true
  if (dataFormRef.value) {
    dataFormRef.value.resetFields()
  }
  dataForm.parentId = parentId
  dataForm.parentName = parentName
}

const dataRules = ref({
  name: [{required: true, message: '必填项不能为空', trigger: 'blur'}],
})

const submitHandle = () => {
  dataForm.perms = permsTags.value.join(',')
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false
    }
    useMenuSubmitApi(dataForm).then(() => {
      ElMessage.success({
        message: '操作成功',
        duration: 500,
        onClose: () => {
          visible.value = false
          emit('refreshDataList')
        }
      })
    })
  })
}

defineExpose({
  init
})
</script>

<style lang="scss" scoped>
</style> 