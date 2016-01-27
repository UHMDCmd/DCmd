#!/bin/bash
#To execute the POST create action with the above json file to create a new Host using CURL, use the following CURL command:

curl -i -X PUT -H "Content-type:application/json" http://dcm51.pvt.hawaii.edu:8080/its/dcmd/api/host/addService/its10 -d "{serviceName:'Banner Forms',serviceEnv:dev}"
