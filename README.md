# DistributedSystems

## Environment

Container Harbor: https://141.22.34.22/

Run the jar with:  `java -cp vsp_abw286.jar vsp/Application *.class`

## Testing of the given REST-api

`curl -H "Content-Type: application/json" -X POST http://172.19.0.3:5000/users -d '{"name":"<username>", "password":"<password>"}'`

