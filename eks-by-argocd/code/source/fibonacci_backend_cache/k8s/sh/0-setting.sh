echo "kind create cluster --name fibonacci-cluster"
kind create cluster --name fibonacci-cluster

echo ""
echo "$ kubectl apply -f ../fibonacci-namespace.yml"
kubectl apply -f ../fibonacci-namespace.yml

echo ""
echo "$ kubectl apply -f ../fibonacci-namespace.yml"
kubectl apply -f ../local-storage.yml