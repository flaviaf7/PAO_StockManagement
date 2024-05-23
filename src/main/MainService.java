package main;

import exception.DistributorNotFoundException;
import model.Distributor;
import model.Product.ProductInterface;
import model.order.Order;
import model.order.OrderFactory;
import model.order.OrderStatus;
import services.*;

import java.util.*;

public class MainService {
    static MainService instance = null;

    private final Hashtable<Integer, String> employeeItems = new Hashtable<Integer, String>();
    private DistributorService distributorService;
    private OrderService orderService;
    private ProductService productService;
    private Scanner scanner;

    static public MainService init(
            OrderService orderService,
            DistributorService distributorService,
            ProductService productService,
            Scanner scanner
    ) {
        if (instance == null) {
            instance = new MainService(
                    orderService,
                    distributorService,
                    productService,
                    scanner
            );
        }
        return instance;
    }

    private MainService(
            OrderService orderService,
            DistributorService distributorService,
            ProductService productService,
            Scanner scanner
    ) {
        this.orderService = orderService;
        this.distributorService = distributorService;
        this.productService = productService;
        this.scanner = scanner;
    }

    public void showMainMenu() {
        System.out.println("Welcome to the Stock Management App!");
        System.out.println("Please choose an option:");
        System.out.println("1. Connect as employee");
        System.out.println("2. Connect as distributor");
        System.out.println("3. Exit");
        System.out.print("Enter option: ");
    }
    public void initEmployeeItems() {
        employeeItems.put(1, "List all distributors");
        employeeItems.put(2, "List products from distributor");
        employeeItems.put(3, "Create order");
        employeeItems.put(4, "List all orders");
        employeeItems.put(5, "See order details");
        employeeItems.put(6, "See order status");
        employeeItems.put(7, "Exit");
    }

    private void showMenuOptions(){
        System.out.println("Employee menu:");
        List<Integer> keys = new ArrayList<>();
        Iterator<Integer> keyIterator = employeeItems.keys().asIterator();
        while (keyIterator.hasNext()) {
            keys.add(keyIterator.next());
        }
        keys.sort(null);

        for (Integer key : keys) {
            System.out.println(key + ". " + employeeItems.get(key));
        }
    }

