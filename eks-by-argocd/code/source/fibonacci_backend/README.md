
## 1. kind setting
```bash
$ kind create cluster --name fibonacci-cluster
```
<br>

## 2. kubectl
```bash
$ kubectl apply -f fibonacci-web-namespace.yml 
namespace/fibonacci created

$ kubectl apply -f fibonacci-web-deploy.yml 
deployment.apps/fibonacci-backend-web-deploy created

$ kubectl apply -f fibonacci-web-service.yml 
service/fibonacci-backend-web-service created
```
<br>
