## Step2.ArgoCD 구축 (1) ArgoCD 공식 제공 yml 을 이용해 ArgoCD 설치

aregocd 설치 명령어는 [argo-cd.readthedocs.io - getting started](https://argo-cd.readthedocs.io/en/stable/getting_started/) 에 자세하게 설명되어 있다.<br>

```bash
## argocd 관리를 위한 namespace 생성
## 설치 전에 namespace 를 생성하지 않고, 설치 시에 namespace 를 지정하지 않으면
## 나중에 리소스들을 삭제하거나 정리할 때 또는 관리할 때 곤란해진다.
$ kubectl create namespace argocd
namespace/argocd created

## argocd 생성
$ kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
## 굉장히 많은 요소들이 설치된다.
## serviceaccount, rbac, rolebinding, configmap, secret, service, deployment, statefulset, networkpolicy

customresourcedefinition.apiextensions.k8s.io/applications.argoproj.io created
customresourcedefinition.apiextensions.k8s.io/applicationsets.argoproj.io created
customresourcedefinition.apiextensions.k8s.io/appprojects.argoproj.io created
serviceaccount/argocd-application-controller created
serviceaccount/argocd-applicationset-controller created
serviceaccount/argocd-dex-server created
serviceaccount/argocd-notifications-controller created
serviceaccount/argocd-redis created
serviceaccount/argocd-repo-server created
serviceaccount/argocd-server created
role.rbac.authorization.k8s.io/argocd-application-controller created
role.rbac.authorization.k8s.io/argocd-applicationset-controller created
role.rbac.authorization.k8s.io/argocd-dex-server created
role.rbac.authorization.k8s.io/argocd-notifications-controller created
role.rbac.authorization.k8s.io/argocd-server created
clusterrole.rbac.authorization.k8s.io/argocd-application-controller created
clusterrole.rbac.authorization.k8s.io/argocd-server created
rolebinding.rbac.authorization.k8s.io/argocd-application-controller created
rolebinding.rbac.authorization.k8s.io/argocd-applicationset-controller created
rolebinding.rbac.authorization.k8s.io/argocd-dex-server created
rolebinding.rbac.authorization.k8s.io/argocd-notifications-controller created
rolebinding.rbac.authorization.k8s.io/argocd-server created
clusterrolebinding.rbac.authorization.k8s.io/argocd-application-controller created
clusterrolebinding.rbac.authorization.k8s.io/argocd-server created
configmap/argocd-cm created
configmap/argocd-cmd-params-cm created
configmap/argocd-gpg-keys-cm created
configmap/argocd-notifications-cm created
configmap/argocd-rbac-cm created
configmap/argocd-ssh-known-hosts-cm created
configmap/argocd-tls-certs-cm created
secret/argocd-notifications-secret created
secret/argocd-secret created
service/argocd-applicationset-controller created
service/argocd-dex-server created
service/argocd-metrics created
service/argocd-notifications-controller-metrics created
service/argocd-redis created
service/argocd-repo-server created
service/argocd-server created
service/argocd-server-metrics created
deployment.apps/argocd-applicationset-controller created
deployment.apps/argocd-dex-server created
deployment.apps/argocd-notifications-controller created
deployment.apps/argocd-redis created
deployment.apps/argocd-repo-server created
deployment.apps/argocd-server created
statefulset.apps/argocd-application-controller created
networkpolicy.networking.k8s.io/argocd-application-controller-network-policy created
networkpolicy.networking.k8s.io/argocd-applicationset-controller-network-policy created
networkpolicy.networking.k8s.io/argocd-dex-server-network-policy created
networkpolicy.networking.k8s.io/argocd-notifications-controller-network-policy created
networkpolicy.networking.k8s.io/argocd-redis-network-policy created
networkpolicy.networking.k8s.io/argocd-repo-server-network-policy created
networkpolicy.networking.k8s.io/argocd-server-network-policy created

```

<br>



그리고 설치된 ArgoCD 는 아래와 같이 확인해보자.

```bash
$ kubectl get po -n argocd
NAME                                               READY   STATUS    RESTARTS   AGE
argocd-application-controller-0                    1/1     Running   0          5m43s
argocd-applicationset-controller-dc5c4c965-qrz7m   1/1     Running   0          5m43s
argocd-dex-server-9769d6499-tl5jd                  1/1     Running   0          5m43s
argocd-notifications-controller-db4f975f8-dhcqj    1/1     Running   0          5m43s
argocd-redis-b5d6bf5f5-d75xc                       1/1     Running   0          5m43s
argocd-repo-server-579cdc7849-6htrl                1/1     Running   0          5m43s
argocd-server-557c4c6dff-px8zw                     1/1     Running   0          5m43s
```

<br>



## 에러 발생할 경우

만약 아래와 같은 에러가 발생한다면

```bash
$ kubectl create namespace argocd
error: You must be logged in to the server (Unauthorized)
```

<br>

이전에 미리 만들어뒀던 [export-acess-key-gitops-study-argocd.sh](http://export-acess-key-gitops-study-argocd.sh/) 을 활용해 Access Key 들을 OS 내에 export 해준다.

```bash
$ source export-acess-key-gitops-study-argocd.sh
```



