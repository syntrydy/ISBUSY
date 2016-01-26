package com.example.gasmyr.isbusy.utils;

/**
 * Created by gasmyr on 01/06/16.
 */
public class PhoneNumber {
    private String number;

    public PhoneNumber(String number) {
        if(number.length()>=6){
            this.number = cleanNumber(number);
        }
        else {
            this.number = "#########";
        }
    }
    private String cleanNumber(String number){
        try {
            return number.substring(number.length()-9,number.length());
        }
        catch (Exception e){
            return "#########";
        }
    }

    public boolean isMtn(){
       return (number.startsWith("67") || number.startsWith("6544") || number.startsWith("6506")
               || number.startsWith("6509") || number.startsWith("6523") || number.startsWith("656"));
    }
    public boolean isOrange(){
        return (number.startsWith("69"));
    }
    public boolean isNexttel(){
        return (number.startsWith("66"));
    }
}
