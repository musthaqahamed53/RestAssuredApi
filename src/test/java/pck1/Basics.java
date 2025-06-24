package pck1;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) throws IOException {
        // Validate if add api is working

        //given - all input details
        //when - submit the api - resource, http method
        //then - validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
//        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//                .body(payload.AddPlace()).when().post("/maps/api/place/add/json")
//                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
//                .header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();


        //content of json file to String -> content of file can convert into Byte -> Byte data to String
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace.json"))))
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();



        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String place_id = js.get("place_id");
        System.out.println(place_id);
        //Add place - update place - get place and validate updation

        //update place
        String newAddress = "Updated Address";
        String response2 = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(payload.UpdatePlace(place_id,newAddress))
                .when().put("/maps/api/place/update/json")
                .then().assertThat() .log().all()
                .body("msg",equalTo("Address successfully updated"))
                .header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response2);

        String getRes = given().log().all().queryParam("key","qaclick123").queryParam("place_id",place_id)
                .when().get("/maps/api/place/get/json").then().assertThat().log().all()
                .body("address",equalTo("Updated Address")).extract().response().asString();

        JsonPath js2 = new JsonPath(getRes);
        String actuAddress = js2.get("address");
        Assert.assertEquals(actuAddress, newAddress);

    }
}
