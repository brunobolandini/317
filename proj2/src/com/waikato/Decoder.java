package com.waikato;

import java.util.ArrayList;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

/**
 * Created by bruno on 16/04/15.
 */

public class Decoder {
    public static String Decode(String dictionary) {
        ArrayList<Record> builtDictionary = buildDictionary(dictionary);
        return buildSentence(builtDictionary).replace("Ξ"," ");
    }

    public static void main (String args[]){
        //Scanner inputString = new Scanner(System.in);
        //String s = inputString.nextLine();
        //s=s.replace(" ","Ξ");
        String in[] = args[1].split(",");
        if (!in[1].equals("lz78")){
            System.out.println("this file is not a .lz78, please choose a .lz78 extension file");
            System.exit(1);
        }
        String txt = loadData(args[0]);

        try{
            throw new Exception("noExtention");

        String r = Decoder.Decode(txt);
        System.out.println(r);
        writeFile(r);

    }

    public static String loadData(String fileName){
        String txt = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(
                fileName))) {
            String CurrentLine = br.readLine();
            txt = CurrentLine+"\n";
            while ((CurrentLine = br.readLine()) != null) {
                txt += CurrentLine+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }
    public static void writeFile(String file){

        try {
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            writer.print(file);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Record> buildDictionary(String dictionary){
        ArrayList<Record> result = new ArrayList<Record>();
        //System.out.println(dictionary);
        String all[] = dictionary.split("\n");

        for(int i=0; i< all.length; i++){
            String part[];
            part=all[i].split("\\|");
            Record a = new Record(part[part.length-1]);
            a.setReference(Integer.parseInt(part[0]));
            result.add(a);
        }
        return result;
    }
    public static String buildSentence(ArrayList<Record> dictionary){
        String result = "";
        for(Record record: dictionary){
            result = result.concat(buildWordNR(record, dictionary));
        }
        return result;
    }

    public static String buildWord(Record record, ArrayList<Record> dic){
        String result= "";
        if(record.getReference()==0) result=result.concat(record.getData());
        else {
            result=result.concat(record.getData());
            buildWord(dic.get(record.getReference()), dic);
        }
        return result;
    }

    public static String buildWordNR(Record record, ArrayList<Record> dic){
        String result= "";
        //System.out.println("BUILDING RECORD "+record);
        if(record.getReference()==0) {
            //System.out.println("Add to string: "+record.getData()+". No reference found. ");
            return record.getData();
        }
        else{
            Stack<String> stack=new Stack();
            while(record.getReference()!=0) {
                //System.out.println("Pushed to stack "+record.getData()+" Next record phrase: " + record.getReference()+". ");
                stack.push(record.getData());
                record=dic.get(record.getReference()-1);
            }
            stack.push(record.getData());
            //System.out.println("STACK: "+stack.toString());
            while(!stack.empty()){
                if(stack.peek().equals("Ø")) break;
                result=result.concat(stack.pop());
            }
        }
        //System.out.println("String returned: "+result);
        return result;
    }
    public static void printDictionary(ArrayList<Record> d){
        System.out.println("#phrase               data              output");
        int i=0;
        for (Record r:d){
            if(i>9) System.out.println(i+"                     "+r.getData()+"               "+r.toString());
            else System.out.println(i+"                      "+r.getData()+"                "+r.toString());
            i++;
        }
    }
}
