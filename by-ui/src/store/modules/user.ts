import { defineStore } from 'pinia'
import { useAccountLoginApi, useMobileLoginApi, useLogoutApi } from '@/api/auth'
import { useUserInfoApi } from '@/api/sys/user'
import cache from '@/utils/cache'
import { useAuthorityListApi } from '@/api/sys/menu'
import { useFieldAuthorityListApi } from '@/api/sys/role'

export const userStore = defineStore('userStore', {
	state: () => ({
		// 用户信息
		user: {
			id: '',
			username: ''
		},
		// 权限列表
		authorityList: [],
		// 字段权限列表
		fieldAuthorityList: [],
		// 登录token
		token: cache.getToken()
	}),
	actions: {
		setUser(val: any) {
			this.user = val
		},
		setToken(val: any) {
			this.token = val
			cache.setToken(val)
		},
		// 账号登录
		async accountLoginAction(loginForm: any) {
			const { data } = await useAccountLoginApi(loginForm)
			this.setToken(data.accessToken)
		},

		// 获取用户信息
		async getUserInfoAction() {
			const { data } = await useUserInfoApi()
			this.setUser(data)
		},
		// 获取权限
		async getAuthorityListAction() {
			const { data } = await useAuthorityListApi()
			this.authorityList = data || []
		},
		// 获取字段权限

		async getFieldAuthorityListAction() {
			const { data } = await useFieldAuthorityListApi()
			this.fieldAuthorityList = data || []
		},



		// 用户退出
		async logoutAction() {
			await useLogoutApi()

			// 移除 token
			this.setToken(null)
		}
	}
})
