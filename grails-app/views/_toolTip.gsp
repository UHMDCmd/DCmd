%{--This template implements a newer version of JqueryUI--}%
%{--The variable creates a no conflict with the already installed version--}%

<!--


<script>
    var jQuery_1_9_1 = $.noConflict(true);
     jQuery_1_9_1(function() {
        jQuery_1_9_1( document ).tooltip({
            position: {
                my: "center bottom-20",
                at: "center top",
                using: function( position, feedback ) {
                    jQuery_1_9_1( this ).css( position );
                    jQuery_1_9_1( "<div>" )
                            .addClass( "arrow" )
                            .addClass( feedback.vertical )
                            .addClass( feedback.horizontal )
                            .appendTo( this );
                }
            }
        });
    });
</script>
<style>

.ui-tooltip, .arrow:after {
    background: #577744;
    border: 2px solid white;
    width: 200px
}
.ui-tooltip {
    padding: 10px 20px;
    color: white;
    border-radius: 20px;
    font: bold 14px "Helvetica Neue", Sans-Serif;
    /*text-transform: uppercase;*/
    box-shadow: 0 0 7px black;
    position:absolute;
}
.arrow {
    width: 70px;
    height: 16px;
    overflow: hidden;
    position: absolute;
    left: 50%;
    margin-left: -35px;
    bottom: -16px;
}
.arrow.top {
    top: -16px;
    bottom: auto;
}
.arrow.left {
    left: 20%;
}
.arrow:after {
    content: "";
    position: absolute;
    left: 20px;
    top: -20px;
    width: 25px;
    height: 25px;
    box-shadow: 6px 5px 9px -9px black;
    -webkit-transform: rotate(45deg);
    -moz-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    -o-transform: rotate(45deg);
    tranform: rotate(45deg);
}
.arrow.top:after {
    bottom: -20px;
    top: -100px;
}
</style>
    -->