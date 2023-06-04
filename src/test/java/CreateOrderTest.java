import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CreateOrderTest extends BaseTest{

    @Test
    public void createOrderWithAuthorization(){
        String accessToken = getUserAPI().registerUser(getRegisterUserPOJO()).jsonPath().get("accessToken");
        Response response = getOrderAPI().createOrder(getCreateOrderPOJO(), accessToken);
        response.then().assertThat()
                .body("name", notNullValue())
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void createOrderWithoutAuthorization(){
        Response response = getOrderAPI().createOrder(getCreateOrderPOJO(), "");
        response.then().assertThat()
                .body("name", notNullValue())
                .and()
                .body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Test
    public void createOrderWithoutIngredients(){
        Response response = getOrderAPI().createOrder(new CreateOrderPOJO(new ArrayList<>()), "");
        response.then().assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .body("success", equalTo(false))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void createOrderWithWrongIngredients(){
        ArrayList<String> ingredientsWrong = new ArrayList<>(Collections.singleton("1234"));
        Response response = getOrderAPI().createOrder(new CreateOrderPOJO(ingredientsWrong), "");
        response.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
