#!/usr/bin/env bash
usage() { echo "Usage: $0 [g|n|i]" 1>&2; exit 1; }

# Colors
red=`tput setaf 1`
green=`tput setaf 2`
yellow=`tput setaf 3`
cyan=`tput setaf 6`
reset=`tput sgr0`

g=0
n=0
i=0

while getopts "gnih" arg; do
  case $arg in
    g)
      g=1
      ;;
    n)
      n=1
      ;;
    i)
      i=1
      ;;
    h)
      usage
      ;;
  esac
done

if [[ $g -eq 1 ]]; then
  echo "${cyan}------------------ greeting-service ------------------${reset}"
  cd greeting-service
  echo "${yellow}--- maven ---${reset}"
  mvn install dockerfile:build
  echo "${yellow}--- push ---${reset}"
  docker push selcukc/greeting-service
  cd ..
fi

if [[ $n -eq 1 ]]; then
  echo "${cyan}------------------ name-service ------------------${reset}"
  cd name-service
  echo "${yellow}--- maven ---${reset}"
  mvn install dockerfile:build
  echo "${yellow}--- push ---${reset}"
  docker push selcukc/name-service
  cd ..
fi

if [[ $i -eq 0 ]]; then
echo "${cyan}------------------ workload delete ------------------${reset}"
kubectl delete -f istio-gateway.yaml
kubectl delete -f workload-inject.yaml
kubectl delete -f workload.yaml
echo "${cyan}------------------ workload apply ------------------${reset}"
kubectl apply -f workload.yaml
echo "${cyan}------------------ get services ------------------${reset}"

elif [[ $i -eq 1 ]]; then
echo "${cyan}------------------ workload-inject delete ------------------${reset}"
kubectl delete -f istio-gateway.yaml
kubectl delete -f workload-inject.yaml
kubectl delete -f workload.yaml
#kubectl delete -f ~/yazilim/other/tools/istio-1.1.7/install/kubernetes/istio-demo-auth.yaml
echo "${cyan}------------------ workload-inject apply ------------------${reset}"
#kubectl apply -f ~/yazilim/other/tools/istio-1.1.7/install/kubernetes/istio-demo-auth.yaml
istioctl kube-inject -f workload.yaml > workload-inject.yaml
kubectl apply -f workload-inject.yaml
kubectl apply -f istio-gateway.yaml
echo "${cyan}------------------ get services ------------------${reset}"
fi

kubectl get services -w