package com.example.demo;

import java.sql.Date;


public class DeleteEnddata {
	
    public static Date today() {
        long miliseconds = System.currentTimeMillis();
        Date date = new Date(miliseconds);
        return date;
    }
    
}