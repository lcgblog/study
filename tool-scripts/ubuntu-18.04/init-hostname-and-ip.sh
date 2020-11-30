#!/bin/bash
if [[ $(whoami) == "root" ]];then
echo "当前执行权限: root"
else
echo "当前用户:" $(whoami)
echo "请使用管理员权限执行脚本."
exit
fi
while read -p "是否设置本机静态IP,退出输入:n,设置IP输入:y  [y|n]" yn
do
    if [[ ${yn} == [Nn] ]];then
        exit
    elif [[ ${yn} == [Yy] ]];then
        while read -p "输入本机IP地址,如192.168.123.100 :" ip
        do
            read -p "将设置ip地址为${ip},请确认[y|n]:" yn
            if [[ ${yn} == [Yy] ]];then
                read -p "输入DNS, 如:192.168.123.1 (Default:192.168.123.1):" dns
                read -p "输入网关,如192.168.123.1 (Default:192.168.123.1):" gateway
                read -p "输入掩码,如255.255.255.0 (Default:255.255.255.0):" mask
                echo "IP: "${ip}
                echo "DNS:"${dns:-"192.168.123.1"}
                echo "网关:"${gateway:-"192.168.123.1"}
                echo "掩码:"${mask:-"255.255.255.0"}
                read -p "以上配置是否正确？若是写错请按n重新配置. 请确认[y|n]:" yn
                if [[ ${yn} == [Yy] ]];then
                    #设置DNS
                    sed -i "/exit/i\nameserver ${dns:-"192.168.123.1"}" /etc/rc.local
                    sed -i "/127.0.0.1/i\127.0.0.1 ${ip//./-}" /etc/hosts
                    #重写interfaces设置IP
                    echo 'source /etc/network/interfaces.d/*' > /etc/network/interfaces
                    echo 'auto lo' >> /etc/network/interfaces
                    echo 'iface lo inet loopback' >> /etc/network/interfaces
                    echo 'auto ens160' >> /etc/network/interfaces
                    echo 'iface ens160 inet static' >> /etc/network/interfaces
                    echo 'address '${ip} >> /etc/network/interfaces
                    echo 'gateway '${gateway:-"192.168.123.1"} >> /etc/network/interfaces
                    echo 'netmask '${mask:-"255.255.255.0"} >> /etc/network/interfaces
                else
                    continue
                fi
            else
                continue
            fi
            read -p "是否将${ip//./-}作为hostname?请确认[y|n]:" yn
            if [[ ${yn} == [Yy] ]];then
                hostnamectl set-hostname ${ip//./-}
            else
                read -p "请手动输入hostname: " hname
                read -p '确认以"'${hname}'"为hostname [y|n]:' yn
                if [[ ${yn} == [Yy] ]];then
                    hostnamectl set-hostname ${hname}
                fi
            fi
            read -p '需要重启生效,是否重启 [y|n]: ' yn
            if [[ ${yn} == [Yy] ]];then
                reboot
            fi
            exit
        done
    fi
done
