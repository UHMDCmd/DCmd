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

<h1>UI Settings</h1>
<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">
        <g:form controller="uisettings">
                <div class="show-wrapper">
                    <table class="floatTables">
                        <tr>
                           <td valign="top" class="name"><b>Theme</b></td>
                            <td valign="value">
                                <select id="theme" name="theme">
                                <option value="1" selected="selected">Grape</option>
                                <option value="2">The Darkness</option>
                                <option value="3">Pepper Grinder</option>
                                <option value="4">Dot Luv</option>
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
