import {BaseApi} from '@/utils/api'

const baseUrl = '/production/team'
const teamApi = new BaseApi({ baseUrl, moduleName: 'team' })

export const useTeamApi = (id: number) => teamApi.getInfo(id)
export const useTeamSubmitApi = (dataForm: any) => teamApi.submit(dataForm)
export const deleteTeamApi = (id: number) => teamApi.delete(id)
export const useTeamListAllApi = () => teamApi.getList()
export const useTeamPageApi = (params: any) => teamApi.getPage(params)
