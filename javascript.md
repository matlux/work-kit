## How to install npm, nodejs and n and yarn

### on Linux
```
sudo apt install nodejs npm
npm install -g n
n 12.13.0
n 16.13.0
n lts
```
## On MacOs


```
brew install node
```

### install yarn
```
sudo curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
sudo sh -c "echo 'deb https://dl.yarnpkg.com/debian/ stable main' >> /etc/apt/sources.list"
sudo apt update
```
or
```
npm install -g yarn
```

## How to install a mini web server with npm

```
npm install -g npx
npx serve
```

## install next

```
yarn add next
```

## How to add a typescript dependency to a node project

```
yarn add -E memjs
yarn add -ED @types/memjs
```
other example:
```
yarn add -E node-gzip
yarn add -ED @types/node-gzip
```

other example:

    yarn add <package...>
    
    yarn add emotion
    
    
### add dev only dependency with yarn

    yarn add <package...> --dev

## How to run tests

### with Yarn

    yarn test

### with a specific test with Node

    npm run test -- src/layout.test.ts

## How to quickly setup a javascript express end point project


run
```
npm init -y
npm install --save express
vim app.js
node app.js
```

insert following code inside `app.js`
```javascript
const express = require("express");
const app = express();
app.set("etag", "strong");
app.get("/", (req, res) => {
  res.json({ id: 1, name: "1234" });
});
app.listen(3001);
```

## How to setup a Typescript node project

ref: https://khalilstemmler.com/blogs/typescript/node-starter-project/

```
mkdir typescript-starter
cd typescript-starter
```

### Setup `Node.js` `package.json`
Using the -y flag when creating a package.json will simply approve all the defaults.

    npm init -y
    
### Add TypeScript as a dev dependency

    npm install typescript --save-dev

### Install ambient Node.js types for TypeScript

    npm install @types/node --save-dev
    
### Create a `tsconfig.json`

```
npx tsc --init --rootDir src --outDir build \
--esModuleInterop --resolveJsonModule --lib es6 \
--module commonjs --allowJs true --noImplicitAny true
```


# AngularJs

## installing AngularJS cli

    npm install -g @angular/cli

# NestJS (Angular-university)

## on client

    npm install
    npm start

## on server

### in prod mode

    npm run build
    node dist/src/main.js

### in auto reload mode

    npm run server
# Sanity

##  export

    sanity dataset export documentation
    
## create document

Change editor if needed
    export EDITOR=$(which subl)
    
    
    sanity documents create --id everythingDocument --watch --replace --dataset documentation

## Edit json with javascrip Chrome console

    copy(JSON.stringify(everything.filter(row => row._type === 'page').flatMap(row => row.shelves), null, 2))
