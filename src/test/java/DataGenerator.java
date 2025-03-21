import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.apache.http.HttpStatus;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class DataGenerator {
    private DataGenerator() {

    }

    private static final Faker FAKER = new Faker(new Locale("en"));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    static void setRequest(DataGenerator.UserInfo userInfo) {
        given()
                .spec(requestSpec)
                .body(userInfo)
                .when().log().all()
                .post("/api/system/users")
                .then().log().all()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return FAKER.name().username();
    }

    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    public static class Registration {
        private Registration() {

        }

        public static UserInfo getUser(String status) {
            return new UserInfo(getRandomLogin(), getRandomPassword(), status);
        }

        public static UserInfo getRegistredUser(String status) {
            var user = getUser(status);
            setRequest(user);
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }


}
