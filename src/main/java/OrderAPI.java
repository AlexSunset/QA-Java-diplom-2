import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderAPI {

    public final static String ORDER_ENDPOINT = "api/orders";

    public Response createOrder(CreateOrderPOJO createOrderPOJO, String accessToken){
        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(createOrderPOJO)
                .post(ORDER_ENDPOINT);
    }

    public Response getUserOrders(String accessToken){
        return given()
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .get(ORDER_ENDPOINT);
    }
}
