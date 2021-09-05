package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {

    String uri = "https://petstore.swagger.io/v2/pet";

    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test(priority = 0)
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then

        given() // Dado
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("name", is("Lessie"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
        ;
    }

    @Test(priority = 1)
    public void consultarPet(){
        String petId = "2111198917";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("status", is("available"))
                .body("category.name", is("dog"))
        ;
    }

    @Test(priority = 2)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given() // Dado
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when() // Quando
                .post(uri)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("name", is("Lessie"))
                .body("status", is("sold"))
                .body("category.name", is("dog"))
        ;

    }

    @Test(priority = 3)
    public void excluirPet() throws IOException {
        String petId = "2111198917";

        given() // Dado
                .contentType("application/json")
                .log().all()
        .when() // Quando
                .delete(uri + "/" + petId)
        .then() // Então
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))


        ;
    }
}
