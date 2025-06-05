import service from "@/utils/request";

const baseUrl = '/sys/tableField'

export const useInfoByTableConfigIdApi = (tableConfigId: number) =>{
  return service.get(`${baseUrl}/infoByTableConfigId?tableConfigId=${tableConfigId}`)
}
export const useListByTableConfigIdApi = (tableConfigId: number) =>{
  return service.get(`${baseUrl}/listByTableConfigId?tableConfigId=${tableConfigId}`)
}

export const useSubmitApi = (dataForm: any) =>{
  return service.post(`${baseUrl}`,dataForm)
}
