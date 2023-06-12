import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class UserOrdersTest extends BaseTest{

    @Test
    @Description("Get order list for authorized user. Basic flow")
    public void getUserOrdersForAuthorizedUserSuccessTrue(){
        String accessToken = getUserAPI().registerUser(getRegisterUserPOJO()).jsonPath().get("accessToken");
        Response response = getOrderAPI().getUserOrders(accessToken);
        response.then().assertThat()
                .body("success", equalTo(true))
                .body("orders", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @Description("Get order list for unauthorized user. Expected unauthorized error")
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
