import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<String> items = new ArrayList<>();
    private boolean bookAdded = false;

    public void addItem(String item) {
        items.add(item);
        if (item.equals("book")) {
            bookAdded = true;
        }
    }

    public void removeItem(String item) {
        items.remove(item);
        if (item.equals("book")) {
            bookAdded = false;
        }
    }

    public int getDiscountPercentage() {
        if (bookAdded) {
            return 5;
        } else {
            return 0;
        }
    }

    public List<String> getItems() {
        return new ArrayList<>(items);
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("Apple");
        System.out.println(cart.getItems());
        System.out.println(cart.getDiscountPercentage());

        cart.addItem("book");
        System.out.println(cart.getItems());
        System.out.println(cart.getDiscountPercentage());

        cart.getItems().remove("book");
        System.out.println(cart.getItems());
        System.out.println(cart.getDiscountPercentage());

        cart.removeItem("book");
        System.out.println(cart.getItems());
        System.out.println(cart.getDiscountPercentage());

        cart.addItem("book");
        cart.addItem("book");
        cart.removeItem("book");
        System.out.println(cart.getItems());
        System.out.println(cart.getDiscountPercentage());
    }
}