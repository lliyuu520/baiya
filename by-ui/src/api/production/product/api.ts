import { BaseApi } from '@/utils/api'
import service from '@/utils/request'

const baseUrl = '/production/product'
const productApi = new BaseApi({ baseUrl, moduleName: 'product' })

export const useProductApi = (id: number) => productApi.getInfo(id)
export const useProductSubmitApi = (dataForm: any) => productApi.submit(dataForm)
export const deleteProductApi = (id: number) => productApi.delete(id)
export const useProductPageApi = (params: any) => productApi.getPage(params)

export const useProductListByParentCodeApi=(parentCode:string)=>{
    const url = `${baseUrl}/listByParentCode?parentCode=${parentCode}`
    return service.get(url)
}
export const useProductListAllApi = (productType?: string) => {
    if(productType){
        return service.get(`${baseUrl}/list?productType=${productType}`)
    }else{
        return service.get(`${baseUrl}/list`)
    }
}

