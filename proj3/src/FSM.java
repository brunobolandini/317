import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;


public class FSM {
    char[] regexp; //holds the reg exp
    char[] ch; //represents the tree arrays of the fsm
    int[] next1;
    int[] next2;
    int state = 1; //start state is 0
    int current_index = 1;
    int fsm_size = 1;


    public static void main(String[] args) {
        if (args.length<1){
            System.out.println("Usage: FMS \"regexp\"");
            System.exit(0);
        }
        FSM fsm = new FSM(args[0]);
    }
    public FSM(String regexp_string) {
        char[] temp = regexp_string.toCharArray(); //from here until —>
        regexp = new char[temp.length + 2]; //start state and end state, 2 states
        regexp[temp.length + 1] = 0; //end state
        regexp[0] = 0; //start state   //until here creating the array e.g “ab*”,  0,a,b,*0
        System.arraycopy(temp, 0, regexp, 1, temp.length);
        ch = new char[regexp.length*2];
        next1 = new int[regexp.length*2];
        next2 = new int[regexp.length*2];
        set_state(0, ' ', 1, 1); //start state
        System.out.println(regexp);
        expression(); //start the building of the fsm
        //once we have built the fsm
        if (current_index>=regexp.length){
            current_index--;
            fsm_size=fsm_size-2;
        }
        if (regexp[current_index] != 0) {
            error("error on: "+regexp[current_index-1]+regexp[current_index]+"\nRegular Expression is not valid");

        } else {
            set_state(fsm_size, ' ', -1, -1); //final state

            System.out.println("FSM for" + regexp_string);
            for (int x = 0; x < fsm_size; x++) {
                System.out.println(x + ":" + ch[x] + ", " + next1[x] + ", " + next2[x]);
            }
            System.out.println();
        }
    }

    private void error() {
        System.out.println("error");
        System.exit(1);
    }
    private void error(String msg) {
        System.out.println(msg);
        System.exit(1);
    }



    private int expression() {
        int r = term();
        if (current_index>=regexp.length){
            return r;
        }
        if (isvocab(regexp[current_index]) || regexp[current_index] == '(' || regexp[current_index]=='['|| regexp[current_index]=='.')
            expression();
        return r;
    }

    private int term() {
        int r, term1, term2, previous_index;
        previous_index = state - 1;
        r = term1 = factor();

        if (current_index>=regexp.length){

            return r;
        }
        switch (regexp[current_index]){
            case '*':
                if (!isvocab(ch[state-1])){
                    error("error on: "+regexp[current_index-1]+regexp[current_index]+"\nRegular Expression is not valid");
                }
                setPreviousStateTo(state, term1);
                set_state(state, ' ', state + 1, term1);

                current_index++;
                r = state;
                state++;
                break;
            case '|':
                setNextState(previous_index, state);
                previous_index = state - 1;
                current_index++;
                r = state;
                state++;
                term2 = term();
                set_state(r, ' ', term1, term2);
                setNextState(previous_index, state);
                break;
            case '?':
                if (!isvocab(ch[state-1])){
                    error("error on: "+regexp[current_index-1]+regexp[current_index]+"\nRegular Expression is not valid");
                }
                System.out.println(previous_index+" "+current_index+" "+state+" "+term1);
                setPreviousStateTo(state, term1);

                current_index++;
                r = state;
                state++;

                term2 = term();

                set_state(r,' ',term1,term2);
                setNextState(term1,term1+1);
                if (r!=state-3)
                    setNextState(state - 3, state - 1);
                System.out.println(previous_index+" "+current_index+" "+state+" "+term1+" "+term2);
            case '[':
                break;
            case ']':
                break;
            case '\\':
                try {
                    current_index++;
                    set_state(state,regexp[current_index],state+1,state+1);
                    r =state;
                    state++;
                    current_index++;
                }catch (Exception e){
                    error("Error: Regular expression is not valid");

                }
                break;
        }
        return (r);
    }

    private void setPreviousStateTo(int state, int from) {
        for (int i = 0; i < state; i++) {
            if (next1[i] == from) next1[i] = state;
            if (next2[i] == from) next2[i] = state;
        }
    }

