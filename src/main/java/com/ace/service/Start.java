package com.ace.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Start {
    private static final Logger LOGGER = LogManager.getLogger(Start.class);

    public static void main(String[] args) {

        if(args.length ==0 ) LOGGER.info("please provide json file path");
        else if( args.length > 0 ){
            String path = args[0];
            Path url = Paths.get(path);
            try {
                new GenFile().genTable(url.toUri().toURL());
            }catch (Exception ex){
                ex.printStackTrace();
                LOGGER.error("ERR001 - Provided PATH is wrong");
            }

        }

    }

}
