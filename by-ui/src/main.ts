import {createPinia} from "pinia";
import "virtual:svg-icons-register";
import {createApp} from "vue";
import App from "./App.vue";
import {router} from "./router";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import {registerStore} from "./store";
import {directive} from "./utils/directive";

import "@/icons/iconfont/iconfont";
import "@/styles/index.scss";

import VXETable from "vxe-table";
import "vxe-table/lib/style.css";
import "xe-utils";

import FastRadioGroup from "@/components/fast-radio-group";
import FastSelect from "@/components/fast-select";
import FastTableColumn from "@/components/fast-table-column";
import FastUser from "@/components/fast-user";
import SvgIcon from "@/components/svg-icon";
import zhCn from "element-plus/es/locale/lang/zh-cn";

VXETable.setConfig({
	zIndex: 3000,
	select: {
		transfer: true
	}
})

const app = createApp(App)

// 注册全局自定义指令
directive(app)

// 注册全局组件
app.component('SvgIcon', SvgIcon)
app.component('FastTableColumn', FastTableColumn)
app.component('FastRadioGroup', FastRadioGroup)
app.component('FastSelect', FastSelect)
app.component('FastUser', FastUser)

// 注册状态管理器
const pinia = createPinia()
app.use(pinia)
registerStore()

// 注册路由
app.use(router)

// 注册 Element Plus
app.use(ElementPlus,{ locale: zhCn })

app.mount('#app')
