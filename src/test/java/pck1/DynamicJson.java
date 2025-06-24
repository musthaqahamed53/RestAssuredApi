package pck1;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String res = given().header("Content-Type","application/json")
                .body(payload.addBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = payload.stringToJson(res);
        String id = js.get("ID");

        given().header("Content-Type","application/json")
                .body("{\n" +
                        "    \"ID\": \""+id+"\"\n" +
                        "}")
                .when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

    }

    @DataProvider(name="BooksData")
    public Object[] []  getData(){
        return new Object[] [] {{"testname1","1234"},{"testname2","1234"},{"testname3","1234"}};
    }
}
