package com.waikato;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lucas on 29/03/15.
 */
public class Polyphase {
    private PriorityQueue pqueue = null;
    private int runsize, numfiles, outputFile,fileCounter = 1,offSet=0;
    private String tempdir, outputfilename, inputfilename;
    private BufferedReader bufferedReader = null;
    private ArrayList<String> currentLine = new ArrayList<String>();

    public Polyphase(PriorityQueue pqueue, int runsize, int numfiles, int outputFile, String tempdir, String outputfilename, String inputfilename) throws IOException {
        this.pqueue = pqueue;
        this.runsize = runsize;
        this.numfiles = numfiles;
        this.outputFile = outputFile;
        this.tempdir = tempdir;
        this.outputfilename = outputfilename;
        this.inputfilename = inputfilename;
        this.bufferedReader =  new BufferedReader(new FileReader("src/com/waikato/"+inputfilename));
    }

    public String[] readInput(){
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

    public void createRun() throws IOException{
        FileWriter fw = new FileWriter("temp"+fileCounter);
        offSet = fillQueue();
        QueueElement element = new QueueElement("",fileCounter);
        while(pqueue.getHeapSize()>0){
            System.out.println(pqueue.getHeapSize()+"");
            System.out.println(pqueue);
            element = putOnFile(fw,element);
            //fw.write("");
        }
        //pqueue.reHeap(0);
        System.out.println(pqueue);
        fw.close();
    }
    public QueueElement putOnFile(FileWriter fileWriter,QueueElement element) throws IOException {


        System.out.println("OffSet: "+offSet);

        if (pqueue.getFirst().getElement().compareTo(element.getElement())>=0){
            fileWriter.write(pqueue.getFirst().getElement());
            element = pqueue.getFirst();
            pqueue.removeFirst();
            System.out.println("REMOVED ELEMENT"+ pqueue);
            String[] line = currentLine.toArray(new String[currentLine.size()]);
            wordToQueue(line);
            //pqueue.reHeap(0);

        }else {

            System.out.println("element frozen1: "+pqueue.getFirst().getElement());
            pqueue.addToQueueForNextRun(element);
            //pqueue.reHeap(pqueue.getHeapSize());
        }
        return element;
    }

    public QueueElement putOnFile(FileWriter fileWriter) throws IOException {

        int offSet = fillQueue();
        //System.out.println("get heap size " + pqueue.getHeapSize() + " pqueue " + pqueue);


        pqueue.removeFirst();

        //System.out.println("tamanho da current line " + currentLine.size());

        String[] line = currentLine.toArray(new String[currentLine.size()]);
        wordToQueue(line);
        //System.out.println(pqueue + " " + pqueue.getHeapSize());
        pqueue.addToQueueForNextRun(pqueue.getFirst());
        System.out.println(pqueue +" "+pqueue.getHeapSize());


        QueueElement element = new QueueElement("oi, eu sou o goku",1);
        return element;
    }

    private int fillQueue() {

        int offSet = 0;
        while (pqueue.getHeapSize()<runsize){
            currentLine = new ArrayList<>();
            offSet = 0;
            String[] line = readInput();
            currentLine.addAll(Arrays.asList(line));
            offSet = lineToQueue(line);
        }
        //System.out.println(pqueue);
        return offSet;
    }

    public int lineToQueue(String[] line){
        System.out.println(line.length);
        while (pqueue.getHeapSize()<runsize && offSet<line.length){
            QueueElement element = new QueueElement(line[offSet].toLowerCase(),outputFile);
            pqueue.addElement(element);
            //System.out.println("first carai: "+pqueue.getFirst().getElement());
            offSet++;
        }
        return offSet;
    }

    public int wordToQueue(String[] line) throws IOException{

        if (pqueue.getHeapSize()==runsize) {
            return -1;
        }
        if (offSet< line.length){
            System.out.println("Add word ***"+line[offSet]+"*** to queue.");
            QueueElement element = new QueueElement(line[offSet].toLowerCase(),outputFile);
            pqueue.addElement(element);
            return offSet++;
        }else {
            System.out.println("put next line");
           return fillQueue();
        }
    }
}
