package com.example.gasmyr.isbusy.src;

import android.os.Build;

/**
 * Created by gasmyr on 25/05/15.
 */
public class Licence {
    private  String name;
    private  String key;
    public Licence(String name) {
        this.name = name;
        this.key = generateKey();
    }
    public String generateKey(){
      String key=Build.SERIAL;
        key.concat(Build.ID);
        return key;
    }


}
