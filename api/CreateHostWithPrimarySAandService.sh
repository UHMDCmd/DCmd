#!/bin/bash
#To execute the POST create action with the above json file to create a new Host using CURL, use the following CURL command:

curl -i -X POST -H "Content-type:application/json" http://www.hawaii.edu/its/dcmd/api/host -d @sample.json
