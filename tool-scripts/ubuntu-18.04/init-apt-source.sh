#!/bin/bash

if [[ $(whoami) == "root" ]];then
  echo "当前执行权限: root"
else
  echo "当前用户: $(whoami)"
  echo "请使用管理员权限执行脚本."
  exit
fi

mv /etc/apt/sources.list /etc/apt/sources.list.bak

core_version=$(lsb_release -c --short)
export core_version

tee /etc/apt/sources.list <<-'EOF'
deb http://mirrors.aliyun.com/ubuntu/ ${core_version} main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ ${core_version} main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${core_version}-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ ${core_version}-security main restricted universe multiverse

deb http://mirrors.aliyun.com/ubuntu/ ${core_version}-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ ${core_version}-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${core_version}-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ ${core_version}-backports main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ ${core_version}-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ ${core_version}-proposed main restricted universe multiverse
EOF

apt-get update