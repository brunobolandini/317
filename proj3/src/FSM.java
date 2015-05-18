import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by bruno on 18/05/15.
 */
public class FSM { //MISSING ‘?’]	 [ ]	 .	 \
    char[] regexp; //holds the reg exp
    char[] ch; //represents the tree arrays of the fsm
    int[] next1;
    int[] next2;
    int state = 1; //start state is 0
    int current_index = 1;
    int fsm_size = 1;

    public FSM(String regexp_string) {
        char[] temp = regexp_string.toCharArray(); //from here until —>
        regexp = new char[temp.length + 2]; //start state and end state, 2 states
        regexp[temp.length + 1] = 0; //end state
        regexp[0] = 0; //start state   //until here creating the array e.g “ab*”,  0,a,b,*0
        System.arraycopy(temp, 0, regexp, 1, temp.length);
        ch = new char[regexp.length];
        next1 = new int[regexp.length];
        next2 = new int[regexp.length];
        set_state(0, ' ', 1, 1); //start state
        expression(); //start the building of the fsm
        //once we have built the fem
        if (regexp[current_index] != 0) {
            error();

        } else {
            set_state(fsm_size, ' ', -1, -1); //final state
            //just for debugging
            System.out.println("FSM for" + regexp_string);
            for (int x = 0; x < fsm_size; x++) {
                System.out.println(x + ":" + ch[x] + ", " + next1[x] + ", " + next2[x]);
            }
            System.out.println();
        }
    }

    private void error() {
        System.exit(1); //we might change it
    }

    private void set_state(int s, char c, int n1, int n2) {
        ch[s] = c;
        next1[s] = n1;
        next2[s] = n2;
        fsm_size++;
    }

    private int expression() {
        int r = term();
        if (isvocab(regexp[current_index]) || regexp[current_index] == '(') expression();
        return r;
    }

    private int term() {
        int r, term1, term2, previous_index;
        previous_index = state - 1;
        r = term1 = factor();
        if (regexp[current_index] == '*') { //we want  ab*c
            setPreviousStateTo(state, term1); //e.g ab* a will point to *
            set_state(state, ' ', state + 1, term1); // * points to previous and next
            current_index++;
            r = state;
            state++;
        }
        if (regexp[current_index] == '|') { // ab | cd
            setNextState(previous_index, state); // ‘a’will point to ‘|’
            previous_index = state - 1;
            current_index++;
            r = state;
            state++;
            term2 = term(); //find the next term to point to
            set_state(r, ' ', term1, term2);
            setNextState(previous_index, state);
        }
        if (regexp[current_index] == '?') {
            //TO DO
        }
        if (regexp[current_index] == '[') {
            //TO DO
        }
        if (regexp[current_index] == ']') {
            //TO DO
        }
        if (regexp[current_index] == '.') {
            //TO DO
        }
        if (regexp[current_index] == '\\') {
            // TO DO
        }

        return (r);
    }

    //points all state which point to ‘from’ to state
    private void setPreviousStateTo(int state, int from) {
        for (int i = 0; i < state; i++) {
            if (next1[i] == from) next1[i] = state;
            if (next2[i] == from) next2[i] = state;
        }
    }

    private void setNextState(int index, int state) {
        if (index != -1) {
            if (next1[index] == next2[index]) //So we dont overwrite closures
                next2[index] = state;
            next1[index] = state;
        }
    }

    private int factor() {
        int r = -1; //intialize
        if (isvocab(regexp[current_index])) {
            set_state(state, regexp[current_index], state + 1, state + 1);
            current_index++;
            r = state;
            state++;
        } else {
            if (regexp[current_index] == '(') {
                current_index++;
                r = expression();
                if (regexp[current_index] == ')')
                    current_index++;
                else
                    error();
            } else error();
        }
        return r;
    }

    private boolean isvocab(char c) {
        if (c != '?' && c != '*' && c != '\\' && c != '.' && c != '[' && c != ']' && c != '(' && c != ')' && c != '|')
            return true;
        return false;
    }
    public void Search(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line=br.readLine())!=null){
                Deque<Integer> deque = new ArrayDeque<Integer>();
                deque.push(-1);//Scan
                char[] charLine=line.toCharArray();
                int currentChar = 0;
                int currentState = 0;
                boolean matched = false;
                do {
                    if (ch[currentState]==charLine[currentChar]){
                        if (next1[currentState]!= next2[currentState]){
                            deque.add(next1[currentState]);
                        }
                        deque.add(next2[currentState]);
                    }else if (ch[currentState]==' '){
                        if (next1[currentState]==-1)
                            matched = true;
                        else {
                            if (next1[currentState]!=next2[currentState])
                                deque.push(next1[currentState]);
                            deque.push(next2[currentState]);
                        }
                    }else {
                        if (deque.size()==1)//only scan
                        break;
                    }
                    currentState=deque.removeFirst();
                    if (currentState==ch.length-1){
                        matched = true;
                        currentState = deque.removeFirst();
                    }
                    if (currentState==-1){//if its the scan
                        deque.add(currentState);
                        currentState = deque.removeFirst();
                        currentChar++;
                        if (currentChar==charLine.length){//if we have read all char
                            if (currentState==ch.length-1||matched){
                                System.out.println("Matched on "+line);
                                break;
                            }else currentChar--;
                        }
                    }
                }while (deque.size()>0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


