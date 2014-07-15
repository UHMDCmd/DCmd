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

<g:javascript library="application" />
    <jq:resources/>

    <jqgrid:resources />
    <jqui:resources themeCss="${resource(dir:'css/le-frog',file:'jquery-ui-1.8.14.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    %{--<r:require modules='application_theme, ui, menu, asset' />--}%
    %{--<g:render template="../themes"/>--}%
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

    <title><g:message code="default.dcmd.label" /></title>
    %{--<meta name="layout" content="main" />--}%

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


    <link type="text/css" href="../js/InfoViz/DCmd_map/css/base.css" rel="stylesheet" />
    <link type="text/css" href="../js/InfoViz/DCmd_map/css/Spacetree.css" rel="stylesheet" />

    <!--[if IE]><script language="javascript" type="text/javascript" src="js/InfoViz/Extras/excanvas.js"></script><![endif]-->

    <!-- JIT Library File -->
    <script language="javascript" type="text/javascript" src="../js/InfoViz/jit.js"></script>
    <script language="javascript" type="text/javascript" src="../js/InfoViz/jit-yc.js"></script>

    <!-- Example File -->
    <script language="javascript" type="text/javascript" src="../js/InfoViz/DCmd_map/Tree/DCmdTree.js"></script>


    <style type="text/css" media="screen">

    #nav {
        margin-top:20px;
        margin-left:30px;
        width:228px;
        float:left;

    }
    .homePagePanel * {
        margin:0px;
    }
    .homePagePanel .panelBody ul {
        list-style-type:none;
        margin-bottom:10px;
    }
    .homePagePanel .panelBody h1 {
        text-transform:uppercase;
        font-size:1.1em;
        margin-bottom:10px;
    }
    .homePagePanel .panelBody {
        background: url(../images/leftnav_midstretch.png) repeat-y top;
        margin:0px;
        padding:15px;
    }
    .homePagePanel .panelBtm {
        background: url(../images/leftnav_btm.png) no-repeat top;
        height:20px;
        margin:0px;
    }

    .homePagePanel .panelTop {
        background: url(../images/leftnav_top.png) no-repeat top;
        height:11px;
        margin:0px;
    }
    h2 {
        margin-top:15px;
        margin-bottom:15px;
        font-size:1.2em;
    }
    h1 {
        font-size: 1.5em;
        color: #e0ffff;
    }
    #pageBody {
        margin-left:150px;
        margin-right:20px;
    }
    </style>




</head>
<body onload="init();">
<div id="container">

    <header id="header">
        <hgroup>

            <h1 class="site_title">

                <a href="/its/dcmd"><img src="${resource(dir:'images/dcmd-theme',file:'uh-logo-white.png')}" border="0" style="width:32%"> DCmd <font size='-1'><sub>1.3.1</sub></font></a>
            </h1>

            <h2 class="section_title">Dashboard</h2>

        </hgroup>
    </header>
</div>

<g:render template="../show_secondary" model="[pageType:'none', objectId:0, action:'home']" />


<div id="pageBody">
    <h1>Welcome to DCmd - Data Center Meta Data</h1>
    <sec:ifLoggedIn>

        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="padding-left: 25px">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>

                Please use the menu in the upper-left to navigate DCmd.  Below is a diagram outlining the major entities of DCmd:
                <br><br>


            </h4>
            <br>

            <div id="viz_container" style="left:20px">

                <div id="left-container">

                    <div class="text">
                        <h4>
                            DCmd Entity Map
                        </h4>


                        <b>Click</b> on a node to select it and see the Attributes.<br /><br />
                        You can <b>select the tree orientation</b> by changing the select box in the right column.<br /><br />
                        You can <b>change the selection mode</b> from <em>Normal</em> selection (i.e. center the selected node) to
                        <em>Set as Root</em>.<br /><br />
                        <b>Drag and Drop the canvas</b> to do some panning.<br /><br />
                        Leaves color depend on the number of children they actually have.

                    </div>

                    <div id="id-list"></div>

                </div>

                <div id="center-container">
                    <div id="infovis"></div>
                </div>

                <div id="right-container">

                    <h4>Tree Orientation</h4>
                    <table>
                        <tr>
                            <td>
                                <label for="r-left">Left </label>
                            </td>
                            <td>
                                <input type="radio" id="r-left" name="orientation" checked="checked" value="left" />
                            </td>
                            <br />
                        </tr>
                        <tr>
                            <td>
                                <label for="r-top">Top </label>
                            </td>
                            <td>
                                <input type="radio" id="r-top" name="orientation"  value="top" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="r-bottom">Bottom </label>
                            </td>
                            <td>
                                <input type="radio" id="r-bottom" name="orientation" value="bottom" />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label for="r-right">Right </label>
                            </td>
                            <td>
                                <input type="radio" id="r-right" name="orientation" value="right" />
                            </td>
                        </tr>
                    </table>

                    <h4>Selection Mode</h4>
                    <table>
                        <tr>
                            <td>
                                <label for="s-normal">Normal </label>
                            </td>
                            <td>
                                <input type="radio" id="s-normal" name="selection" value="normal" />
                            </td>
                            <br />
                        </tr>
                        <tr>
                            <td>
                                <label for="s-root">Set as Root </label>
                            </td>
                            <td>
                                <input type="radio" id="s-root" name="selection" checked="checked" value="root" />
                            </td>
                        </tr>

                    </table>

                    <div id="log"></div>
                    <br><br>
                    <dl>
                        <span>Legend:</span>
                        <br> <br>
                        <dt>

                        <div id="label1">
                            Entity
                        </div>
                    </dt>
                        <br>

                        <dt>
                        <div id="label2">
                            Sub Entity
                        </div>
                    </dt>
                        <br>

                        <dt>      <div id="label3">
                        Entity Attribute
                    </div></dt>
                        <br>
                    </dl>
                    <style>
                    span{
                        color:#405B5B;
                        text-align: center;
                        text-decoration: none;
                        font: 14px / 100% Arial, Helvetica, Bolder, sans-serif;
                        padding: 1em 3.5em 1em;
                    }
                    dl
                    {
                        width: 150px;
                        background: #fff;
                        border: 3px solid #2B3844;
                        padding: 5px 15px;
                        margin-left: 8px
                    }

                    dd
                    {
                        background-color: #aa6644;
                        width: 80px ;
                        color:#000;
                    }
                    #label1
                    {
                        background-color: #FFFF77;
                        width: 80px ;
                        color:#000;
                    }
                    #label2
                    {
                        background-color: #CCAAAA;
                        width: 80px ;
                        color:#000;
                    }
                    #label3
                    {
                        background-color: #BBAAAA;
                        width: 80px ;
                        color:#000;
                    }

                    dt
                    {
                        font-style:oblique;
                        display: inline;

                    }
                    </style>
                </div>


        </section>
    </sec:ifLoggedIn>
    <sec:ifNotLoggedIn>
        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="padding-left: 25px">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>
                Please <b><a href="https://login.its.hawaii.edu/cas/login?url=http://localhost:8080/its/dcmd/">Login</a></b> to use DCmd.
            </h4>
        </section>
    </sec:ifNotLoggedIn>
</div>

</div>

</body>


</html>
