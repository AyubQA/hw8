import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;


@Getter
public class Authorization {
    private static Authorization instance;
    private String authToken;

    public static String testUserLogin = "string";
    public static String testUserPassword = "string";


    private Authorization() {
        // Private constructor to prevent instantiation
    }

    public static Authorization getInstance() {
        if (instance == null) {
            instance = new Authorization();
            instance.retrieveAuthToken();
        }
        return instance;
    }

    private void retrieveAuthToken() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new User(testUserLogin, testUserPassword))
                .when()
                .post(Urls.loginURL);

        if (response.getStatusCode() == 200) {
            authToken = response.jsonPath().getString("access_token");
            System.out.println("access_token: " + authToken);
        } else {
            System.out.println("Failed to retrieve access token");
        }
    }

}
