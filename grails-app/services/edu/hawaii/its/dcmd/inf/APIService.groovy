package edu.hawaii.its.dcmd.inf

class APIService {

    def serviceMethod() {

    }

    Host getHostByIdOrHostname(String hostname) {
        def hostInstance
        try {
            hostInstance = Host.get(hostname)
        }
        catch(Exception E) {
            hostInstance = Host.findByHostname(hostname)
        }
        return hostInstance
    }
}
