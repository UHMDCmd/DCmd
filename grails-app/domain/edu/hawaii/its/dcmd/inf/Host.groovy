package edu.hawaii.its.dcmd.inf

class Host extends SupportableObject {

	String hostname
	Integer solarisFssShare
	boolean nwaccScan = false
	Environment env
	Date dateCreated = new Date()
	Date lastUpdated = new Date()
	Integer updatedById
	String supportableType = "host"
//    Asset asset
    String generalNote
    String changeNote
    String planningNote
    Status status
    String os
    String type = "VMware"
    String serviceContact

    //Attributes for exporting to spreadsheet
    String psaName
    String appList
    String serviceList
    String appAdmin
    String location
    String rackAssignment

    Cluster cluster
//	List hostSupporters = new ArrayList()

    static auditable = true

    static belongsTo = [asset: Asset]

	static hasMany = [
//		applicationAssignments: ApplicationServerAssignment,
//		hostSupporters:SupportRole,
//		resourceAllocations:ResourceAllocation,
//        applications: Application
        tiers:Tier
	]

//    static fetchMode = [supporters: 'eager', status: 'eager', asset: 'eager', env:'eager']

//    static mappedBy = [asset: 'hosts']

    static mapping = {
//        asset cascade: 'save-update'
        tiers cascade: 'all-delete-orphan'
    }

	static constraints = {
		hostname(blank: false, maxSize: 45, nullable:false) //unique attribute removed
		solarisFssShare(nullable: true)
//		applicationAssignments(nullable: true)
 //       resourceAllocations(nullable: true)
        asset(nullable: true)
        status(nullable: true)
        os(nullable:true)

        cluster(nullable:true)
        tiers(nullable:true)

        serviceContact(nullable: true)
		updatedById(nullable: true)
        generalNote(nullable: true, maxSize: 1024)
        changeNote(nullable: true, maxSize: 1024)
        planningNote(nullable: true, maxSize: 1024)
        env(nullable:true)

        psaName(nullable: true)
        appList(nullable: true)
        serviceList(nullable:true)
        appAdmin(nullable: true)
        location(nullable: true)
        rackAssignment(nullable: true)
	}


	
	String toString() {
		hostname
	}

    //method for exporting to spreadsheet
    void setExportParams(String psa, String apps, String admin, String loc, String rack){
          psaName = psa;
        appList = apps;
        appAdmin = admin;
        location = loc;
        rackAssignment = rack;
    }

    String getServerOrClusterLink() {
        String buffer = ""
        if(this.type == 'VMware Guest') {
            return "<a href='../cluster/show?id=${this.cluster?.id}'>${this.cluster.toString()}</a>"
        }
        else
            return "<a href='../asset/show?id=${this.asset?.id}'>${this.asset.toString()}</a>"
    }

/*
    String listApplicationsAsString() {

        ArrayList<String> buf = new ArrayList()
        if(!applications.empty) {
            applications.each {
                buf.add("<a href='../application/show?id=${it.id}'>${it.applicationTitle}</a>")
            }
        }
        return buf.toString().substring(1, buf.toString().length()-1)
    }

    String listApplicationsToCompare() {
        ArrayList<String> buf = new ArrayList()
        if(!applications.empty) {
            applications.each {
                buf.add(it.applicationTitle)
            }
        }
        return buf.toString()
    }
    */
}

