import model.Product.Product;
import model.Product.ProductInterface;
import model.order.Order;
import model.order.OrderFactory;
import model.order.OrderStatus;
import services.*;
import model.*;
import java.util.*;

public class MainService {
    static MainService instance = null;

    private Dictionary<Integer, String> employeeItems = new Hashtable<Integer, String>();
    private DistributorService distributorService;
    private OrderService orderService;
    private Scanner scanner;

    static public MainService init(
            OrderService orderService,
            DistributorService distributorService,
            Scanner scanner
    ) {
        if (instance == null) {
            instance = new MainService(
                    orderService,
                    distributorService,
                    scanner
            );
        }
        return instance;
    }

    private MainService(
            OrderService orderService,
            DistributorService distributorService,
            Scanner scanner
    ) {
        this.orderService = orderService;
        this.distributorService = distributorService;
        this.scanner = scanner;
    }

    public void initEmployeeItems() {
        employeeItems.put(1, "List all distributors");
        employeeItems.put(2, "List products from distributor");
        employeeItems.put(3, "Create order");
        employeeItems.put(4, "List all orders");
        employeeItems.put(5, "See order details");
        employeeItems.put(6, "Place order");
        employeeItems.put(7, "See order status");
        employeeItems.put(8, "Exit");
    }

    private void showMenuOptions(){
        System.out.println("Employee menu:");
        ArrayList<Integer> keys = new ArrayList<Integer>();
        Iterator<Integer> keyIterator = employeeItems.keys().asIterator();
        while (keyIterator.hasNext()) {
            keys.add(keyIterator.next());
        }
        keys.sort(null);

        for (Integer key : keys) {
            System.out.println(key + ". " + employeeItems.get(key));
        }
    }

    private void main () {
        System.out.println("\n\n");
        System.out.println("Welcome to the Stock Management App!");
        showMenuOptions();
        System.out.print("Enter option: ");
        Integer option = scanner.nextInt();
        switch (option) {
            case 1:
                listAllDistributors();
                break;
            case 2:
                showAllProductsSupplied();
                break;
            case 3:
                createOrder();
                break;
            case 4:
                listAllOrders();
                break;
            case 5:
                seeOrderDetails();
                break;
            case 6:
                placeOrder();
                break;
            case 7:
                seeOrderStatus();
                break;
            case 8:
                exit();
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }


    private void listAllDistributors(){
        distributorService.listAllDistributors();
    }

    private void showAllProductsSupplied(){
        listAllDistributors();
        System.out.println("Enter distributor ID: ");
        Integer distributorId = scanner.nextInt();
        distributorService.showAllProductsSupplied(distributorId);
    }

    private void showAllProductsSupplied(Integer distributorId){
        distributorService.showAllProductsSupplied(distributorId);
    }

    private void createOrder(){
        Double price = 0.0;
        Dictionary<ProductInterface, Integer> products = new Hashtable<ProductInterface, Integer>();

        listAllDistributors();
        System.out.println("Choose a distributor: ");
        Integer distributorId = scanner.nextInt();

        distributorService.showAllProductsSupplied(distributorId);
        while (true){
            System.out.println("Enter product id: ");
            Integer productId = scanner.nextInt();
            System.out.println("Enter quantity: ");
            Integer quantity = scanner.nextInt();

            ProductInterface product = distributorService.getProductFromDistributor(distributorId, productId);
            products.put(product, quantity);
            price += product.getPrice() * quantity;

            System.out.println("Add another product? (y/n)");
            String answer = scanner.next();
            if (answer.equals("n")) {
                break;
            }
        }

        Order order = OrderFactory.createOrder(distributorService.getDistributorById(distributorId), products, OrderStatus.PENDING, price);
        orderService.placeOrder(order);
        System.out.println("Order placed successfully!");
    }

    private void listAllOrders() {
        orderService.listAllOrders();
    }

    private void seeOrderDetails() {
        System.out.print("Enter order ID: ");
        Integer orderId = scanner.nextInt();
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            System.out.println(order.toString());
        } else {
            System.out.println("Order not found.");
        }
    }

    private void placeOrder() {
        createOrder();
    }

    private void seeOrderStatus() {
        System.out.print("Enter order ID: ");
        Integer orderId = scanner.nextInt();
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            System.out.println("Order Status: " + order.getOrderStatus());
        } else {
            System.out.println("Order not found.");
        }
    }

    private void exit() {
        System.out.println("Exiting the Stock Management App. Goodbye!");
        System.exit(0);
    }

    public void mainLoop(){
        while (true) {main();}
    }
}
