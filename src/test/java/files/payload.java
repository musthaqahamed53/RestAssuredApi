package files;

import io.restassured.path.json.JsonPath;

public class payload {

    public static String AddPlace(){

        return """
                {
                          "location": {
                            "lat": 12.9356582,
                            "lng": 80.1431601
                          },
                          "accuracy": 50,
                          "name": "Chitlapakkam",
                          "phone_number": "(+91) 9578312323",
                          "address": "9/5 Gandhi Street",
                          "types": [
                            "home",
                            "shop"
                          ],
                          "website": "http://google.com",
                          "language": "English-IN"
                        }""";
    }

    public static String UpdatePlace(String place_id,String Address){

        return "{\n" +
                "\"place_id\":\""+place_id+"\",\n" +
                "\"address\":\""+Address+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }

    public static String removeQuotesAndBackslashes(String input) {
        if (input == null) return null;
        return input.replace("\"", "").replace("\\", "");
    }


    public static JsonPath stringToJson(String response){
        if (response == null || response.trim().isEmpty()) {
            throw new IllegalArgumentException("Response body is null or empty");
        }
        return new JsonPath(response);
    }

    public static String addBook(String isbn,String aile){
        return "{\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aile+"\",\n" +
                "\"author\":\"John foer\"\n" +
                "}\n";
    }

}
