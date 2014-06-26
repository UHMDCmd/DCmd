<html>
<head>
    <sec:ifLoggedIn>
        <meta http-equiv="refresh" content="0; person/home">


    </sec:ifLoggedIn>
    <jq:resources/>

    <jqgrid:resources />
    <jqui:resources themeCss="${resource(dir:'css/le-frog',file:'jquery-ui-1.8.14.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <r:require modules='application_theme,ui, menu, asset' />
    %{--<g:render template="themes"/>--}%
    <r:layoutResources/>



    <title><g:message code="default.dcmd.label" /></title>
    %{--<meta name="layout" content="main" />--}%

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


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
        background: url(images/leftnav_midstretch.png) repeat-y top;
        margin:0px;
        padding:15px;
    }
    .homePagePanel .panelBtm {
        background: url(images/leftnav_btm.png) no-repeat top;
        height:20px;
        margin:0px;
    }

    .homePagePanel .panelTop {
        background: url(images/leftnav_top.png) no-repeat top;
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
<body>
<div id="container">

    <header id="header">
        <hgroup>

            <h1 class="site_title">

                <a href="/its/dcmd"><img src="${resource(dir:'images/dcmd-theme',file:'uh-logo-white.png')}" border="0" style="width:32%"> DCmd <font size='-1'><sub>1.5.0</sub></font></a>
            </h1>

            <h2 class="section_title"></h2>

        </hgroup>
    </header>
</div>

<g:render template="../show_secondary" model="[pageType:'login', objectId:0, action:'home']" />


<div id="pageBody">


        <center>
            <h1>Welcome to DCmd - Data Center Meta Data</h1>

        <section id="main" class="pagebody">
            <h4 class="mainpage_info" style="padding-left: 25px">DCmd is the repository of the meta-data used to manage all aspects of the UH Data Center.
                <br><br>
                Please <b><a href="${createLink(uri: '/person/home')}">Login</a></b> to use DCmd.
            </h4>
        </section></center>

</div>

</body>


</html>
