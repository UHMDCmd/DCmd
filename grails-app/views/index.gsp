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

<sec:ifLoggedIn>
        <meta http-equiv="refresh" content="0; person/home">


    </sec:ifLoggedIn>
    <jq:resources/>

    <jqgrid:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <r:require modules='boiler_plate, asset' />
    <r:layoutResources/>



    <title><g:message code="default.dcmd.label" /></title>
    %{--<meta name="layout" content="main" />--}%

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


</head>
<body>
<div id="container">

    <header id="header">
        <hgroup>

            <h1 class="site_title">

                <a href="/its/dcmd"><img src="${resource(dir:'images/dcmd-theme',file:'uh-logo-white.gif')}" border="0" style="width:32%"> DCmd <font size='-1'><sub>1.6.0</sub></font></a>
            </h1>

            <h2 class="section_title"></h2>

        </hgroup>
    </header>
</div>

<g:render template="../show_secondary" model="[pageType:'login', objectId:0, action:'home']" />


<div id="pageBody">


        <center>
            <h1 style="color:white;">Welcome to DCmd - Data Center Meta Data</h1>

        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="padding-left: 25px">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>
                Please <b><a href="${createLink(uri: '/person/home')}">Login</a></b> to use DCmd.
            </h4>
        </section></center>

</div>

</body>


</html>
