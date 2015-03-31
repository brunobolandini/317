package com.waikato;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by lucas on 29/03/15.
 */
public class Polyphase {
    private PriorityQueue pqueue = null;
    private int runsize, numfiles, outputFile = 1, offSet = 0,totalRuns=0;
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
        while ((line=bufferedReader.readLine())!=null) {
            //System.out.println(pqueue+"\n");
            String elementWriter = pqueue.getFirst().getElement();
            fileWriter.write(elementWriter+"\n");

            String elementNew = line;
            if (elementNew.compareTo(elementWriter)<0){
                pqueue.addToQueueForNextRun(new QueueElement(elementNew,outputFile));
            }else {
                pqueue.addToRootHeap(new QueueElement(elementNew, outputFile));
            }if (pqueue.getHeapSize()==0){
                fileWriter.write("#DUMMY----\n");
                fileWriter.close();
                totalRuns++;
                runs[outputFile]++;
                if (outputFile==numfiles-1){
                    outputFile = 1;
                }else {
                    outputFile++;
                }
                fileWriter = new FileWriter("temp"+outputFile,true);
                pqueue.reHeap(0);
            }
        }
        int lastHeapSize = pqueue.getHeapSize();
        while(pqueue.getHeapSize()>0) {
            fileWriter.write(pqueue.removeFirst().getElement()+"\n");
        }
        fileWriter.close();
        runs[outputFile]++;
        totalRuns++;
        if (lastHeapSize < runsize){
            if (outputFile==numfiles-1){
                outputFile = 1;
            }else {
                outputFile++;
            }
            fileWriter = new FileWriter("temp"+outputFile);
            pqueue.reHeap(lastHeapSize);
            while (pqueue.getHeapSize()>0){
                fileWriter.write(pqueue.removeFirst().getElement()+"\n");
            }
            fileWriter.close();
            runs[outputFile]++;
            totalRuns++;
        }
        System.out.println(pqueue);
        System.out.println(pqueue.getHeapSize());
        pqueue.reHeap(0);
        System.out.println(pqueue);
        System.out.println(pqueue.getHeapSize());
        fileWriter.close();
        fileWriter = new FileWriter("temp"+numfiles,true);
        fileWriter.close();
        merge();
    }

    private void merge(){
        ArrayList<File> files = new ArrayList<File>();

        for (int i=1;i<numfiles+1;i++){
            File file = new File("temp"+i);
            files.add(file);
        }
        Merge m = new Merge(files,outputfilename );
        //System.out.println(files.get(3).length());
        try {
            m.mergeFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
