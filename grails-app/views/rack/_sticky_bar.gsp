<!--Script for the sticky navigation/tool bar at header -->
<script type="text/javascript">
    $(document).ready(function() {
        var $window = $(window),
                $stickyEl = $('#stickyDiv'),
                $stickyButtons = $('#rackButtons');

        var elTop = $stickyEl.offset().top-38;
        var buttonTop = $stickyButtons.offset().top-70;

        $window.scroll(function() {
            var windowTop = $window.scrollTop();
            if(windowTop > elTop) {
                var $myWidth = $stickyButtons.width();
            }

                $stickyEl.toggleClass('sticky', windowTop > elTop);
                $stickyButtons.toggleClass('sticky', windowTop > elTop);

            if(windowTop > elTop) {
                $stickyEl.width($myWidth);
                $stickyButtons.width($myWidth);
            }
        });
    });
</script>