import java.util.ArrayList;

/**
 * Created by bruno on 19/05/15.
 */
public class Deque {
    ArrayList<Integer> deque;

    Deque(){
        this.deque = new ArrayList<Integer>();
        this.insertFirst(-1); //Adding scan
    }
    void insertFirst(int e){
        deque.add(0,e);
    }
    void insertLast(int e){
        deque.add(this.getSize(), e);
    }
    int removeFirst(){
        return deque.remove(0);
    }
    int removeLast(){
        return deque.remove(deque.size()-1);
    }
    int getSize(){
        return deque.size();
    }
}
