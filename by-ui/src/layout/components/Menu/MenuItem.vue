<template>
	<el-sub-menu v-if="menu.children.length > 0" :key="menu.path" :index="menu.path">
		<template #title>
			<svg-icon v-if="showIcon && menu.meta.icon" :icon="menu.meta.icon"></svg-icon>
			<span>{{ menu.meta.title }}</span>
		</template>
		<menu-item v-for="sub in menu.children" :key="sub.path" :menu="sub"></menu-item>
	</el-sub-menu>
	<el-menu-item v-else :key="menu.path" :index="menu.path" @click="handleClickMenu(menu)">
		<svg-icon v-if="showIcon && menu.meta.icon" :icon="menu.meta.icon"></svg-icon>
		<template #title>
			{{ menu.meta.title }}
		</template>
	</el-menu-item>
</template>

<script setup lang="ts">
import store from '@/store'
import {isExternalLink, replaceLinkParam} from '@/utils/tool'
import {computed, PropType} from 'vue'
import {useRouter} from 'vue-router'

// 显示icon图标
const showIcon = computed(() => {
	return store.appStore.theme.layout !== 'columns'
})

defineProps({
	menu: {
		type: Object as PropType<any>,
		required: true
	}
})

const router = useRouter()

// 菜单点击事件
const handleClickMenu = (menu: any) => {
	// 不是新开页面，则直接切换路由
	if (!menu.meta.newOpen) {
		router.push(menu.path)
		return
	}

	// 新开页面逻辑
	if (isExternalLink(menu.meta.url)) {
		// 外链
		window.open(replaceLinkParam(menu.meta.url), '_blank')
	} else {
		// 内部组件
		window.open('#' + menu.meta.url, '_blank')
	}
}
</script>
