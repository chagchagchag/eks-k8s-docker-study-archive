echo "kind create cluster --name fibonacci-cluster --config {ingress config}"
echo "see more : https://kind.sigs.k8s.io/docs/user/ingress/#ingress-nginx"
cat <<EOF | kind create cluster --name fibonacci-cluster --config=-
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
- role: control-plane
  kubeadmConfigPatches:
  - |
    kind: InitConfiguration
    nodeRegistration:
      kubeletExtraArgs:
        node-labels: "ingress-ready=true"
  extraPortMappings:
  - containerPort: 80
    hostPort: 80
    protocol: TCP
  - containerPort: 443
    hostPort: 443
    protocol: TCP
EOF

echo ""
echo "[crate] ingress-nginx"
echo "see more : https://kind.sigs.k8s.io/docs/user/ingress/#ingress-nginx"
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

echo ""
echo "[wait] ingress-nginx ready"
echo "see more : https://kind.sigs.k8s.io/docs/user/ingress/#ingress-nginx"
kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s

echo ""
echo "$ kubectl apply -f ../fibonacci-common/fibonacci-namespace.yml"
kubectl apply -f ../fibonacci-common/fibonacci-namespace.yml

echo ""
echo "$ kubectl apply -f ../fibonacci-common/local-storage.yml"
kubectl apply -f ../fibonacci-common/local-storage.yml

echo ""
echo "[redis]"
echo "kubectl apply -f ../fibonacci-common/redis-service.yml"
kubectl apply -f ../fibonacci-common/redis-service.yml
echo "kubectl apply -f ../fibonacci-common/redis-pod.yml"
kubectl apply -f ../fibonacci-common/redis-pod.yml
