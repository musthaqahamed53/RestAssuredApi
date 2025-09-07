package pck1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class JsonPathVsPOJOComparison {

    public static void main(String[] args) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Path.of(System.getProperty("user.dir") + "//src//test//java//files//AddPlace2.json")));
        
        System.out.println("🔥 JSONPATH vs POJO VALIDATION COMPARISON\n");
        
        // ========== JSONPATH APPROACH ==========
        System.out.println("📍 JSONPATH APPROACH:");
        long jsonPathStart = System.currentTimeMillis();
        
        JsonPath js = new JsonPath(jsonContent);
        
        // Quick validations with JsonPath
        String placeName = js.get("[0].place.name");
        Float latitude = js.get("[0].place.location.coordinates.lat");
        String email = js.get("[0].place.contact.email");
        int addressCount = js.getList("[0].place.addresses").size();
        
        System.out.println("✅ Place Name: " + placeName);
        System.out.println("✅ Latitude: " + latitude);
        System.out.println("✅ Email: " + email);
        System.out.println("✅ Address Count: " + addressCount);
        
        long jsonPathEnd = System.currentTimeMillis();
        System.out.println("⏱️ JsonPath Time: " + (jsonPathEnd - jsonPathStart) + "ms\n");
        
        // ========== POJO APPROACH ==========
        System.out.println("🏗️ POJO APPROACH:");
        long pojoStart = System.currentTimeMillis();

        //Deserialization: Turning Data into POJO
        ObjectMapper mapper = new ObjectMapper();
        PlacePOJO[] placesArray = mapper.readValue(jsonContent, PlacePOJO[].class);
        List<PlacePOJO> places = Arrays.asList(placesArray);

        //User userPojo = mapper.readValue(jsonString, User.class); // Deserialization

        //Serialization of POJO Array to JSON
        ObjectMapper mapper2 = new ObjectMapper();
        String jsonArray = mapper.writeValueAsString(placesArray);


        // Type-safe validations with POJO
        PlacePOJO firstPlace = places.get(0);
        String pojoPlaceName = firstPlace.getPlace().getName();
        double pojoLatitude = firstPlace.getPlace().getLocation().getCoordinates().getLat();
        String pojoEmail = firstPlace.getPlace().getContact().getEmail();
        int pojoAddressCount = firstPlace.getPlace().getAddresses().size();

        System.out.println("✅ Place Name: " + pojoPlaceName);
        System.out.println("✅ Latitude: " + pojoLatitude);
        System.out.println("✅ Email: " + pojoEmail);
        System.out.println("✅ Address Count: " + pojoAddressCount);

        long pojoEnd = System.currentTimeMillis();
        System.out.println("⏱️ POJO Time: " + (pojoEnd - pojoStart) + "ms");

        // Demonstrate List advantages
        System.out.println("🎯 List<PlacePOJO> Benefits:");
        System.out.println("✅ Total places: " + places.size());
        System.out.println("✅ Stream operations available: " +
            places.stream().map(p -> p.getPlace().getName()).toList());
        System.out.println("✅ Safe access: " + (!places.isEmpty() ? "Has data" : "Empty"));
        
        // ========== COMPARISON ANALYSIS ==========
        System.out.println("\n📊 DETAILED COMPARISON:\n");
        
        demonstrateJsonPathAdvantages(js);
        demonstratePOJOAdvantages(firstPlace);
        
        System.out.println("\n🎯 INTERVIEW RECOMMENDATIONS:");
        printInterviewGuidance();
    }
    
    private static void demonstrateJsonPathAdvantages(JsonPath js) {
        System.out.println("🚀 JSONPATH ADVANTAGES:");
        
        // 1. Quick field access
        System.out.println("1. Quick Access: js.get(\"[0].place.name\") = " + js.get("[0].place.name"));
        
        // 2. Dynamic path building
        String dynamicPath = "[0].place.addresses.find { it.type == 'office' }.area";
        System.out.println("2. Dynamic Filtering: " + dynamicPath + " = " + js.get(dynamicPath));
        
        // 3. Array operations
        List<String> addressTypes = js.getList("[0].place.addresses.type");
        System.out.println("3. Array Extraction: All address types = " + addressTypes);
        
        // 4. No class creation needed
        System.out.println("4. No Setup Required: Direct JSON parsing without POJO classes");
        
        System.out.println("✅ Best for: Quick validations, exploratory testing, dynamic paths\n");
    }
    
    private static void demonstratePOJOAdvantages(PlacePOJO place) {
        System.out.println("🏗️ POJO ADVANTAGES:");
        
        // 1. Type safety
        double lat = place.getPlace().getLocation().getCoordinates().getLat(); // Compile-time type checking
        System.out.println("1. Type Safety: Latitude as double = " + lat);
        
        // 2. IDE support (autocomplete, refactoring)
        System.out.println("2. IDE Support: Full autocomplete and refactoring support");
        
        // 3. Object-oriented operations
        PlacePOJO.Address homeAddress = place.getPlace().getAddresses().stream()
                .filter(addr -> "home".equals(addr.getType()))
                .findFirst()
                .orElse(null);
        System.out.println("3. OOP Operations: Home address = " + 
                (homeAddress != null ? homeAddress.getArea() : "Not found"));
        
        // 4. Validation methods
        boolean hasValidEmail = place.getPlace().getContact().getEmail().contains("@");
        System.out.println("4. Business Logic: Email validation = " + hasValidEmail);
        
        System.out.println("✅ Best for: Complex validations, type safety, maintainable code\n");
    }
    
    private static void printInterviewGuidance() {
        System.out.println("""
        
        📋 WHEN TO USE EACH APPROACH:
        
        🔥 USE JSONPATH WHEN:
        ✅ Quick API response validation
        ✅ Exploratory testing (don't know full structure)
        ✅ Simple field checks
        ✅ Dynamic path building
        ✅ Prototyping and debugging
        ✅ Testing APIs with frequently changing schemas
        
        🏗️ USE POJO WHEN:
        ✅ Complex business logic validation
        ✅ Type safety is critical
        ✅ Long-term maintainable test suites
        ✅ Object-oriented operations needed
        ✅ IDE support and refactoring important
        ✅ Working with well-defined, stable APIs
        
        🎯 HYBRID APPROACH (BEST PRACTICE):
        ✅ Use JsonPath for quick checks
        ✅ Use POJO for complex validations
        ✅ Combine both in comprehensive test suites
        
        💡 INTERVIEW TIP:
        "I choose JsonPath for quick validations and POJO for complex 
        business logic. The decision depends on test complexity, 
        maintenance requirements, and team preferences."
        """);
    }
}
