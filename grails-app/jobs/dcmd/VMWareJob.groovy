package dcmd

import edu.hawaii.its.dcmd.inf.VMService


class VMWareJob {

    def VMService = new VMService()

    static triggers = {
              simple name: 'mySimpleTrigger', startDelay: 30000,  repeatInterval: 3600000, repeatCount: 0

        //fire every weekday mon-fri at 4:04 pm
        //  cron name: 'cronTrigger', cronExpression: "0 4 16 ? * MON-FRI"

        //fire everyday at 2:00am
        //cron name: 'cronTrigger', cronExpression: "0 0 2 1/1 * ? *"
    }

    def execute() {
        // execute job
        print("****running VM update****")
        //     new VMwareController().index()
        VMService.syncToVCenter()

    }
}