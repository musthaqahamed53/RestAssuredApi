package pck1;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.AddPlaceBody;
import pojo.AddPlaceLocation;
import pojo.GetCourse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SerializeEg {

    @Test
    public void serTest(){

        AddPlaceLocation addPlaceLocation = new AddPlaceLocation();
        addPlaceLocation.setLat(12.12345);
        addPlaceLocation.setLng(13.134);

        AddPlaceBody addPlaceBody = getAddPlaceBody(addPlaceLocation);


        RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123").build();;

//        RestAssured.baseURI = "https://rahulshettyacademy.com";
//        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//                .body(addPlaceBody)
//                .when().post("/maps/api/place/add/json").then().log().all().extract().response();
//

//        given().log().all().spec(req)
//                .body(addPlaceBody)
//                .when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response();

        ResponseSpecification res = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody("scope", equalTo("APP")).build();

        given().log().all().spec(req)
                .body(addPlaceBody)
                .when().post("/maps/api/place/add/json").then().log().all().spec(res).extract().response();

    }

    private static AddPlaceBody getAddPlaceBody(AddPlaceLocation addPlaceLocation) {
        AddPlaceBody addPlaceBody;
        addPlaceBody = new AddPlaceBody();

        addPlaceBody.setLocation(addPlaceLocation);
        addPlaceBody.setWebsite("https://www.sheik.com");
        addPlaceBody.setAccuracy(50);
        addPlaceBody.setAddress("23/15 Pandian Nagar");
        addPlaceBody.setName("Sheik Musthaq Ahamed");
        addPlaceBody.setLanguage("English");
        List<String> types = new ArrayList<>();
        types.add("home");
        types.add("shop");
        addPlaceBody.setTypes(types);
        addPlaceBody.setPhone_number("123456789");
        return addPlaceBody;
    }
}
