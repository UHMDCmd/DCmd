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

<script language="javascript" type="text/javascript" src="../js/isotope.pkgd.min.js"></script>
<script language="javascript" type="text/javascript">
    function setup(hostInstance) {

        var $container = $('#container');
        $container.isotope({
            itemSelector: '.item',
            layoutMode: 'fitRows'
        });
    }

</script>

<style>
.value input[type="text"] {
    background:transparent;
    border: 0px;
    color: white;
}

.editing input[type="text"] {
    background:white;
    border: 1px black;
    color: black;
}
</style>


<div class="dialog">
    <script type="text/x-handlebars-template" id="server_template">

    <div id="container" class="js-isotope" data-isotope-options='{ "columnWidth":400}' style="min-height:275px">

        <input type="button" id="unlock" value="Unlock" /><input type="button" id="lock" value="Lock" />

        <div class="item">
            <table class="floatTables" style="border:1px solid #CCCCCC;">
                <tr><td colspan="2"><center><b>General Information</b></center></td></tr>
                <tr>
                    <td valign="top" class="name">hostname</td>
                    <td valign="top" class="value">
                        <input type='text' value="{{host.hostname}}" />
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="name">OS</td>
                    <td valign="top"class="value">
                        <input type='text' value="{{host.os}}" />
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <br />

    </script>



</div>