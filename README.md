# jcoffeeshop

# 📚 目录

TODO

#  🛠 Get Started

TODO

# 🧪 功能概述

系统一种有五大模块：

1. 类目模块
2. 商品模块
3. 用户模块
4. 购物车模块
5. 订单模块

## 类目模块

### 1. 新增类目

🚀 API路径：`POST /api/category`

📥 HTTP请求数据模型：

```json
{
  "name": "炸鸡🍗",
  "description": "好吃不上火哦～"
}
```

📤 HTTP响应数据模型：

```json
{
  "message": "添加类目成功",
  "code": 200
}
```

### 2. 类目列表

🚀 API路径：`GET /api/category/categories`

📤 HTTP响应数据模型：

```json
{
  "message": "类目查询成功",
  "code": 200,
  "data": [
    {
      "categoryId": "hQ5qC8ex3uJzJgwk7FK",
      "name": "饮品",
      "description": "咖啡、奶茶、牛奶🥛等",
      "productTypeCount": 3,
      "productCount": 181
    },
    {
      "categoryId": "t93aQfee3c6A0c4t42C",
      "name": "糕点",
      "description": "沙琪玛、蔓越莓堡等",
      "productTypeCount": 4,
      "productCount": 46
    }
  ]
}
```

## 商品模块

### 1.新增商品

🚀 API路径：`POST /api/product`

📥 HTTP请求数据模型：

```json
{
  "name": "汉堡🍔",
  "description": "厚甲的汉堡哦",
  "price": 26.00,
  "stock": 110,
  "categoryId": "t93aQfee3c6A0c4t42C",
  "imgUrls": [
    "https://picsum.photos/200/300",
    "https://picsum.photos/200/300"
  ]
}
```

📤 HTTP响应数据模型：

```json
{
  "message": "添加商品成功",
  "code": 200
}
```

### 2.菜单

🚀 API路径：`GET /api/product/menu?search=咖啡&page=1&size=2`

📤 HTTP响应数据模型：

```json
{
  "message": "成功",
  "code": 200,
  "data": {
    "categories": [
      {
        "categoryId": "hQ5qC8ex3uJzJgwk7FK",
        "name": "饮品",
        "description": "咖啡、奶茶、牛奶🥛等",
        "createTime": "2021-05-22",
        "products": [
          {
            "productId": "RLW1hI2CRXsyy4x1Q4f",
            "categoryId": "hQ5qC8ex3uJzJgwk7FK",
            "name": "抹茶咖啡",
            "price": 22,
            "stock": 99,
            "imgUrls": [
              "https://picsum.photos/200/300",
              "https://picsum.photos/200/300",
              "https://picsum.photos/200/300"
            ],
            "description": "抹茶搭配香浓咖啡！",
            "createTime": "2021-05-22",
            "updateTime": "2021-05-22"
          },
          {
            "productId": "ieQLGVdTSHhKBTNZ9AD",
            "categoryId": "hQ5qC8ex3uJzJgwk7FK",
            "name": "红茶咖啡",
            "price": 20,
            "stock": 66,
            "imgUrls": [
              "https://picsum.photos/200/300",
              "https://picsum.photos/200/300",
              "https://picsum.photos/200/300"
            ],
            "description": "红茶搭配香浓咖啡！",
            "createTime": "2021-05-22",
            "updateTime": "2021-05-22"
          }
        ]
      }
    ],
    "total": 2
  }
}
```

## 用户模块

### 1.注册

🚀 API路径：`POST /api/user/sign-up`

📥 HTTP请求数据模型：

```json
{
  "nickname": "Bruce",
  "username": "bruce",
  "password": "Bruce123#",
  "phoneNumber": "13000000000"
}
```

📤 HTTP响应数据模型：

```json
{
    "message": "注册成功",
    "code": 200
}
```

### 2.登录

🚀 API路径：`POST /api/user/sign-in`

📥 HTTP请求数据模型：

```json
{
    "username": "luca",
    "password": "Luca123#"
}
```

📤 HTTP响应数据模型：

```json
{
    "message": "登录成功",
    "code": 200,
    "data": {
        "userId": "MYTkr30TXL0CCxo9gDe",
        "nickname": "鲁卡",
        "username": "luca",
        "phoneNumber": "13000000000",
        "signUpTime": "2021-05-22T13:22:16.884+00:00"
    }
}
```

## 购物车模块

### 1.我的购物车

🚀 API路径：`GET /api/cart/my-cart`

📤 HTTP响应数据模型：

