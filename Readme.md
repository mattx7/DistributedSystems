# Distributed Systems

## Test of the given REST-api

### Login the docker container
Log into the HAW jump host (IP:141.22.34.22)... 
```
ssh -p 443 <A-Identifer>@141.22.34.22
```
...and then into the container. IP is visible in "Container Harbor"
```
ssh -p 22 root@<ip from the docker container>
```
### Blackboard

Ask for blackbord service with netcat.
```
netcat -ulp 24000
```
We receive `{"blackboard_port":5000}`. 

Log into `172.19.0.3` (the blackboard) port 5000.

### User

See all registerd users: 
```
curl 172.19.0.3:5000/users
```
Register a `/users` with this post ( `{“name”:”<username>”, “password”:”<password>”}` )
```
curl -H "Content-Type: application/json" -X POST -d '{"name":"<username>","password":"<password>"}' http://172.19.03:5000/users
```

Login via get `/login` and receive your authentication token:
```
curl -u <username>:<passwor> http://172.19.03:5000/login
```
You receive a token.

It's possible to check your login via `whoami`

### Quests

To see quests on big black board do:
```
curl http://172.19.0.3:5000/blackboard/quests
```

You will then see a list of quests. You can get a specific quest by typing the task number:
```
curl http://172.19.0.3:5000/blackboard/tasks/1
```

You will get a JSON responce like:
```json
{
  "object": {
    "_links": {
      "self": "/blackboard/tasks/1"
    }, 
    "deliverables": [
      1, 
      2, 
      3, 
      4, 
      5, 
      6, 
      7, 
      8, 
      9, 
      10, 
      11, 
      12, 
      13, 
      14, 
      15, 
      16, 
      17, 
      18, 
      19, 
      20, 
      21, 
      22, 
      23, 
      24, 
      25, 
      26, 
      27
    ], 
    "description": "Go and visit the throneroom. Use your /map to find it", 
    "id": 1, 
    "location": "/map/Throneroom", 
    "name": "A kingly welcome", 
    "quest": 1, 
    "required_players": 1, 
    "requirements": null, 
    "resource": "/visits", 
    "token": null
  }, 
  "status": "success"
}
```
There you can see information about the quest like the location.

### Location

You can get more information about the location via...
```
curl http://172.19.0.3:5000/map/Throneroom
```

...and get a JSON responce like: 
```json
{
  "object": {
    "host": "172.19.0.4:5000", 
    "name": "Throneroom", 
    "tasks": [
      1, 
      2, 
      4, 
      5
    ], 
    "visitors": []
  }, 
  "status": "success"
}
```

To visit the location type:
```
curl -X POST --h "Authorization:Token <extremly long token>" http://172.19.0.4:5000/visits
```

You have now fullfilled the first quest by visiting the throneroom
```json
{
  "message": "\nWelcome to the Throneroom! Well they have told you to come visit the King?\nWell, do you have an audience? NO? Then go away and tell your supervisor that the King does not have time\nfor every new greenhorn in town. But consider your quest as fullfilled - at least you visited the Throneroom.\nGive this token back as a symbol of fullfillment ( quest deliveries, POST {\"tokens\":{\"<task_uri>\":token}} )\n", 
  "token": "Z0FBQUFBQmFBRmpRQVIxVlVnNndaX3MyNVlQTEtheFdTZmFJVDNvZ05MOXZSWjEtUEg2TE02X0w1N2dINW91YmwxbFR0cjM3dGZia18xd2VTY2J2Tk43OW5EaHc3ZmNabDJvT1pQdkY2RzIxSXY1ZWljcmxpenZTaHVxeXFDenZmTXlHN1l2NWJhRmsxS0c1UTJ2LVRRZ0JHVUJkN01JT19uQndickdHSnEwd09jOVgzYXF5YmRaaG8ycEg0Qy1HdjlRWEhtSHJZUXBReEhPYS05MGNSaHdBY1pTN2htZW1OUWNSYTMzUlh6dExGcDNrOFlrYVNRamh4eHdPZ3VlUHNpS04tTUFKS21xY1pLZFNGN2J4NjV2clFCWGJUaDN0bDFVeEw2UmluMUl4YlVYSUxvc3hidTQ4dXppRmFXNU81ZDZ0X09YRDZJejZ2TjRFMVhHOTBucUtaN2RMbExxNTNmeXphcTdiVTVRY3Vsd002c0E2ekcyV0VFS3k5YzRwdGhvWjQxZmJ2N2NXaDN0VURZR2d1R0VHRnYzWWFieGFyanM3SVJ6eHJ2cUg4RldScGNyb2JfRk9XeDNyNnFaUE1lQTdYVkpBOFJ0Sko4aC1GNHZXV0lDeU1HdWlJTXh5cFVzX2JGTkRmUGxDaHJFRElPSDRiT3p5emM4RTJ1eEFPdnJPcDMtY3RpOWlzMHZGR01ROFVHUmQ1MFloVVRzY081V3Y5bWZtODkyRlVVd2lQNUNBLXo5RFFacVAxYTlBemdYZmFzUXROMk10V0RYRlBzRDk2Y0dNVVhUMHpzaWJRQUpodEpmcUotck1Vd3JfYXhMVkpUaz0="
}
```
