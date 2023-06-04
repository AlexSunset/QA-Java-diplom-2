import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserOrdersTest extends BaseTest{

    @Test
    public void getUserOrdersForAuthorizedUserSuccessTrue(){
        String accessToken = getUserAPI().registerUser(getRegisterUserPOJO()).jsonPath().get("accessToken");
        Response response = getOrderAPI().getUserOrders(accessToken);
        response.then().assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void getUserOrdersForNotAuthorizedUserUnauthorizedError(){
        Response response = getOrderAPI().getUserOrders("");
        response.then().assertThat()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
