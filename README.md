# jcoffeeshop

## 功能概述

### 类目模块

- [ ] 新增类目
- [ ] 类目列表

### 商品模块

- [ ] 新增商品
- [ ] 菜单 (按类目划分商品)

### 用户模块

- [ ] 注册
- [ ] 登录

### 购物车模块

- [ ] 我的购物车
- [ ] 添加商品
- [ ] 移除商品
- [ ] 清空购物车

### 订单模块

- [ ] 我的订单
- [ ] 确认订单
- [ ] 付款
- [ ] 发货
- [ ] 确认收货
- [ ] 退款

## 数据模型

### 🗄 类目

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

### ☕️ 商品  

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

### 🧑‍💻 用户

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

### 🛒 购物车明细

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

### 📝 订单

```sql
create table t_order (
    id int not null auto_increment,
    order_id char(64) not null comment '订单唯一标识',
    details text not null comment '订单明细包括收货地址、手机号、收货人、商品id、数目等',
    status tinyint(4) null default null comment '订单状态 0-? 1-?',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);
```

## TODO

1. 添加Spock单元测试依赖
2. 添加接口文档
3. 参考阿里巴巴分层领域模型规约: DO DTO AO VO Query
4. 建立数据库索引
