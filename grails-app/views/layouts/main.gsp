<!DOCTYPE html>
<html>
<head>


    <title><g:layoutTitle default="Grails" /></title>
    <g:javascript library="application" />
    <jq:resources/>

    <jqgrid:resources />
    <jqui:resources themeCss="${resource(dir:'css/grape-theme',file:'jquery-ui-1.8.15.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <r:require modules='application_theme,menu, asset' />

<script>
    $(document).ready(function() {
        try{
        <g:set var="themeVal" value="${edu.hawaii.its.dcmd.inf.User.findByUsername(sec.username().toString()).themeVal}"/>
        }
        catch(err){
            if((${themeVal}.equals(null)){
            ${themeVal} = 1
            }
        }
    });
</script>

   <g:if test="${themeVal == 1}" >
    <r:require module="grape_theme"/>
    </g:if>
    <g:elseif test="${themeVal == 2}" >
    <r:require module="darkness_theme"/>
    </g:elseif>
    <g:elseif test="${themeVal == 3}" >
        <r:require module="dotluv_theme"/>
    </g:elseif>
    <g:else>
        <r:require module="grape_theme"/>
    </g:else>


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

            <h2 class="section_title"></h2>

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
