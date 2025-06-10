import {BaseApi} from "@/utils/api";
import service from "@/utils/request";


const baseUrl = '/sys/codeRule'
const codeRuleApi = new BaseApi({ baseUrl, moduleName: 'codeRule' })

// 分页查询
export const useCodeRulePageApi = (params: any) => codeRuleApi.getPage(params)

// 获取信息
export const useCodeRuleApi = (id: number) => codeRuleApi.getInfo(id)

// 新增/修改
export const useCodeRuleSubmitApi = (dataForm: any) => codeRuleApi.submit(dataForm)

// 删除
export const useCodeRuleDeleteApi = (id: number) => codeRuleApi.delete(id)

// 获取所有编码规则
export const useCodeRuleListAllApi = () => codeRuleApi.getList()

// 设置为当前
export const useCodeRuleSetCurrentApi = (id: number) => {
    return service.post(`${baseUrl}/setCurrent?id=${id}`)
}
