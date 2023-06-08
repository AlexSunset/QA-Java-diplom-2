import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class RegisterUserWithoutRequiredFieldTest extends BaseTest{

    private  String email;
    private  String password;
    private  String name;

    public RegisterUserWithoutRequiredFieldTest(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] registerUserData() {
        return new Object[][]{
                {"", "123", "Alex6"},
                {"azakatov6@it-one.ru", "", "Alex6"},
                {"azakatov6@it-one.ru", "123", ""},
        };
    }

    @Test
    @Description("Trying to register user with missing fields. Parametrized test")
    public void registerUserWithoutRequiredFieldCorrectResponseBodyAndCode(){
        RegisterUserPOJO registerUserPOJO = new RegisterUserPOJO(email, password, name);
        Response response = getUserAPI().registerUser(registerUserPOJO);
        response.then().assertThat().body("success", equalTo(false))
                .and()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }
}
