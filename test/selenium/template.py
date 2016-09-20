import sys
from selenium import webdriver
from selenium.webdriver.common.keys import Keys

def main(args): 
    try:
        # login
        print "from Python, here is your password:", args[0] 
        print "from Python, here is your user:", args[1] 

        # do selenium stuffs here
        driver = webdriver.PhantomJS()
        driver.set_window_size(1120,550)
        driver.get("dcm51.its.hawaii.edu:8080/its/dcmd")
        print "now at", driver.current_url
        
        
        # make assertion heere
        assert True

        # exit
        print __file__, 'passed...'
        sys.exit(0)
    except AssertionError:
        
        # exit
        print __file__, 'failed...'
        sys.exit(1) 

if __name__ == "__main__":
    main(sys.argv)

