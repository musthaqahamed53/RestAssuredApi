package pck1;

import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.Student;
import io.restassured.module.jsv.JsonSchemaValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

public class POJOvsSchemaTest {

    public static void main(String[] args) throws IOException {
        System.out.println("🔍 POJO vs JSON SCHEMA VALIDATION COMPARISON\n");

        // Read INVALID student data
        String invalidJson = new String(Files.readAllBytes(
            Path.of(System.getProperty("user.dir") + "//src//test//java//files//InvalidStudent.json")));

        System.out.println("📄 Testing with INVALID data:");
        System.out.println("- Empty name");
        System.out.println("- Invalid email format");
        System.out.println("- Negative age (-25)");
        System.out.println("- Invalid GPA (15.5 - should be 0-4.0)");
        System.out.println("- Invalid graduation year (1800)\n");

        // ========== POJO TEST ==========
        System.out.println("🏗️ POJO DESERIALIZATION TEST:");
        try {
            ObjectMapper mapper = new ObjectMapper();
            Student student = mapper.readValue(invalidJson, Student.class);

            System.out.println("✅ POJO Deserialization: SUCCESS (but data is invalid!)");
            System.out.println("Name: '" + student.getName() + "' (empty - should be invalid!)");
            System.out.println("Email: '" + student.getEmail() + "' (invalid format - should fail!)");
            System.out.println("Age: " + student.getAge() + " (negative - should be invalid!)");
            System.out.println("GPA: " + student.getGpa() + " (>4.0 - should be invalid!)");
            System.out.println("Graduation Year: " + student.getMetadata().getGraduationYear() + " (1800 - invalid!)");
            
            System.out.println("\n💡 POJO CONCLUSION: Accepts ANY data that matches structure!");
            System.out.println("❌ No validation of data quality or business rules");

        } catch (Exception e) {
            System.out.println("❌ POJO Deserialization failed: " + e.getMessage());
        }

        // ========== JSON SCHEMA TEST ==========
        System.out.println("\n📋 JSON SCHEMA VALIDATION TEST:");
        try {
            // Create a simple schema for student validation
            String studentSchema = """
                {
                  "$schema": "http://json-schema.org/draft-07/schema#",
                  "type": "object",
                  "properties": {
                    "id": {"type": "integer", "minimum": 1},
                    "name": {"type": "string", "minLength": 1},
                    "email": {"type": "string", "format": "email"},
                    "age": {"type": "integer", "minimum": 0, "maximum": 120},
                    "gpa": {"type": "number", "minimum": 0.0, "maximum": 4.0},
                    "address": {
                      "type": "object",
                      "properties": {
                        "state": {"type": "string", "maxLength": 2}
                      }
                    },
                    "metadata": {
                      "type": "object",
                      "properties": {
                        "graduationYear": {"type": "integer", "minimum": 2020, "maximum": 2030}
                      }
                    }
                  },
                  "required": ["id", "name", "email", "age"]
                }
                """;

            assertThat(invalidJson, matchesJsonSchema(studentSchema));
            System.out.println("✅ JSON Schema Validation: SUCCESS");

        } catch (AssertionError e) {
            System.out.println("❌ JSON Schema Validation: FAILED (as expected!)");
            System.out.println("Schema caught invalid data: " + e.getMessage().substring(0, 200) + "...");
            System.out.println("\n💡 SCHEMA CONCLUSION: Properly validates data quality!");
            System.out.println("✅ Catches business rule violations");
        }

        // ========== COMPARISON SUMMARY ==========
        System.out.println("\n📊 FINAL COMPARISON:");
        System.out.println("""
        
        🏗️ POJO DESERIALIZATION:
        ✅ Maps JSON structure to Java objects
        ✅ Handles type conversion (String → int, etc.)
        ✅ Fast and efficient for data access
        ❌ NO data quality validation
        ❌ NO business rule enforcement
        ❌ Accepts ANY data that fits structure
        
        📋 JSON SCHEMA VALIDATION:
        ✅ Validates data quality and constraints
        ✅ Enforces business rules (email format, ranges)
        ✅ Catches invalid data before processing
        ✅ Provides detailed error messages
        ❌ Doesn't create Java objects
        ❌ Additional step in processing
        
        🎯 BEST PRACTICE: Use BOTH!
        1. JSON Schema validation FIRST (catch bad data)
        2. POJO deserialization SECOND (work with objects)
        """);
    }
}
