package com.waikato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lucas on 30/03/15.
 */
public class TextProcessing {
    public static void CreateInput(String fileName){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/com/waikato/"+fileName));
            FileWriter fileWriter = new FileWriter("iniInput.txt");
            String string = new String();
            while ((string=bufferedReader.readLine())!=null){
                String[] line = string.split(" ");
                for (int i=0;i<line.length;i++){
                    fileWriter.write(line[i]+"\n");
                }
            }
            fileWriter.close();
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
}
