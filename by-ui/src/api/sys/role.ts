import service from '@/utils/request'

export const useRoleMenuApi = () => {
	return service.get('/sys/role/menu')
}

export const useRoleApi = (id: number) => {
	return service.get('/sys/role/' + id)
}

export const useRoleListApi = () => {
	return service.get('/sys/role/list')
}

export const useRoleSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/sys/role', dataForm)
	} else {
		return service.post('/sys/role', dataForm)
	}
}

export const useRoleFieldSubmitApi = (dataForm: any) => {
	return service.post('/sys/role/saveField', dataForm)
}

export const useGetFieldIdByRoleId = (id: number) => {
	return service.get('/sys/role/getFieldIdByRoleId?id=' + id)


	
}

export const useFieldAuthorityListApi = () => {
	return service.get("/sys/role/getFieldPermission")
	
}


