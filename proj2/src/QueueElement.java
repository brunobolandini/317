/**
 * Created by bruno on 22/03/15.
 */
public class QueueElement{
    int element;
    int file_pointer;

    public QueueElement(int ele, int file) {
        element = ele;
        file_pointer = file;
    }

    public int getFile(){
        return file_pointer;
    }

    public int getElement(){
        return element;
    }

}

