package edu.hawaii.its.dcmd.inf

class Environment {

	String name                                  // e.g.: Production
	String abbreviation                          // e.g.: prod
	String description


    static auditable = true


    // While we don't want cascading deletes, we want to ensure that
	// an environment name can't be deleted if it is associated with any
	// Applications or Servers.
	static hasMany = [
		applications:Application,
		hosts:Host,
        services: Service
	]

	static constraints = {
		name(nullable:false, blank:false, unique:true)
		abbreviation(nullable:true)
		description(nullable:true)
	}

	String toString(){
         abbreviation
	}
}
