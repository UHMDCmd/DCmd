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

<script language="javascript" type="text/javascript" src="../js/mustache.js">
    </script>

<section id="under_bar"></section>

<section id="secondary_bar">

<div class="user">

    <span id="fl_menu">
        <!--Script for the sticky navigation/tool bar at header -->
        <script type="text/javascript">
            $(document).ready(function() {

                var $window = $(window),
                        $stickyEl = $('#secondary_bar'),
                        $underEl = $('#under_bar'),
                        $stickyButtons = $('#fl_menu');

                var elTop = $stickyEl.offset().top;
                var buttonTop = $stickyButtons.offset().top;

                $window.scroll(function() {
                    var windowTop = $window.scrollTop();
                    $stickyEl.toggleClass('sticky', windowTop > elTop);
                    $underEl.toggleClass('sticky', windowTop > elTop);

                });

                $("#searchDialog").dialog({
                    autoOpen: false,
                    width: '80%',
                    position: ['center', 80],
                    modal: true
                });

              //  alert("cookie set to: "+ document.cookie);
            /*    if (document.cookie == "bc=false"){
                    $('#breadCrumb').css("display","none");
                }*/

            });
        </script>
        <script type="text/javascript">
            $(function() {
                $( "#elog_dialog" ).dialog({
                    autoOpen: false,
                    width: '600px',
                    show: {
                        effect: "blind",
                        duration: 1000
                    },
                    hide: {
                        effect: "blind",
                        duration: 1000
                    }
                });

                function getElog() {

                    jQuery.ajax({
                        async: false,
                        url: "/its/dcmd/${pageType}/elog",
                        data: {id:$('#id').val()},
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        success: function(data) {
                            document.getElementById('elog_dialog').innerHTML = data.message;
                            $( "#elog_dialog").dialog( "open" );
                        },
                        error: function () { alert('Error retrieving elog info'); }
                    });

                }

                $( "#elog_opener" ).click(function() {
                    getElog();
                });
            });

        </script>

        <sec:ifLoggedIn>


        <div class="label" style="position:static">MENU</div>
        <div class="menu" style="z-index:1">

            <div class="menu_item" onclick="location.href='/its/dcmd/host/list';" style="cursor:pointer">Hosts</div>
            <div class="menu_item" onclick="location.href='/its/dcmd/application/list';" style="cursor:pointer">Applications</div>
            <div class="menu_item" onclick="location.href='/its/dcmd/service/list';" style="cursor:pointer">Services</div>

 %{--           <div class="menu_item" onclick="location.href='/its/dcmd/asset/list';" style="cursor:pointer">All Assets</div>--}%
            <div class="menu_item" onclick="location.href='/its/dcmd/physicalServer/list';" style="cursor:pointer">Physical Servers</div>

            <div class="menu_item" onclick="location.href='/its/dcmd/person/list';" style="cursor:pointer">Staff
                <a href="/its/dcmd/person/list" class="submenu_item">All Staff</a>
                <a href="/its/dcmd/host/supportList" class="submenu_item">Host Support</a>
                <a href="/its/dcmd/application/supportList" class="submenu_item">Application Support</a>
            </div>
            <div class="menu_item" onclick="location.href='/its/dcmd/cluster/list';" style="cursor:pointer">VM Clusters</div>
            <div class="menu_item" onclick="location.href='/its/dcmd/host/reports';" style="cursor:pointer">Reports</div>
%{--            <div class="menu_item" onclick="location.href='/its/dcmd/power/list';" style="cursor:pointer">Power</div> --}%
            <div class="menu_item" onclick="location.href='/its/dcmd/audit/list';" style="cursor:pointer">Audit Log</div>
            <div class="menu_item" onclick="location.href='/its/dcmd/user/list';" style="cursor:pointer">DCmd Access</div>

        </div>


        </sec:ifLoggedIn>
    </span>

    <!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
</div>

<div class="actionsbar_container">
    <sec:ifLoggedIn>
        <article class="actionsbar">
        <g:if test="${action == 'home'}">
            <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
        </g:if>
        <g:if test="${action == 'supportList'}">
            <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
        </g:if>
        <g:if test="${action == 'reports'}">
            <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
        </g:if>

        <g:if test="${action == 'list'}">
            <g:form controller="${pageType}">

                <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
                <div class="actions_divider"></div>
                <g:if test="${pageType != 'user' && pageType != 'tier' && pageType != 'rack' && pageType != 'asset'}">
                    <g:actionSubmit controller="${pageType}" class="create_button" action="create" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </g:if>
            </g:form>
        </g:if>

        <g:if test="${action == 'show'}">
            <g:form controller="${pageType}">
                <g:hiddenField name="id" value="${params.id}" />
                <g:hiddenField name="version" value="${params.version}" />

                <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
                <div class="actions_divider"></div>

                <g:if test="${pageType != 'denied' && assetType != 'Rack' && pageType != 'asset' && pageType != 'cluster'}">
                    <g:actionSubmit controller="${pageType}" class="create_button" action="create" value="${message(code: 'default.button.create.label', default: 'Create')}" />

                    <g:actionSubmit controller="${pageType}" class="edit_button" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" />

                    <g:actionSubmit class="delete_button" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />

%{--                    <g:if test="${(pageType == 'asset')||(pageType == 'host')||(pageType == 'application')}" >
                    <g:actionSubmit controller="${pageType}" class="clone_button" action="clone" value="${message(code: 'default.button.clone.label', default: 'Clone')}"
                                    onclick="return confirm('${message(code: 'default.button.clone.confirm.message', default: 'Clone This Entity?')}');"/>
                    </g:if>
--}%
                </g:if>
                <g:if test="${pageType == 'asset'}">
