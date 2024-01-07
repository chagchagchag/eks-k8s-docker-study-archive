80, 443 포트를 사용하는 예제

조금 더 자세한 쉘스크립트나 CHART 등은 꾸준히 업데이트 예정!!

<BR>



## 클러스터 세팅

```bash
## 클러스터 생성
$ cat <<EOF | kind create cluster --name fibonacci-cluster --config=-
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


## ingress-nginx install
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

## standby pod
kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s
```



## redis 기동

```bash
$ kubectl apply -f redis-pod.yml
```



## WAS 기동

```bash
$ kubectl apply -f fibonacci-backend-cache.yml
```

