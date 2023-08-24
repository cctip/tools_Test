## facebook hash key generate：(replace the alias and keystore_file_path)
`
keytool -exportcert -alias $alias -keystore $keystore_file_path | openssl sha1 -binary | openssl base64
`

## show keystore info
`
keytool -list -v -keystore $keystore_file_path
`

## Update project name
### Android
1. update app.json, change the name
2. open MainActivity, change the return value of function getMainComponentName

### IOS
TODO