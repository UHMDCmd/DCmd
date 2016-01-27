#!/bin/bash
#To execute the POST create action with the above json file to create a new Host using CURL on the test environment, use the following CURL command:

curl -i -X POST -H "Content-type:application/json" http://dcm51.pvt.hawaii.edu:8080/its/dcmd/api/host -d @sample.json

