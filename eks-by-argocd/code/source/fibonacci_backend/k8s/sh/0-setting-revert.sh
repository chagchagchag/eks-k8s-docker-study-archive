echo "[delete] storage, namespace"
kubectl delete -f ../fibonacci-common/local-storage.yml
kubectl delete -f ../fibonacci-common/fibonacci-namespace.yml

echo ""
echo "[delete] redis setting"
echo "kubectl delete -f ../fibonacci-common/redis-pod.yml"
kubectl delete -f ../fibonacci-common/redis-pod.yml
echo "kubectl delete -f ../fibonacci-common/redis-service.yml"
kubectl delete -f ../fibonacci-common/redis-service.yml

echo ""
echo "[delete] cluster"
kind delete cluster --name fibonacci-cluster