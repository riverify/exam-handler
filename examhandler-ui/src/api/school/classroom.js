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
export function getClassroom(classroomNumber) {
  return request({
    url: '/school/classroom/' + classroomNumber,
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
export function delClassroom(classroomNumber) {
  return request({
    url: '/school/classroom/' + classroomNumber,
    method: 'delete'
  })
}
