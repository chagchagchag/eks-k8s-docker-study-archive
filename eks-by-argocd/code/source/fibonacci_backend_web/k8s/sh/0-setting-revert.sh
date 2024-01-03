kubectl delete -f ../local-storage.yml
kubectl delete -f ../fibonacci-namespace.yml
kind delete cluster --name fibonacci-cluster

echo "--- redis setting (delete) ---"
echo "kubectl delete -f ../redis-pod.yml"
kubectl delete -f ../redis-pod.yml
echo "kubectl apply -f ../redis-service.yml"
kubectl delete -f ../redis-service.yml