```json
{
  "message": "查询成功",
  "code": 200,
  "data": {
    "items": [
      {
        "productId": "uFlmIxTTBAnX8rREjZn",
        "name": "包子",
        "price": 2,
        "imgUrls": "https://picsum.photos/200/300,https://picsum.photos/200/300,https://picsum.photos/200/300",
        "description": "刚出炉的香喷喷包子哦~",
        "categoryName": "糕点",
        "count": 5,
        "totalPrice": 10
      },
      {
        "productId": "uX8rREjZnFlmIxTTBAn",
        "name": "紫薯面包",
        "price": 10,
        "imgUrls": "https://picsum.photos/200/300,https://picsum.photos/200/300,https://picsum.photos/200/300",
        "description": "刚出炉的香喷喷紫薯哦~",
        "categoryName": "糕点",
        "count": 10,
        "totalPrice": 100
      }
    ],
    "count": 2,
    "total": 110
  }
}
```

### 2.添加商品到购物车

🚀 API路径：`POST /api/cart/add`

📥 HTTP请求数据模型：

```json
{
  "productId": "uX8rREjZnFlmIxTTBAn",
  "count": 2
}
```

📤 HTTP响应数据模型：

```json
{
  "message": "操作成功",
  "code": 200
}
```

### 3.移除购物车的商品

🚀 API路径：`DELETE /api/cart/remove`

📥 HTTP请求数据模型：

```json
{
  "productId": "uX8rREjZnFlmIxTTBAn",
  "count": 3,
  "evict": false
}
```

📤 HTTP响应数据模型：

```json
{
  "message": "操作成功",
  "code": 200
}
```

### 4.清空购物车

🚀 API路径：`DELETE /api/cart/clear`

📤 HTTP响应数据模型：

```json
{
  "message": "操作成功",
  "code": 200
}
```

## 订单模块

订单状态机：

```
  -----> 已关闭（订单超时）
  |
未付款 -> 已付款 -> 已发货---> 已完成（确认收货）
            |        |
            --------------> 已取消（退款）        
```

- [x] 我的订单
- [x] 确认订单
- [x] 付款
- [x] 查询订单状态
- [x] 发货
- [x] 确认收货
- [ ] 发起退款
- [ ] 确认退款
- [ ] 订单超时检查

# 🦕 数据模型

## 🗄 类目

```sql
create table t_category (
    id int not null auto_increment,
    category_id char(64) not null comment '唯一标识',
    name varchar(255) not null comment '名称',
    description varchar(255) not null comment '描述',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);
```

注意：类目下的商品类型数量、总库存数量由程序计算得出，不直接存储到库中。

## ☕️ 商品  

```sql
create table t_product (
    id int not null auto_increment,
    product_id char(64) not null comment '唯一标识',
    name varchar(255) not null comment '名称',
    price bigint not null comment '价格',
    stock int not null default 0 comment '库存',
    image_urls text not null comment '例图链接组合',
    description varchar(255) not null comment '描述',
    category_id char(64) not null comment '类目标识',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);
```

## 🧑‍💻 用户

```sql
create table t_user (
    id int not null auto_increment,
    nickname varchar(255) not null,
    phone_number char(11) not null comment '中国大陆手机号码格式',
    username varchar(255) not null comment '账号',
    password varchar(255) not null comment 'MD5加盐加密后的密码',
    sign_up_time timestamp not null default current_timestamp comment '注册时间',
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id),
    unique key (username)
);
```

## 🛒 购物车明细

```sql
create table t_cart_item (
    id int not null auto_increment,
    item_id char(64) not null comment '购物车项唯一标识',
    user_id char(64) not null comment '用户标识',
    product_id char(64) not null comment '商品标识',
    count int not null comment '商品数目',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);
```

## 🧾 订单

```sql
create table t_order (
    id int not null auto_increment,
    order_id char(64) not null comment '订单唯一标识',
    user_id char(64) not null comment '用户标识',
    details text not null comment '订单明细包括收货地址、手机号、收货人等',
    total bigint not null comment '订单总计',
    count int not null default 0 comment '商品数目',
    status tinyint(4) null default null comment '订单状态 0-? 1-?',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);
```

## 🗃 订单项

```sql
create table t_order_item (
  id int not null auto_increment,
  order_item_id char(64) not null comment '订单项目唯一标识',
  order_id char(64) not null comment '属于哪个订单',
  product_id char(64) not null comment '商品ID',
  product_price char(64) not null comment '商品价格',
  count int not null comment '数目',
  order_item_price bigint not null comment '订单项价格',
  create_time timestamp not null default current_timestamp,
  update_time timestamp not null default current_timestamp on update current_timestamp,
  is_del tinyint(1) not null default 0,
  primary key (id)
);
```

# 📝 TODO

1. 添加Spock单元测试依赖
2. 添加接口文档
3. 参考阿里巴巴分层领域模型规约: DO DTO AO VO Query
4. 建立数据库索引
