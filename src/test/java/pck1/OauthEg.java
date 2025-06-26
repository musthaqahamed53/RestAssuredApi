package pck1;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.GetCourse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class OauthEg {
    @Test
    public void oauth_eg() {

        String response =

                given().formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .formParams("grant_type", "client_credentials")
                        .formParams("scope", "trust")
                        .when().log().all()
                        .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");
        System.out.println(accessToken);

//        String r2 = given()
//                .queryParams("access_token", accessToken)
//                .when()
//                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
//                .asString();
//
//        System.out.println(r2);

        GetCourse gc = given()
                .queryParams("access_token", accessToken)
                .when()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);

        String title1 = gc.getCourses().getWebAutomation().get(1).getCourseTitle();

        System.out.println(title1);
    }

}
