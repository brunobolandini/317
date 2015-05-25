package com.waikato;

import com.waikato.PriorityQueue;
import com.waikato.QueueElement;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by bruno on 30/03/15.
 */
public class Merge {
    String tempdir;
    String outputname;
    ArrayList<File> files;
    PriorityQueue selectword;
    private int outputIndex = 0;


    Merge(ArrayList<File> files, String tempdir, String outputname){
        this.files=files;
        this.tempdir=tempdir;
        this.outputname=outputname;
        this.selectword=new PriorityQueue(files.size()-1);
    }

    public int fileOutput(){
        for(File file : files){
            if(file.length()==0) {
                System.out.println("Outputfile is file of index: " + files.indexOf(file)+".");
                return files.indexOf(file);
            }
        }
        System.out.println("Could not set output file.");
        return -1;
    }

    public boolean allFilesEmptyButOne(){
        int i=0;
        for(File file:files){
            if(i==files.size()-1) {
                System.out.println("All files empty but one.");
                return true;
            }
            if(file.length()==0)
                i++;
        }
        System.out.println("Number of empty files: "+i+" ---- Number of not empty files: "+(files.size()-i));
        return false;
    }
    private String readElement(File f) throws IOException {
        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String text= bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();
        return text;

    }
    private String removeLine(File file){
        String string ="";
        try{
            Scanner fileScanner = new Scanner(file);
            string = fileScanner.nextLine();
            FileWriter fileStream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fileStream);
            while(fileScanner.hasNextLine()) {
                String next = fileScanner.nextLine();
                if (next.equals("\n")){
                    out.newLine();
                }else {
                    out.write(next);
                    out.newLine();
                }
            }
            out.close();
            return string;
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return string;
    }
    /*public void fillQueue() throws IOException {
        int output_file=-1;
        int input_file;
        for(File f:files){
            if(fileOutput()!=files.indexOf(f)){ // WHEN IS OUTPUT AND WHEN IT BECAME EMPTY???
                BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
                selectword.addElement(new QueueElement(readElement(f),files.indexOf(f)));
                while (selectword.getHeapSize() > 0) {
                    QueueElement element_writ = selectword.getFirst();
                    String element_written = element_writ.getElement();
                    input_file = element_writ.getFilePointer();
                    FileWriter fw = new FileWriter(f);
                    fw.write(element_written+"\n");
                    String temp = readElement(f);
                    if (temp==null){
                        selectword.addToQueueForNextRun(new QueueElement(null, output_file));
                    }
                    else {
                        String element_new = temp;
                        if(element_new.compareTo(element_written)>0) {
                            selectword.addToQueueForNextRun(new QueueElement(element_new, input_file));
                        }
                        else {
                            selectword.addToRootHeap (new QueueElement(element_new, input_file));
                        }
                    }
                }
            }
            else
                output_file = files.indexOf(f);
        }
    }*/
    public void writeElement(String text, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String t = text.concat("\n");
        bufferedWriter.append(t);
        bufferedWriter.close();
        fileWriter.close();
    }

    /*public void mergeFiles() throws IOException {
        while ()
    }*/

    /*public void mergeFiles() throws IOException{
        int currentoutput=-1;
        for(File file:files){
            if(readElement(file)!=null){
                QueueElement e = new QueueElement(readElement(file),files.indexOf(file));
                selectword.addElement(e);
                System.out.println(readElement(file)+" file index "+files.indexOf(file));
                removeLine(file);

            }
            else {
                System.out.println("First outputfile is: index " + files.indexOf(file) + ".");
                currentoutput=files.indexOf(file);
            }
        }
        while(!allFilesEmptyButOne()){
//            System.out.println(selectword);
            writeElement(selectword.getFirst().getElement(),files.get(currentoutput));
            System.out.println("Writing in the file: "+currentoutput+" WORD: "+selectword.getFirst().getElement());
            QueueElement newElement
                    = new QueueElement(readElement(files.get(selectword.getFirst().getFile())),
                    selectword.getFirst().getFile());
            //selectword.removeFirst();
            removeLine(files.get(newElement.getFile()));
            selectword.addToRootHeap(newElement);
            System.out.println("***********");
        }

    }*/
    public void mergeFiles() throws IOException {
        outputIndex = fileOutput();
        int inputIndex = outputIndex+1;
        FileWriter fileWriter = new FileWriter("temp" + inputIndex+".txt");
        int i=0;
        while (i < files.size()) {
            if (i==outputIndex){
                i++;
            }else {
                QueueElement element = new QueueElement(readElement(files.get(i)), i);
                selectword.addElement(element);
                i++;
            }
        }
        /*while (selectword.getHeapSize()>0){
            QueueElement elementWriter = selectword.getFirst();
            String element = elementWriter.getElement();
            inputIndex = elementWriter.getFile();
            fileWriter.write(elementWriter.getElement()+"\n");

        }*/
        inputIndex = selectword.getFirst().getFile()+1;
        String temp = removeLine(new File("temp"+inputIndex+".txt"));
        System.out.println(temp);
        System.out.println(selectword);
    }
}
/*
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by bruno on 30/03/15.

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<File> files = new ArrayList<File>();
        File f1 = new File("temp1.txt");
        File f2 = new File("temp2.txt");
        File f3 = new File("temp3.txt");
        File f4 = new File("temp4.txt");
        File f5 = new File("temp5.txt");
        File f6 = new File("temp6.txt");
        File f7 = new File("temp7.txt");
        files.add(f1);
        files.add(f2);
        files.add(f3);
        files.add(f4);
        files.add(f5);
        files.add(f6);
        files.add(f7);
        Merge m = new Merge(files,"tempdir","outputname" );
        m.mergeFiles();
    }
}
*/