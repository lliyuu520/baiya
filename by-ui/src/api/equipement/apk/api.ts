import {BaseApi} from '@/utils/api'
import service from "@/utils/request";

const baseUrl = '/equipment/apk'
const apkApi = new BaseApi({ baseUrl, moduleName: 'apk' })

export const useApkApi = (id: number) => apkApi.getInfo(id)
export const useApkSubmitApi = (dataForm: any) => apkApi.submit(dataForm)

export const useUploadApkApi = (formData: FormData) => {
    return service.post('/equipment/apk/uploadApk', formData)

}
