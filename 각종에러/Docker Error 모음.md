## Docker Error 모음

## 401 Unauthorized - docker hub 비밀번호 변경 후 나타나는 에러

docker jib 빌드를 수행할 때 아래와 같은 에러가 나타났었다.

```plain
401 Unauthorized 
GET https://auth.docker.io/token?service=registry.docker.io&scope=repository:library/amazoncorretto:pull {"details":"incorrect username or password"}
```

<br>



이 문제의 해결방법을 요약하면 아래와 같다.

-  `~/.docker/config.json` 파일 내에 `"credsStore": "desktop"` 을 삭제한 후 저장한다.
- 터미널에서 `docker login` 명령을 수행해서 터미널 로그인을 수행한다. (Docker Desktop 과는 별개로 로그인해야 한다.)
- `./gradlew jibDockerBuild` 와 같은 명령을 수행했을 때 에러가 없는지 확인한다.

<br>



아래의 내용은 이 문제를 해결하기 위해 어떤 방식으로 원인을 찾아서 해결했는지에 대한 내용이다. 나중에 기억을  떠올릴때 조금이나마 더 잘 떠오를 것 같아 기록해뒀다.<br>

최근에 도커 허브 패스워드를 바꾼적이 있었다. 이런 문제로 바꾼 계정 정보가 `~/.docker/config.json` 에 반영이 되지 않았고 패스워드 역시 부정확한 정보로 기록되어 있었다.<br>

오류가 나던 `~/.docker/config.json` 의 내용은 아래와 같다.

 `~/.docker/config.json`

```json
{
	"auths": {
		"https://index.docker.io/v1/": {}
	},
	"credsStore": "desktop",
	"currentContext": "default"
}
```

<br>



위의  `~/.docker/config.json` 파일은 파일명을 바꿔서  `~/.docker/config.json.back` 이라는 파일로 변경해주었고, 새로  `~/.docker/config.json` 파일을 만들어서 아래와 같이 `"credsStore": "desktop",` 을 삭제해준 후 저장해줬다.

 `~/.docker/config.json`

```json
{
	"auths": {
		"https://index.docker.io/v1/": {}
	},
	"currentContext": "default"
}
```

<br>



이제 터미널에서 `docker login` 을 수행해줘야 한다. 

> 단순히 Docker Desktop 에서 로그인한다고 해서 `~/.docker/config.json` 에 계정 정보가 반영되지 않았었다. 이 증상이 파악되지 않은채 뒤죽박죽으로 생각이 꼬여서 잠시 컴퓨터를 껐다가 다시 키고 차분하게 생각했었다 ㅋㅋㅋ<br>

```bash
$ docker login

Log in with your Docker ID or email address to push and pull images from Docker Hub. If you don't have a Docker ID, head over to https://hub.docker.com/ to create one.
You can log in with your password or a Personal Access Token (PAT). Using a limited-scope PAT grants better security and is required for organizations using SSO. Learn more at https://docs.docker.com/go/access-tokens/

Username: chagchagchag
Password:

//...

See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
```

<br>



이제 `~/.docker/config.json` 을 확인해보면 아래와 같이 변경되어 있다.

```json
{
	"auths": {
		"https://index.docker.io/v1/": {
			"auth": "암호화된 비밀번호"
		}
	},
	"currentContext": "default"
}
```

<br>



이제 ./gradlew jibDockerBuild 명령을 수행하면서 정상적으로 수행되는지 확인해본다.

```bash
$ ./gradlew jibDockerBuild

...

BUILD SUCCESSFUL in 18s
3 actionable tasks: 1 executed, 2 up-to-date
```





