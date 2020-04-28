

## quickly setup a javascript express end point


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