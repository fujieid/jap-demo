#!/bin/bash

# 参考自 hutool 工具
help(){
  echo "--------------------------------------------------------------------------"
  echo ""
  echo "usage: ./jap.sh p"
  echo ""
  echo "-p   git push."
  echo ""
  echo "--------------------------------------------------------------------------"
}

case "$1" in
  'p')
    bin/push.sh
	;;
  *)
    help
esac
