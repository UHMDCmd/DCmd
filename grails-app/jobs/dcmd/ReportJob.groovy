package dcmd

import edu.hawaii.its.dcmd.inf.PersonService

/**
 * Created by Ben on 10/22/2015.
 */
class ReportJob {
    def PersonService = new PersonService()

    static triggers = {
        simple name: 'reportTrigger', startDelay: 27000000,  repeatInterval: 86400000, repeatCount: -1

        //fire every weekday mon-fri at 4:04 pm
        //  cron name: 'cronTrigger', cronExpression: "0 4 16 ? * MON-FRI"

        //fire everyday at 2:00am
        //cron name: 'cronTrigger', cronExpression: "0 0 2 1/1 * ? *"
    }

    def execute() {
        // execute job
        println("*** Running change report ***")
        //     new VMwareController().index()
        PersonService.staffChangeReport()
        PersonService.roleChangeReport()

    }

}
