package pck1;

import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.pojo_sheik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class pojosheiktrail {

    public static void main(String[] args) throws IOException {
//        String jsonbody = new String(readvaluefromfile("src\\test\\java\\pck1\\AddPlace2.json"));

        //Deserialization: Turning Data into POJO .....JSON → POJO array → List
        String mockPayload = new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace2.json")));
        ObjectMapper mapper11 = new ObjectMapper();
        pojo.pojo_sheik[] pojoSheikArray = mapper11.readValue(mockPayload, pojo.pojo_sheik[].class);
        List<pojo_sheik> places = Arrays.asList(pojoSheikArray);


        // 🔧 MODIFY DATA HERE
        pojo_sheik firstPlace = pojoSheikArray[0];
        firstPlace.getPlace().setName("UPDATED PLACE NAME");
        firstPlace.getPlace().getContact().setEmail("newemail@test.com");
        firstPlace.getMeta().setVerified(false);


        //Serialization: Turning POJO into Data....POJO array → JSON string
        ObjectMapper mapper22 = new ObjectMapper();
        String jsonArray = mapper22.writeValueAsString(pojoSheikArray);
        System.out.println(jsonArray);
    }

}
