package dcmd

import edu.hawaii.its.dcmd.inf.DcimConnectorController
import edu.hawaii.its.dcmd.inf.DcimUpdateService


class DcimUpdateJob {
    static triggers = {
       // simple name: 'mySimpleTrigger', startDelay: 30000,  repeatInterval: 30000, repeatCount: 1

       //fire every weekday mon-fri at 4:04 pm
      //  cron name: 'cronTrigger', cronExpression: "0 4 16 ? * MON-FRI"

        //fire everyday at 2:00am
        cron name: 'cronTrigger', cronExpression: "0 0 2 1/1 * ? *"
    }

    def execute() {
        // execute job
        print("****running dcim update****")
        def updateService =  new DcimUpdateService()
        updateService.runQuery()

      }
}



