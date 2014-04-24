<script>

    $(document).ready(function() {
        try{
        <g:set var="theme" value="${edu.hawaii.its.dcmd.inf.User.findByUsername(sec.username().toString()).themeVal}"/>
        }
        catch(err){
            if((${theme}.equals(null)){
            ${theme} = 1
            }
        }
    });
</script>

%{--<h1>theme val set to: ${theme}</h1>--}%

<g:if test="${theme == 1}" >
    <r:require modules="grape_theme,application_theme,menu,asset"/>
    <jq:resources/>
    <jqgrid:resources />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</g:if>

<g:elseif test="${theme == 2}" >
    <r:require modules='darkness_theme,app2_theme,asset' />
    <jq:resources/>
    <jqgrid:resources />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</g:elseif>

<g:elseif test="${theme == 3}" >
    <r:require module="pepper_theme"/>
    <r:require modules='application_theme,menu, asset' />
    <jq:resources/>
    <jqgrid:resources />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</g:elseif>

<g:elseif test="${theme == 4}" >
    <r:require module="dotluv_theme"/>
    <r:require modules='application_theme,menu, asset' />
    <jq:resources/>
    <jqgrid:resources />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</g:elseif>
<g:else>
    <r:require modules='grape_theme,application_theme,menu, asset' />
    <jq:resources/>
    <jqgrid:resources />
    <jqui:resources />
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
</g:else>
