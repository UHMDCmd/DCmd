<html>
<head>
    <meta content="main" name="layout" />
    <title>%{--
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

<g:message code="default.dcmd.label"/></title>
    <jqDT:resources jqueryUi="true" type="js" />
</head>
<body>

<div id="container">
    <g:render template="../show_secondary" model="[pageType:'denied', objectId:0, action:'show']" />

</div>

<div class="pageBodyNoMargin">
    <article class="module width_full">
        <div class="module_content">


            <center><h3>Permission Denied</h3>
            <br><br>
            <g:if test="${params.username == 'anonymousUser'}">
                <h3>User not permitted access to DCmd.  Contact karsin@hawaii.edu to gain access.</h3>
            </g:if>
            <g:if test="${params.username != 'anonymousUser'}">
                <h3>User <b>${params.username}</b> not permitted to view this page.  Contact karsin@hawaii.edu to gain access.</h3>
            </g:if>

            </center>
        </div> <!-- end pagebody -->


    </article>
</div>

</body>
</html>

