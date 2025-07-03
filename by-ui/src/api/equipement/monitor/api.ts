import request from '@/utils/request';

// 分页查询设备监控列表
export function getEquipmentMonitorPage(params: { pageNum?: number; pageSize?: number }) {
  return request.get('/equipment/monitor/page', { params });
}

// 获取设备监控详情
export function getEquipmentMonitorInfo(id: number) {
  return request.get(`/equipment/monitor/info/${id}`);
} 