package com.waikato;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lucas on 29/03/15.
 */
public class Polyphase {
    private PriorityQueue pqueue = null;
    private int runsize, numfiles, outputFile;
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

    public void putOnFile() throws IOException{

        String text = new String();
        int offSet = fillQueue();
        System.out.println("get heap size "+pqueue.getHeapSize()+" pqueue "+pqueue);
        pqueue.removeFirst();
        System.out.println("tamanho da current line "+currentLine.size());
        String[] line = currentLine.toArray(new String[currentLine.size()]);

        wordToQueue(line,offSet);

        pqueue.reHeap(0);
        System.out.println(pqueue);
        /*String[] input = {"the","Fulton","evidence","that","any","irregularities","took"};
        int i=0;
        while (pqueue.getHeapSize()<runsize ){
            QueueElement element = new QueueElement(input[i].toLowerCase(),outputFile);
            pqueue.addElement(element);
            i++;
        }
        System.out.println(pqueue);
        pqueue.removeFirst();*/

    }

    private int fillQueue() {

        int offSet = 0;
        while (pqueue.getHeapSize()<runsize){
            currentLine = new ArrayList<>();
            offSet = 0;
            String[] line = readInput();
            currentLine.addAll(Arrays.asList(line));
            offSet = lineToQueue(line,offSet);
        }
        System.out.println(pqueue.getHeapSize()+"----"+runsize);
        //System.out.println(pqueue);
        return offSet;
    }

    public int lineToQueue(String[] line,int offSet){
        System.out.println(line.length);
        while (pqueue.getHeapSize()<runsize && offSet<line.length){
            QueueElement element = new QueueElement(line[offSet].toLowerCase(),outputFile);
            pqueue.addElement(element);
            //System.out.println("first carai: "+pqueue.getFirst().getElement());
            offSet++;
        }
        return offSet;
    }

    public int wordToQueue(String[] line, int offSet) throws IOException{

        if (pqueue.getHeapSize()==runsize)
            return -1;
        if (offSet< line.length){
            QueueElement element = new QueueElement(line[offSet].toLowerCase(),outputFile);
            pqueue.addElement(element);
            return offSet;
        }else {
            System.out.println("put next line");
           return fillQueue();
        }
    }
}
