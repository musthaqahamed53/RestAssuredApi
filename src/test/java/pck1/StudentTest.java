package pck1;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.path.json.JsonPath;
import pojo.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class StudentTest {

    public static void main(String[] args) throws IOException {
        System.out.println("🎓 STUDENT POJO DEMO - Single JSON Object (Not Array)\n");

        // Read JSON file (single object, not array)
        String jsonContent = new String(Files.readAllBytes(
            Path.of(System.getProperty("user.dir") + "//src//test//java//files//Student.json")));

//        ObjectMapper obb = new ObjectMapper();
//        Object object = obb.readValue(jsonContent, Object.class);


        System.out.println("Json Pathing");
        JsonPath jsonPath = new JsonPath(jsonContent);
        System.out.println(jsonPath.getString("id"));
        LinkedHashMap<String,Object> linkedHashMap = jsonPath.get("metadata");
        System.out.println(linkedHashMap);


        System.out.println("📄 Original JSON:");
        System.out.println(jsonContent + "\n");

        // Deserialization: JSON → POJO (Notice: No array, just single object)
        System.out.println("📥 DESERIALIZATION (JSON → POJO):");
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(jsonContent, Student.class);  // ← Single object, not array!

        System.out.println("✅ Successfully loaded student data");
        System.out.println("Student ID: " + student.getId());
        System.out.println("Student Name: " + student.getName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Age: " + student.getAge());
        System.out.println("GPA: " + student.getGpa());
        System.out.println("Is Active: " + student.getIsActive());
        System.out.println("Address: " + student.getAddress().getStreet() + ", " + 
                          student.getAddress().getCity() + ", " + student.getAddress().getState());
        System.out.println("Courses: " + student.getCourses());
        System.out.println("Graduation Year: " + student.getMetadata().getGraduationYear());
        System.out.println("Has Scholarship: " + student.getMetadata().isScholarship());

        // 🔧 MODIFY POJO DATA
        System.out.println("\n🔧 MODIFYING STUDENT DATA:");
        System.out.println("Original name: " + student.getName());
        student.setName("Jane Smith - UPDATED");
        student.setEmail("jane.smith@university.edu");
        student.setGpa(3.95);
        student.setAge(23);
        student.getAddress().setCity("Los Angeles");
        student.getAddress().setState("CA");
        student.getCourses().add("Data Science");
        student.getMetadata().setGraduationYear(2024);

        System.out.println("New name: " + student.getName());
        System.out.println("New email: " + student.getEmail());
        System.out.println("New GPA: " + student.getGpa());
        System.out.println("New city: " + student.getAddress().getCity());
        System.out.println("Updated courses: " + student.getCourses());

        // Serialization: POJO → JSON
        System.out.println("\n📤 SERIALIZATION (POJO → JSON):");
        String modifiedJson = mapper.writeValueAsString(student);

        // Pretty print the modified JSON
        Object jsonObject = mapper.readValue(modifiedJson, Object.class);
        String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

        System.out.println("✅ Modified JSON output:");
        System.out.println(prettyJson);

        System.out.println("\n🎉 Single JSON Object POJO demo completed successfully!");
        System.out.println("💡 Notice: No arrays involved - just single Student object!");
    }
}
