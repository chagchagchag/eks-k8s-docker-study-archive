## local kind 클러스터 환경에 ArgoCD 설치

## 참고자료

- https://argo-cd.readthedocs.io/en/stable/developer-guide/running-locally/

<br>



## 클러스터 생성

아래 파일을 작성

`argocd-cluster.yml`

```yaml
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
```

<br>



클러스터 생성

```bash
$ kind create cluster --name argocd-cluster --config=argocd-cluster.yml
Creating cluster "argocd-cluster" ...
 ✓ Ensuring node image (kindest/node:v1.27.3) 🖼
 ✓ Preparing nodes 📦
 ✓ Writing configuration 📜
 ✓ Starting control-plane 🕹️
 ✓ Installing CNI 🔌
 ✓ Installing StorageClass 💾
Set kubectl context to "kind-argocd-cluster"
You can now use your cluster with:

kubectl cluster-info --context kind-argocd-cluster

Thanks for using kind! 😊
```

<br>



## ingress-nginx 설치

```bash
$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml
...

$ kubectl wait --namespace ingress-nginx \
  --for=condition=ready pod \
  --selector=app.kubernetes.io/component=controller \
  --timeout=90s
...

```

<br>



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

<br>



## host 파일 수정

`C:\Windows\System32\drivers\etc\hosts` 파일을 메모장으로 열어서 아래의 내용을 추가해준다.

```txt
# ...

# 추가해준 내용
127.0.0.1	argocd-server.local
```

<br>



## 패스워드 변경

```bash
$ kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d

NZ2UvgLqQZU6HGtF
```

<br>



## 접속

[http://argocd-server.local](http://argocd-server.local) 으로 접속한다.

그리고 비밀번호를 변경해준다. 접속 사용자 명은 `admin` 이고 비밀번호는 위에서 얻은 패스워드를 통해 접속 후 비밀번호를 변경해주면 된다.

<br>



