import request from '@/utils/request'

// 查询考场列表
export function listClassroom(query) {
  return request({
    url: '/school/classroom/list',
    method: 'get',
    params: query
  })
}

// 查询考场详细
export function getClassroom(columnId) {
  return request({
    url: '/school/classroom/' + columnId,
    method: 'get'
  })
}

// 新增考场
export function addClassroom(data) {
  return request({
    url: '/school/classroom',
    method: 'post',
    data: data
  })
}

// 修改考场
export function updateClassroom(data) {
  return request({
    url: '/school/classroom',
    method: 'put',
    data: data
  })
}

// 删除考场
export function delClassroom(columnId) {
  return request({
    url: '/school/classroom/' + columnId,
    method: 'delete'
  })
}
