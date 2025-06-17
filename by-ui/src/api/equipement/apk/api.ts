import {BaseApi} from '@/utils/api'

const baseUrl = '/sys/apk'
const apkApi = new BaseApi({ baseUrl, moduleName: 'apk' })

export const useApkApi = (id: number) => apkApi.getInfo(id)
export const useApkSubmitApi = (dataForm: any) => apkApi.submit(dataForm)
export const deleteApkApi = (id: number) => apkApi.delete(id)
export const useApkListAllApi = () => apkApi.getList()
export const useApkPageApi = (params: any) => apkApi.getPage(params)
