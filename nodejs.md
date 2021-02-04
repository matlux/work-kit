

# NodeJS - NPM


## npm

### npm behind a proxy with nexus

ref: https://stackoverflow.com/questions/35043155/how-should-i-set-auth-in-npmrc-when-using-a-nexus-https-npm-registry-proxy

create `.npmrc` file with the following content:

```
; Nexus proxy registry pointing to http://registry.npmjs.org/
registry = https://<host>/nexus/content/repositories/npmjs-registry/

; base64 encoded authentication token
_auth = <see question below>

; required by Nexus
email = <valid email>

; force auth to be used for GET requests
always-auth = true

; we don't want to put certificates in .npmrc
strict-ssl = false

loglevel = silly
```



To create you auth value on Windws. Open your browser developer tools console and type:

```
btoa('admin:admin123')
```


Executing below command to verify your config file:
```
npm --loglevel infor view hello
```
