import { BaseApi } from "@/utils/api";

const baseUrl = '/production/departAndWorkshop';
const departAndWorkshopApi = new BaseApi({ baseUrl, moduleName: 'depart' })

export const useDepartAndWorkshopApi = (id: number) => departAndWorkshopApi.getInfo(id)
export const useDepartAndWorkshopSubmitApi = (dataForm: any) => departAndWorkshopApi.submit(dataForm)
export const deleteDepartAndWorkshopApi = (id: number) => departAndWorkshopApi.delete(id)
export const useDepartAndWorkshopListAllApi = () => departAndWorkshopApi.getList()
export const useDepartAndWorkshopPageApi = (params: any) => departAndWorkshopApi.getPage(params)
