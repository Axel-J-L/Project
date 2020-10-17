package controller;

import items.*;
import items.properties.*;
import people.*;
import people.features.*;
import people.features.membership.*;
import tools.Input;

import java.time.LocalDate;
import java.util.*;

public class Storage {

    private Employee employee = new Employee();
    private Customer customer = new Customer();
    private Album album = new Album();
    private Rental rental = new Rental();
    private Game game = new Game();
    private Rating rating = new Rating();
    private Message message = new Message();
    private Input input = Input.getInstance();

    // "kind of" Storage

    private ArrayList<Album> albums = new ArrayList<>(Arrays.asList(
            new Album("London Calling", "The Clash", 1980, 14.99, false, null),
            new Album("Legend", "Bob Marley & The Wailers", 1984, 17.99, true, LocalDate.of(2020, 8, 23)),
            new Album("The Dark Side of the Moon", "Pink Floyd", 1973, 24.99, false, null),
            new Album("The Black Album", "Metallica", 1991, 19.99, true, LocalDate.of(2020, 8, 23)),
            new Album("Blood Sugar Sex Magik", "Red Hot Chili Peppers", 1991, 18.99, false, null)));

    private ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(
            new Employee("Bob", 1974, "1044 Randolph Street", 13457),
            new Employee("Jill", 1985, "3845 Rainbow Street", 14568),
            new Employee("Jack", 1934, "1453 Tilden Street", 16893),
            new Employee("Anna", 1959, "1854 Rose Avenue", 13578),
            new Employee("Sam", 1993, "1784 Sunrise Blvd", 12385),
            new Employee("Emanuel", 1992, "1039 Surfer's Paradise Lane", 12547)));

    private ArrayList<Game> games = new ArrayList<>(Arrays.asList(
            new Game("Sonic: The Hedgehog", "Explore", 18.99, 1857, false, null),
            new Game("Crash Bandicoot", "Racing", 17.59, 1957, false, null),
            new Game("The Legend of Zelda", "Explore", 12.29, 1874, true, LocalDate.of(2020, 8, 20)),
            new Game("Prince of Persia", "Impossible", 15.39, 1984, false, null),
            new Game("Super Mario", "Classic", 18.99, 1999, false, null),
            new Game("Street Fighter", "Fighting", 11.99, 1991, true, LocalDate.of(2020, 8, 20)),
            new Game("Tekken", "Fighting", 17.99, 1932, false, null)));

    private ArrayList<Customer> customerList = new ArrayList<>(Arrays.asList(
            new Customer("Vernita", new Silver()),
            new Customer("Navya", new Gold()),
            new Customer("Drake", new Platinum()),
            new Customer("Altan"),
            new Customer("Karen"),
            new Customer("Axel", new Silver())));

    private ArrayList<Rental> rentalHistory = new ArrayList<>(Arrays.asList(
            new Rental("bob", "test1", 1756.34),
            new Rental("bob", "test2", 1546.65),
            new Rental("test3", "test3", 2247.93),
            new Rental("test4", "test4", 1966.28)
    ));

    public ArrayList<Rental> getRentalHistory() {
        return rentalHistory;
    }
    private HashMap<String, Membership> membershipRequests = new HashMap<>(); // not being used yet

    public void itemsByProfit() {
        rentalHistory.sort(Comparator.comparingDouble(Rental::getRentExpense));
        Collections.reverse(rentalHistory);
        rentalHistory.forEach(System.out::println);
    }

    public void bestCustomer() {
        ArrayList<Customer> customerExpenditure = new ArrayList<Customer>();
        for (Customer customer : customerList){
            double rentalExpense = 0;
            for (Rental rental : rentalHistory){
                if (customer.getId().equals(rental.getCustomerId())){
                    rentalExpense =+ rental.getRentalIncome();
                }
            }
            customerExpenditure.add(new Customer(customer.getName(), rentalExpense));
        }
        customerExpenditure.sort(Comparator.comparingDouble(Customer::getSpentMoney));
        Collections.reverse(customerExpenditure);
        System.out.println("Name: " + customerExpenditure.get(0).getName() + input.EOL + "Total rental expense: " + customerExpenditure.get(0).getSpentMoney());
    }

    public void rentalFrequency() {
        ArrayList<Inventory> rentalFrequency = new ArrayList<>();
        for (Game game : games){
            int rentalTimes = 0;
            for (Rental rental : rentalHistory){
                if (game.getId().equals(rental.getItemId())){
                    rentalTimes =+ 1;
                }
            }
           if (rentalTimes != 0) {
               rentalFrequency.add(new Inventory(game.getTitle(), rentalTimes));
           }
        }
        for (Album album : albums) {
            int rentalTimes = 0;
            for (Rental rental : rentalHistory) {
                if (album.getId().equals(rental.getItemId())) {
                    rentalTimes = +1;
                }
            }
            if (rentalTimes != 0){
                rentalFrequency.add(new Inventory(album.getTitle(), rentalTimes));
        }
        }
        for (Inventory rentalItem : rentalFrequency)
        System.out.println("Title: " + rentalItem.getTitle() + input.EOL + "Times rented: " + rentalItem.getRentalFrequency() + input.EOL);
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Customer> getCustomers() {
        return customerList;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer() {
        this.customer = customer;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public void rentGame() {
        String maxRentals = "You currently have too many rentals for your membership! That's awesome that you enjoy our products so much!";
        String user = input.getInput(input.EOL + "Customer Name: ");
        for (Customer customer : customerList) {
            if (user.equalsIgnoreCase(getCustomer().getName())) {
                if (customer.getMembership().maxRentals(customer.getCurrentRentals())) {
                        rental.rentGame(getGames());
                        customer.incrementRentals();
                    } else {
                        System.out.println(maxRentals);
                    }
                }
            }
            rental.rentGame(getGames());
        }

    public void returnGame() {
        String name = input.getInput("Hiya! What is your name, customer? ");
        boolean contains = false;
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(name)) {
                contains = true;
                viewGames();
                String rentId = input.getInput(input.EOL + "Enter the ID of the game would you like to return: ");
                Game gameToReturn = retrieveGame(rentId);
                if (gameToReturn != null){
                    if (gameToReturn.getRentStatus()) {
                        Rental newTransaction = rental.returnGame(customer, gameToReturn);
                        getRentalHistory().add(newTransaction);
                    } else {
                        System.out.println("This game hasn't been rented. Try again.");
                        returnGame();
                    }
                } else {
                    System.out.println("Game with this ID doesn't exist in this dimension. Try again.");
                    returnGame();
                }
            }
        }
        if (!contains) {
            System.out.println("That customer doesn't exist on our database, please try again.");
            returnGame();
        }
    }

    private Game retrieveGame(String rentId) {
        Game registeredGame = null;
        Iterator<Game> searching = games.iterator();
        while(searching.hasNext() && registeredGame == null){
            Game currentGame = searching.next();
            if(currentGame.getId().equals(rentId)){
                registeredGame = currentGame;
                return registeredGame;
            }
        }
        return null;
    }

    public void viewTransactions() {
        for (Rental rental : getRentalHistory()) {
            System.out.println(rental);
        }
    }

    public void totalProfit() {
        double profit = rental.getRentalIncome();
        System.out.println("The total profit is " + profit + " kr");
    }

    public void ratingAverage() {
        int average = 0;
        System.out.println("The average rating is " + average);
    }

    public void addCustomer() {
        String name = input.getInput("Enter the Customers Name: ");
        while (name.isBlank() && name.isEmpty()){
            System.out.println();
            name = input.getInput("Invalid input, name can not be empty or composed of spaces" + input.EOL + "Name: ");
        }
        customerList.add(new Customer(name));
    }

    public void removeCustomer() {
        viewCustomer();
        String removeId = input.getInput("Enter the ID of the customer you want to remove. " + input.EOL + "ID: ");
        this.customerList.removeIf(customer -> customer.getId().equals(removeId));
    }

    public void viewCustomer() {
        for (Customer customer : customerList) {
            System.out.println(customer.toString());
        }
    }

    public void membershipRequestList() {
        for (String key : membershipRequests.keySet()) {
            String choice = input.getInput((key + " has requested a membership upgrade" + input.EOL + "Approve (Y/N): "));
            if (choice.equalsIgnoreCase("y")) {
                    switch (membershipRequests.get(key).membershipType()) {
                        case "Regular":
                            for (Customer customer : customerList) {
                                if (customer.getName().equalsIgnoreCase(key)) {
                                    customer.setMembership(new Silver());
                                    System.out.println(key + " has been upgraded to Silver Membership");
                                }
                            }
                            break;
                        case "Silver":
                            for (Customer customer : customerList) {
                                if (customer.getName().equalsIgnoreCase(key)) {
                                    customer.setMembership(new Gold());
                                    System.out.println(key + " has been upgraded to Gold Membership");
                                }
                            }
                            break;
                        case "Gold":
                            for (Customer customer : customerList) {
                                if (customer.getName().equalsIgnoreCase(key)) {
                                    customer.setMembership(new Platinum());
                                    System.out.println(key + " has been upgraded to Platinum Membership");
                                }
                            }
                            break;
                        default:
                            System.out.print("Membership is not valid for upgrade");
                            break;
                    }
            } else {
                for (Customer customer : customerList) {
                    if (customer.getName().equalsIgnoreCase(key)) {
                        customer.getInbox().add(new Message("Recent Membership Request",
                                "Your membership request at this time has been reviewed and unfortunately at this time has been denied." + input.EOL,
                                "Management",
                                "DART"));
                    }
                }
            }
        }
        membershipRequests.clear();
    }

    public void requestMembership(){ // move to customer
        boolean contains = false;
        String name = input.getInput("Customer Name: ");
        for (Customer customer : customerList){
            if (name.equalsIgnoreCase(customer.getName())){
                contains = true;
                if (customer.getMembershipType().equals("Regular")){
                    membershipRequests.put(customer.getName(), customer.getMembership());
                    System.out.print("Request for Silver Membership has been submitted for review"+input.EOL);
                }
            }
        }
        if (!contains){
            System.out.println("Customer does not exist, please contact and employee to be added to the system");
        }
    }

    public void upgradeMembership(){ // move to customer
        String name = input.getInput("Customer Name: ");
        for(Customer customer : customerList){
            if (name.equalsIgnoreCase(customer.getName())){
                if (customer.getMembershipType().equals("Regular")) {
                    System.out.println("This customer does not seem to have a membership try requesting one");
                } else if (customer.getMembershipType().equalsIgnoreCase("Platinum")) {
                    System.out.println("Platinum Members cannot upgrade further");
                } else { // needs error handling for platinum requests
                    membershipRequests.put(customer.getName(), customer.getMembership());
                    System.out.println("Application for membership upgrade has been submitted for review");
                }
            }
        }
    }

    public void addEmployee() {
        boolean isRunning = false;
        do {
            try {
                String name = input.getInput("Name: ");
                int birthYear = input.getInt("Birth year: ");
                String address = input.getInput("Address: ");
                double monthlySalary = input.getDouble("Monthly gross salary: ");
                double grossSalary = monthlySalary * employee.MONTHS;
                employees.add(new Employee(name, birthYear, address, grossSalary));
                isRunning = true;
            } catch (InputMismatchException e) {
                System.out.println("That's not the correct input, be careful and please try again.");
                input.input.nextLine();

            }
        } while (!isRunning);
    }

    public void removeEmployee() {
        String removeID = input.getInput("Enter the ID of the employee you want to remove." + input.EOL + "Employee ID: ");
        this.employees.removeIf(employee -> employee.getId().equals(removeID));
        System.out.println("Employee has been deleted of the face of the earth!" + input.EOL);
    }

    public void viewEmployee() {
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    public void addAlbum() {
        this.albums.add(album.addAlbum());
    }

    public void removeAlbum() {
        String removeID = input.getInput("Remove." + input.EOL + "Album ID: ");
        this.albums.removeIf(album -> album.getID().equals(removeID));
        System.out.println("Album has been sent to the moon and is no longer retrievable!" + input.EOL);
    }


    public void rentAlbum() {
        String maxRentals = "Sorry you need to return an item to able rent a new one, you've reached your memberships maximum rentals. Let the other customers enjoy stuff also!";
        String user = input.getInput("Please enter your name: ");
        for (Customer customer : customerList) {
            if (user.equalsIgnoreCase(getCustomer().getName())) {
                if (customer.canRent()) {
                    rental.rentGame(getGames());
                    customer.incrementRentals();
                } else {
                    System.out.println(maxRentals);
                }
            }
        }
        rental.rentAlbum(getAlbums());
    }

    public void returnAlbum() {
        String name = input.getInput("Type your name to begin a lengthy and tedious return process: ");
        boolean contains = false;
        for (Customer customer : customerList) {
            if (customer.getName().equalsIgnoreCase(name)) {
                customer.applyCredits();
                contains = true;
                Rental newTransaction = rental.returnAlbum(customer.getCredits(), customer.getMembership().membershipType(), customer.getId(), getAlbums());
                getRentalHistory().add(newTransaction);
            }
        }
        if (!contains) {
            System.out.println("That customer doesn't exist within the void that we store our customer information, please try again.");
            returnAlbum();
        }
    }

    public void viewAlbums() {
        albums.sort(Comparator.comparingInt(Album::getYear)); //change to lambda
        Collections.reverse(albums);
        albums.forEach(System.out::println);
    }

    public void searchAlbums() {
        int google = input.getInt("Album Search" + input.EOL + "Year: ");
        boolean yearExists = false;
        for (Album album : albums) {
            if (album.getYear() == google) {
                yearExists = true;
                System.out.println(album.toString());
            }
        }
        if (yearExists = false){
            System.out.println("There is no album available for this year.");
        }
    }

    public void viewAlbumsByRating() {
        // sorts albums by comparing the rating value
        // the :: (invokes the method getRating from the album class. compares albums ratings as a double)
        albums.sort(Comparator.comparingDouble(Album::getRating)); //Change to lambda?
        Collections.reverse(albums);
        albums.forEach(System.out::println); // using the :: operator to invoke the println function for each album
    }

    public void addGame() {
        int countArray = games.size();
        String newGameTitle = input.getInput("Title:  ");
        String newGameGenre = input.getInput("Genre:  ");
        int newGameYear = input.getInt("Year:  ");
        double newGameRentCost = input.getDouble("Daily Rent Fee:  ");
        input.input.nextLine();

        games.add(new Game(newGameTitle, newGameGenre, newGameRentCost, newGameYear ));
        System.out.println("Game Added Successfully : " + games.get(games.size()-1).toString());
    }

    public void removeGame() {
        viewGames();
        System.out.print("Which game should be removed? Enter the Game ID: ");
        String gameId = input.input.nextLine();
        boolean contains = false;
        if (games.contains(gameId)) {
            contains = true;
            System.out.println("Are you sure you want to remove this game from the directory?" + input.EOL + games.toString() + input.EOL + "(Y/N)");
            String doubleCheck = input.input.nextLine();
            if (doubleCheck.equalsIgnoreCase("y")) {
                games.remove(gameId);
                System.out.println("Game fed to a bunch of alpaca's theres no retrieving it anymore...");
            } else {
                System.out.println("Okay, no problem. ");
            }
        } else {
            System.out.println("That game doesn't seem to be in the directory.");
        }
        viewGames();
    }

    public void viewGames() {
        games.sort(Comparator.comparingInt(Game::getYear));
        Collections.reverse(games);
        games.forEach(System.out::println);
    }

    public void searchGames() {
        String google = input.getInput("Game Search" + input.EOL + "Genre: ");
        for (Game game : games) {
            if (game.getGenre().equalsIgnoreCase(google)) {
                System.out.println(game.toString());
            }
        }
    }

    public void viewGamesByRating() {
        games.sort(Comparator.comparingDouble(Game::getRating));
        Collections.reverse(games);
        games.forEach(System.out::println);
    }

    public void sendMessage() {
        viewCustomer();
        String recipientId = input.getInput(input.EOL + "Enter the customer ID of the person you want to communicate with (for those times were communication is necessary with other humans...): ");
        for (Customer customer : customerList) {
            if (customer.getId().equalsIgnoreCase(recipientId)) {
                String senderID = input.getInput("Type your ID: ");
                String senderName = input.getInput("Type your Name: ");
                String subject = input.getInput("Type your Title: ");
                String body = input.getInput("Type your message: ");
                Message newMessage = new Message(subject, body, senderID, senderName);
                System.out.println("Your message has been delivered to our own personal flying T-rex for prompt delivery (we breed them specifically for speed and agility).");

                customer.getInbox().add(newMessage);
            }
        }
    }

    public void viewMessages() {
        String name = input.getInput("Type your name to view your inbox (we know you don't want to see all those unread messages! but you've got to sort it out sometime!): ");
        for (Customer reader : customerList) {
            if (reader.getName().equalsIgnoreCase(name) && reader.getInbox().size() != 0) {
                Collections.reverse(reader.getInbox());
                System.out.println(input.EOL + ">> List of messages in order received <<");
                System.lineSeparator();
                customer.viewMessages(reader);
                Collections.reverse(reader.getInbox());
            } else if (reader.getName().equalsIgnoreCase(name) && reader.getInbox().size() == 0) {
                System.out.println(input.EOL + "No messages to view (guess no one likes you...).");
            }
        }
    }

    public void removeMessages() {
        viewMessages();
        String removeMessage = input.getInput("Enter the message ID you want to delete: ");
        for (Customer customer : customerList) {
            customer.getInbox().removeIf(message -> message.getMessageId().equalsIgnoreCase(removeMessage));
        }
        System.out.println("The message has been deleted.");
        viewMessages();
    }
}