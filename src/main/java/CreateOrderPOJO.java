import java.util.ArrayList;

public class CreateOrderPOJO {

    private ArrayList<String> ingredients;

    public CreateOrderPOJO(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
