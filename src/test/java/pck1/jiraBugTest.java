package pck1;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class jiraBugTest {


    @Test
    public void bugtest() {
        RestAssured.baseURI = "https://musthaq53ahamed.atlassian.net";
        String createIssueResponse = given().header("Content-Type", "application/json").header("Authorization", "Basic bXVzdGhhcTUzYWhhbWVkQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjA1S1lyNG1weEVORW05M3l5WEJJNllFNFo1RTVjTjRBaGNURkl6enFNSGl1YnF4and3Y3lsSUpGeElFd042OHB4amtaZXl3RnhXOW5JWGRSQzVtdk02M1l5ZW9JOU1DSnpLR3NpSWhucG5nc1dqME1LZjBQNWRqc3VVYzFDZjZUdS1TU0FFSFdmbi1HSFhpZGY2VWhqOWhZd0hvV2NMbmdBVHFaY1pyNUxLQlE9NTIxRDFEMzE=")
                .body("{\n" +
                        "    \"fields\":{\n" +
                        "        \"project\":{\n" +
                        "            \"key\": \"AT\"\n" +
                        "        },\n" +
                        "        \"summary\":\"Test Bug Summary4\",\n" +
                        "        \"issuetype\":{\n" +
                        "            \"name\":\"Bug\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}").when().post("/rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = payload.stringToJson(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println(issueId);


        String addAttachRes = given()
                .header("Authorization", "Basic bXVzdGhhcTUzYWhhbWVkQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjA1S1lyNG1weEVORW05M3l5WEJJNllFNFo1RTVjTjRBaGNURkl6enFNSGl1YnF4and3Y3lsSUpGeElFd042OHB4amtaZXl3RnhXOW5JWGRSQzVtdk02M1l5ZW9JOU1DSnpLR3NpSWhucG5nc1dqME1LZjBQNWRqc3VVYzFDZjZUdS1TU0FFSFdmbi1HSFhpZGY2VWhqOWhZd0hvV2NMbmdBVHFaY1pyNUxLQlE9NTIxRDFEMzE=")
                .header("X-Atlassian-Token", "no-check").pathParam("issueId", issueId)
                .multiPart("file", new File((System.getProperty("user.dir") + "//src//test//java//files//AddPlace.json")))
                .when().post("/rest/api/3/issue/{issueId}/attachments")
                .then().log().all().assertThat().statusCode(200).body(matchesJsonSchemaInClasspath("addAttachResSchema.json")).extract().response().asString();

        String flename = payload.stringToJson(addAttachRes).getString("[0].filename");
        System.out.println(flename);

    }
}
