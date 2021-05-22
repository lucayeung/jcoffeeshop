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

create table t_user (
    id int not null auto_increment,
    user_id char(64) not null comment '唯一标识',
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

create table t_order (
    id int not null auto_increment,
    order_id char(64) not null comment '订单唯一标识',
    details text not null comment '订单明细包括收货地址、手机号、收货人等',
    total bigint not null comment '订单总计',
    status tinyint(4) null default null comment '订单状态 0-? 1-?',
    create_time timestamp not null default current_timestamp,
    update_time timestamp not null default current_timestamp on update current_timestamp,
    is_del tinyint(1) not null default 0,
    primary key (id)
);

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
