package com.waikato;
/**
 * Created by bruno on 14/04/15.
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.Scanner;

public class Main {

    public static void main (String args[]){
        System.out.println("Enter a string.");
        Scanner inputString = new Scanner(System.in);
        String s = inputString.nextLine();
        s=s.replace(" ","Ξ");
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.lz78", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.print(file);
        writer.close();
        String r = Encoder.encoder(s);
        System.out.println("DICTIONARY: "+ r);
        System.out.println("DECODED STRING: "+Decoder.Decoder(r));

    }
}
