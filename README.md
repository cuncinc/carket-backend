# API接口

## User

| path                  | 紧急 | 完成 | method | return  | 意义                                 |
| --------------------- | :--: | :--: | :----: | :-----: | :----------------------------------- |
| /user/loginByPassword |  1   |  √   |  POST  |  token  | 通过手机和密码登录：手机号、密码     |
| /user/loginByCode     |  1   |  √   |  POST  |  token  | 通过手机和验证码登录：手机号、验证码 |
| /user/sendSmsCode     |  1   |  √   |  GET   |  状态   | 通过手机发送验证码：手机号           |
| /user/logon           |  1   |  √   |  POST  | 用户id  | 注册：手机、密码、验证码             |
| /user/updatePassword  |  2   |      |  POST  |  bool   | 修改密码：id、验证码、密码           |
| /user/updateAvatar    |  1   |  √   |  POST  |  bool   | 修改头像：id、图片流                 |
| /user/updateInfo      |  2   |  √   |  POST  |  bool   | 通过id修改个人信息：name、联系信息   |
| /user/updatePhone     |  2   |      |  POST  |  bool   | 通过id修改手机：旧手机验证码、新手机 |
| /user/logout          |  2   |      |  POST  |  bool   | 通过id注销：验证码                   |
| /user/getBalance      |  1   |      |  GET   | balance | 通过id获取余额                       |
| /user/updateBalance   |  1   |      |  POST  |  bool   | 通过id更新余额                       |
| /user/getUserById     |  1   |  √   |  GET   |  用户   | 通过id获取用户                       |

## Goods

| path                | 紧急 | 完成 | method |  return  | 意义                                                         |
| ------------------- | :--: | :--: | :----: | :------: | :----------------------------------------------------------- |
| /goods/release      |  1   |  √   |  POST  |  商品id  | 发布一个商品：用户id、描述、金额、图片、分类                 |
| /goods/updateInfo   |  1   |  √   |  POST  |   bool   | 通过id修改商品信息：用户id、商品id、描述、金额、图片(验证用户id) |
| /goods/updateStatus |  1   |      |  POST  |   bool   | 通过id修改状态：用户id、商品id、状态                         |
| /goods/getById      |  1   |  √   |  GET   |   商品   | 通过id获取商品                                               |
| /goods/getAllByUser |  1   |      |  Get   | 商品列表 | 通过用户id获取所有商品                                       |
| /goods/             |      |      |        |          |                                                              |
|                     |      |      |        |          |                                                              |

## Order

| path                  | 紧急 | 完成 | method | return | 意义                       |
| --------------------- | :--: | :--: | :----: | :----: | :------------------------- |
| /order/buy            |  1   |  √   |  POST  |  bool  | 购买商品：用户id、商品id   |
| /order/getBuyerDoing  |  1   |      |  POST  |        | 获取买家正在买的订单列表   |
| /order/getBuyerDone   |  1   |      |  POST  |        | 获取卖家已完成买的订单列表 |
| /order/getSellerDoing |  1   |      |  POST  |        | 获取买家正在买的订单列表   |
| /order/getSellerDone  |  1   |      |  POST  |        | 获取卖家已完成的订单列表   |
|                       |      |      |        |        |                            |
|                       |      |      |        |        |                            |
|                       |      |      |        |        |                            |
|                       |      |      |        |        |                            |

## Star

| path         | 紧急 | 完成 | method | return | 意义                     |
| ------------ | :--: | :--: | :----: | :----: | :----------------------- |
| /star/add    |  2   |  √   |  POST  |  bool  | 点赞商品：用户id、商品id |
| /star/delete |  2   |      |  POST  |  bool  | 点赞商品：用户id、商品id |

## Admin

| path   | 紧急 | 完成 | method | return | 意义                     |
| ------ | :--: | :--: | :----: | :----: | :----------------------- |
| /admin |  2   |  √   |  POST  |  bool  | 点赞商品：用户id、商品id |
| /admin |  2   |      |  POST  |  bool  | 点赞商品：用户id、商品id |

## Feedback

| path          | 紧急 | 完成 | method | return | 意义                       |
| ------------- | :--: | :--: | :----: | :----: | :------------------------- |
| /feedback/add |  2   |  √   |  POST  |  bool  | 反馈：用户、内容、反馈时间 |
|               |      |      |        |        |                            |

