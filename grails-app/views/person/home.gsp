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
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Staff')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
    <jqDT:resources jqueryUi="true" type="js" />
    <script type="text/javascript">


        $(document).ready(function() {

            //When page loads...
            $(".tab_content").hide(); //Hide all content
            $("ul.tabs li:first").addClass("active").show(); //Activate first tab
            $(".tab_content:first").show(); //Show first tab content

            //On Click Event
            $("ul.tabs li").click(function() {

                $("ul.tabs li").removeClass("active"); //Remove any "active" class
                $(this).addClass("active"); //Add "active" class to selected tab
                $(".tab_content").hide(); //Hide all tab content

                var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
                $(activeTab).fadeIn(); //Fade in the active ID content
                return false;
            });

        });
    </script>

    <title><g:message code="default.dcmd.label" /></title>
    %{--<meta name="layout" content="main" />--}%

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />



    <g:applyLayout name="breadcrumb_bar">
        <g:include controller="person" action="setBreadCrumbForCurrentItem" params="[pageType: 'person']"/>
    </g:applyLayout>




</head>
<body onload="init();">

<g:render template="../show_secondary" model="[pageType:'none', objectId:0, action:'home']" />
<g:render template="../breadcrumbs" model="[pageType:'person', action:'list']"/>


<div style="padding-top:25px;"></div>
<div id="pageBody" style="text-align:center; font-family: 'LucidaGrande', Verdana; " >

    <sec:ifLoggedIn>

        <h1 style="color:white;">Welcome to DCmd - Data Center Meta Data</h1>
        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="font-weight:normal; text-decoration:none;">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>
                <g:render template="quickSearch" />

        %{--
                <br><br>
                Please use the menu in the upper-left to navigate DCmd or choose from an entity below:
                <br><br>
                <div class="mainpage_entities"><h3>Physical Entities</h3>
                <ul>
                <li><a href="../asset">Assets</a> - Any and every physical asset.</li>
                <li><a href="../physicalServer">Physical Servers</a> - Physical hardware of any Server (a type of Asset).</li>
                <li><a href="../rack">Racks</a> - Racks (aka Cabinets) where physical servers and switches are located.</li>
                <li><a href="../location">Locations</a> - Physical Locations where Assets can be located.</li>
                </ul>
                <h3>OS Entities</h3>
                <ul>
                <li><a href="../host">Hosts</a> - Virtual Hosts (OS) running on Physical Servers or Virtual Cluster.</li>
                <li><a href="../cluster">Clusters</a> - Clusters of Virtual Hosts (for VMware).</li>
                <li><a href="../tier">Software Instances</a> - Instances of software running on Hosts, providing Services.</li>
                </ul>
                <h3>Virtual Entities</h3>
                <ul>
                <li><a href="../application">Applications</a> - Sets of Services being provided to end users.</li>
                <li><a href="../service">Services</a> - Individual Services being offered to users.</li>
                </ul>
                <h3>Other Entities/Pages</h3>
                <ul>
                <li><a href="../person">Staff</a> - Staff members who can be assigned to support other entities.</li>
                <li><a href="../audit">Audit Log</a> - Chronological log of actions performed on DCmd.</li>
                <li><a href="../user">DCmd Access</a> - Administrative control over access to DCmd.</li>
                </ul>
                                                              <br>
                  </div>
            </h4>
            <br>
                         --}%
    </sec:ifLoggedIn>
    <sec:ifNotLoggedIn>
        <center>
            <h1>Welcome to DCmd - Data Center Meta Data</h1>

        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="padding-left: 25px">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>
                Please <b><a href="${createLink(uri: '/user/home')}">Login</a></b> to use DCmd.
            </h4>
        </section></center>

    </sec:ifNotLoggedIn>
</div>

</div>

</body>


</html>
