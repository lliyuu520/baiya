import { BaseApi } from '@/utils/api'

const baseUrl = '/production/factory'
const factoryApi = new BaseApi({ baseUrl, moduleName: 'factory' })

export const useFactoryApi = (id: number) => factoryApi.getInfo(id)
export const useFactorySubmitApi = (dataForm: any) => factoryApi.submit(dataForm)
export const deleteFactoryApi = (id: number) => factoryApi.delete(id)
export const useFactoryListAllApi = () => factoryApi.getList()
export const useFactoryPageApi = (params: any) => factoryApi.getPage(params)
