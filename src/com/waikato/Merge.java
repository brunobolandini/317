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

    String outputname;
    ArrayList<File> files;
    PriorityQueue selectword;
    int passes;
    //ArrayList<Integer> emptyfiles;


    Merge(ArrayList<File> files, String outputname) {
        this.files = files;

        this.outputname = outputname;
        this.selectword = new PriorityQueue(files.size());
        this.passes=0;
    }

    public int fileOutput() {
        int outputfile = -1;
        for (File file : files) {
            if (file.length() == 0) {
                outputfile = files.indexOf(file);
                passes++;
            }
        }
        if(outputfile==-1) {
            System.out.println("ERROR: Could not set an output file.");
        }
        else System.out.println("Outputfile is file of index: " + outputfile + ".");

        return outputfile;
    }
    public int numberOfEmptyFiles(){
        int i=0;
        for (File file : files) {
            if (file.length() == 0) {
                i++;
            }
        }
        return i;
    }

    public boolean allFilesEmptyButOne() {
        int i = 0;
        for (File file : files) {
            if (file.length() == 0) {
                i++;
                //emptyfiles.add(files.indexOf(file));
            }
            if (i == files.size() - 1) {
                System.out.println("All files empty but one.");
                return true;
            }

        }
        System.out.println("Number of empty files: " + i + " ---- Number of not empty files: " + (files.size() - i));
        return false;
    }

    private String readElement(File f) throws IOException {
        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String text = bufferedReader.readLine();
        bufferedReader.close();
        fileReader.close();
        return text;

    }

    private String readLastElement(File f) throws IOException {
        FileReader fileReader = new FileReader(f);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String lastElement = "";
        while (bufferedReader.readLine() != null) {
            lastElement = bufferedReader.readLine();
        }
        bufferedReader.close();
        fileReader.close();
        return lastElement;
    }

    private void removeLine(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            linha = br.readLine();
            ArrayList<String> salvar = new ArrayList();
            while (linha != null) {
                salvar.add(linha);
                linha = br.readLine();
            }
            br.close();
            fr.close();
            FileWriter fw2 = new FileWriter(file, true);
            fw2.close();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < salvar.size(); i++) {
                bw.write(salvar.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();
        } catch (IOException ex) {

        }
    }


    public void writeElement(String text, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String t = text;
        bufferedWriter.append(t);
        bufferedWriter.close();
        fileWriter.close();
    }

    public void mergeFiles() throws IOException {
        int currentoutput = -1;
        int last = files.size() - 1;
        while (selectword.getHeapSize() < files.size() - 1) { //while heap is not full, fill i
            for (File file : files) {
                if (readElement(file) != null) {
                    QueueElement e = new QueueElement(readElement(file), files.indexOf(file));
                    selectword.addElement(e);
                    System.out.println(readElement(file) + " file index " + files.indexOf(file) + " in tree.");
                    removeLine(file);

                } else {
                    System.out.println("First outputfile is: index " + files.indexOf(file) + ".");
                    currentoutput = files.indexOf(file);
                    selectword.pqueue[last] = null;
                    last--;
                }
            }
        }
        int input_file;

        while (!allFilesEmptyButOne()) {
            while (selectword.getHeapSize() > 0) {
                QueueElement element_writ = selectword.getFirst();
                String element_written = element_writ.getElement();
                input_file = element_writ.getFile();
                writeElement(element_written + "\n", files.get(currentoutput));
                String temp = readElement(files.get(input_file));
                removeLine(files.get(input_file));
                if (temp != null) {
                    QueueElement element_new = new QueueElement(temp, input_file);
                    if (element_new.getElement().compareTo(element_written) < 0)
                        selectword.addToQueueForNextRun(new QueueElement(element_new.getElement(), input_file));
                    else
                        selectword.addToRootHeap(new QueueElement(element_new.getElement(), input_file));
                } else {
                    selectword.addToQueueForNextRun(new QueueElement(null, currentoutput));
                }
            }
            if(currentoutput!=fileOutput()){
                currentoutput=fileOutput();
            }
            if(numberOfEmptyFiles()==files.size()-1) continue;
            else selectword.reHeap(0);
            System.err.println("Passes to achieve the final sort  " + passes);

        }
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