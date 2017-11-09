# DistributedSystems

## Environment

Container Harbor: https://141.22.34.22/

Run the jar with:  `java -cp vsp_abw286.jar vsp/Application *.class`

## Testing of the given REST-api

### Registration

request:

```
curl -H "Content-Type: application/json" -X POST http://172.19.0.3:5000/users -d '{"name":"<username>", "password":"<password>"}'
```

responce:

```
{
  "encryption": {
    "HMACK": "sha256", 
    "algorithm": "AES", 
    "encryption": "Fernet", 
    "key": "Fl9rpbOJzFn10kk4kirbK0LBUFOXsBwedATJ+XEc04k=\n", 
    "key_encoding": "base64", 
    "keylength": "128", 
    "mode": "CBC", 
    "padding": "PKCS7"
  }, 
  "message": "Created User", 
  "object": [
    {
      "_links": {
        "encryption_key": "/users/1234/encryption_key", 
        "self": "/users/1234"
      }, 
      "deliverables_done": [], 
      "delivered": [], 
      "ip": null, 
      "location": null, 
      "name": "1234"
    }
  ], 
  "status": "success"
}
```

### Login via basic auth

```
curl -H "Content-Type: application/json" -X GET http://1234:1234@172.19.0.3:5000/login
```

Receive a token like:

```
{
  "message": "Welcome. To use your token set header 'Authorization:Token <tokenvalue>'", 
  "token": "eyJhdXRoIjogIlowRkJRVUZCUW1GQ1JEbFpiM2xKVFhka1ZUWjJVVWhvY3pacU5EZFhNRE5YVEdkTE1DMDVhbFJpWWpOTlNVVnVURk5UTjFwWlQxSklWMnRyYzJsUlQxTnFXRXhYWTNaTFQydGpkRkowYzJaVVdIaHlXV0prU0cxRlRVbzBiRXBKVUhSb1MwZHJXSFpXYkcxcmMxZE5OMk5SZFVWa2MzWmpiek5OU0hSME4xbHdSa0pIVGtNMFVtbFBibkJsVGpjelNqTkJOMUZDTkVKS1R6aFlWV0p4Ym5oUlBUMD0iLCAidXNlcm5hbWUiOiAiMTIzNCJ9", 
  "valid_till": 1510256600.95467
}
```
`
