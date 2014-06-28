<%@ page import="edu.hawaii.its.dcmd.inf.Uisettings"%>
<html>
<head>


    <meta content="main" name="layout" />

    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'settings']"/>
    </g:applyLayout>

</head>
<body>

<div id="container">
    <g:render template="../show_secondary" />

    <g:render template="../breadcrumbs" model="[pageType:'settings', action:'index']"/>

</div>


<br>
<div class="pageBodyNoMargin">
    <article class="module width_full" style="width:50% ">
        <div class="module_content">

        <h1>UI Settings</h1>
        <g:form controller="uisettings">
                <div class="show-wrapper">
                    <table class="floatTables">
                        <tr>
                           <td valign="top" class="name"><b>Theme</b></td>
                            <td valign="value">
                                <select id="theme" name="theme">
                                <option value="0">--Select a Theme--</option>
                                <option value="1">Grape</option>
                                <option value="2">The Darkness</option>
                                <option value="3">Dot Luv</option>
                                
                            </select>
                                </td>

                        </tr>
                        <td valign="top" class="name"><b>Font Size</b></td>
                        <td valign="value">
                            <select id="fontSize" name="fontSize">
                                <option value="11">11</option>
                                <option value="12">12</option>
                                <option value="13">13</option>
                                <option value="14">14</option>
                                <option value="15">15</option>
                                <option value="16">16</option>
                            </select>
                        </td>

                        <td valign="top" class="name"><b>Font Style</b></td>
                        <td valign="value">
                            <select id="fontStyle" name="fontStyle">
                                <option value="1">x</option>
                                <option value="2">x</option>
                                <option value="3">x</option>
                                <option value="4">x</option>
                                <option value="5">x</option>
                                <option value="6">x</option>
                            </select>
                        </td>

                    </table>
                </div>
            <g:actionSubmit id="settingsSubmit" value="Save Settings" action="save_settings" style="display: none"/>
          </g:form>
            </div>

    </article>
</div>

<div id="themePreview" style="float:left">
<g:render template="preview" />
</div>

<script>
    $("#theme").change(function(){
//        alert("changed");
     $("#settingsSubmit").click();
    });
</script>


</body>


</html>
