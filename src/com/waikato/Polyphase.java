package com.waikato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lucas on 29/03/15.
 */
public class Polyphase {
    private PriorityQueue pqueue = null;
    private int runsize, numfiles, outputFile = 1, offSet = 0;
    private String tempdir, outputfilename, inputfilename;
    private BufferedReader bufferedReader = null;
    private ArrayList<String> currentLine = new ArrayList<String>();
    private int[] runs;

    public Polyphase(PriorityQueue pqueue, int runsize, int numfiles, String tempdir, String outputfilename, String inputfilename) throws IOException {
        this.pqueue = pqueue;
        this.runsize = runsize;
        this.numfiles = numfiles;
        this.tempdir = tempdir;
        this.outputfilename = outputfilename;
        this.inputfilename = inputfilename;
        this.bufferedReader = new BufferedReader(new FileReader("iniInput.txt"));
        this.runs = new int[runsize];
    }

    public String readInput() {

        try {
            String line = bufferedReader.readLine();
            return line;
        } catch (IOException e) {
            System.out.println("EOF");
            return null;
        }
    }

    public void createRun() throws IOException {
        FileWriter fileWriter = new FileWriter("temp" + outputFile);
        fillQueue();
        QueueElement element = new QueueElement("", outputFile);
        //System.out.println(pqueue+"\n");
        String line = "";
        while (pqueue.getHeapSize() > 0) {
            System.out.println(pqueue+"\n");
            String elementWriter = pqueue.getFirst().getElement();
            fileWriter.write(elementWriter+"\n");

            String elementNew = bufferedReader.readLine();
            if (elementNew.compareTo(elementWriter)<0){
                pqueue.addToQueueForNextRun(new QueueElement(elementNew,outputFile));
            }else {
                pqueue.addToRootHeap(new QueueElement(elementNew, outputFile));
            }
        }

        pqueue.reHeap(0);
        System.out.println(pqueue.getHeapSize());
        fileWriter.close();
    }

    private void fillQueue() {

        while (pqueue.getHeapSize() < runsize) {
            String line = readInput();
            QueueElement element = new QueueElement(line.toLowerCase(), outputFile);
            pqueue.addElement(element);
        }
    }

    public void lineToQueue(String line) {
        //System.out.println(line);

    }

}
