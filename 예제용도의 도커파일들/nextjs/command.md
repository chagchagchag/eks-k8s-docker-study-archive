## 참고자료

- [Dockerize a Next.js App](https://medium.com/@2018.itsuki/dockerize-a-next-js-app-4b03021e084d)
- [How to yarn build on Dockerfile](https://stackoverflow.com/questions/74222441/how-to-yarn-build-on-dockerfile)
- [Docker 기본 (4/8) docker build & push](https://medium.com/dtevangelist/docker-%EA%B8%B0%EB%B3%B8-4-8-docker-build-push-71d740c1d629)

<br>



## 참고

next.js 는 Node 18.14.1 이상부터 동작

```bash
$ yarn create next-app --javascript
yarn create v1.22.19
[1/4] Resolving packages...
[2/4] Fetching packages...
error create-astro@4.5.1: The engine "node" is incompatible with this module. Expected version ">=18.14.1". Got "16.20.2"
error Found incompatible module.
info Visit https://yarnpkg.com/en/docs/cli/create for documentation about this command.
```

<br>



노드 버전을 바꿔준다.

```bash
## 노드 버전 확인
$ node -v
v16.20.2

# next.js 는 18 버전 이상부터 실행가능하다.
## 20 버전으로 바꿔준다.
## nvm 버전
$ nvm ls

    20.10.0
  * 16.20.2 (Currently using 64-bit executable)

## 20.10.0 이 이미 설치되어 있으니 사용
$ nvm use 20.10.0
Now using node v20.10.0 (64-bit)
```

<br>



## 설치 & 구동 

```bash
## 앱생성
$ yarn create next-app --typescript

success Installed "create-next-app@14.0.4" with binaries:
      - create-next-app
√ What is your project named? ... nextjs-app-ts
√ Would you like to use ESLint? ... No / Yes
√ Would you like to use Tailwind CSS? ... No / Yes
√ Would you like to use `src/` directory? ... No / Yes
√ Would you like to use App Router? (recommended) ... No / Yes
√ Would you like to customize the default import alias (@/*)? ... No / Yes

... 

## 프로젝트 디렉터리로 이동
$ cd nextjs-app-ts/


## yarn install
$ yarn install


## 구동 
$ yarn dev
또는
$ npm run dev
```

<br>



## Dockerfile

```dockerfile
FROM node:18-alpine as base
RUN apk add --no-cache g++ make py3-pip libc6-compat
WORKDIR /app
COPY package*.json ./
EXPOSE 3000

FROM base as builder
WORKDIR /app
COPY . .
RUN npm run build


FROM base as production
WORKDIR /app

ENV NODE_ENV=production
RUN npm ci

RUN addgroup -g 1001 -S nodejs
RUN adduser -S nextjs -u 1001
USER nextjs


COPY --from=builder --chown=nextjs:nodejs /app/.next ./.next
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/public ./public

CMD npm start

FROM base as dev
ENV NODE_ENV=development
RUN npm install 
COPY . .
CMD npm run dev
```

<br>



## Dockerfile 빌드

```bash
$ docker build -t nextjs-app-ts .
```

<br>

docker hub 이미지로 빌드할 것이라면 아래와 같이하자

```bash
$ docker build -t chagchagchag/nextjs-app-ts:v0.0.1 .
```

<br>



## Docker image push

```bash
$ docker push chagchagchag/nextjs-app-ts:v0.0.1
```

<br>



## 컨테이너 구동 

3000 번 포트 구동

```bash
$ docker container run --rm -d -p 3000:3000 nextjs-app-ts
```

확인 : [http://localhost:3000](http://localhost:3000)

<br>



3001 번 포트 구동

포트를 다르게 해서 구동하고 싶다면 아래와 같이 수행한다.

```bash
$ docker container run --rm -d -p 3001:3000 nextjs-app-ts
```

확인 : [http://localhost:3001](http://localhost:3001)

<br>



만약 컨테이너를 종료시킨 다음에도 이미지를 유지하고 싶다면 --rm 옵션을 빼고 아래와 같이 수행

```bash
$ docker container run -d -p 3000:3000 nextjs-app-ts
```

확인 : [http://localhost:3000](http://localhost:3000)

<br>



만약 데몬으로 띄우지 않고 front로 돌려서 로그도 같이보고 싶다면 아래와 같이 수행

```bash
$ docker container run -p 3000:3000 nextjs-app-ts
```

확인 : [http://localhost:3000](http://localhost:3000)

<br>



## docker-compose

이건 나중에 진행.







