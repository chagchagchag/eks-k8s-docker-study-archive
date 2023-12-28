echo "./gradlew clean && ./gradlew build"
./gradlew clean && ./gradlew build

echo ""
echo "docker build --tag chagchagchag/minikube-example-boot-plaindockerfile:latest ."
docker build --tag chagchagchag/minikube-example-boot-plaindockerfile:latest .

echo ""
echo "docker image ls | findstr chagchagchag/minikube-example-boot-plaindockerfile"
docker image ls | findstr chagchagchag/minikube-example-boot-plaindockerfile

echo ""
echo "docker push chagchagchag/minikube-example-boot-plaindockerfile:latest"
docker push chagchagchag/minikube-example-boot-plaindockerfile:latest