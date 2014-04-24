package edu.hawaii.its.dcmd.inf

class Application extends SupportableObject {

	String applicationTitle
	String applicationDescription

	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById
    String generalNote
    String changeNote
    String planningNote
    Status status
    Environment env

	String supportableType = "application"
	
	static hasMany = [
        supportStaff:PersonSupportRole,
        services: Service,
        tiers: Tier
	]

    static mapping = {
        table 'applications'
        version false
    }

	static constraints = {
		applicationTitle(blank: false, maxSize: 256)
		applicationDescription(nullable: true, maxSize: 256)
//		applicationTier(nullable: true, maxSize: 45)
		dateCreated(nullable:  false)
		lastUpdated(nullable:  false)
		updatedById(nullable:  true)
        generalNote(nullable: true, maxSize: 1024)
        changeNote(nullable: true, maxSize: 1024)
        planningNote(nullable: true, maxSize: 1024)
        status(nullable: true, default:"Available")
        services(nullable: true)
        env(nullable: true)

	} \

    static auditable = true

	String toString(){
        "${applicationTitle} (${env})"
	}
}

