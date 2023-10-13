import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public static int getDiscountPercentage(List<String> items) {
        if (items.contains("book")) {
            return 5;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        List<String> items = new ArrayList<>();
        System.out.println(items);
        System.out.println(ShoppingCart.getDiscountPercentage(items));

        items.add("Apple");
        System.out.println(items);
        System.out.println(ShoppingCart.getDiscountPercentage(items));

        items.add("book");
        System.out.println(items);
        System.out.println(ShoppingCart.getDiscountPercentage(items));

        items.remove("book");
        System.out.println(items);
        System.out.println(ShoppingCart.getDiscountPercentage(items));

        items.add("book");
        items.add("book");
        items.remove("book");
        System.out.println(items);
        System.out.println(ShoppingCart.getDiscountPercentage(items));
    }
}