echo ""
echo "[delete] fibonacci-backend-web"
kubectl delete -f ../fibonacci-backend-web/fibonacci-web-service.yml
kubectl delete -f ../fibonacci-backend-web/fibonacci-web-hpa.yml
kubectl delete -f ../fibonacci-backend-web/fibonacci-web-deploy.yml

echo ""
echo "[delete] fibonacci-backend-cache"
kubectl delete -f ../fibonacci-backend-cache/fibonacci-cache-deploy.yml
kubectl delete -f ../fibonacci-backend-cache/fibonacci-cache-ingress.yml
kubectl delete -f ../fibonacci-backend-cache/fibonacci-cache-service.yml
kubectl delete -f ../fibonacci-backend-cache/fibonacci-cache-config.yml
kubectl delete -f ../fibonacci-backend-cache/fibonacci-cache-log-pvc.yml

echo "[delete] secret"
kubectl -n fibonacci delete secret fibonacci-cache-secret
