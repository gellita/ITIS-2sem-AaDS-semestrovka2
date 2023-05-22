package RBTree;

import java.io.FileWriter;
import java.io.IOException;

public class DataWriter {
    public void dataWrite(String fileName, long count,long timer){
        try(FileWriter writer = new FileWriter(fileName, true))
        {
            // запись всей строки
            String text = Long.toString(count) + ';' + Long.toString(timer) + ';' ;
            writer.write(text);
            writer.append('\n');



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
