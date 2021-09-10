package com.m2i.demomedical.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class LoggingHelper {


    public void log( String text ) throws IOException {
        try{
            FileWriter myWriter = new FileWriter("filename.txt");
            myWriter.write(text );
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}