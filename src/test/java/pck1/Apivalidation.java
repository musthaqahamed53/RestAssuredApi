package pck1;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.io.InputStream;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Apivalidation {

    public static void main(String[] args) throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
//        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//                .body(new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace2.json"))))
//                .when().post("/maps/api/place/add/json")
//                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
//                .header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
//        System.out.println(response);
//
        String mockPayload = new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace2.json")));
//        System.out.println(mockPayload);

        JsonPath js = new JsonPath(mockPayload);
        
        // ========== JSON PATH VALIDATION EXAMPLES FOR INTERVIEW PRACTICE ==========
        System.out.println("🔍 Starting JsonPath validation examples...\n");
        
        // 1. Basic field extraction and validation
        System.out.println("1. BASIC FIELD VALIDATION:");
        String placeName = js.get("[0].place.name");
        System.out.println("Place Name: " + placeName);
        assert placeName.equals("Chitlapakkam") : "Place name validation failed!";
        
        // 2. Nested object validation
        System.out.println("\n2. NESTED OBJECT VALIDATION:");
        Float latitude = js.get("[0].place.location.coordinates.lat");
        Float longitude = js.get("[0].place.location.coordinates.lng");
        System.out.println("Coordinates: " + latitude + ", " + longitude);
        assert latitude.equals(12.9356582f) : "Latitude validation failed!";
        assert longitude.equals(80.1431601f) : "Longitude validation failed!";
        
        // 3. Array size validation
        System.out.println("\n3. ARRAY SIZE VALIDATION:");
        int totalPlaces = js.getList("").size();
        int addressCount = js.getList("[0].place.addresses").size();
        int facilitiesCount = js.getList("[0].place.facilities").size();
        System.out.println("Total places: " + totalPlaces);
        System.out.println("Addresses count: " + addressCount);
        System.out.println("Facilities count: " + facilitiesCount);
        assert totalPlaces == 2 : "Total places count validation failed!";
        assert addressCount == 3 : "Address count validation failed!";
        
        // 4. Array element validation by index
        System.out.println("\n4. ARRAY ELEMENT VALIDATION:");
        String firstAddressType = js.get("[0].place.addresses[0].type");
        String firstAddressCity = js.get("[0].place.addresses[0].city");
        System.out.println("First address type: " + firstAddressType);
        System.out.println("First address city: " + firstAddressCity);
        assert firstAddressType.equals("home") : "First address type validation failed!";
        assert firstAddressCity.equals("Chennai") : "City validation failed!";
        
        // 5. Find specific array elements using JsonPath filters
        System.out.println("\n5. JSONPATH FILTER EXAMPLES:");
        // Find office address
        String officeArea = js.get("[0].place.addresses.find { it.type == 'office' }.area");
        System.out.println("Office area: " + officeArea);
        assert officeArea.equals("Guindy") : "Office area validation failed!";
        
        // Find all address types
        java.util.List<String> addressTypes = js.getList("[0].place.addresses.type");
        System.out.println("All address types: " + addressTypes);
        assert addressTypes.contains("home") && addressTypes.contains("office") && addressTypes.contains("warehouse") 
            : "Address types validation failed!";
        
        // 6. String validation and pattern matching
        System.out.println("\n6. STRING VALIDATION:");
        String email = js.get("[0].place.contact.email");
        String phoneNumber = js.get("[0].place.contact.phone.number");
        String pincode = js.get("[0].place.addresses[0].pincode");
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Pincode: " + pincode);
        assert email.contains("@") : "Email format validation failed!";
        assert phoneNumber.length() == 10 : "Phone number length validation failed!";
        assert pincode.matches("\\d{6}") : "Pincode format validation failed!";
        
        // 7. Boolean and metadata validation
        System.out.println("\n7. METADATA VALIDATION:");
        boolean isVerified = js.get("[0].meta.verified");
        String source = js.get("[0].meta.source");
        String timestamp = js.get("[0].meta.timestamp");
        System.out.println("Verified: " + isVerified);
        System.out.println("Source: " + source);
        System.out.println("Timestamp: " + timestamp);
        assert isVerified == true : "Verification status validation failed!";
        assert source.equals("Community") : "Source validation failed!";
        
        // 8. Advanced JsonPath - collect all facility names
        System.out.println("\n8. ADVANCED JSONPATH OPERATIONS:");
        java.util.List<String> facilityNames = js.getList("[0].place.facilities.name");
        System.out.println("All facility names: " + facilityNames);
        assert facilityNames.contains("Grocery Store") && facilityNames.contains("Playground") 
            : "Facility names validation failed!";
        
        // 9. Cross-validation between multiple places
        System.out.println("\n9. CROSS-VALIDATION:");
        String place1Name = js.get("[0].place.name");
        String place2Name = js.get("[1].place.name");
        System.out.println("Comparing places: " + place1Name + " vs " + place2Name);
        assert place1Name.equals(place2Name) : "Places should have same name!";
        
        // 10. Null and existence checks
        System.out.println("\n10. NULL/EXISTENCE CHECKS:");
        String website = js.get("[0].place.contact.website");
        System.out.println("Website exists: " + (website != null));
        assert website != null : "Website should not be null!";
        
        System.out.println("\n✅ All JsonPath validations passed! Great for interview practice!");
        
        // Schema validation
        System.out.println("\n🔍 Running JSON Schema validation...");
        InputStream schemaStream = Apivalidation.class.getClassLoader()
                .getResourceAsStream("place-schema.json");
        JsonSchemaValidator sss = matchesJsonSchema(schemaStream);
        assertThat(mockPayload, matchesJsonSchema(schemaStream));
        System.out.println("✅ JSON schema validation successful!");

    
    }


}
