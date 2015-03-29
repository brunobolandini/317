package com.waikato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by lucas on 29/03/15.
 */
public class Polyphase {
    private PriorityQueue pqueue = null;
    private int runsize, numfiles, outputFile;
    private String tempdir, outputfilename, inputfilename;

    public Polyphase(PriorityQueue pqueue, int runsize, int numfiles, int outputFile, String tempdir, String outputfilename, String inputfilename) {
        this.pqueue = pqueue;
        this.runsize = runsize;
        this.numfiles = numfiles;
        this.outputFile = outputFile;
        this.tempdir = tempdir;
        this.outputfilename = outputfilename;
        this.inputfilename = inputfilename;
    }

    public String[] readInput(BufferedReader bufferedReader){
        String line = "";
        try {
            line = bufferedReader.readLine();
            String[] in= line.split(" ");
            return in;
        } catch (IOException e) {
            System.out.println("EOF");
            return null;
        }
    }

    public void putOnFile(){
        PriorityQueue pqueue = new PriorityQueue(runsize);
        String text = new String();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/com/waikato/"+inputfilename));

            //int count =0;

            fillQueue(bufferedReader);
            /*do {
                String[] words=readInput(bufferedReader);
                int i=0;

                //while (i<words.length && pqueue.getHeapSize()!=runsize){
                while (i<words.length) {
                    QueueElement element = new QueueElement(words[i].toLowerCase(),outputFile);
                    //pqueue.addElement(element);
                    this.fillQueue(words);

                    //System.out.println("first carai: "+pqueue.getFirst().getElement());
                    i++;
                }


                System.out.println(pqueue);
                currentLine = bufferedReader.readLine();
                count++;
            }while (currentLine!=null && count!=1);*/
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean fillQueue(BufferedReader bufferedReader){

        int offSet = 0;

        while (pqueue.getHeapSize()<runsize){
            offSet = 0;
            String[] line =readInput(bufferedReader);
            offSet = lineToQueue(line,offSet);
        }
        System.out.println(pqueue);
        return false;
    }

    public int lineToQueue(String[] line,int offSet){

        while (pqueue.getHeapSize()!=runsize && offSet!=line.length){
            QueueElement element = new QueueElement(line[offSet].toLowerCase(),outputFile);
            pqueue.addElement(element);
            //System.out.println("first carai: "+pqueue.getFirst().getElement());
            offSet++;
        }

        return offSet;
    }
}
