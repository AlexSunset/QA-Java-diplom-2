import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterUserTest extends  BaseTest{

    @Test
    public void registerNewUserCorrectResponseBodyAndStatusCode(){
        Response response = getUserAPI().registerUser(getRegisterUserPOJO());
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(super.getEmail()))
                .and()
                .body("user.name", equalTo(super.getName()))
                .and()
                .body("accessToken", notNullValue())
                .and()
                .body("refreshToken", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void registerUserAlreadyExistCorrectResponseBodyAndStatusCode(){
        getUserAPI().registerUser(getRegisterUserPOJO());
        Response response = getUserAPI().registerUser(getRegisterUserPOJO());
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
}
