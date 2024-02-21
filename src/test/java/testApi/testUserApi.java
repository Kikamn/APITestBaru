package testApi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;


public class testUserApi {
    Response res;
    Integer new_created_id;
    Random rand = new Random();

    @Test
    public void getListUser() {
        File jsonSchema = new File("src/test/resources/jsonSchema/schemaUser");

        RestAssured.given().when()
                .get("https://gorest.co.in/public/v2/users") //masukan link api
                .then().log().all()
                .assertThat().statusCode(200);
    }

    @Test
    public void postListUser() {
        String valueName = "Sinta";
        String valueEmail = "sinta" + rand.nextInt(1000) + "@gmail.com";
        String valueGender = "female";
        String valueStatus = "active";

        JSONObject obj = new JSONObject();
        obj.put("name", valueName); // ambil sesuai dengan case
        obj.put("email", valueEmail);
        obj.put("gender", valueGender);
        obj.put("status", valueStatus);

        res = RestAssured.given()
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 38cebe5affdc6038017ae850b112f50d15613c3f40d9d2a5d7bfd75f6218fcc2")
                .body(obj.toString())
                .when()
                .post("https://gorest.co.in/public/v2/users");
        String name = res.jsonPath().getString("name");
        String email = res.jsonPath().getString("email");
        String gender = res.jsonPath().getString("gender");
        String status = res.jsonPath().getString("status");
        assertThat(name).isEqualTo(valueName);
        assertThat(email).isEqualTo(valueEmail);
        assertThat(gender).isEqualTo(valueGender);
        assertThat(status).isEqualTo(valueStatus);

        new_created_id = res.jsonPath().getInt("id");
    }

    @Test
    public void postListUser_delete() {
        int id = new_created_id;

        RestAssured.given()
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer 38cebe5affdc6038017ae850b112f50d15613c3f40d9d2a5d7bfd75f6218fcc2")
                .when().delete("https://gorest.co.in/public/v2/users/" + id)
                .then().statusCode(204);
    }
}
