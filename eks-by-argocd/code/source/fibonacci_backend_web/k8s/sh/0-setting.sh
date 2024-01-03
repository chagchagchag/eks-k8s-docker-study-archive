echo "kind create cluster --name fibonacci-cluster"
kind create cluster --name fibonacci-cluster

echo ""
echo "$ kubectl apply -f ../fibonacci-common/fibonacci-namespace.yml"
kubectl apply -f ../fibonacci-common/fibonacci-namespace.yml

echo ""
echo "$ kubectl apply -f ../fibonacci-common/fibonacci-namespace.yml"
kubectl apply -f ../fibonacci-common/local-storage.yml

echo "--- redis setting ---"
echo "kubectl apply -f ../fibonacci-common/redis-pod.yml"
kubectl apply -f ../fibonacci-common/redis-pod.yml
echo "kubectl apply -f ../fibonacci-common/redis-service.yml"
kubectl apply -f ../fibonacci-common/redis-service.yml