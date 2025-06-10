import {BaseApi} from "@/utils/api";

const baseUrl = '/production/productCategory'
const productCategoryApi = new BaseApi({ baseUrl, moduleName: 'productCategory' })

export const useProductCategoryApi = (id: number) => productCategoryApi.getInfo(id)
export const useProductCategorySubmitApi = (dataForm: any) => productCategoryApi.submit(dataForm)
export const deleteProductCategoryApi = (id: number) => productCategoryApi.delete(id)
export const useProductCategoryListAllApi = () => productCategoryApi.getList()
export const useProductCategoryPageApi = (params: any) => productCategoryApi.getPage(params)
