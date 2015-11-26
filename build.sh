#!/bin/sh
# try to build the release project
mvn clean package

#/Users/wangxiaoyi/codes/HotStockDetector/hsd-crawler/target/lib

cp ./hsd-crawler/target/lib/* ./lib/

rm -rf ./hsd-crawler/target/lib/