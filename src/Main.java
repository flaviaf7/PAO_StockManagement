import model.Address;
import services.DistributorService;
import services.OrderService;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DistributorService distributorService = new DistributorService();
        OrderService orderService = new OrderService();
        MainService mainService = MainService.init(
                orderService,
                distributorService,
                scanner
                );
        mainService.initEmployeeItems();
        mainService.mainLoop();
    }
}