package controller;

import items.properties.Rental;
import people.Customer;
import people.features.Membership;
import tools.Input;
import tools.Menus;
import tools.s.Secret;

import java.util.ArrayList;

public class DartController {
    private final Menus menus;
    private final Input input = Input.getInstance();
    private final Storage storage;
    private final String invalidInput = System.lineSeparator() + "--- Invalid input ---";
    private ArrayList<Membership> requestList = null;

    public DartController() {
        this.menus = new Menus();
        this.storage = new Storage();
    }

    public void run() {
        mainMenu();
    }

    private void exit() {
        Input.getInstance().tearDown();
        System.out.println(input.EOL + menus.DIVIDER + input.EOL + "     Good Bye!" + input.EOL + menus.DIVIDER);
        System.exit(0);
    }

    public boolean authenticate(String password) { // authenticates passwords
        String enteredPassword = input.getInput("Enter your password: ");
        return password.equals(enteredPassword);
    }

    public void authManager() {
        String password = "admin1234";
        boolean authSuccess = authenticate(password);

        if (authSuccess) {
            managerMenu();
        } else {
            System.out.println(input.EOL + "*** Wrong password *** " + input.EOL);
            mainMenu();
        }
    }

    public void authEmployee() {
        String password = "password123";

        boolean authSuccess = authenticate(password);

        if (authSuccess) {
            employeeMenu();
        } else {
            System.out.println(input.EOL + "*** Wrong password ***" + input.EOL);
            mainMenu();
        }
    }


    private void membershipRequestList() {
        for (Membership request : requestList) {
            System.out.println("The following Customer has requested a membership: ");
            System.out.println("Customer : " + request.getName() + input.EOL + " Requesting: " + request.getType() + " membership");
            String requestListAnswer = input.getInput("(Y/N): ");
            if (requestListAnswer.equalsIgnoreCase("Y")) {
                for (Customer requested : storage.getCustomers()) {
                    if (requested.getName().equalsIgnoreCase(request.getName())) {
                        requested.setMembershipType(request.getType());
                    }
                }
            } else {
                System.out.println("Okay, fair enough.");
            }
        }
        requestList.clear();
    }


