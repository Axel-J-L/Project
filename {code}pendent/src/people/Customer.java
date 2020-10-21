package people;

import controller.DartController;
import exceptions.InvalidInputException;
import people.features.membership.Membership;
import people.features.Message;
import people.features.membership.Regular;
import tools.Input;
import java.util.ArrayList;
import java.util.Arrays;

public class Customer extends Person {

    private Membership membership;
    private double spentMoney;
    private ArrayList<Message> inbox;
    private final Input input = Input.getInstance();
    private int credits;
    private int currentRentals;
    private ArrayList<Customer> memberRequest = new ArrayList<>();
    private String type;

    public Customer() {
    }

    public Customer(String id, String name) throws InvalidInputException {
        super(id, name);
        this.membership = new Regular();
        this.credits = 0;
        this.currentRentals = 0;
        this.inbox = new ArrayList<>(Arrays.asList(
                new Message("Welcome!", "Welcome to your inbox to send a message or view your messages simply use the menu!" + input.EOL, "Management", "DART")));
    }

//    public Customer(String name, String type) {
//        super(name);
//        this.type = type;
//        this.membership = new Regular();
//    }

    public Customer(String id, String name, double spentMoney) throws InvalidInputException {
        super(id, name);
        this.spentMoney = spentMoney;
        this.membership = new Regular();
    }
//
//    public Customer(String id, String name, Membership membership){
//        super(id, name);
//        this.inbox = new ArrayList<>(Arrays.asList(
//                new Message("Welcome!", "Welcome to your inbox to send a message or view your messages simply use the menu!" + input.EOL, "Management", "DART")));
//        this.membership = membership;
//        this.credits = 0;
//        this.currentRentals = 0;
//    }

    public void setMembership(Membership type) {
        this.membership = type;
    }

    public boolean canRent() {
        return this.membership.maxRentals(this.currentRentals);
    }
    public void applyCredits(){
        setCredit(this.membership.applyCredit(this.credits));
    }
    public double memberDiscount(double userBill){
        return this.membership.discount(userBill);
    }
    public String getMembershipType() {
        return this.membership.membershipType();
    }

    public int getCredits() {
        return this.credits;
    }

    public void setCredit(int newCredits){
        this.credits = newCredits;
    }

    public int getCurrentRentals() {
        return this.currentRentals;
    }

    public String getId() {
        return super.getId();
    }

    public String getName() {
        return super.getName();
    }

    public ArrayList<Message> getInbox() {
        return inbox;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(double spentMoney) {
        this.spentMoney = spentMoney;
    }

    public String getType() {
        return type;
    }

    public Membership getMembership() {
        return this.membership;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void viewMessages(Customer customer) {
        for (Message message : customer.getInbox()) {
            if (!message.getReadStatus()) {
                message.setReadStatus(true);
                System.out.print(Input.ANSI_RED + "Unread" + Input.ANSI_RESET);
            } else {
                System.out.print(Input.ANSI_CYAN + "Read" + Input.ANSI_RESET);
            }
            System.out.println(message.toString());
        }
    }


//    public ArrayList<Customer> requestMembership(ArrayList<Customer> customerList) {
//        ArrayList<Customer> requestList = null;
//        boolean contains = false;
//        String name = input.getInput("What is your name?: ");
//        for (Customer customer : customerList) {
//            if (customer.getName().equalsIgnoreCase(name)) {
//                contains = true;
//                if (customer instanceof Membership) {
//                    String type = null;
//                    int membershipType = input.getInt("Which membership do you want to apply for? " + input.EOL + " 1) Silver " + input.EOL + " 2) Gold " + input.EOL + " 3) Platinum" + input.EOL);
//                    switch (membershipType) {
//                        case 1:
//                            type = "Silver";
//                            break;
//                        case 2:
//                            type = "Gold";
//                            break;
//                        case 3:
//                            type = "Platinum";
//                            break;
//                        default:
//                            System.out.println("Not a valid input.");
//                            break;
//                    }
//                    System.out.println("Your request for a " + type + " membership will be reviewed shortly.");
//                    memberRequest.add(new Customer(name, type));
//                    requestList = memberRequest;
//                } else {
//                    System.out.println("Hi " + customer.getName() + "! You are one of our most valued customers and have a " + customer.getMembership() + " membership already. Perhaps you want to try upgrading instead."+input.EOL);
//                }
//            }
//        }
//        if (!contains) {
//            System.out.println("Sorry, " + name + " doesn't exist in this dimension");
//        }
//        return requestList;
//    }
//    public ArrayList<Customer> upgradeMembership(ArrayList<Customer> customerList) {
//        ArrayList<Customer> upgradeList = null;
//        String inputName = input.getInput("What is your name?: ");
//        boolean contains = false;
//
//        // requestingMember = customerList.stream().filter(customer -> getName().equalsIgnoreCase(name));
//        for (Customer customer : customerList) {
//            if (customer.getName().equalsIgnoreCase(inputName)) {
//                contains = true;
//                String type = customer.membership.membershipType();
//                String name = customer.getName();
//                if (type.equalsIgnoreCase("No membership")) {
//                    System.out.println("Sorry " + name + ", it seems you don't have a membership yet. Perhaps you can try to register for our Silver Membership instead?");
//                    return null;
//                }
//                System.out.println("Hi " + name + "! You currently have a " + type + " membership. " + input.EOL + "Which Membership would you like to upgrade to?: " + input.EOL);
//                String requestType = null;
//                int userInput;
//                if (type.equals("Silver")) {
//                    userInput = input.getInt("1) Gold " + input.EOL + "2) Platinum " + input.EOL + "3) Back to Customer Menu" + input.EOL);
//                    switch (userInput) {
//                        case 1:
//                            type = "Gold";
//                            break;
//                        case 2:
//                            type = "Platinum";
//                            break;
//                        case 3:
//                            DartController dartController = new DartController();
//                            dartController.customerMenu();
//                        default:
//                            System.out.println("Not a valid input.");
//                            break;
//                    }
//                } else if (type.equals("Gold")) {
//                    userInput = input.getInt("1) Platinum " + input.EOL + "2) Back to Customer Menu" + input.EOL);
//                    switch (userInput) {
//                        case 1:
//                            type = "Platinum";
//                            break;
//                        case 2:
//                            DartController dartController = new DartController();
//                            dartController.customerMenu();
//                        default:
//                            System.out.println("Not a valid input.");
//                            break;
//                    }
//                    System.out.println("Your request for a " + requestType + " Membership will be reviewed shortly.");
//                    memberRequest.add(new Customer(name, type));
//                    upgradeList = memberRequest;
//                }
//            }
//            if (!contains) {
//                System.out.println("Sorry, " + inputName + " is not in our Customer database.");
//            }
//        }
//        return upgradeList;
//    }

        public void addSpentMoney ( double transactionCost){
            this.spentMoney = this.spentMoney + transactionCost;
        }

        public void incrementRentals(){
            currentRentals++;
        }

        public String toString () {
        String currentMembership;
            if (this.membership == null){
                currentMembership = "No Membership";
            }else{
                currentMembership = this.membership.membershipType();
            }
            return input.EOL + "Customer ID: " + super.getId() + input.EOL + "Name: " + this.getName() + input.EOL + currentMembership + input.EOL + "Credits: " + this.credits + input.EOL + "-----";
        }
    }











