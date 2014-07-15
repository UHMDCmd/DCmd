<!DOCTYPE html>
<html>
<head>


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

<g:layoutTitle default="Grails" /></title>
    <g:javascript library="application" />
    <jq:resources/>

    <jqgrid:resources />
    <jqui:resources themeCss="${resource(dir:'css/le-frog',file:'jquery-ui-1.8.14.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <r:require modules='application_theme,menu, asset' />


   <g:if test="${session.getValue("themeVal") == 1}" >
    <r:require module="grape_theme"/>
    </g:if>

    <g:if test="${session.getValue("themeVal") == 2}" >
    <r:require module="darkness_theme"/>
    </g:if>
    <g:if test="${session.getValue("themeVal") == 3}" >
        <r:require module="pepper_theme"/>
    </g:if>
    <g:if test="${session.getValue("themeVal") == 4}" >
        <r:require module="dotluv_theme"/>
    </g:if>
    <g:else>
        <r:require module="grape_theme"/>
    </g:else>

   %{-- <g:if test="${session.themeVal == 3}" >
        <r:require module="theme3"/>
    </g:if>--}%



    <r:layoutResources/>

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


    <g:layoutHead />
</head>

<body>
<r:layoutResources/>


<div id="container">

    <header id="header">
        <hgroup>

            <h1 class="site_title">

                <a href="/its/dcmd"><img src="${resource(dir:'images/dcmd-theme',file:'uh-logo-white.png')}" border="0" style="width:32%"> DCmd <font size='-1'><sub>1.5.1</sub></font></a>
            </h1>

            <h2 class="section_title">Dashboard</h2>

        </hgroup>
    </header>
    <!-- end of header bar -->
    <%--
            <section id="secondary_bar">

                <div class="user">
                    <p>
                      John Doe
                        <!--  John Doe (<a href="#">3 Messages</a>)-->
                    </p>
                    <!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
                </div>

                <div class="breadcrumbs_container"><%--
                    <article class="breadcrumbs">
                        <a href="${createLink(uri: '/')}">DCMD Home</a>
                        <div class="breadcrumb_divider"></div>
                        <a class="current">Dashboard</a>
                    </article>
                </div>

            </section>
    --%>
    <!-- end of secondary bar -->





    <g:layoutBody />

    <div class="push"></div>
</div>
<!-- End of Container -->
<footer></footer>


</body>
</html>
