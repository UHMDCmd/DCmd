package dcmd

import edu.hawaii.its.dcmd.inf.VMwareController
import edu.hawaii.its.dcmd.inf.DcimConnectorController
import edu.hawaii.its.dcmd.inf.DcimUpdateService
import grails.async.Promise
import grails.async.Promises.*

/*
class DcimUpdateJob {
    def dcimUpdateService

    static triggers = {
        simple name: 'mySimpleTrigger', startDelay: 10000,  repeatInterval: 90000, repeatCount: 0

        //fire every weekday mon-fri at 4:04 pm
        //  cron name: 'cronTrigger', cronExpression: "0 4 16 ? * MON-FRI"

        //fire everyday at 2:00am
        //cron name: 'cronTrigger', cronExpression: "0 0 2 1/1 * ? *"
    }

    def execute() {
        // execute job
        print("****running dcim update****")
        dcimUpdateService.startUpdate()

    }
}

  */

