import {BaseApi} from '@/utils/api'
import service from "@/utils/request";

const baseUrl = '/equipment/client'
const clientApi = new BaseApi({ baseUrl, moduleName: 'client' })


const useUpdatePasswordApi=(data:any)=> {

    return service.post(`${baseUrl}/updatePassword`,data)
}