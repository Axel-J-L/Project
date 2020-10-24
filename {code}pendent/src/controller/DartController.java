package controller;

import tools.Input;
import tools.Menus;

public class DartController {
    private final Menus menus;
    private final Input input = Input.getInstance();
    private final Storage storage;
    private final String invalidInput = System.lineSeparator() + "--- Invalid input ---";

    public DartController() {
        this.menus = new Menus();
        this.storage = new Storage();
    }

    public void run() {
        storage.readFile();
        mainMenu();
    }

    private void exit() {
        Input.getInstance().tearDown();
        System.out.println(Input.EOL + Menus.DIVIDER + Input.EOL + "     Good Bye!" + Input.EOL + Menus.DIVIDER);
        System.exit(0);
    }

    private boolean authenticate(String password) {
        String enteredPassword = input.getInput("Enter your password: ");
        return password.equals(enteredPassword);
    }

    private void authManager() {
        String password = "admin1234";
        boolean authSuccess = authenticate(password);

        if (authSuccess) {
            managerMenu();
        } else {
            System.out.println(Input.EOL + "*** Wrong password *** " + Input.EOL);
            mainMenu();
        }
    }

    private void authEmployee() {
        String password = "password123";

        boolean authSuccess = authenticate(password);

        if (authSuccess) {
            employeeMenu();
        } else {
            System.out.println(Input.EOL + "*** Wrong password ***" + Input.EOL);
            mainMenu();
        }
    }

