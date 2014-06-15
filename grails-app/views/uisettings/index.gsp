<html>
<head>

    <r:require modules='footer, tabletools' />

    <meta content="main" name="layout" />

    <jqDT:resources jqueryUi="true" type="js" />

    <meta content="main" name="layout" />


</head>
<body>

%{--<script type="text/javascript">--}%
    %{--$(document).ready(function() {--}%
    %{--function refreshSettings(){--}%


    %{--}--}%
    %{--});--}%



%{--</script>--}%


<div id="container">
    <g:render template="../show_secondary" />
</div>


<div class="pageBodyNoMargin">
    <article class="module width_full" style="display: block; width:50%">
        <h1>UI Settings</h1>
        <div class="module_content" >
        <g:form controller="uisettings">
                <div class="show-wrapper">
                    <table class="floatTables">
                        <tr>
                           <td valign="top" class="name"><b>Theme</b></td>
                            <td valign="value">
                                <select id="theme" name="theme">
                                <option value="1" selected="selected">Grape</option>
                                <option value="2">The Darkness</option>
                                <option value="3">Dot Luv</option>
                                
                            </select>
                                </td>
                        </td>
                        </tr>

                        <tr>
                            <td valign="top" class="name"><b>Rows Per Page</b></td>
                            <td valign="value"> <g:field type="String" name="rows" /></td>
                        </td>
                        </tr>

                        <tr>
                            <td valign="top" class="name"><b>Auto Login</b></td>
                            <td valign="value"><g:radio name="auto_login" value="true"/></td>
                        </td>
                        </tr>

                    </table>
                </div>
            </div>

                        <g:actionSubmit value="Save Settings" action="save_settings"/>
                    </g:form>

    </article>
</div>
    %{--<div class="show-wrapper">--}%
     %{--<h1>Preview</h1>--}%
    <g:render template="preview" />
       %{--</div>--}%
    </div>



</body>
</html>
