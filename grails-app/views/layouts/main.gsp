<!DOCTYPE html>
<html>
<head>


    <title><g:layoutTitle default="Grails" /></title>
    <g:javascript library="application" />
    <jq:resources/>

    <jqgrid:resources />
    %{--<jqui:resources themeCss="${resource(dir:'css/grape-theme',file:'jquery-ui-1.8.15.custom.css')}" />--}%
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <r:require modules='boiler_plate, asset' />

    <script>
        $(document).ready(function() {
            try{
                <g:set var="themeVal" value="${(edu.hawaii.its.dcmd.inf.User.findByUsername(sec.username().toString())).userSettings.themeVal}"/>
                <g:set var="background" value="${(edu.hawaii.its.dcmd.inf.User.findByUsername(sec.username().toString())).userSettings.background}"/>
            }
            catch(err){
             console.log("themeVal error caught")
                        }
        });
    </script>
%{--grid and color scheme--}%
    <g:if test="${themeVal == 1}" >
        <r:require module="grape_theme"/>
    </g:if>
    <g:elseif test="${themeVal == 2}" >
        <r:require module="darkness_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == 3}" >
        <r:require module="lightness_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == 4}" >
        <r:require module="dot_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == 5}" >
        <r:require module="kermit_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == 6}" >
        <r:require module="mint_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == null}">
        <r:require module="grape_theme"/>
    </g:elseif>
%{--grid and color scheme--}%

    <g:if test="${background == 1}" >
    <r:require module="layout_basic"/>
    </g:if>
    <g:elseif test="${background == 2}" >
    <r:require module="layout_blue"/>
    </g:elseif>
    <g:elseif test="${background == 3}" >
    <r:require module="layout_black"/>
    </g:elseif>
    <g:elseif test="${background == 4}" >
    <r:require module="layout_plant"/>
    </g:elseif>
    <g:elseif test="${background == 5}" >
    <r:require module="layout_orange"/>
    </g:elseif>
    <g:elseif test="${background == null}">
    <r:require module="layout_basic"/>
    </g:elseif>

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

                <a href="/its/dcmd"><img src="${resource(dir:'images/dcmd-theme',file:'uh-logo-white.png')}" border="0" style="width:32%"> DCmd <font size='-1'><sub>1.5.0</sub></font></a>
            </h1>
            <g:set var="currentMode" value="${session.getAttribute("modeType")}"/>

            <g:if test="${currentMode == 'edit mode'}">
                <h2 class="section_title" style="color:#798898"> [ <span class="blink_me" style="color:#86aeb2">${currentMode}</span> ] </h2>
            </g:if>
            <g:elseif test="${currentMode == 'create mode'}">
                <h2 class="section_title" style="color:#798898"> [ <span class="blink_me" style="color:#c04469">${currentMode}</span> ] </h2>
            </g:elseif>
            <g:else>
                <h2 class="section_title"></h2>
            </g:else>

            <style>
            .blink_me {
                -webkit-animation-name: blinker;
                -webkit-animation-duration: 2s;
                -webkit-animation-timing-function: linear;
                -webkit-animation-iteration-count: infinite;

                -moz-animation-name: blinker;
                -moz-animation-duration: 1s;
                -moz-animation-timing-function: linear;
                -moz-animation-iteration-count: infinite;

                animation-name: blinker;
                animation-duration: 1s;
                animation-timing-function: linear;
                animation-iteration-count: infinite;
            }

            @-moz-keyframes blinker {
                0% { opacity: 1.0; }
                50% { opacity: 0.0; }
                100% { opacity: 1.0; }
            }

            @-webkit-keyframes blinker {
                0% { opacity: 1.0; }
                50% { opacity: 0.0; }
                100% { opacity: 1.0; }
            }

            @keyframes blinker {
                0% { opacity: 1.0; }
                50% { opacity: 0.0; }
                100% { opacity: 1.0; }
            }
            </style>


        </hgroup>
    </header>

    <g:layoutBody />

    <div class="push"></div>


</div>
<footer></footer>

</body>
</html>
