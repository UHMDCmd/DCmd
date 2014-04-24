<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<script type="text/javascript" >
    $("#btn${selectName}").bind('click', function() {

        document.getElementById("stripType-dialog").style.visibility = 'visible';
        //  console.log(document.getElementById('itsId').value);
        $( "#stripType-dialog" ).dialog("open");
        $( "#stripType-dialog" ).focus();


        document.getElementById("connectorsDiv").innerHTML =
                "<tr style='margin-bottom:5px;'><td class='ui-widget' style='width:150px;'>" +
                        "Connector Type</td><td style='width:60px;'>Qty</td><td style='width:50px;'></td></tr>" +
                        "<tr class='dialog-tr'><td><input type='hidden' id='connectorType1'/></td>" +
                        "<td><input type='number' id='connectorNum1' class='text ui-widget-content ui-corner-all' style='width:40px; margin-left:0;'/>" +
                        "</td></tr>";

        document.getElementById("numConnectorRows").value = 1;
        document.getElementById("selectObjectName").value = (${selectName}).id;

        $("#connectorType1").select2({
            placeholder:'Please Select...',
            maximumInputLength: 20,
            width:150,
            data: [${genService.listConnectorsAsSelect()}]
        });
    });
</script>


<input class="ui-corner-all" id="btn${selectName}" type="button" value="+" style="width:22px; height:25px; padding-bottom:6px; margin-top:3px; background: -moz-linear-gradient(center bottom , #CCCCCC 0%, #EEEEEE 60%) repeat scroll 0 0 #CCCCCC; vertical-align: middle;"/>
