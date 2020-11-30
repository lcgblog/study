#!/bin/bash

if [ "$(id -u)" != "0" ]; then
  echo "只有root用户才能运行" 1>&2
  exit 1
fi

ipreg="((2(5[0-5]|[0-4][0-9]))|[0-1]?[0-9]{1,2})(\.((2(5[0-5]|[0-4][0-9]))|[0-1]?[0-9]{1,2})){3}"
destfile=/etc/netplan/$(ls /etc/netplan/)
echo "-----"
echo 请按提示输入，如不做改变，请直接回车。
echo 设置完毕后，请再次确认配置，按Y开始修改。
echo "-----"
nowipmask=$(sed -rn "/- ${ipreg}\/([0-9]$|[1-2][0-9]|3[0-2])/p" $destfile)
nowipmask=${nowipmask:14}
nowip=${nowipmask%/*}
nowmask=${nowipmask#*/}

#sed -ri "s/gateway4: ${ipreg}$/gateway4: ${gateway}/" $destfile
nowgateway=$(sed -rn "/gateway4: ${ipreg}/p" $destfile)
nowgateway=${nowgateway:22}

nowdnsip=$(sed -rn "/- ${ipreg}$/p" $destfile)
nowdnsip=${nowdnsip:18}
echo $nowdnsip

while true; do
  echo -en "请输入IP地址(${nowip}): \t"
  read ipaddr
  if [[ $ipaddr == "" ]]; then
    ipaddr=$nowip
  fi
  if [[ $ipaddr =~ ^$ipreg$ ]]; then
    break
  else
    echo "输入的ip地址不合法"
  fi
done

while true; do
  echo -en "请输入子网掩码(${nowmask}): \t\t"
  read mask
  if [[ $mask == "" ]]; then
    mask=$nowmask
  fi
  if [[ $mask =~ ^([8-9]|[1-2][0-9]|3[0-2])$ ]]; then
    break
  else
    echo "输入的mask地址不合法"
  fi
done

while true; do
  echo -en "请输入网关(${nowgateway}): \t"
  read gateway
  if [[ $gateway == "" ]]; then
    gateway=$nowgateway
  fi
  if [[ $gateway =~ ^$ipreg$ ]]; then
    break
  else
    echo "输入的gateway地址不合法"
  fi
done

while true; do
  echo -en "请输入DNS地址(${nowdnsip}): \t"
  read dnsip
  if [[ $dnsip == "" ]]; then
    dnsip=$nowdnsip
  fi
  if [[ $dnsip =~ ^$ipreg$ ]]; then
    break
  else
    echo "输入的dnsip地址不合法"
  fi
done

echo ""
echo -e "修改内容汇总"
echo -e "---------------------------------"
echo -e "IP地址: \t$ipaddr/$mask"
echo -e "网关地址: \t$gateway"
echo -e "DNS服务器地址: \t$dnsip"
echo "如果是远程连过来修改ip，请千万小心，配置错误会导致与服务器失联"

check_yes_no() {
  while true; do
    read -p "$1" yn
    if [ "$yn" = "" ]; then
      yn='Y'
    fi
    case "$yn" in
    [Yy])
      break
      ;;
    [Nn])
      echo "放弃..."
      exit 1
      ;;
    *)
      echo "请输入y或n来代表是或否"
      ;;
    esac
  done
}

read -p "请手动输入hostname: " hname
read -p '确认以"'${hname}'"为hostname [y|n]:' yn
if [[ ${yn} == [Yy] ]]; then
  hostnamectl set-hostname ${hname}
fi

if check_yes_no "是否开始设置本机静态IP? [Y/n] "; then
  sed -ri "s/- ${ipreg}\/([0-9]$|[1-2][0-9]|3[0-2])/- ${ipaddr}\/${mask}/" $destfile
  sed -ri "s/gateway4: ${ipreg}$/gateway4: ${gateway}/" $destfile
  sed -ri "s/- ${ipreg}$/- ${dnsip}/" $destfile
  echo "设置完成，退出。"
  echo "如果你是用ssh连过来的话，可能因IP变化导致中断"
  echo "请使用新IP地址(${ipaddr})再次连接"
  # 重新应用新的配置
  sudo netplan apply
fi

read -p '需要重启生效,是否重启 [y|n]: ' yn
if [[ ${yn} == [Yy] ]]; then
  reboot
fi