package com.main.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

/**
 * Created by romain on 05/11/16.
 */
public enum  ColorLine {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    BROWN("brown"),
    ORANGE("orange");

    private String name = "";

    ColorLine(String name){
        this.name =name;
    }

    public String toString(){
        return this.name;
    }

    public static ArrayList<ColorLine> getRandom(){
        ArrayList<ColorLine> l = new ArrayList<ColorLine>(EnumSet.allOf(ColorLine.class));
        Collections.shuffle(l);
        return l;
    }

    public static ArrayList<ColorLine> getValues(){
        return new ArrayList<ColorLine>(EnumSet.allOf(ColorLine.class));
    }
}
