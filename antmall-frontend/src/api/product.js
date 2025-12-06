import request from '../utils/request'

// 商品相关API
const productApi = {
  // 分页查询商品
  page: (queryBO) => {
    return request({
      url: '/product/page',
      method: 'get',
      params: queryBO
    })
  },
  
  // 添加商品
  add: (addBO) => {
    return request({
      url: '/product/add',
      method: 'post',
      data: addBO
    })
  },
  
  // 编辑商品
  edit: (editBO) => {
    return request({
      url: '/product/edit',
      method: 'post',
      data: editBO
    })
  },
  
  // 删除商品
  delete: (idList) => {
    return request({
      url: '/product/delete',
      method: 'post',
      data: idList
    })
  }
}

export default productApi
