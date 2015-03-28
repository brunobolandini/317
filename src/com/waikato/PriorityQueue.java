package com.waikato;

import java.util.Arrays;

/**
 * Created by bruno on 22/03/15.
 */
public class PriorityQueue {
    QueueElement[] pqueue;
    int heap_size;
    heap minHeapMethods;

    public PriorityQueue(int size_of_array) {
        this.pqueue = new QueueElement[size_of_array];
        minHeapMethods = new heap();
        heap_size = 0;
    }

    public QueueElement getFirst() {
        return pqueue[0];
    }

    public QueueElement removeFirst() {
        QueueElement first = pqueue[0];
        pqueue[0] = pqueue[heap_size - 1];
        heap_size--;
        minHeapMethods.DownHeap(pqueue, heap_size);
        return first;
    }

    public void addToRootHeap(QueueElement element) {
        pqueue[0] = element;
        minHeapMethods.DownHeap(pqueue, heap_size);
    }

    public void addToQueueForNextRun(QueueElement element) {
        pqueue[0] = pqueue[heap_size - 1];
        heap_size--;
        minHeapMethods.DownHeap(pqueue, heap_size);
        pqueue[heap_size] = element;
    }

    public void addElement(QueueElement element) {
        minHeapMethods.AddElement(element, pqueue, heap_size);
        heap_size++;
    }

    public void reHeap(int start) {
        for (int i = start; i < pqueue.length; i++) {
            minHeapMethods.AddElement(pqueue[i], pqueue, i);
            heap_size++;
        }
    }

    public int getHeapSize() {
        return heap_size;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i=0;i<pqueue.length;i++){
            str = str+" "+pqueue[i].getElement();
        }
        return str;
    }

    public void printQueue() {
        for (int i = 0; i < pqueue.length; i++) {
            if(i==0){
                //for(int j=0; j<heap_size/2;j++)
                    System.out.print("         ");
                System.out.print(pqueue[i].getElement());
            }
            if(i==1){
                for(int j=0;j<2;j++){
                    //for(int l=0; l<heap_size/3;l++)
                        System.out.print("    ");
                    System.out.print(pqueue[i].getElement()+"  ");
                    i++;
                }
                System.out.print("\n");
            }
            if(i==3){
                for(int j=0;j<4;j++){
                    //for(int l=0; l+1<heap_size/3;l++)
                        System.out.print("  ");
                    System.out.print(pqueue[i].getElement());
                    i++;
                }
            }
            System.out.print("\n");
            //System.out.println(pqueue[i].getElement());
        }
    }
}
