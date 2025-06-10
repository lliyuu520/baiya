import {BaseApi} from "@/utils/api";
import service from "@/utils/request";

const baseUrl = '/sys/tableConfig'
const tableConfigApi = new BaseApi({ baseUrl, moduleName: 'tableConfig' })

export const useTableConfigApi = (id: number) => tableConfigApi.getInfo(id)
export const useTableConfigSubmitApi = (dataForm: any) => tableConfigApi.submit(dataForm)

export const useTableConfigListAllApi = () => tableConfigApi.getList()

export const useTableConfigVOListApi = () => {
    return service.get(`${baseUrl}/listAllVO`)
}
