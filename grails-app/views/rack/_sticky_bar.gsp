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