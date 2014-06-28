
<%@ page import="edu.hawaii.its.dcmd.inf.Host"%>
<html>
    <head>
        <meta content="main" name="layout" />
        <title><g:message code="default.dcmd.label"/></title>
        <jqDT:resources jqueryUi="true" type="js" />


        <g:applyLayout name="breadcrumb_bar">
            <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'host']"/>
        </g:applyLayout>

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
        }

    </style>

        <script type="text/javascript">

            $(function() {
                $( "#report_dialog" ).dialog({
                    autoOpen: false,
                    width: 800,
                    height: 400,
                    show: {
                        effect: "blind",
                        duration: 1000
                    },
                    hide: {
                        effect: "blind",
                        duration: 1000
                    }
                });

                function getHostSupportReport() {
                    document.getElementById("loadingModal").style.display="inline";
                    var oData = new FormData(document.forms.namedItem("fileinfo"));
                    console.log(oData);
                    var url="${createLink(controller:'host',action:'shortSupportersReport')}";
                    jQuery.ajax({
                        async: false,
                        url: url,
                        type:'POST',
                        data: oData,
                        processData:false,
                        contentType: false,
                        success: function(data) {
                            document.getElementById('report_dialog').innerHTML = data.message;
                            $( "#report_dialog").dialog( "open" );
                            document.getElementById("loadingModal").style.display="none";
                        },
                        error: function () { alert('Error retrieving elog info'); }
                    });

                }

                $( "#btnShortSupporterReport" ).click(function() {
                    getHostSupportReport();
                });

            $( "#btnHostSupportReport" ).click(function() {
                    document.getElementById("loadingModal").style.display="inline";
                    var oData = new FormData(document.forms.namedItem("fileinfo"));
                    console.log(oData);
                    var url="${createLink(controller:'host',action:'hostSupportReport')}";
                    jQuery.ajax({
                        async: false,
                        url: url,
                        type:'POST',
                        data: oData,
                        processData:false,
                        contentType: false,
                        success: function(data) {
                            document.getElementById('report_dialog').innerHTML = data.message;
                            $( "#report_dialog").dialog( "open" );
                            document.getElementById("loadingModal").style.display="none";
                        },
                        error: function () { alert('Error retrieving elog info'); }
                    });

            });
            });


        </script>

 </head>
 <body>
 <div id="report_dialog" title="Report Window"></div>

 <div id="container">
        <g:render template="../show_secondary" model="[pageType:'host', objectId:0, action:'reports']" />

        <g:render template="../breadcrumbs" model="[pageType:'host', action:'reports']"/>

    </div>


 <div class="pageBodyNoMargin">
        <article class="module width_full">
			<div class="module_content">
                <g:render template="../content_title" model="[entityName: 'Reports', code:'default.show.label']" />

                <table class="floatTables" style="border:1px solid #CCCCCC; width:600px;">
                <tr><td><center><h3>Host Support Staff Report</h3></center>
                </td></tr>
                <tr><td>
                Select a file containing a <b>newline-delimited</b> list of Hostnames to get a list of all affected support Staff.
                </td></tr>
                <tr><td>
                    <div style="margin-top:5px">
                        <g:form enctype="multipart/form-data" class="upload" name="fileinfo">
                            <input name="uploadField" type="file">
                            <br>
                            <table style="border-top:1px solid #CCCCCC"><tr><td style="width:50%; border-right:1px solid #CCCCCC; padding-left:5px;">
                                Report Short list of all users (with emails) assigned to given list of Hosts and affected Applications and Services.
                            <center>
                            <input class="ui-corner-all" id="btnShortSupporterReport" type="button" value="Short Staff Report"/>
                            </center>
                            </td><td style="padding-left:5px;">
                                Report Support Roles assigned to each Host from the given List.<br><br>
                            <center>
                            <input class="ui-corner-all" id="btnHostSupportReport" type="button" value="Host Support Report"/>
                            </center>
                            </td></tr>
                            </table>

                        </g:form>
                </div>
                </td></tr>

                </table>


		</article>
		</div>
 <div class="loadingModal" id="loadingModal"></div>
	</body>
</html>

