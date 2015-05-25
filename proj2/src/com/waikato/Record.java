package com.waikato;

/**
 * Created by bruno on 15/04/15.
 */

public class Record {
    private int reference;
    private String data;
    int index=0;

    Record(String d){
        this.reference =-2;
        this.data=d;
        this.index++;
    }
    public String getData(){
        return data;
    }
    public char getLastChar(){
        if(data.equals("")) return 'Ø';
        return data.charAt(data.length()-1);
    }
    public boolean containsSub(String sub){
        if(data.contains(sub)) return true;
        return false;
    }
    public boolean equalsSub(String sub){
        if(data.equals(sub)) return  true;
        return false;
    }
    public int samePrefix(String sub){
        int r=0, i=0;
        while(sub.charAt(i)==data.charAt(i)) {
            r++;
            i++;
        }
        return r;
    }
    public int getReference(){
        return reference;
    }
    public void setReference(int i){
        reference =i;
    }
    public String toString(){
        if(reference==0&&data=="Ø") return "";
        return ""+reference+"|"+getLastChar()+"";
    }

}
