package pck1;

import files.payload;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class GenAiPromptChaining {

    @Test
    public void genAiTest() {

        String geminiKey = "AIzaSyB9usEacqlwV2yFcW8TQmEjK45oha2cb8c";
        String gptModel = "gemini-2.0-flash";
        String prompt = "there are 2 items which needed to be split up on multiple lots and multiple the items are ['item1','item2'] and the lot availble are ['lot1','lot2','lot3'] allocate them in this format [item-lot] for 5 lined order. give the result alone. no extra words. one line contains only one item";

        RestAssured.baseURI = "https://generativelanguage.googleapis.com";
        String response1 = given().urlEncodingEnabled(false)
                .log().all().queryParam("key",geminiKey).header("Content-Type","application/json")
                .body("{\n" +
                        "    \"contents\": [\n" +
                        "      {\n" +
                        "        \"parts\": [\n" +
                        "          {\n" +
                        "            \"text\": \""+prompt+"\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }").when().post("/v1beta/models/"+gptModel+":generateContent").then().log().all().extract().response().asString();
        System.out.println(response1);
        String gptResult1 = payload.stringToJson(response1).getString("candidates[0].content.parts[0].text");
        String cleaned = payload.removeQuotesAndBackslashes(gptResult1);

        String prompt2 = "Modify this to 10 words - "+cleaned;

        String response2 = given().urlEncodingEnabled(false)
                .log().all().queryParam("key",geminiKey).header("Content-Type","application/json")
                .body("{\n" +
                        "    \"contents\": [\n" +
                        "      {\n" +
                        "        \"parts\": [\n" +
                        "          {\n" +
                        "            \"text\": \""+prompt2+"\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }").when().post("/v1beta/models/"+gptModel+":generateContent").then().log().all().extract().response().asString();

        String gptResult2 = payload.stringToJson(response2).get("candidates[0].content.parts[0].text");
        System.out.println(gptResult2);
    }

}
