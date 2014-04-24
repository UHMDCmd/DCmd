package edu.hawaii.its.dcmd.inf

class StatusController {

    def scaffold = Status

    def listStatusAsSelect={
        def lst = Status.createCriteria().list {
            order('abbreviation', 'asc')
        }

        StringBuffer buf = new StringBuffer("<select>")
        lst.each{
            buf.append("<option value=\"${it.id}\">")
            buf.append(it.toString())
            buf.append("</option>")
        }
        buf.append("</select>")

        render buf.toString()
    }

    def listStatusAsSelect2 = {
        def lst = Status.createCriteria().list {
            order('abbreviation', 'asc')
        }

        StringBuffer buf = new StringBuffer("")
        lst.each{
            buf.append("{id:\'${it.id}\',text:'")
            buf.append(it.toString())
            buf.append("'},")
        }

        return buf.toString()
    }

}
