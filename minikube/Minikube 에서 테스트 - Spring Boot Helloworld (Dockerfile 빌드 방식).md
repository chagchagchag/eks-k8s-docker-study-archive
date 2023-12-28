## Minikube 에서 테스트 - Spring Boot Helloworld (Dockerfile 빌드 방식)

## Spring Boot 애플리케이션

### RestController

GET `/hello` 요청시 "안녕하세요. 미니큐브 예제입니다." 라는 문자열을 리턴하는 RestController 이다.

```java
package dev.k8s.gogogo.k8s_dockerfile_demo.application;

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

> 습관적으로 jib 을 추가해버렸지만, 이번 예제에서는 jib을 사용하지 않을 예정.

- spring-boot-starter-web
- gradle jib
  - [github](https://github.com/GoogleContainerTools/jib)

<br>

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
		image = "chagchagchag/minikube-example-boot-plaindockerfile"
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



### Dockerfile

```dockerfile
FROM openjdk:17-alpine AS jar-image
WORKDIR deploy
COPY build/libs/k8s_dockerfile_demo-0.0.1-SNAPSHOT.jar app.jar
RUN java -jar -Djarmode=layertools app.jar extract

FROM openjdk:17-alpine
WORKDIR deploy
COPY --from=jar-image deploy/dependencies/ ./
COPY --from=jar-image deploy/snapshot-dependencies/ ./
COPY --from=jar-image deploy/spring-boot-loader/ ./
COPY --from=jar-image deploy/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

<br>



```bash
## 빌드
$ ./gradlew clean && ./gradlew build

## 도커 이미지 빌드
$ docker build --tag chagchagchag/minikube-example-boot-plaindockerfile:latest .
```

