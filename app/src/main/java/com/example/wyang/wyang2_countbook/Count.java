package com.example.wyang.wyang2_countbook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Count implements Serializable {
    private String name, comment;
    private Date date;
    private Integer currentValue;
    private Integer  initialValue;


    public Count(String name,Integer initial_value,String comment){
        this.name = name;
        this.initialValue = initial_value;
        this.currentValue = initialValue;
        this.comment = comment;
        this.date = new Date();
    }
    public Count(String name, Integer initial_value){
        this(name,initial_value,"");
    }


    public String getName(){
        return this.name;
    }

    public String getDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(date);
        return dateString;
    }

    public Integer getInitialValue(){
        return  initialValue;
    }

    public Integer getCurrentValue(){
        return  currentValue;
    }


    public String getText(){
        return comment;
    }


    public void increaseValue(){
        this.currentValue += 1;
        return;
    }


    public void decreaseValue(){
        this.currentValue -= 1;
        if (this.currentValue >=0){
            return;
        }else{this.currentValue = 0; return;}
    }


    public void resetValue(){
        this.currentValue = initialValue;
        return;
    }


    public void updateName(String name){
        this.date = new Date();
        this.name = name;
        return;
    }


    public void updateValue(Integer initial_value){
        this.date = new Date();
        this.initialValue = initial_value;
        return;
    }

    public void updateTime(Integer value){
        this.date = new Date();
        this.currentValue = value;
        return;
    }


    public void updateComment(String comment){
        this.date = new Date();
        this.comment = comment;
        return;
    }
    @Override
    public String toString(){
        DateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = Format.format(date);
        return "Name: "+name +"   Value: "+ Integer.toString(currentValue) +"   Date: "+ dateString;
    }
}