%{--                    <g:actionSubmit controller="physicalServer" class="create_button" action="create" value="${message(code: 'default.button.create.label', default: 'Create')}" /> --}%

                    <g:actionSubmit class="delete_button" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                                    onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                </g:if>
            </g:form>
        </g:if>

        <g:if test="${action == 'edit'}">
        <script>
           ${session.setAttribute("modeType","edit mode")}
        </script>

            <a class = "home_button" href="${createLink(uri: '/person/home')}">DCMD Home</a>
            <div class="actions_divider"></div>

            <g:actionSubmit class="update_button"
                            action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}" />

            <g:actionSubmit class="delete_button"
                            action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </g:if>

        <g:elseif test="${action == 'create'}">
            <script>
                ${session.setAttribute("modeType","create mode")}
            </script>
            <a class = "home_button" href="${createLink(uri: '/person/home')}">DCmd Home</a>

            <div class="actions_divider"></div>
            <g:actionSubmit
                    class="saveEdit_button"
                    action="saveEdit"
                    value="${message(code: 'default.button.saveEdit.label', default: 'Save & Edit')}" />
            <g:actionSubmit
                    class="saveList_button"
                    action="saveList"
                    value="${message(code: 'default.button.saveList.label', default: 'Save & List')}" />

            <g:actionSubmit
                    class="saveCreate_button"
                    action="saveCreate"
                    value="${message(code: 'default.button.saveCreate.label', default: 'Save & Create')}" />
            <g:actionSubmit
                    class="discard_button"
                    action="discard"
                    value="${message(code: 'default.button.saveCreate.label', default: 'Discard')}" />
        </g:elseif>
        <g:else>
            %{--clear the session variable--}%
            <script>
                ${session.removeAttribute("modeType")}
            </script>
        </g:else>
    </article>
    </sec:ifLoggedIn>
%{--
    <g:if test="${action == 'show'}">
        <g:if test="${pageType == 'host'}">
        <article class="actionsbar">
            <div id="elog_dialog" title="eLog information"></div>


            <a href="#" class="elog_opener" id="elog_opener" >eLog Report</a>
        </article>
        </g:if>
    </g:if>
--}%
<g:if test="${params.username != 'anonymousUser'}">

    <article class="actionsbar" style="float:right">

        <sec:ifLoggedIn>
        <a class="logout_button"  href="${createLink(uri: '/logout')}">Logout (<sec:username/>)</a>

        </sec:ifLoggedIn>

        <sec:ifNotLoggedIn>
            <a class="logout_button"  href="${createLink(uri: '/person/home')}">Login</a>

        </sec:ifNotLoggedIn>

    </article>

%{--<g:if test="${(action == "show")||(action == "list")}">
  %{--  <article class = "actionsbar" style="float:right; margin-right: 0">
    <a class ="breadcrumbTrash_button" id ="bctrash"></a>
    </article>--}%
    <g:if test="${pageType != 'login'}">
    %{--
<article class="actionsbar" style="float:right; margin-right: 0">

<a class ="settings_button" id ="settings" title="Settings" href="/its/dcmd/uisettings"></a>
    </article>

--}%
    <article class="actionsbar" style="float:right; margin-right: 0">

        %{--BreadCrumbs button--}%
        <a class="breadcrumb_ button" id = "bcButton" title="On/Off">Breadcrumbs</a>



            <script type="text/javascript">

                $("#bcButton").click(function(){
                    $('#breadCrumb').fadeToggle("slow", "linear");
                });
             </script>

        <div class="actions_divider"></div>
        <a class ="breadcrumbTrash_button" id ="bctrash" title="Trash Trail"></a>

        <script type="text/javascript">
            $("#bctrash").click(function(){
                $.ajax({url:'/its/dcmd/person/resetBreadCrumbs'});
                $("#crumbTrail").remove();

                $('#breadCrumb').append("");
            });

        </script>



        </article>
        <g:if test="${action != 'home'}">

            <article class="actionsbar" style="float:right; margin-right: 0">

            <a id='searchBtn' href="#">Search</a>
            <script type="text/javascript">

                $("#searchBtn").click(function(){
                    document.getElementById("searchDialog").style.visibility = 'visible';
                    $("#searchDialog").dialog("open");
                    document.getElementById("searchBox").innerHTML = "";
                });
            </script>

            <div id='searchDialog' style="visibility:hidden; width:80%; min-height:300px;">
            <g:render template="../person/quickSearch"/>
            </div>
        </article>
            </g:if>




    </g:if>

%{-- </g:if>

    %{--<g:if test="${(action == "show")||(action == "list")}">



      </g:if>--}%

</g:if>

</div>



<!--floating menu script-->
<script>



    //config
    $float_speed=1500; //milliseconds
    $float_easing="easeOutQuint";
    $menu_fade_speed=1; //milliseconds
    $closed_menu_opacity=0.75;

    //cache vars
    $fl_menu=$("#fl_menu");
    $fl_menu_menu=$("#fl_menu .menu");
    $fl_menu_label=$("#fl_menu .label");

    $(window).load(function() {
        menuPosition=$('#fl_menu').position().top;
        FloatMenu();
        $fl_menu.hover(
                function(){ //mouse over
                    $fl_menu_label.fadeTo($menu_fade_speed, 1);
                    $fl_menu_menu.fadeIn($menu_fade_speed);
                },
                function(){ //mouse out
                    $fl_menu_label.fadeTo($menu_fade_speed, $closed_menu_opacity);
                    $fl_menu_menu.fadeOut($menu_fade_speed);
                }
        );
    });

    $(window).scroll(function () {
        FloatMenu();
    });

    function FloatMenu(){

    }
</script>


</section>
