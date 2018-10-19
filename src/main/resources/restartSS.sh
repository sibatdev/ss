#!/usr/bin/env bash

ss_conf_path=/etc/shadowsocks.json

log_path=/home/vpn/logs/ss.log

/bin/cp /etc/shadowsocks.json /etc/shadowsocks.json.bak

p_name=ssserver
pid="$(ps -ef|grep $p_name|awk '{print $2}'|head -n1)"
kill -9 $pid

nohup ssserver -c $ss_conf_path start > /dev/null 2>$log_path &

echo "restart shadowsocks successful"