    private void main () throws DistributorNotFoundException {
        showMainMenu();
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                AuditService.getInstance().logAction("Connected as employee.");
                connectAsEmployee();
                break;
            case 2:
                AuditService.getInstance().logAction("Connected as distributor.");
                connectAsDistributor();
                break;
            case 3:
                exitMainMenu();
                AuditService.getInstance().logAction("Exited main menu.");
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }

    }

    private void connectAsEmployee() throws DistributorNotFoundException {
        while(true) {
            System.out.println("Connecting as employee...");
            showMenuOptions();
            int option2 = scanner.nextInt();
            switch (option2) {
                case 1:
                    AuditService.getInstance().logAction("Listed all Distributors.");
                    listAllDistributors();
                    break;
                case 2:
                    AuditService.getInstance().logAction("Listed all Products Supplied.");
                    showAllProductsSupplied();
                    break;
                case 3:
                    AuditService.getInstance().logAction("Created order.");
                    createOrder();
                    break;
                case 4:
                    AuditService.getInstance().logAction("Listed all Orders.");
                    listAllOrders();
                    break;
                case 5:
                    AuditService.getInstance().logAction("Showed order details.");
                    seeOrderDetails();
                    break;
                case 6:
                    AuditService.getInstance().logAction("Showed order status.");
                    seeOrderStatus();
                    break;
                case 7:
                    AuditService.getInstance().logAction("Exited employee menu.");
                    System.out.println("Exiting Employee Menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }

            try {
                System.out.println("Press Enter to continue...");
                System.in.read();
                Thread.sleep(1000);  // Pause for 1 second
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connectAsDistributor() {
        System.out.println("Connecting as distributor...");
        try {
            System.out.println("Welcome! Please log in as a distributor.");

            System.out.println("Available distributors:");
            distributorService.listAllDistributors();

            System.out.print("Enter the ID of the distributor: ");
            Integer distributorId = scanner.nextInt();
            scanner.nextLine();

            Distributor selectedDistributor = distributorService.getDistributorById(distributorId);

            System.out.println();
            System.out.print("Enter your email address: ");
            String email = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (selectedDistributor.getEmailAddress().equals(email) && selectedDistributor.getPassword().equals(password)) {
                System.out.println("Login successful!");
                AuditService.getInstance().logAction("Logged in as Distributor.");
                distributorMenu(selectedDistributor);
            } else {
                System.out.println("Incorrect email address or password. Please try again.");
            }
        } catch (DistributorNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void orderManagement(Distributor distributor) {
        while (true) {
            System.out.println("\nOrder Management Menu:");
            System.out.println("1. List all orders");
            System.out.println("2. Update order status");
            System.out.println("3. Delete order");
            System.out.println("4. Return to distributor menu");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    AuditService.getInstance().logAction("Listed all Orders of a distributor.");
                    orderService.listAllOrders(distributor);
                    break;
                case 2:
                    AuditService.getInstance().logAction("Updated order.");
                    orderService.updateOrder(scanner, distributor);
                    break;
                case 3:
                    AuditService.getInstance().logAction("Removed order.");
                    orderService.removeOrder(scanner, distributor);
                    break;
                case 4:
                    AuditService.getInstance().logAction("Exited order management menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }

            try {
                System.out.println("Press Enter to continue...");
                System.in.read();
                Thread.sleep(1000);  // Pause for 1 second
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void productManagement(Distributor distributor) {
        while (true) {
            System.out.println("\nProduct Management Menu:");
            System.out.println("1. List all products");
            System.out.println("2. Add new product");
            System.out.println("3. Update product");
            System.out.println("4. Delete product");
            System.out.println("5. Return to distributor menu");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    AuditService.getInstance().logAction("Listed all products of a distributor.");
                    productService.listAllProducts(distributor);
                    break;
                case 2:
                    AuditService.getInstance().logAction("Added a product.");
                    productService.addProduct(scanner, distributor);
                    break;
                case 3:
                    AuditService.getInstance().logAction("Updated a product.");
                    productService.updateProduct(scanner, distributor);
                    break;
                case 4:
                    AuditService.getInstance().logAction("Removed a product.");
                    productService.removeProduct(scanner, distributor);
                    break;
                case 5:
                    AuditService.getInstance().logAction("Exited product management menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }

            try {
                System.out.println("Press Enter to continue...");
                System.in.read();
                Thread.sleep(1000);  // Pause for 1 second
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void distributorMenu(Distributor distributor) throws DistributorNotFoundException {
        if (distributor == null){
            return;
        }
        while (true) {
            System.out.println("\nWelcome, " + distributor.getName() + "!");
            System.out.println("Distributor Menu:");
            System.out.println("1. Order Management");
            System.out.println("2. Product Management");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    AuditService.getInstance().logAction("Entered order management menu.");
                    orderManagement(distributor);
                    break;
                case 2:
                    AuditService.getInstance().logAction("Enter product management menu.");
                    productManagement(distributor);
                    break;
                case 3:
                    AuditService.getInstance().logAction("Exited distributor menu.");
                    System.out.println("Exiting distributor session.");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
                    break;
            }
        }
    }

    private void listAllDistributors(){
        distributorService.listAllDistributors();

    }

    private void showAllProductsSupplied() {
        listAllDistributors();
        System.out.println("Enter distributor ID: ");
        Integer distributorId = scanner.nextInt();
        distributorService.showAllProductsSupplied(distributorId);
    }

    //
    private void createOrder() throws DistributorNotFoundException {
        Double price = 0.0;
        Hashtable<ProductInterface, Integer> products = new Hashtable<>();

        if (distributorService.getDistributors().isEmpty()) {
            System.out.println("No distributors found. Cannot create an order.");
            return;
        }

        listAllDistributors();
        System.out.println("Choose a distributor: ");
        Integer distributorId = scanner.nextInt();
        scanner.nextLine();

        Distributor selectedDistributor = distributorService.getDistributorById(distributorId);
        if (selectedDistributor.getProductsSupplied() == null || selectedDistributor.getProductsSupplied().isEmpty()) {
            System.out.println("The selected distributor does not have any products supplied. Cannot create an order.");
            return;
        }

        distributorService.showAllProductsSupplied(distributorId);
        while (true) {
            System.out.println("Enter product id: ");
            Integer productId = scanner.nextInt();
            scanner.nextLine(); // Consumăm newline-ul

            ProductInterface product = distributorService.getProductFromDistributor(distributorId, productId);
            if (product == null) {
                System.out.println("Product with ID " + productId + " not found.");
                System.out.println("Try again with a new ID? (y/n)");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("n")) {
                    break;
                } else if (answer.equalsIgnoreCase("y")) {
                    continue;
                } else {
                    System.out.println("Not a valid choice. Please enter 'y' or 'n'.");
                    continue;
                }
            }

            System.out.println("Enter quantity: ");
            Integer quantity = scanner.nextInt();
            scanner.nextLine();

            products.put(product, quantity);
            price += product.getPrice() * quantity;

            System.out.println("Add another product? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("n")) {
                break;
            } else if (answer.equalsIgnoreCase("y")) {
                continue;
            } else {
                System.out.println("Not a valid choice. Please enter 'y' or 'n'.");
            }
        }

        Order order = OrderFactory.createOrder(selectedDistributor, products, OrderStatus.PENDING, price);
        orderService.addOrder(order);
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
            System.out.println(order);
        } else {
            System.out.println("Order not found.");
        }
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

    private void exitMainMenu() {
        System.out.println("Exiting the Stock Management App.");
        System.exit(0);
    }

    public void mainLoop() throws DistributorNotFoundException {
        while (true) {
            main();
        }
    }
}
