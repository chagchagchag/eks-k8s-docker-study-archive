## local kind 클러스터 환경에 ArgoCD 설치

## 참고자료

https://argo-cd.readthedocs.io/en/stable/developer-guide/running-locally/

<br>



## 클러스터 생성

```bash
$ cat <<EOF | kind create cluster --name gitops-study-k8scluster --config=-
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
Creating cluster "gitops-study-k8scluster" ...
 ✓ Ensuring node image (kindest/node:v1.27.3) 🖼
 ✓ Preparing nodes 📦
 ✓ Writing configuration 📜
 ✓ Starting control-plane 🕹️
 ✓ Installing CNI 🔌
 ✓ Installing StorageClass 💾
Set kubectl context to "kind-gitops-study-k8scluster"
You can now use your cluster with:

kubectl cluster-info --context kind-gitops-study-k8scluster

Not sure what to do next? 😅  Check out https://kind.sigs.k8s.io/docs/user/quick-start/
```



## ingress-nginx 설치

```bash
$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
...

$ kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s
pod/ingress-nginx-controller-864894d997-hlldg condition met

```



## argocd 설치

```bash
$ kubectl create namespace argocd
namespace/argocd created


$ kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml


$ kubectl get po -n argocd
NAME                                               READY   STATUS            RESTARTS   AGE
argocd-application-controller-0                    1/1     Running           0          32s
argocd-applicationset-controller-dc5c4c965-n6df8   1/1     Running           0          32s
argocd-dex-server-9769d6499-97c8j                  0/1     PodInitializing   0          32s
argocd-notifications-controller-db4f975f8-ssn5s    1/1     Running           0          32s
argocd-redis-b5d6bf5f5-t4gnv                       1/1     Running           0          32s
argocd-repo-server-579cdc7849-cctjr                0/1     PodInitializing   0          32s
argocd-server-557c4c6dff-22qt6                     1/1     Running           0          32s
```







