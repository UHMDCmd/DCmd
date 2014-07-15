/*
 * Copyright (c) 2014 University of Hawaii
 *
 * This file is part of DataCenter metadata (DCmd) project.
 *
 * DCmd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DCmd is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCmd.  It is contained in the DCmd release as LICENSE.txt
 * If not, see <http://www.gnu.org/licenses/>.
 */

modules = {
 grape_theme {
     resource url: '/css/grape-theme/jquery-ui-1.8.15.custom.css'
     resource url: '/css/grape-theme/BreadCrumb.css'

    }

    darkness_theme {
        resource url: '/css/darkness_theme/jquery-ui-1.10.3.custom.css'
        resource url: '/css/darkness_theme/BreadCrumb.css'

//        resource url:'css/darkness_theme/layout_theme/layout.css'
//        resource url:'css/darkness_theme/layout_theme/ie.css'

        resource url: 'css/darkness_theme/slide_menu/slide_menu.css'
        resource url: '/js/jquery.easing.1.3.js'

    }

    pepper_theme {
        resource url: '/css/pepper_theme/jquery-ui-1.10.3.custom.css'
        resource url: '/css/pepper_theme/BreadCrumb.css'

//        resource url: '/css/admin_theme/layout.css'
//        resource url: '/css/admin_theme/ie.css'
    }

    dotluv_theme{

   resource url: '/css/dotluv_theme/jquery-ui-1.10.3.custom.css'
    resource url: '/css/dotluv_theme/BreadCrumb.css'

//        resource url: '/css/admin_theme/layout.css'
//        resource url: '/css/admin_theme/ie.css'
}

}