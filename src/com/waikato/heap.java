package com.waikato;

/**
 * Created by bruno on 21/03/15.
 */
public class heap {

    private int Left (int element_index) {
        return ((element_index*2)+1);
    }

    private int Right (int element_index) {
        return ((element_index*2)+2);
    }

    private int Parent (int element_index) {
        return ((element_index-1) / 2);
    }

    private void Swap (int element_index_x, int element_index_y, int [] heap_array) { //swap index x -> index y
        int element_temp = heap_array[element_index_x];
        heap_array[element_index_x] = heap_array[element_index_y];
        heap_array[element_index_y] = element_temp;
    }

    private int SwapWithChild (int element_index, int [] heap_array, int heap_size) {
        int element_left_index = Left (element_index);
        int element_right_index = Right (element_index);
        int element_smallest_index = element_index;
        if (element_right_index < heap_size) {
            if (heap_array[element_left_index] < heap_array [element_right_index]) {
                element_smallest_index = element_left_index;
            }
            else {
                element_smallest_index = element_right_index;
            }
            if (heap_array[element_index] < heap_array[element_smallest_index]) {
                element_smallest_index = element_index;
            }
        }
        else if (element_left_index < heap_size) {
            if(heap_array[element_index] > heap_array[element_left_index]) {
                element_smallest_index = element_left_index
            }
        }
        if (heap_array[element_index] > heap_array[element_smallest_index]) {
            Swap(element_index, element_smallest_index, heap_array);
        }
        return element_smallest_index;
    }

    public void RemoveRoot (int[] heap_array, int heap_size) { // That’s the remove class
        heap_array[0] = heap_array[heap_size-1]; // last element from the heap and put it as the root
        int swapping_index = 0; //which one we are swapping
        int swapped_index = SwapWithChild( 0, heap_array, heap_size-1); // which one we swapped
        while (swapped_index != swapping_index) {
            swapping_index = swapped_index;
            swapped_index = SwapWithChild( swapped_index, heap_array, heap_size-1);
        }
    }

    private int SwapWithParent (int index, int[] heap_array) {
        if (index < 1)
            return index;
        int parent_index = Parent(index);
        if (heap_array[index] < heap_array[parent_index]) {
            Swap (index, parent_index, heap_array);
            return parent_index;
        }
        else {
            return index;
        }
    }
    public void AddElement(QueueElement new_element, QueueElement[] heap_array, int heap_size) {
        heap_array[heap_size] = new_element;
        int swapping_index = heap_size;
        int swapped_index = SwapWithParent(swapping_index, heap_array);
        while (swapping_index != swapped_index) {
            swapping_index = swapped_index;
            swapped_index = SwapWithParent(swapped_index, heap_array);
        }
    }
    public void DownHeap (QueueElement[] heap_array, int heap_size) {
        int swapping_index = 0;
        int swapped_index = SwapWithChild(0, heap_array, heap_size);
        while (swapped_index != swapping_index) {
            swapping_index = swapped_index;
            swapped_index = SwapWithChild(swapped_index, heap_array, heap_size);
        }
    }
}
