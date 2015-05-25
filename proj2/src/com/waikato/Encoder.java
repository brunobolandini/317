package com.waikato;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by bruno on 14/04/15.
 */
public class Encoder extends ArrayList {
    public static String encoder(String word) {
        //System.out.println(word);
        ArrayList<Record> dictionary = new ArrayList<Record>();


        for (int i = 0; i < word.length(); i++) { //for every letter
            String substring, nextSubstring;
            int index = -2;
            substring = String.valueOf(word.charAt(i));
            //System.out.print("Prefix " + i + " " + substring + ". ");
            if (i + 1 < word.length()) nextSubstring = substring + String.valueOf(word.charAt(i + 1));
            else nextSubstring = "";
            //System.out.print("Next substring " + nextSubstring + ". \n");
            for(int j=0; j<dictionary.size(); j++) {

                if(dictionary.get(j).equalsSub(substring)){
                    index = j;
                    substring=nextSubstring;
                    j=0;
                    i++;
                    if(i+1>=word.length()) {
                        break;
                    }
                    else {
                        nextSubstring = nextSubstring+String.valueOf(word.charAt(i + 1));
                    }

                //if(i==word.length() ) break;
                }

            }
            if(index == -2){
                Record rec = new Record(substring);
                rec.setReference(0);
                dictionary.add(rec);
                //System.out.println("Not found record: "+substring+". Dictionary added "+rec+". Data: "+substring);

            }
            else  {
                Record rec = new Record(substring);
                rec.setReference(index+1);
                dictionary.add(rec);
                //System.out.println("Not found next substring: "+nextSubstring+ ". Dictionary added "+rec+". Data: "+substring);
            }
        }
        return dictionary.toString();
    }
}
