package com.example.demo;

import java.sql.Date;


public class today {
	
    public static Date get() {
        long miliseconds = System.currentTimeMillis();
        Date date = new Date(miliseconds);
        return date;
    }
    
}