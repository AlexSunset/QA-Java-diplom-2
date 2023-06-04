import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest{

    @Test
    public void correctLoginResponseAndStatusCode(){
        getUserAPI().registerUser(getRegisterUserPOJO());
        Response response = getUserAPI().loginUser(getLoginUserPOJO());
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
    public void loginWithIncorrectEmail(){
        getUserAPI().registerUser(getRegisterUserPOJO());
        LoginUserPOJO loginUserPOJO = new LoginUserPOJO("", super.getPassword());
        Response response = getUserAPI().loginUser(loginUserPOJO);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void loginWithIncorrectPassword(){
        getUserAPI().registerUser(getRegisterUserPOJO());
        LoginUserPOJO loginUserPOJO = new LoginUserPOJO(super.getEmail(), "");
        Response response = getUserAPI().loginUser(loginUserPOJO);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
