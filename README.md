# DistributedSystems

## Environment

## Set up

* Go to Container Harbor: https://141.22.34.22/ (Login with HAW-account is needed)
* Create a docker container like discribed in the  task and notice the IP for later.
* Enter the Jumphost via `ssh -p 443 <HAW-ID>@141.22.34.22`.
* Enter your Docker Container from the jumphost via `ssh -p 22 root@<IP-of-the-container>`.
* Run the project with:  `java -jar vsp_abw286.jar`
* Register with new User or Log in with existing one
* type !help to see available commands

## Testing of the given REST-api

### Registration


________________________________________________________________

request:

```
curl -H "Content-Type: application/json" -X POST http://172.19.0.3:5000/users -d '{"name":"<name>", "password":"<password>"}'
```

response:

```json
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

```json
{
  "message": "Welcome. To use your token set header 'Authorization:Token <tokenvalue>'", 
  "token": "eyJhdXRoIjogIlowRkJRVUZCUW1GQ1JEbFpiM2xKVFhka1ZUWjJVVWhvY3pacU5EZFhNRE5YVEdkTE1DMDVhbFJpWWpOTlNVVnVURk5UTjFwWlQxSklWMnRyYzJsUlQxTnFXRXhYWTNaTFQydGpkRkowYzJaVVdIaHlXV0prU0cxRlRVbzBiRXBKVUhSb1MwZHJXSFpXYkcxcmMxZE5OMk5SZFVWa2MzWmpiek5OU0hSME4xbHdSa0pIVGtNMFVtbFBibkJsVGpjelNqTkJOMUZDTkVKS1R6aFlWV0p4Ym5oUlBUMD0iLCAidXNlcm5hbWUiOiAiMTIzNCJ9", 
  "valid_till": 1510256600.95467
}
```

### Who am I

```
curl -H "Content-Type: application/json" -X GET http://1234:1234@172.19.0.3:5000/whoami
```

```json
{
  "message": "You are authenticated", 
  "user": {
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
}
```

### Questing

```
curl -H "Content-Type: application/json" -X GET http://1234:1234@172.19.0.3:5000/blackboard/quests
```

```json
{
  "objects": [
    {
      "_links": {
        "deliveries": "/blackboard/quests/1/deliveries", 
        "self": "/blackboard/quests/1", 
        "tasks": "/blackboard/quests/1/tasks"
      }, 
      "deliveries": [
        1, 
        2, 
        5, 
        8, 
        9, 
        13, 
        14, 
        15, 
        16, 
        19, 
        20, 
        21, 
        22, 
        23, 
        24, 
        25, 
        26, 
        27, 
        28, 
        29, 
        30, 
        31, 
        32, 
        33, 
        35, 
        36, 
        37, 
        178, 
        179, 
        180, 
        181, 
        188, 
        190, 
        193, 
        194
      ], 
      "description": "Welcome young adventurer! I'm so glad you are here. We are in grave danger and we are so relieved you answered the call. You should first go an see the king to be welcomed to the empire", 
      "followups": [], 
      "id": 1, 
      "name": "A kingly welcome", 
      "prerequisites": [], 
      "requirements": null, 
      "reward": 1, 
      "tasks": [
        "/blackboard/tasks/2"
      ]
    }, 
    {
      "_links": {
        "deliveries": "/blackboard/quests/2/deliveries", 
        "self": "/blackboard/quests/2", 
        "tasks": "/blackboard/quests/2/tasks"
      }, 
      "deliveries": [
        95, 
        112
      ], 
      "description": "\nBrave one! We need help! Our dungeons are overrun by rats.\nPlease can you get rid of this plague.", 
      "followups": [], 
      "id": 2, 
      "name": "The Rat Hunt", 
      "prerequisites": [], 
      "requirements": null, 
      "reward": 1, 
      "tasks": [
        "/blackboard/tasks/3"
      ]
    }, 
    {
      "_links": {
        "deliveries": "/blackboard/quests/3/deliveries", 
        "self": "/blackboard/quests/3", 
        "tasks": "/blackboard/quests/3/tasks"
      }, 
      "deliveries": [], 
      "description": "\nWhile you where away the Throneroom got attacked.\nWe were able to fight off the scum, but there are still a lot of wounded.\nPlease help to carry the wounded to the infirmary.", 
      "followups": [], 
      "id": 3, 
      "name": "Help the Wounded", 
      "prerequisites": [], 
      "requirements": "group", 
      "reward": 1, 
      "tasks": [
        "/blackboard/tasks/4"
      ]
    }, 
    {
      "_links": {
        "deliveries": "/blackboard/quests/4/deliveries", 
        "self": "/blackboard/quests/4", 
        "tasks": "/blackboard/quests/4/tasks"
      }, 
      "deliveries": [], 
      "description": "\nThe Elves have reached out to us. The world is at great danger!\nPlease go with a group to their gathering and listen to what they have to say.", 
      "followups": [], 
      "id": 4, 
      "name": "Save the World", 
      "prerequisites": [], 
      "requirements": "group, election-algorithm", 
      "reward": 1, 
      "tasks": [
        "/blackboard/tasks/5"
      ]
    }
  ], 
  "status": "success"
}
```


```
curl -H "Authorization:Token eyJhdXRoIjogIlowRkJRVUZCUW1GRVFXcEpOVE5uUlVaRWVuTlhibFJaVTJoTVNIUjVNREp2VkhGVk5tSmlOVzlmT0hjM2VFdFlSRWMwY2xGNE0xVjRaMDF0TFVSWlJUbFJTR0p1U1ZGWk1XWnhSbFpWUzB4M2MyaGFkVUp5TUhCYVJGOU5abGQ0VVc5T01HRjVVVk5qTXpCWlpuWXhRV013VFU1SGNGOURWakl6Tmw5Q1dWaHFkRGg1VlRoRk9HdHRWVmhqWTNacmVtbFFhRTVmU0ZsRFIwOUxTVnBSVjFCblBUMD0iLCAidXNlcm5hbWUiOiAicGV0ZXIifQ==" 172.19.0.32:5000/visits 
```

"message": "\nThe Throneroom lays ahead and a buttler stays in front of it. Do you want to approach it? (POST)\n   

``` 
curl -X POST -H "Authorization:Token eyJhdXRoIjogIlowRkJRVUZCUW1GRVFXcEpOVE5uUlVaRWVuTlhibFJaVTJoTVNIUjVNREp2VkhGVk5tSmlOVzlmT0hjM2VFdFlSRWMwY2xGNE0xVjRaMDF0TFVSWlJUbFJTR0p1U1ZGWk1XWnhSbFpWUzB4M2MyaGFkVUp5TUhCYVJGOU5abGQ0VVc5T01HRjVVVk5qTXpCWlpuWXhRV013VFU1SGNGOURWakl6Tmw5Q1dWaHFkRGg1VlRoRk9HdHRWVmhqWTNacmVtbFFhRTVmU0ZsRFIwOUxTVnBSVjFCblBUMD0iLCAidXNlcm5hbWUiOiAicGV0ZXIifQ==" 172.19.0.32:5000/visits 
```
 "message": "\nWelcome to the Throneroom! Well they have told you to come visit the King?\nWell, do you have an audience? NO? Then go away and tell your supervisor that the King does not have time\nfor every new greenhorn in town. But consider your quest as fullfilled - at least you visited the Throneroom.\nGive this token back as a symbol of fullfillment ( quest deliveries, POST {\"tokens\":{\"<task_uri>\":token}} )\n", 
  "token": "Z0FBQUFBQmFEQXU0Z1VrQXplT1l0akVoSlVEWnA2WjJxLWVKcG1mNmNVNU5vYVV6TGlUNnRwT01SdVJnY2lLMTdFbFR3VF9sVHBmZWlaOWNxOUFjVjdCTkhuS0dlcDZLWnpPZmk0V1g0NTdhXzRXOUxIRFRucEZ6NXZPbFBDa0JQOW9qWHpHcU5nZjFJNHI4S3FwdWY5bVR3MDliUDNWS0QweDlVaXZwenlZUG1ZdmliVjUtaUhBdWVuU2dWWU8xdUYycVJrMXNUSEVqMEZhOGlVdnRuTFBuUGQ4ZUY0b3QwS1JuRU5oWXc4dFh1VmZjN2R4RVA1X0FnVnBfZ1p2M1FrQ0k0a1Jab3JRWTBrLU5nRDFhT29jT1g2RGhXekhyaml6djhYcmtFN3lGMmhyeE9ERTFzVlBFR1lIVVJrN0pYa3VtLUlvNXJKallXQmI5ZS1vd0hRUGZENXFGcDBFaDRfYWpvckhRcFhqaFdHS0JLWFNRS3BudHAzRldOaXMta1FmX0ExdTVRZDdzMnhybzZJWll3U3FxUk5femRQLVhLdU1hVmtjYlpJVDVnZVNGYTF6UWRMOUhaenEySjZFSGNvSVl4MXZfYU5Nb1doNDNsLWlHb2tJcGRFMEQtWTNNb0UzMGFqZnhJYlBlX21LSGtfaE52eXRzaWxzb2YwT01HVUMwUW4yTUgzUm1LS0lzcU82UUVxaVNJYlhncjdTX3JPVjh0bDJpNmdzVlNyT3FCeGlodmVFYWUyWXQ2bG0tZnhXN2pMS2hUb3pveXJzR3phcWVzQWRLSnVBc2RwVU1OTGFzREY0U0YtSU5zcVZJbUs2bEl1bDlGaUtidkxDLXNKSUZWRWVQTDdWUw==", 
  "token_name": "Token:Visit the Throneroom"

```
curl -X POST -H "Authorization:Token eyJhdXRoIjogIlowRkJRVUZCUW1GRVFXcEpOVE5uUlVaRWVuTlhibFJaVTJoTVNIUjVNREp2VkhGVk5tSmlOVzlmT0hjM2VFdFlSRWMwY2xGNE0xVjRaMDF0TFVSWlJUbFJTR0p1U1ZGWk1XWnhSbFpWUzB4M2MyaGFkVUp5TUhCYVJGOU5abGQ0VVc5T01HRjVVVk5qTXpCWlpuWXhRV013VFU1SGNGOURWakl6Tmw5Q1dWaHFkRGg1VlRoRk9HdHRWVmhqWTNacmVtbFFhRTVmU0ZsRFIwOUxTVnBSVjFCblBUMD0iLCAidXNlcm5hbWUiOiAicGV0ZXIifQ==" -d '{"tokens":{"/blackboard/tasks/2":"Z0FBQUFBQmFEQXU0Z1VrQXplT1l0akVoSlVEWnA2WjJxLWVKcG1mNmNVNU5vYVV6TGlUNnRwT01SdVJnY2lLMTdFbFR3VF9sVHBmZWlaOWNxOUFjVjdCTkhuS0dlcDZLWnpPZmk0V1g0NTdhXzRXOUxIRFRucEZ6NXZPbFBDa0JQOW9qWHpHcU5nZjFJNHI4S3FwdWY5bVR3MDliUDNWS0QweDlVaXZwenlZUG1ZdmliVjUtaUhBdWVuU2dWWU8xdUYycVJrMXNUSEVqMEZhOGlVdnRuTFBuUGQ4ZUY0b3QwS1JuRU5oWXc4dFh1VmZjN2R4RVA1X0FnVnBfZ1p2M1FrQ0k0a1Jab3JRWTBrLU5nRDFhT29jT1g2RGhXekhyaml6djhYcmtFN3lGMmhyeE9ERTFzVlBFR1lIVVJrN0pYa3VtLUlvNXJKallXQmI5ZS1vd0hRUGZENXFGcDBFaDRfYWpvckhRcFhqaFdHS0JLWFNRS3BudHAzRldOaXMta1FmX0ExdTVRZDdzMnhybzZJWll3U3FxUk5femRQLVhLdU1hVmtjYlpJVDVnZVNGYTF6UWRMOUhaenEySjZFSGNvSVl4MXZfYU5Nb1doNDNsLWlHb2tJcGRFMEQtWTNNb0UzMGFqZnhJYlBlX21LSGtfaE52eXRzaWxzb2YwT01HVUMwUW4yTUgzUm1LS0lzcU82UUVxaVNJYlhncjdTX3JPVjh0bDJpNmdzVlNyT3FCeGlodmVFYWUyWXQ2bG0tZnhXN2pMS2hUb3pveXJzR3phcWVzQWRLSnVBc2RwVU1OTGFzREY0U0YtSU5zcVZJbUs2bEl1bDlGaUtidkxDLXNKSUZWRWVQTDdWUw=="}}' 172.19.0.3:5000/blackboard/quests/1/deliveries 
```

 "message": "Created Delivery", 
  "object": [
    {
      "deliverables": [
        162
      ], 
      "id": 354, 
      "quest": 1, 
      "timestamp": "2017-11-15T10:42:47.348559+00:00", 
      "user": "peter"
    }
  ], 
  "status": "success"
  # Quest 2
```
curl -H "Authorization:Token eyJhdXRoIjogIlowRkJRVUZCUW1GRVFXcEpOVE5uUlVaRWVuTlhibFJaVTJoTVNIUjVNREp2VkhGVk5tSmlOVzlmT0hjM2VFdFlSRWMwY2xGNE0xVjRaMDF0TFVSWlJUbFJTR0p1U1ZGWk1XWnhSbFpWUzB4M2MyaGFkVUp5TUhCYVJGOU5abGQ0VVc5T01HRjVVVk5qTXpCWlpuWXhRV013VFU1SGNGOURWakl6Tmw5Q1dWaHFkRGg1VlRoRk9HdHRWVmhqWTNacmVtbFFhRTVmU0ZsRFIwOUxTVnBSVjFCblBUMD0iLCAidXNlcm5hbWUiOiAicGV0ZXIifQ==" 172.19.0.4:5000/floor
```

>"message": "\nYou enter the dungeon and find a total mess.\nIn the far back you hear the shuffling of rats.\n        ", 
  "next": "/floor_u1/rats"
  
```
curl -H "Authorization:Token eyJhdXRoIjogIlowRkJRVUZCUW1GRVFXcEpOVE5uUlVaRWVuTlhibFJaVTJoTVNIUjVNREp2VkhGVk5tSmlOVzlmT0hjM2VFdFlSRWMwY2xGNE0xVjRaMDF0TFVSWlJUbFJTR0p1U1ZGWk1XWnhSbFpWUzB4M2MyaGFkVUp5TUhCYVJGOU5abGQ0VVc5T01HRjVVVk5qTXpCWlpuWXhRV013VFU1SGNGOURWakl6Tmw5Q1dWaHFkRGg1VlRoRk9HdHRWVmhqWTNacmVtbFFhRTVmU0ZsRFIwOUxTVnBSVjFCblBUMD0iLCAidXNlcm5hbWUiOiAicGV0ZXIifQ==" 172.19.0.4:5000/floor_u1/rats
```
> "message": "\nYou approach the rats and they obviously do not fear you.\nRemember you are not done until all rats are gone or you are gone.\n(Resolve all steps and post the tokens here afterwards)\n        ", 
  "required_players": 1, 
  "required_tokens": [
    "Token:Rat Tail", 
    "Token:Rat Eye", 
    "Token:Rat Leg"
  ], 
  "steps_todo": [
    "/floor_u1/rats/1", 
    "/floor_u1/rats/2", 
    "/floor_u1/rats/3"
  ]
