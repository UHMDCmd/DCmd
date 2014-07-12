
<%@ page import="edu.hawaii.its.dcmd.inf.Host" %>
<html>
<head>
%{--    <r:require modules='ui,menu,footer, tabletools, bootstrap' />--}%
    <export:resource/>
    <meta content="main" name="layout" />
    <g:set var="entityName" value="${message(code: 'host.label', default: 'Host')}" />
    <title><g:message code="default.dcmd.label" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js">
    </script>

    %{--<g:applyLayout name="breadcrumb_bar">--}%
        %{--<g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'host']"/>--}%
    %{--</g:applyLayout>--}%

%{--
    <style>
    .loadingModal {
        display:    none;
        position:   fixed;
        z-index:    1000;
        top:        0;
        left:       0;
        height:     100%;
        width:      100%;
        background: rgba( 255, 255, 255, .8 )
        url('http://i.stack.imgur.com/FhHRx.gif')
        50% 50%
        no-repeat;
    }
    body.loading {
        overflow: hidden;
    }
    body.loading .modal {
        display: block;
        background-color: grey;
    }

    </style>
--}%
    <g:if test="${session.getValue("hostSize") > 0}">

        <script type="text/javascript">

            $(window).load(function(){
                $('#sModal').modal().show();
            });

         $(window).onbeforeunload(function(){    //reset the session vars if the update was execuited
            ${session.setAttribute("hostSize", 0)}
             ${session.setAttribute("newHosts", null)}
         });

             //alert('Hosts Have Been updated with Vcenter')

        </script>

    </g:if>
    <g:if test="${session.getAttribute("status") == false}">

       <script type="text/javascript">
           $(window).load(function(){
            alert('VCenter Connection Timed Out. No Hosts were updated.')
           });
        </script>

        ${session.removeAttribute("status")}
    </g:if>

</head>
<body>
<div id="container">
    <g:render template="../show_secondary" model="[pageType:'host', objectId:0, action:'list']" />
    <g:render template="../breadcrumbs" model="[pageType:'host', action:'list']"/>


</div>


 <div class="pageBody" id="outerElement">
%{--     <export:formats formats="['csv', 'excel', 'ods', 'pdf', 'rtf', 'xml']" /> --}%
     <g:render template="../toolTip" />
     <g:render template="listGrid" />


</div>
%{--
<div class="pageBody" id="outerElement">
<export:formats formats="['csv', 'excel', 'ods', 'pdf', 'rtf', 'xml']" />
<g:render template="../toolTip" />
    <g:render template="listGrid" />

%{--
<g:form controller="VMWare">
    <g:actionSubmit id='btn_update_hosts' value="Update Hosts" action="index"/>
</g:form>

    <div class="loadingModal" id="loadingModal"></div>

    <script type="text/javascript">
        $( "#btn_update_hosts" ).click(function() {
            document.getElementById("loadingModal").style.display="inline";
            $("#loadingModal").html("<div style='font-family:Veranda, Helvetica, sans-serif ; font-size: 24px; text-align: center; padding-top: 40%;'>" +
             "Connecting to Vcenter, Updating Hosts...Please Wait<div>");
        });
    </script>

   <!-- Modal -->
    <div class="modal fade" id="sModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Update Hosts Successful!</h4>
                </div>
                <div class="modal-body">
                   Hosts Have Been Updated With Vcenter.
                   <b>${session.getValue("hostSize")}</b> New Hosts Have Been Created.
                    <br>
                    Here is a list of the new Hosts that have been created:
                    <br>
                    <g:each in="${params.newHosts}">
                       <p>${it}</p>
                    </g:each>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>

%{--<g:button id='btn_update_hosts' >Update Hosts</g:button>--}%


</body>
</html>
