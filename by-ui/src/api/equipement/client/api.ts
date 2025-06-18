import {BaseApi} from '@/utils/api'
import service from "@/utils/request";

const baseUrl = '/equipment/client'
const clientApi = new BaseApi({ baseUrl, moduleName: 'client' })
export const useClientApi = (id: number) => clientApi.getInfo(id)


export const useUpdatePasswordApi=(data:any)=> {

    return service.post(`${baseUrl}/updatePassword`,data)
}