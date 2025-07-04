package java_learn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReviseJava {
    public static void main(String[] args) {

        Path p1 = Paths.get("C:\\Users\\Lenovo\\IdeaProjects\\RestAssuredApi\\src\\test\\java\\java_learn\\read_file.txt");

        try{
            String content = Files.readString(p1);
            System.out.println(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String value = "New Val";
        try{
            Files.writeString(p1,value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String word = "GGood Moooorning Sheik";

        char [] crr = word.toCharArray();

        for(int i=0; i<crr.length;i++){
            int count =0;
            for(int j=0; j<crr.length;j++){
                if(crr[i]==crr[j]){
                    count++;
                }
            }
            if(count==1){
                System.out.print(crr[i]+" ");
                break;
            }
        }

    }
}
