echo "[create] secret"
kubectl -n fibonacci create secret generic fibonacci-cache-secret --from-literal=api-key=abcd-efgh-ijkl-1111

echo ""
echo "[create] fibonacci-backend-cache"
kubectl apply -f ../fibonacci-backend-cache/fibonacci-cache-log-pvc.yml
kubectl apply -f ../fibonacci-backend-cache/fibonacci-cache-config.yml
kubectl apply -f ../fibonacci-backend-cache/fibonacci-cache-service.yml
kubectl apply -f ../fibonacci-backend-cache/fibonacci-cache-ingress.yml
kubectl apply -f ../fibonacci-backend-cache/fibonacci-cache-deploy.yml

echo ""
echo "[create] fibonacci-backend-web"
kubectl apply -f ../fibonacci-backend-web/fibonacci-web-service.yml
kubectl apply -f ../fibonacci-backend-web/fibonacci-web-hpa.yml
kubectl apply -f ../fibonacci-backend-web/fibonacci-web-deploy.yml