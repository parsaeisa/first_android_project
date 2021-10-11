package com.example.firstproject.Converters;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

public class StringToArray {

    @TypeConverter
    public String ArrayToString (ArrayList<String> input)
    {
        String converted = "" ;
        try {
            for (String in :
                    input) {
                converted = converted + "," + in;
            }
        } catch (Exception e){

        }

        return  converted ;
    }

    @TypeConverter
    public ArrayList<String> StringToArray (String input)
    {
        ArrayList<String> converted = new ArrayList<>();
        for (String s : input.split(",")){
            if(! s.equals(""))
                converted.add(s);
        }

        return  converted ;
    }
}
