#!/usr/bin/python

import readline
import json
import requests

dcmd_url = "http://dcm01.pvt.hawaii.edu:8081/its/dcmd/api/host/"

# Create a new http session with the following auth details and disabling ssl verification.
s = requests.Session()
s.verify = False

with open("./hosts") as f:
    for host in f:
    #print host.rstrip()
        r = s.get(dcmd_url + host.rstrip())
        if r.status_code == 200:
                hostInfo = r.json()
                supportStaff = hostInfo["SupportStaff"]
                for staff in supportStaff:
                    if staff["Role"] == "Primary SA":
                        print host.rstrip() + "," + staff["Person"]
            else:
                print host.rstrip() + "," + "not found"
