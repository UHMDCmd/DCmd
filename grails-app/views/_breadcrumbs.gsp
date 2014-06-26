<g:if test="${(action == 'show')||(action == 'list') || (action == 'create') || (action == 'edit')||(action == 'index')}">

    <script type="text/javascript">
        $(document).ready(function(){
            if (document.cookie == "BreadCrumb=false"){
                $('#breadCrumb').css("display","none");
            }

         });
    </script>


    <script type="text/javascript">

        var labelString = "${session.getValue("bcLabels")}";
        labelString = labelString.replace('[','');
        labelString = labelString.replace(']','');
        var labels = labelString.split(',');

        var linkString = "${session.getValue("bcLinks")}";
        linkString = linkString.replace('[','');
        linkString = linkString.replace(']','');
        var extensions = linkString.split(',');


        var crumbTrail = {
            crumbTrail: [
                {
                    title : labels[0], link: extensions[0], text: labels[0]
                },
                {
                    title : labels[1], link: extensions[1], text: labels[1]
                },
                {
                    title : labels[2], link: extensions[2], text: labels[2]
                },
                {
                    title : labels[3], link: extensions[3], text: labels[3]
                },
                {
                    title : labels[4], link: extensions[4], text: labels[4]
                },
                {
                    title : labels[5], link: extensions[5], text: labels[5]
                },
                {
                    title : labels[6], link: extensions[6], text: labels[6]
                },
                {
                    title : labels[7], link: extensions[7], text: labels[7]
                },
                {
                    title : labels[8], link: extensions[8], text: labels[8]
                },
                {
                    title : labels[9], link: extensions[9], text: labels[9]
                }
            ]};


        var template = "<ul>{{#crumbTrail}}" +
                "<li><a href= \"{{link}}\">{{text}}</a></li>" +
                "{{/crumbTrail}}</ul>";

        $(document).ready(function() {
            $("#crumbTrail").html(Mustache.to_html(template, crumbTrail));
        });

    </script>
</g:if>


<div id="breadCrumb" class="breadCrumb module" style="display:block">
        <div id="crumbTrail"></div>

</div>


    <script type="text/javascript">

    $(window).on('unload', function(){

   //     reset the cookie to accept a new value
      //  document.cookie = null;

        var isVisible = $('#breadCrumb').is(':visible');
        var isHidden = $('#breadCrumb').is(':hidden');

        if (isVisible){
            document.cookie = "BreadCrumb=true; path=/its/dcmd/";
        }
        else if (isHidden){
            document.cookie = "BreadCrumb=false; path=/its/dcmd/";
        }
      });


</script>

