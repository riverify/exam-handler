import request from '@/utils/request'

// 查询view列表
export function listView(query) {
  return request({
    url: '/school/view/list',
    method: 'get',
    params: query
  })
}

// 查询view详细
export function getView(studentId) {
  return request({
    url: '/school/view/' + studentId,
    method: 'get'
  })
}

// 新增view
export function addView(data) {
  return request({
    url: '/school/view',
    method: 'post',
    data: data
  })
}

// 修改view
export function updateView(data) {
  return request({
    url: '/school/view',
    method: 'put',
    data: data
  })
}

// 删除view
export function delView(studentId) {
  return request({
    url: '/school/view/' + studentId,
    method: 'delete'
  })
}
