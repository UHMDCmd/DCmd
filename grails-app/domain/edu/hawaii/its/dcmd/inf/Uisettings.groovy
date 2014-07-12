package edu.hawaii.its.dcmd.inf

class Uisettings {

    int themeVal
    int header
    int font
    int background

    static constraints = {
        themeVal(nullable: true)
        header(nullable: true)
        font(nullable: true)
        background(nullable: false)
    }

   void init(){
            themeVal = 1
            header = 1
            font = 1
            background = 1

    }

}
