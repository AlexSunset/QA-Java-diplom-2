import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTest extends BaseTest{

    @Test
    @Description("Changing name and email for authorized user. Basic flow")
    public void changeUserDataWithAuthorization(){
        Response responseCreate = getUserAPI().registerUser(getRegisterUserPOJO());
        String accessToken = responseCreate.jsonPath().get("accessToken");
        Response responseChange = getUserAPI().changeUserData(getChangeUserDataPOJO(), accessToken);
        responseChange.then().assertThat().body("success", equalTo(true))
                .and()
                .body("user.email", equalTo(super.getChangeEmail()))
                .and()
                .body("user.name", equalTo(super.getChangeName()))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @Description("Trying changing name and email for not authorized user. Expected unauthorized error")
    public void changeUserDataWithoutAuthorization(){
        Response responseChange = getUserAPI().changeUserData(getChangeUserDataPOJO(), "");
        responseChange.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
