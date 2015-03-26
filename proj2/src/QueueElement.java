/**
 * Created by bruno on 22/03/15.
 */
public class QueueElement{
    String element;
    int file_pointer;

    public QueueElement(String ele, int file) {
        element = ele;
        file_pointer = file;
    }

    public int getFile(){
        return file_pointer;
    }

    public String getElement(){
        return element;
    }

}