    private void setNextState(int index, int state) {
        if (index != -1) {
            if (next1[index] == next2[index])
                next2[index] = state;
            next1[index] = state;
        }
    }
    private void set_state(int s, char c, int n1, int n2) {
        ch[s] = c;
        next1[s] = n1;
        next2[s] = n2;
        fsm_size++;
    }

    private int factor() {
        int r = -1;
        if (isvocab(regexp[current_index])) {
            set_state(state, regexp[current_index], state + 1, state + 1);
            current_index++;
            r = state;
            state++;
        } else {
            switch (regexp[current_index]){
                case '.':

                    set_state(state,'\uFFFF',state+1,state+1);
                    current_index++;
                    r = state;
                    state++;
                    break;
                case '(':
                    current_index++;

                    r = expression();
                    if (current_index>=regexp.length){
                        error("error :\nCheck your parenthesis");
                    }
                    if (regexp[current_index] == ')')
                        current_index++;
                    else {
                        error("error on: " + regexp[current_index - 1] + regexp[current_index] + "\nThe parenthesis was never closed");
                    }
                    break;
                case '[':
                    int counter = 0;
                    int index = current_index+1;
                    String brackets = "";
                    r=state;
                    while (counter<2||regexp[index]!=']') {
                        if (regexp[index]==']'){
                            counter++;
                        }
                        if (counter<2) {
                            brackets += regexp[index];
                            index++;
                        }
                    }
                    current_index=index+1;

                    for (int i=0;i<brackets.length();i++) {
                        if (i==brackets.length()-1){
                            set_state(state,brackets.charAt(i),((brackets.length()*2)-1)+r,((brackets.length()*2)-1)+r);
                            state++;
                        }else {
                            set_state(state, ' ', state + 1, state + 2);
                            state++;
                            set_state(state,brackets.charAt(i),((brackets.length()*2)-1)+r,((brackets.length()*2)-1)+r);
                            state++;
                        }
                    }
                    /*
                    do {
                        switch (regexp[current_index]) {
                            case ']':
                                counter++;
                                if (counter>1){
                                    break;
                                }else {
                                    setNextState(state - 1, state);
                                    r = state;
                                    state++;
                                    set_state(r, ' ', state, state + 1);
                                    set_state(state,regexp[current_index],);
                                    break;
                                }
                            default:
                                break;
                        }
                    }while (regexp[current_index]!=']');*/
            }
        }
        return r;
    }

    private boolean isvocab(char c) {
        if (c != '?' && c != '*' && c != '\\' && c != '.' && c != '[' && c != ']' && c != '(' && c != ')' && c != '|')
            return true;
        return false;
    }
    /* public void Search(String filename){
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
    }*/
    void search(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            Deque myDeque = new Deque();
            this.regexp = line.toCharArray();
            int currentChar = 0;
            int currentState = 0;
            boolean match = false;
            this.state = 0;
            do {
                do {
                    if ((regexp[state] == ch[state]) || (regexp[state] == '.')) { // if the current char matches the current state char or is a wildcard character
                        if (next1[currentState] != next2[currentState]) {
                            myDeque.insertLast(next1[currentState]);
                        }
                        myDeque.insertLast(next2[currentState]); //insert next states at the back of the deque
                    } else if (ch[currentState] == ' ') { //if the state doesn't consume any char
                        if (next1[currentState] == -1) //scan found, no states in the deque
                            match = true;
                        else {
                            if (next1[currentState] != next2[currentState])
                                myDeque.insertFirst(next1[currentState]);
                            myDeque.insertFirst(next2[currentState]);
                        }
                    } else {
                        if (myDeque.getSize() == 1) break; //only scan
                    }
                    currentState = myDeque.removeFirst(); //if we are on the final state we have matched the regexp but we haven't finish reading the line
                    if (currentState == ch.length - 1) {
                        match = true;
                        currentState = myDeque.removeFirst();
                    }
                    if (currentState == -1) {//if its the scan
                        myDeque.insertFirst(currentState); //put the scan back in the deque
                        currentState = myDeque.removeFirst(); //read the next char of the l
                        currentChar++;
                        if (currentChar == this.regexp.length) {//if we have read all char
                            if (currentState == ch.length - 1 || match) {
                                System.out.println("Matched on " + line);
                                break;
                            } else currentChar--;
                        }
                    }
                } while (myDeque.getSize() > 0);
            }
        }
    }
}


