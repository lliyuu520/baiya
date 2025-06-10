import {BaseApi} from "@/utils/api";

const baseUrl = '/production/shift'
const shiftApi = new BaseApi({ baseUrl, moduleName: 'shift' })

export const useShiftApi = (id: number) => shiftApi.getInfo(id)
export const useShiftSubmitApi = (dataForm: any) => shiftApi.submit(dataForm)
export const deleteShiftApi = (id: number) => shiftApi.delete(id)
export const useShiftListAllApi = () => shiftApi.getList()
export const useShiftPageApi = (params: any) => shiftApi.getPage(params)
