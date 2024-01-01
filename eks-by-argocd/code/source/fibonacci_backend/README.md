
## kubectl
```bash
$ kubectl apply -f fibonacci-backend-namespace.yml 
namespace/fibonacci created

$ kubectl apply -f fibonacci-backend-deploy.yml 
deployment.apps/my-fibonacci-app created

$ kubectl apply -f fibonacci-backend-service.yml 
service/fibonacci-app-service created
```

