<%@ page import="edu.hawaii.its.dcmd.inf.Uisettings"%>
<html>
<head>

    %{--
  - Copyright (c) 2014 University of Hawaii
  -
  - This file is part of DataCenter metadata (DCmd) project.
  -
  - DCmd is free software: you can redistribute it and/or modify
  - it under the terms of the GNU General Public License as published by
  - the Free Software Foundation, either version 3 of the License, or
  - (at your option) any later version.
  -
  - DCmd is distributed in the hope that it will be useful,
  - but WITHOUT ANY WARRANTY; without even the implied warranty of
  - MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  - GNU General Public License for more details.
  -
  - You should have received a copy of the GNU General Public License
  - along with DCmd.  It is contained in the DCmd release as LICENSE.txt
  - If not, see <http://www.gnu.org/licenses/>.
  --}%


    <meta content="main" name="layout" />

    <jqDT:resources jqueryUi="true" type="js" />
    <script language="javascript" type="text/javascript" src="../js/mustache.js"></script>

    %{--<g:applyLayout name="breadcrumb_bar">--}%
        %{--<g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'settings']"/>--}%
    %{--</g:applyLayout>--}%


</head>
<body>

<div id="container">
    <g:render template="../show_secondary" />

    <g:render template="../breadcrumbs" model="[pageType:'settings', action:'index']"/>

</div>


<br>

<div class="pageBodyNoMargin">
    <article class="module width_full" >
        <div class="module_content">

        <h1>UI Settings</h1>
        <g:form controller="uisettings">
                <div class="show-wrapper">
                    <table class="floatTables">
<tr>
                        <td>
                        <tr>
                           <td valign="top" class="name"><b>Theme</b></td>
                            <td valign="value">
                                <select id="theme" name="theme">
                                <option value="0">--Select a Theme--</option>
                                <option value="1">Grape</option>
                                <option value="2">Darkness</option>
                                    <option value="3">Lightness</option>
                                    <option value="4">Dot Love</option>
                                <option value="5">Kermit</option>
                                <option value="6">Mint Choc</option>

                            </select>
                            </td>
                        </tr>


                        <tr>
                        <td valign="top" class="name"><b>Font Size</b></td>
                        <td valign="value">
                            <select id="fontSize" name="fontSize">
                                <option value="11">11</option>
                                <option value="14">14</option>
                                <option value="16">16</option>
                                <option value="18">18</option>
                                <option value="20">20</option>
                            </select>
                        </td>
                        </tr>


                    </td>


                        <td valign="top" class="name"><b>Background Style</b></td>
                        <td valign="value">

                            <ol id="selectable" name="background select">
                                <li class="ui-state-default" style="background-image: url('../css/admin_theme/content_images/bigstripes2-lite-xl.png')"></li>
                                <li class="ui-state-default" style="background-image: url('../css/admin_theme/content_images/blue.png')"></li>
                                <li class="ui-state-default" style="background-image: url('../css/admin_theme/content_images/black.png')"></li>
                                <li class="ui-state-default" style="background-image: url('../css/admin_theme/content_images/green.png')"></li>
                                <li class="ui-state-default" style="background-image: url('../css/admin_theme/content_images/purple.png')"></li>

                            </ol>
                            <style>
                            #feedback { font-size: 1.4em; }
                            #selectable .ui-selecting { border: 4px solid #ffbc1a; }
                            #selectable .ui-selected { border: 4px solid #F39814; color: white; }
                            #selectable { list-style-type: none; margin: 0; padding: 0; width: 650px; }
                            #selectable li { margin: 4px; padding: 1px; float: left; width: 50px; height: 40px; font-size: 4em; text-align: center; }
                            </style>
<script>
    $(document).ready(function(){

                        $('#selectable').selectable({
                         selected: function (event, ui) {
                         var selected = ($(this).find('.ui-selected').attr('id'));
                           // alert(selected);

                             var params = new Object();
                             params.selected = selected;

                             $.ajax({
                                 async:false,
                                 url: '../Uisettings/changeBackground',
                                 data: params,
                                 type:'GET',
                                 contentType: 'application/json; charset=utf-8',
                                 dataType: 'json',
                                success: function(data) {
                                     sessionStorage.backgroundColor = data.returnVal;
                                     location.reload();

                                },
                                 error: function () { console.log('Error updating page content'); }
                             });
                         }
                        });
                                //dynamically create ids for the list items
                                $("#selectable li").attr('id', function(i){
                                 return 'selectable' + (i+1);
                                    });

        loadDetails();

        function loadDetails() {
            $("#fontSize").val(sessionStorage.fontVal);
            $("#selectable").val(sessionStorage.backgroundColor);
            $("#theme").val(sessionStorage.selectedTheme);
        }
    });




                            </script>
                        </td>
                    </tr>
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

    //submit change function, automatically called on select change
    $("#theme").change(function(){
     $("#settingsSubmit").click();
        sessionStorage.selectedTheme = $("#theme").val();
    });

    //listener for font size
    $("#fontSize").change(function(){

        var selected = $("#fontSize").val();
        var params = Object();
        params.fontValue = selected;

        $.ajax({
            async:false,
            url: '../Uisettings/changeFont',
            data: params,
            type:'GET',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(data) {
                sessionStorage.fontVal =  data.returnVal;
                location.reload();

            },
            error: function () { console.log('Error updating page content'); }
        });
    });
</script>


</body>


</html>
