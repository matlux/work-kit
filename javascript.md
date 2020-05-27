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

