<%@  page import="edu.hawaii.its.dcmd.inf.GeneralService" %>
<%
    def genService = grailsApplication.classLoader.loadClass('edu.hawaii.its.dcmd.inf.GeneralService').newInstance()
%>

<r:require modules='select2' />

<script>
    $(document).ready(function() { $("#statusSelect${idNum}").select2({
//        placeholder: 'Please Select...',
        maximumInputLength: 20,
        width:150,
        createSearchChoice:function(term, data) { if ($(data).filter(function() { return this.text.localeCompare(term)===0; }).length===0) {return {id:term, text:term};} },
        initSelection: function(element, callback) {
            if("${objectInstance?.status?.id}" != "") {
                var data = {id: "${objectInstance?.status?.id}", text: "${objectInstance?.status?.abbreviation}"};
            }
            else
                var data = {id:"1", text:"Available"};

            callback(data);
        },
        data: [${genService.listStatusAsSelect()}]
    }).select2('val', '0');

});

</script>


        <input type="hidden" name="statusSelect${idNum}" id="statusSelect${idNum}" />
