package com.waikato;

/**
 * Created by bruno on 21/03/15.
 */
public class PriorityQueue {
    QueueElement[] pqueue;
    int heap_size;
    heap minHeapMethods;

    public PriorityQueue(int size_of_array) {
        pqueue = new QueueElement[size_of_array];
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

    public void addToQueueForNextRun() {
        QueueElement element;
    }

    public void unknown(QueueElement element){
        pqueue[0] = pqueue[heap_size - 1];
        heap_size--;
        minHeapMethods.DownHeap(pqueue, heap_size);
        pqueue[heap_size] = element;
    }

    public void addElement(int element) {
        minHeapMethods.AddElement(element, pqueue, heap_size);
        heap_size++;
    }

    public void reHeap(int start) {
        for (int i = 0; i < pqueue.length; i++) {
            minHeapMethods.AddElement(pqueue[i], pqueue, i);
            heap_size++;
        }
    }

    public int getHeapSize() {
        return heap_size;

    }
}
