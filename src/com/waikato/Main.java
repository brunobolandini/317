package com.waikato;
/**
 * Created by bruno on 24/03/15.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/*public class Main {
    void create_intial_runs(String input_file_name, int run_size, int num_files, String tempdir, PriorityQueue pqueue) throws IOException {
        int[] runs = new int [num_files];
        int total_runs = 0;
        FileInputStream stream = new FileInputStream(input_file_name);	DataInputStream in = new DataInputStream (stream);
        int output_file = 0;
        FileWriter fw = new FileWriter((tempdir +" "+output_file+".txt "), true);
        for(int i=0; i<run_size; i++) {
            if(in.available()!=0){
                pqueue.minHeapMethods.AddElement((new QueueElement(Integer.parseInt(in.readLine()), output_file)));
            }
        }
        while(in.available() != 0) {
            int element_written = pqueue.getFirst().getElement();
            fw.write(element_written + "\n");
            int element_new = Integer.parseInt(in.readLine());
            if (element_new < element_written){
                pqueue.addToQueueForNextRun(new QueueElement(element_new, output_file));
            }
            else {
                pqueue.addToRootHeap(new QueueElement(element_new, output_file));
            }
            if(pqueue.getHeapSize()==0) {
                fw.close();
                runs[output_file]++;
                total_runs++;
                output_file = (int) (Math.random() * (num_files-2)); //TO DO BETTER METHOD fibonacci sequence?
                fw = new FileWriter((tempdir+output_file+".txt"), true);
                pqueue.reHeap(0);
            }
            int lastHeapSize = pqueue.getHeapSize();
            while(pqueue.getHeapSize()>0) {
                fw.write(pqueue.removeFirst().getElement()+"\n");
            }
            fw.close();
            runs[output_file]++;
            total_runs++;
            if(lastHeapSize < 7) {
                output_file = (int) Math.random() * (num_files-2);
                fw = new FileWriter((tempdir+output_file+".txt"), true);
                pqueue.reHeap(lastHeapSize);
                while(pqueue.getHeapSize()>0){
                    fw.write(pqueue.removeFirst().getElement()+"\n");
                }
                fw.close();
                runs[output_file]++;
                total_runs++;
            }
            System.out.println("Total initial runs: "+total_runs+"distribution: "+ Arrays.toString(runs));
            in.close();
        }
    }*/

public class Main {


    public static void main(String[] args) {
        int runsize=7, numfiles=2;
        String tempdir="standard_directory", outputfilename="output", inputfilename="BC.txt";
        PriorityQueue pqueue = new PriorityQueue(runsize);
        int outputFile = 0;
        /*for (int i = 0; i < args.length; i++) {
            //System.out.println(args[i]);
            if(args[i].contains("-r"))
                runsize = Integer.parseInt(args[i+1]);
            if(args[i].contains("-k"))
                numfiles = Integer.parseInt(args[i+1]);
            if(args[i].contains("-d"))
                tempdir = args[i+1];
            if(args[i].contains("-o"))
                outputfilename = args[i+1];
        }

        if ((args.length>0)&&(!(args[args.length-2].contains("-")))) {
            inputfilename = args[args.length - 1];
        }*/

        System.out.println("runsize: "+runsize+"\nnumfiles: "+numfiles+"\ntempdir: "+tempdir+"\noutputfilename: "
                +outputfilename+"\ninputfilename: "+inputfilename);

        String text = new String();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/com/waikato/"+inputfilename));
            String currentLine = bufferedReader.readLine();
            int count =0;
            do {
                String[] words = currentLine.split(" ");
                int i=0;

                while (i<words.length && pqueue.getHeapSize()!=runsize){
                    QueueElement element = new QueueElement(words[i].toLowerCase(),outputFile);
                    pqueue.addElement(element);

                    //System.out.println("first carai: "+pqueue.getFirst().getElement());
                    i++;
                }
                /*pqueue.reHeap(0);
                if ("an".compareTo("fulton")>0){
                    System.out.println("vish");
                }else System.out.println("ue");*/
                System.out.println(pqueue);
                pqueue.printQueue();
                currentLine = bufferedReader.readLine();
                count++;
            }while (currentLine!=null && count!=1);
            }catch (IOException e){
            e.printStackTrace();
        }
    }
}