    private void mainMenu() {
        menus.mainMenu();
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    authManager();
                    managerMenu();
                    input.userCheck();
                }
                case "2" -> {
                    authEmployee();
                    employeeMenu();
                    input.userCheck();
                }
                case "3" -> {
                    customerMenu();
                    input.userCheck();
                }
                case "4" -> exit();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    mainMenu();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void managerMenu() {
        menus.managerMenu();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.print(Input.EOL + Input.ANSI_PURPLE + ">> New Employee" + Input.ANSI_RESET + Input.EOL);
                    storage.addEmployee();
                    input.userCheck();
                    managerMenu();
                }
                case "2" -> {
                    storage.viewEmployee();
                    storage.removeEmployee();
                    input.userCheck();
                    managerMenu();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Employees" + Input.ANSI_RESET);
                    storage.viewEmployee();
                    input.userCheck();
                    managerMenu();
                }
                case "4" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Transaction History" + Input.ANSI_RESET);
                    storage.viewTransactions();
                    input.userCheck();
                    managerMenu();
                }
                case "5" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Most Profitable Item" + Input.ANSI_RESET);
                    storage.itemsByProfit();
                    input.userCheck();
                    managerMenu();
                }
                case "6" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> View Rent Frequency" + Input.ANSI_RESET);
                    storage.rentalFrequency();
                    input.userCheck();
                    managerMenu();
                }
                case "7" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Most Profitable Customer" + Input.ANSI_RESET);
                    storage.bestCustomer();
                    input.userCheck();
                    managerMenu();
                }
                case "8" -> mainMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    managerMenu();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void employeeMenu() {
        menus.employeeMenu();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> empGameOptions();
                case "2" -> empAlbumOptions();
                case "3" -> empCustomerOptions();
                case "4" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Total Rent Profit" + Input.ANSI_RESET);
                    storage.totalProfit();
                    input.userCheck();
                    employeeMenu();
                }
                case "5" -> mainMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    employeeMenu();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void empGameOptions() {
        menus.empGameOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> New Game" + Input.ANSI_RESET);
                    storage.addGame();
                    input.userCheck();
                    empGameOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Remove Game" + Input.ANSI_RESET);
                    storage.viewGames();
                    storage.removeGame();
                    input.userCheck();
                    empGameOptions();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Games" + Input.ANSI_RESET);
                    storage.viewGames();
                    input.userCheck();
                    empGameOptions();
                }
                case "4" -> employeeMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    empGameOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void empAlbumOptions() {
        menus.empAlbumOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> New Album" + Input.ANSI_RESET);
                    storage.addAlbum();
                    input.userCheck();
                    empAlbumOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Remove Album" + Input.ANSI_RESET);
                    storage.viewAlbums();
                    storage.removeAlbum();
                    input.userCheck();
                    empAlbumOptions();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Albums" + Input.ANSI_RESET);
                    storage.viewAlbums();
                    input.userCheck();
                    empAlbumOptions();
                }
                case "4" -> employeeMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    empAlbumOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void empCustomerOptions() {
        menus.empCustomerOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Membership Requests" + Input.ANSI_RESET);
                    storage.membershipRequestList();
                    input.userCheck();
                    empCustomerOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> New Customer" + Input.ANSI_RESET);
                    storage.addCustomer();
                    input.userCheck();
                    empCustomerOptions();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Remove Customer" + Input.ANSI_RESET);
                    storage.removeCustomer();
                    input.userCheck();
                    empCustomerOptions();
                }
                case "4" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Customers" + Input.ANSI_RESET);
                    storage.viewCustomer();
                    input.userCheck();
                    empCustomerOptions();
                }
                case "5" -> employeeMenu();
                case "secret" -> s();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    empCustomerOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void customerMenu() {
        menus.customerMenu();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> cusGameOptions();
                case "2" -> cusAlbumOptions();
                case "3" -> cusMembershipOptions();
                case "4" -> inboxMenu();
                case "5" -> mainMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    customerMenu();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void cusGameOptions() {
        menus.cusGameOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Rent Game" + Input.ANSI_RESET);
                    storage.rentGame();
                    input.userCheck();
                    cusGameOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Return Game" + Input.ANSI_RESET);
                    storage.returnGame();
                    input.userCheck();
                    cusGameOptions();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Search Games by Genre" + Input.ANSI_RESET);
                    storage.searchGames();
                    input.userCheck();
                    cusGameOptions();
                }
                case "4" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Games by Year" + Input.ANSI_RESET);
                    storage.viewGames();
                    input.userCheck();
                    cusGameOptions();
                }
                case "5" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Games by Rating" + Input.ANSI_RESET);
                    storage.viewGamesByRating();
                    input.userCheck();
                    cusGameOptions();
                }
                case "6" -> customerMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    cusGameOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void cusAlbumOptions() {
        menus.cusAlbumOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Rent Album" + Input.ANSI_RESET);
                    storage.rentAlbum();
                    input.userCheck();
                    cusAlbumOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Return Album:" + Input.ANSI_RESET);
                    storage.viewAlbums();
                    storage.returnAlbum();
                    input.userCheck();
                    cusAlbumOptions();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Search Albums by Year" + Input.ANSI_RESET);
                    storage.searchAlbums();
                    input.userCheck();
                    cusAlbumOptions();
                }
                case "4" -> {
                    System.out.println(Input.EOL +
                            Input.ANSI_PURPLE + ">> All Albums by Year" + Input.ANSI_RESET);
                    storage.viewAlbums();
                    input.userCheck();
                    cusAlbumOptions();
                }
                case "5" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> All Albums by Rating" + Input.ANSI_RESET);
                    storage.viewAlbumsByRating();
                    input.userCheck();
                    cusAlbumOptions();
                }
                case "6" -> customerMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    cusAlbumOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void cusMembershipOptions() {
        menus.cusMembershipOptions();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Add Membership" + Input.ANSI_RESET);
                    storage.requestMembership();
                    input.userCheck();
                    cusMembershipOptions();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Upgrade Membership" + Input.ANSI_RESET);
                    storage.upgradeMembership();
                    input.userCheck();
                    cusMembershipOptions();
                }
                case "3" -> customerMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    cusMembershipOptions();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void inboxMenu() {
        menus.inboxMenu();
        System.out.print(Menus.PROMPT);
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> View Messages" + Input.ANSI_RESET);
                    storage.viewMessages();
                    input.userCheck();
                    inboxMenu();
                }
                case "2" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Send Message" + Input.ANSI_RESET);
                    storage.sendMessage();
                    input.userCheck();
                    inboxMenu();
                }
                case "3" -> {
                    System.out.println(Input.EOL + Input.ANSI_PURPLE + ">> Delete Message" + Input.ANSI_RESET);
                    storage.removeMessages();
                    input.userCheck();
                    inboxMenu();
                }
                case "4" -> customerMenu();
                default -> {
                    System.out.println(Menus.DIVIDER + Input.EOL + invalidInput);
                    inboxMenu();
                    System.out.print(Menus.PROMPT);
                }
            }
        } while (true);
    }

    private void secretMenu() {
        menus.secret();
        System.out.print(Input.ANSI_WHITE + "---------------------" + Input.ANSI_RESET + Input.EOL + "Choose your option: ");
        do {
            String choice = Input.input.nextLine();
            switch (choice) {
                case "1" -> {
                    storage.team();
                    input.userCheck();
                    secretMenu();
                }
                case "2" -> {
                    System.out.println("Congrats on finding the Secret Menu");
                    mainMenu();
                }
                default -> {
                    System.out.println(Input.ANSI_WHITE + "---------------------" + Input.ANSI_RESET + Input.EOL + "--- Invalid input ---");
                    secretMenu();
                    System.out.print(Input.ANSI_WHITE + "---------------------" + Input.ANSI_RESET + Input.EOL + "Choose your option: ");
                }
            }
        } while (true);
    }

    private void s() {
        String str = "open";
        boolean n = authenticate(str);
        if (n) {
            secretMenu();
        } else {
            mainMenu();
        }
    }
}