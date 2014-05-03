package edu.hawaii.its.dcmd.inf


    class DeviceImage {
        byte[] imageFile

        static constraints = {
            //limit upload file soe to 2MB
            imageFile maxSize:  1024 * 1024 * 2
        }
    }