    public void mainMenu() {
        menus.mainMenu();
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    //authManager();
                    managerMenu();
                    input.userCheck();
                    break;
                case "2":
                    // authEmployee();
                    employeeMenu();
                    input.userCheck();

                    break;
                case "3":
                    customerMenu();
                    input.userCheck();
                    break;
                case "4":
                    exit();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    mainMenu();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void managerMenu() {
        menus.managerMenu();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.print(input.EOL + input.ANSI_PURPLE + ">> New Employee" + input.ANSI_RESET + input.EOL);
                    storage.addEmployee();
                    input.userCheck();
                    managerMenu();
                    break;
                case "2":
                    storage.viewEmployee();
                    storage.removeEmployee();
                    input.userCheck();
                    managerMenu();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Employees" + input.ANSI_RESET);
                    storage.viewEmployee();
                    input.userCheck();
                    managerMenu();
                    break;
                case "4":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Transaction History" + input.ANSI_RESET);
                    storage.viewTransactions();
                    input.userCheck();
                    managerMenu();
                    break;
                case "5":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Most Profitable Item" + input.ANSI_RESET);
                    storage.itemsByProfit();
                    input.userCheck();
                    managerMenu();
                    break;
                case "6":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> View Rent Frequency" + input.ANSI_RESET);
                    storage.gamesByFrequency();
                    storage.albumsByFrequency();
                    input.userCheck();
                    managerMenu();
                    break;
                case "7":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Most Profitable Customer" + input.ANSI_RESET);
                    System.out.println("to be added.");
                    input.userCheck();
                    managerMenu();
                    break;
                case "8":
                    mainMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    managerMenu();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void employeeMenu() {
        if (requestList != null) {
            membershipRequestList();
        }
        menus.employeeMenu();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    empGameOptions();
                    break;
                case "2":
                    empAlbumOptions();
                    break;
                case "3":
                    empCustomerOptions();
                    break;
                case "4":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Total Rent Profit" + input.ANSI_RESET);
                    Rental rental = new Rental();
                    storage.totalProfit();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "5":
                    mainMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    employeeMenu();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void empGameOptions() {
        menus.empGameOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> New Game" + input.ANSI_RESET);
                    storage.addNewGame();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Remove Game" + input.ANSI_RESET);
                    storage.viewGames();
                    storage.removeGame();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Games" + input.ANSI_RESET);
                    storage.viewGames();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "4":
                    employeeMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    empGameOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void empAlbumOptions() {
        menus.empAlbumOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> New Album" + input.ANSI_RESET);
                    storage.addAlbum();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Remove Album" + input.ANSI_RESET);
                    storage.viewAlbums();
                    storage.removeAlbum();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Albums" + input.ANSI_RESET);
                    storage.viewAlbums();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "4":
                    employeeMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    empAlbumOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void empCustomerOptions() {
        menus.empCustomerOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Membership Requests" + input.ANSI_RESET);
                    membershipRequestList();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> New Customer" + input.ANSI_RESET);
                    storage.addCustomer();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Remove Customer" + input.ANSI_RESET);
                    storage.removeCustomer();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "4":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Customers" + input.ANSI_RESET);
                    storage.viewCustomer();
                    input.userCheck();
                    employeeMenu();
                    break;
                case "5":
                    employeeMenu();
                    break;
                case "secret":
                    s();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    empCustomerOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void customerMenu() {
        menus.customerMenu();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    cusGameOptions();
                    break;
                case "2":
                    cusAlbumOptions();
                    break;
                case "3":
                    cusMembershipOptions();
                case "4":
                    inboxMenu();
                    break;
                case "5":
                    mainMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    customerMenu();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void cusGameOptions() {
        menus.cusGameOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Rent Game" + input.ANSI_RESET);
                    storage.viewGames();
                    storage.rentGame();
                    input.userCheck();
                    cusGameOptions();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Return Game" + input.ANSI_RESET);
                    storage.returnGame();
                    input.userCheck();
                    cusGameOptions();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Search Games by Genre" + input.ANSI_RESET);
                    storage.searchGames();
                    input.userCheck();
                    cusGameOptions();
                    break;
                case "4":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Games by Year" + input.ANSI_RESET);
                    storage.viewGames();
                    input.userCheck();
                    cusGameOptions();
                    break;
                case "5":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Games by Rating" + input.ANSI_RESET);
                    storage.viewGamesByRating();
                    input.userCheck();
                    cusGameOptions();
                    break;
                case "6":
                    customerMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    cusGameOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void cusAlbumOptions() {
        menus.cusAlbumOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Rent Album" + input.ANSI_RESET);
                    storage.viewAlbums();
                    storage.rentAlbum();
                    input.userCheck();
                    cusAlbumOptions();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Return Album:" + input.ANSI_RESET);
                    storage.viewAlbums();
                    storage.returnAlbum();
                    input.userCheck();
                    cusAlbumOptions();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Search Albums by Year" + input.ANSI_RESET);
                    storage.searchAlbums();
                    input.userCheck();
                    cusAlbumOptions();
                    break;
                case "4":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Albums by Year" + input.ANSI_RESET);
                    storage.viewAlbums();
                    input.userCheck();
                    cusAlbumOptions();
                    break;
                case "5":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> All Albums by Rating" + input.ANSI_RESET);
                    storage.viewAlbumsByRating();
                    input.userCheck();
                    cusAlbumOptions();
                    break;
                case "6":
                    customerMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    cusAlbumOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void cusMembershipOptions() {
        menus.cusMembershipOptions();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Add Membership" + input.ANSI_RESET);
                    requestList = storage.addMembership();
                    input.userCheck();
                    cusMembershipOptions();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Upgrade Membership" + input.ANSI_RESET);
                    requestList = storage.upgradeMembership();
                    input.userCheck();
                    cusMembershipOptions();
                    break;
                case "3":
                    customerMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    cusMembershipOptions();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
    }

    public void inboxMenu() {
        menus.inboxMenu();
        System.out.print(menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> View Messages" + input.ANSI_RESET);
                    storage.viewMessages();
                    input.userCheck();
                    inboxMenu();
                    break;
                case "2":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Send Message" + input.ANSI_RESET);
                    storage.sendMessage();
                    input.userCheck();
                    inboxMenu();
                    break;
                case "3":
                    System.out.println(input.EOL + input.ANSI_PURPLE + ">> Delete Message" + input.ANSI_RESET);
                    storage.removeMessages();
                    input.userCheck();
                    inboxMenu();
                    break;
                case "4":
                    customerMenu();
                    break;
                default:
                    System.out.println(menus.DIVIDER + input.EOL + invalidInput);
                    inboxMenu();
                    System.out.print(menus.PROMPT);
                    break;
            }
        } while (true);
}



















































































private void s(){Secret s=new Secret();String str ="open";boolean n=authenticate(str);if(n){s.secret();} else{mainMenu();}}}