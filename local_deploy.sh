#!/bin/bash

# 编译打包
mvn package

# 构建镜像
docker build -t jcoffeeshop .

# 运行应用
docker run --name jcs-app -p 8080:8080 jcoffeeshop
