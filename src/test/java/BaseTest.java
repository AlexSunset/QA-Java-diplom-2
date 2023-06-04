import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;

public class BaseTest {

    private final String email = "azakatov8@it-one.ru";
    private final String password = "123";
    private final String name = "Alex8";
    private final String changeEmail = "change_" + email;
    private final String changeName = "change_" + name;
    private final ArrayList<String> ingredients = new ArrayList<>(Collections.singleton("61c0c5a71d1f82001bdaaa6f"));


    private final RegisterUserPOJO registerUserPOJO = new RegisterUserPOJO(email, password, name);
    private final LoginUserPOJO loginUserPOJO = new LoginUserPOJO(email, password);
    private final UserAPI userAPI = new UserAPI();
    private final ChangeUserDataPOJO changeUserDataPOJO = new ChangeUserDataPOJO(changeEmail, changeName);
    private final CreateOrderPOJO createOrderPOJO = new CreateOrderPOJO(ingredients);
    private final OrderAPI orderAPI = new OrderAPI();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public void cleanUp(){
        Response responseNoChange = getUserAPI().loginUser(getLoginUserPOJO());
        if(responseNoChange.jsonPath().get("success").equals(true)){
            getUserAPI().deleteUser(responseNoChange.jsonPath().get("accessToken"));
        }
        Response responseChange = getUserAPI().loginUser(new LoginUserPOJO(changeEmail, password));
        if(responseChange.jsonPath().get("success").equals(true)){
            getUserAPI().deleteUser(responseChange.jsonPath().get("accessToken"));
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public RegisterUserPOJO getRegisterUserPOJO() {
        return registerUserPOJO;
    }

    public LoginUserPOJO getLoginUserPOJO() {
        return loginUserPOJO;
    }

    public UserAPI getUserAPI() {
        return userAPI;
    }


    public String getChangeEmail() {
        return changeEmail;
    }

    public String getChangeName() {
        return changeName;
    }

    public ChangeUserDataPOJO getChangeUserDataPOJO() {
        return changeUserDataPOJO;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public CreateOrderPOJO getCreateOrderPOJO() {
        return createOrderPOJO;
    }

    public OrderAPI getOrderAPI() {
        return orderAPI;
    }
}
