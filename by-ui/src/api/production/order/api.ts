import {BaseApi} from '@/utils/api'
import service from '@/utils/request'

const baseUrl = '/production/order'
const orderApi = new BaseApi({ baseUrl, moduleName: 'order' })

export const useOrderApi = (id: number) => orderApi.getInfo(id)
export const useOrderSubmitApi = (dataForm: any) => orderApi.submit(dataForm)
export const deleteOrderApi = (id: number) => orderApi.delete(id)
export const useOrderListAllApi = () => orderApi.getList()
export const useOrderPageApi = (params: any) => orderApi.getPage(params)

export const useReworkApi = (id: number) => {
    return service.post(`${baseUrl}/rework?id=${id}`)
}

export const useCancelReworkApi = (id: number) => {
    return service.post(`${baseUrl}/cancelRework?id=${id}`)
}

