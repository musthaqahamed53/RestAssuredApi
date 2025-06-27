package ecommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import pojo.CreateOrderEcom;
import pojo.LoginRequestEcom;
import pojo.LoginResponseEcom;
import pojo.Orders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class EcommerceApiTest {

    public String tokenid;

    @Test(priority = 1)
    public void login_ecom(){

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

        LoginRequestEcom loginRequestEcom =new LoginRequestEcom();
        loginRequestEcom.setUserEmail("musma080@gmail.com");
        loginRequestEcom.setUserPassword("Qwertyuiop@123");

        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequestEcom);

        LoginResponseEcom loginResponseEcom = reqLogin.when().post("/api/ecom/auth/login")
                .then().log().all().extract().response().as(LoginResponseEcom.class);

        tokenid = loginResponseEcom.getToken();
        String userid = loginResponseEcom.getUserId();

        System.out.println(tokenid);
        System.out.println(userid);

        //AddProuduct

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",tokenid).build();

        //param or formParam
        //given().relaxedHTTPSValidation() trust all hosts regardless if ssl certificate is invalid
        RequestSpecification reqAddProd = given().relaxedHTTPSValidation().log().all().spec(addProductBaseReq)
                .formParam("productName","SheikTestProduct2")
                .param("productAddedBy",userid)
                .formParam("productCategory","fashion")
                .param("productSubCategory","shirts")
                .param("productPrice","11500")
                .param("productDescription","Addias Originals")
                .param("productFor","women")
                .multiPart("productImage",new File("C:\\Users\\Lenovo\\Downloads\\sampleimage.jpg"));

        String resAddProduct = reqAddProd.when().post("/api/ecom/product/add-product")
                .then().log().all().assertThat().
                body("message",equalTo("Product Added Successfully")).extract().response().asString();

        JsonPath jp = files.payload.stringToJson(resAddProduct);
        String productId = jp.get("productId");

        System.out.println(productId);

        //Create Order
        pojo.CreateOrderEcom createOrderEcom = new CreateOrderEcom();
        List<Orders> ordersList = new ArrayList<>();
        Orders orders1 = new Orders();
        orders1.setCountry("India");
        orders1.setProductOrderedId(productId);
        ordersList.add(orders1);
        createOrderEcom.setOrders(ordersList);

        String orderStat = given().spec(addProductBaseReq).log().all().body(createOrderEcom).when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();

        System.out.println(orderStat);

    }
//    @Test(priority = 2)
//    public void create_product(){
//
//
//        System.out.println(tokenid);
//    }
}
