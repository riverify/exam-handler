import request from '@/utils/request'

// 查询考场安排列表
export function listManager(query) {
  return request({
    url: '/school/manager/list',
    method: 'get',
    params: query
  })
}

// 查询考场安排详细
export function getManager(managerId) {
  return request({
    url: '/school/manager/' + managerId,
    method: 'get'
  })
}

// 新增考场安排
export function addManager(data) {
  return request({
    url: '/school/manager',
    method: 'post',
    data: data
  })
}

// 修改考场安排
export function updateManager(data) {
  return request({
    url: '/school/manager',
    method: 'put',
    data: data
  })
}

// 删除考场安排
export function delManager(managerId) {
  return request({
    url: '/school/manager/' + managerId,
    method: 'delete'
  })
}
