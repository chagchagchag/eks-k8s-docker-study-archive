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

## 20.10.0 이 이미 설치되어 있으니 설치
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











