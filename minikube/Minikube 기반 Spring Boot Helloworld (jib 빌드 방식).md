## Minikube 기반 Spring Boot Helloworld (jib 빌드 방식)



## spring boot 애플리케이션

### RestController

GET `/hello`  요청시 "안녕하세요. 미니큐브 예제입니다." 라는 문자열을 리턴하는 간단한 RestController

```java
package dev.k8s.gogogo.k8s_jib_demo.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String getHello(){
        return "안녕하세요. 미니큐브 예제입니다.";
    }

}
```

<br>



### 의존성

- spring-boot-starter-web
- gradle jib
  - [github](https://github.com/GoogleContainerTools/jib)



build.gradle

```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.2.1'
	id 'io.spring.dependency-management' version '1.1.4'
	id 'com.google.cloud.tools.jib' version '3.4.0'
}

group = 'dev.k8s.gogogo'
version = '0.0.1-SNAPSHOT'

java {
	sourceCompatibility = '17'
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}

jib{
	from {
		image = "amazoncorretto:17"
	}
	to {
		image = "chagchagchag/minikube-example-boot"
		tags = ["latest"]
	}
	container{
		creationTime = "USE_CURRENT_TIMESTAMP"

		jvmFlags = [
				"-XX:+UseContainerSupport",
				"-XX:+UseG1GC",
				"-verbose:gc",
				"-XX:+PrintGCDetails",
				"-Dserver.port=8080",
				"-Dfile.encoding=UTF-8",
		]

		ports = ["8080"]

		labels = [maintainer: "chagchagchag <chagchagchag.dev@gmail.com>"]
	}
}
```

<br>



### build & push

```bash
$ gradlew clean
$ gradlew jibDockerBuild
$ gradlew jib
```

<br>



## kubernetes

### namespace.yml

```yaml
apiVersion: v1
kind: Namespace
metadata:
  name: minikube-example-boot
```

<br>



### deployment.yml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: minikube-example-boot
  namespace: minikube-example-boot
  labels:
    app: minikube-example-boot
spec:
  replicas: 1
  selector:
    matchLabels:
      app: minikube-example-boot
  template:
    metadata:
      labels:
        app: minikube-example-boot
    spec:
      containers:
        - name: minikube-example-boot
          image: chagchagchag/minikube-example-boot:latest
          ports:
            - containerPort: 8080
```

<br>



### service.yml

replicaset 안의 pod 들은 자기 자신은 8080 포트로 자기자신을 노출시키고 있다.

replicaset 안에 포함되는 pod 들은 Nodeport 의 30080 포트를 통해 외부에 공개한다.

```yaml
apiVersion: v1
kind: Service
metadata:
  name: minikube-example-boot
  namespace: minikube-example-boot
  labels:
    app: minikube-example-boot
spec:
  selector:
    app: minikube-example-boot
  ports:
    - protocol: TCP
      port: 8080
      nodePort: 30080
  type: NodePort
```

<br>



### 배포

```bash
$ kubectl create -f namespace.yml
namespace/minikube-example-boot created

$ kubectl apply -f deployment.yml 
deployment.apps/minikube-example-boot created

$ kubectl apply -f service.yml 
service/minikube-example-boot created

$ minikube service minikube-example-boot -n minikube-example-boot

W1228 08:20:44.930430   10172 main.go:291] Unable to resolve the current Docker CLI context "default": context "default": context not found: open C:\Users\soong\.docker\contexts\meta\37a8eec1ce19687d132fe29051dca629d164e2c4958ba141d5f4133a33f0688f\meta.json: The system cannot find the path specified.
|-----------------------|-----------------------|-------------|---------------------------|
|       NAMESPACE       |         NAME          | TARGET PORT |            URL            |
|-----------------------|-----------------------|-------------|---------------------------|
| minikube-example-boot | minikube-example-boot |        8080 | http://192.168.49.2:30080 |
|-----------------------|-----------------------|-------------|---------------------------|
🏃  minikube-example-boot 서비스의 터널을 시작하는 중
|-----------------------|-----------------------|-------------|------------------------|
|       NAMESPACE       |         NAME          | TARGET PORT |          URL           |
|-----------------------|-----------------------|-------------|------------------------|
| minikube-example-boot | minikube-example-boot |             | http://127.0.0.1:13675 |
|-----------------------|-----------------------|-------------|------------------------|
🎉  Opening service minikube-example-boot/minikube-example-boot in default browser...
❗  Because you are using a Docker driver on windows, the terminal needs to be open to run it.
```

<br>



## 출력결과

<img src="./img/jib-minikube/1.png"/>























