# DistributedSystems

## Environment

Container Harbor: https://141.22.34.22/
Enter your Docker Container 

Run the jar with:  `java -jar vsp_abw286.jar`

Register with new User or Login with existing one

type !help to see available commands





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


