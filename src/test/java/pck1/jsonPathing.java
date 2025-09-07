package pck1;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class jsonPathing {
    public static void main(String[] args) throws IOException {
        String rrr = new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace2.json")));

        JsonPath js = new JsonPath(rrr);
        System.out.println(js.getString("place.name"));
        System.out.println(js.getString("place.location.coordinates.lat"));
        System.out.println(js.getString("place.location.coordinates.lng"));
        System.out.println(js.getString("place.contact.phone.countryCode")+"-"+js.getString("place.contact.phone.number"));

        List<LinkedHashMap> tt = js.get("[0].place.addresses");
        for(LinkedHashMap t : tt){
            System.out.println("-   "+t);
        }

        System.out.println(js.getString("place.languages_supported"));
        System.out.println(js.getString("place.languages_supported[0]"));
        System.out.println(js.getString("[0].place.languages_supported"));
        System.out.println(js.getBoolean("meta.verified"));
        System.out.println(js.getString("meta.timestamp").substring(0,4));

        List<LinkedHashMap> hmp = js.get("[0].place.addresses");
        System.out.println(hmp.size());
        String comp;
        int i=0;
        String s11 = "";
        for (LinkedHashMap m1: hmp){
            s11 = s11+m1.get("type")+" - "+m1.get("street");
            i++;
            if (i < hmp.size()) {
                s11 += ", ";
            }
        }
        System.out.println(s11);
        List<String> lst1 = js.getList("[0].place.addresses.pincode");
        System.out.println(lst1.get(1));

        List<String> k1 = js.getList("[0].place.addresses.type");
        System.out.println(k1);
    }
}
