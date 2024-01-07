## argocd Local 설치 과정

참고자료 : 

- [Local Kubernetes Setup & ArgoCD on Windows 11](https://youtu.be/IyauUBMe2ds?si=kAGqUnogvKmkVM5K)
- [인그레스 개념](https://blog.naver.com/alice_k106/221502890249)

<br>



kind 클러스터 생성

```bash
$ kind create cluster --name argocd-cluster --config=argocd-cluster.yml

[create] cluster creating...
Creating cluster "argocd-cluster" ...
 ✓ Ensuring node image (kindest/node:v1.27.3) 🖼
 ✓ Preparing nodes 📦 📦 📦 📦
 ✓ Writing configuration 📜
 ✓ Starting control-plane 🕹️
 ✓ Installing CNI 🔌
 ✓ Installing StorageClass 💾
 ✓ Joining worker nodes 🚜
Set kubectl context to "kind-argocd-cluster"
You can now use your cluster with:

kubectl cluster-info --context kind-argocd-cluster

Thanks for using kind! 😊



$ kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/kind/deploy.yaml

namespace/ingress-nginx created
serviceaccount/ingress-nginx created
serviceaccount/ingress-nginx-admission created
role.rbac.authorization.k8s.io/ingress-nginx created
role.rbac.authorization.k8s.io/ingress-nginx-admission created

...

```





argocd 생성

```bash
$ kubectl create namespace argocd
...

$ kubectl -n argocd apply -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
...
```





생성된 리소스 확인

```bash
$ kubectl -n argocd get all
NAME                                                   READY   STATUS    RESTARTS   AGE
pod/argocd-application-controller-0                    1/1     Running   0          68s
pod/argocd-applicationset-controller-dc5c4c965-cw9h2   1/1     Running   0          69s
pod/argocd-dex-server-9769d6499-p6g9w                  1/1     Running   0          69s
pod/argocd-notifications-controller-db4f975f8-hlfhb    1/1     Running   0          69s
pod/argocd-redis-b5d6bf5f5-8l82l                       1/1     Running   0          69s
pod/argocd-repo-server-579cdc7849-qsg6p                1/1     Running   0          69s
pod/argocd-server-557c4c6dff-4sf9p                     1/1     Running   0          68s

NAME                                              TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                      AGE
service/argocd-applicationset-controller          ClusterIP   10.96.231.169   <none>        7000/TCP,8080/TCP            69s
service/argocd-dex-server                         ClusterIP   10.96.242.10    <none>        5556/TCP,5557/TCP,5558/TCP   69s
service/argocd-metrics                            ClusterIP   10.96.159.29    <none>        8082/TCP                     69s
service/argocd-notifications-controller-metrics   ClusterIP   10.96.127.245   <none>        9001/TCP                     69s
service/argocd-redis                              ClusterIP   10.96.181.243   <none>        6379/TCP                     69s
service/argocd-repo-server                        ClusterIP   10.96.127.136   <none>        8081/TCP,8084/TCP            69s
service/argocd-server                             ClusterIP   10.96.92.144    <none>        80/TCP,443/TCP               69s
service/argocd-server-metrics                     ClusterIP   10.96.30.238    <none>        8083/TCP                     69s

NAME                                               READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/argocd-applicationset-controller   1/1     1            1           69s
deployment.apps/argocd-dex-server                  1/1     1            1           69s
deployment.apps/argocd-notifications-controller    1/1     1            1           69s
deployment.apps/argocd-redis                       1/1     1            1           69s
deployment.apps/argocd-repo-server                 1/1     1            1           69s
deployment.apps/argocd-server                      1/1     1            1           69s

NAME                                                         DESIRED   CURRENT   READY   AGE
replicaset.apps/argocd-applicationset-controller-dc5c4c965   1         1         1       69s
replicaset.apps/argocd-dex-server-9769d6499                  1         1         1       69s
replicaset.apps/argocd-notifications-controller-db4f975f8    1         1         1       69s
replicaset.apps/argocd-redis-b5d6bf5f5                       1         1         1       69s
replicaset.apps/argocd-repo-server-579cdc7849                1         1         1       69s
replicaset.apps/argocd-server-557c4c6dff                     1         1         1       69s

NAME                                             READY   AGE
statefulset.apps/argocd-application-controller   1/1     68s
```

<br>



포트포워딩 

- 아래의 포트 포워딩을 Ingress/ClusterIP/NodePort 등으로 변경해야 한다.

```bash
$ kubectl port-forward service/argocd-server 8080:80 -n argocd
Forwarding from 127.0.0.1:8080 -> 8080
Forwarding from [::1]:8080 -> 8080
Handling connection for 8080
Handling connection for 8080
Handling connection for 8080
Handling connection for 8080
E0106 22:35:13.400231   11432 portforward.go:394] error copying from local connection to remote stream: read tcp6 [::1]:8080->[::1]:8654: wsarecv: An existing connection was forcibly closed by the remote host.
```

