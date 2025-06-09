import { BaseApi } from "@/utils/api";
import service from "@/utils/request";

const baseUrl = '/production/departAndWorkshop';
const departAndWorkshopApi = new BaseApi({ baseUrl, moduleName: 'depart' })

export const useDepartAndWorkshopApi = (id: number) => departAndWorkshopApi.getInfo(id)
export const useDepartAndWorkshopSubmitApi = (dataForm: any) => departAndWorkshopApi.submit(dataForm)
export const deleteDepartAndWorkshopApi = (id: number) => departAndWorkshopApi.delete(id)
export const useDepartAndWorkshopListAllApi = (parentCode?: string) => departAndWorkshopApi.getList({ parentCode })
export const useDepartAndWorkshopPageApi = (params: any) => departAndWorkshopApi.getPage(params)

export const useConfigCodeRuleApi = (dataForm: any) => {
    return service.post(`${baseUrl}/configCodeRule`, dataForm)
}

export const useConfigAliasApi = (dataForm: any) => {
    return service.post(`${baseUrl}/configAlias`, dataForm)
}
