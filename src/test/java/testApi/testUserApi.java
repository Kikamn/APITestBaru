package testApi;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.File;


public class testUserApi {
    @Test
    public void getListUser(){
        File jsonSchema = new File("src/test/resources/jsonSchema/schemaUser");

        RestAssured.given().when()
                .get("https://gorest.co.in/public/v2/users") //masukan link api
                .then().log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void postListUser(){
            String valueName = "Sinta";
            String valueEmail = "sinta@gmail.com";
            String valueGender = "female";
            String valueStatus = "active";

            JSONObject obj = new JSONObject();
            obj.put("name", valueName); // ambil sesuai dengan case
            obj.put("email", valueEmail);
            obj.put("gender", valueGender);
            obj.put("status", valueStatus);

            RestAssured.given()
                    .header("Content-type", "application/json")
                    .header("Accept", "application/json")
                    .body(obj.toString())
                    .when()
                    .post("https://gorest.co.in/public/v2/users")
                    .then().log().all()
                    .assertThat().statusCode(201)
                    .assertThat().body("name", Matchers.equalTo(valueName))
                    .assertThat().body("email", Matchers.equalTo(valueEmail))
                    .assertThat().body("gender", Matchers.equalTo(valueGender))
                    .assertThat().body("status", Matchers.equalTo(valueStatus));
    }

    public void deleteListUser() {
        int id = 5913837;

        RestAssured.given().delete("https://gorest.co.in/public/v2/users", id)
                .then().statusCode(204);
    }
}
