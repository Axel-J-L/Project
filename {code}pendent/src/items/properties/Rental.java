package items.properties;

import items.Album;
import items.Inventory;
import people.Customer;
import tools.Input;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class Rental {

    private String customerId;
    private String itemId;
    private double rentExpense;
    private Rating rating;
    private static double rentalIncome = 0;
    private Input input = Input.getInstance();

    // Default Constructor
    public Rental() {
    }

    public Rental(String customerId, String itemId, double rentExpense) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.rentExpense = rentExpense;
    }

    public Rental(String customerId, String itemId, double rentExpense, Rating rating) {
        this.customerId = customerId;
        this.itemId = itemId;
        this.rentExpense = rentExpense;
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public double getRentExpense() {
        return rentExpense;
    }

    public void totalProfit() {
        System.out.println("Total profit: " + rentalIncome);
    }

    public double getRentalIncome() {
        return rentalIncome;
    }

    public void rentItem(Inventory itemToRent) {
            if (!itemToRent.isRentStatus()) {
                itemToRent.setRentStatus(true);
                itemToRent.setRentedDate(LocalDate.now());
                System.out.println("Game has been rented. Enjoy!");
            } else {
                System.out.println("Sorry, that game is being rented at the moment " + input.EOL);
            }
    }

   public double returnGame(Customer customer, Inventory rentedGame) {
        long daysRented = 0;
        double userBill = 0;
        daysRented = DAYS.between(rentedGame.getRentedDate(), LocalDate.now());
            if (daysRented > 0) {
                rentedGame.setRentStatus(false);
                if (customer.getCredits() == 5) {
                    userBill = 0;
                } else userBill = customer.memberDiscount(daysRented * rentedGame.getDailyRent());
                rentalIncome = rentalIncome + userBill;
                System.out.println(input.EOL + "You rented " + rentedGame.getTitle() + " for " + daysRented + " days. " + input.EOL + "Your total is " + Math.round(userBill * 100.0) / 100.0 + " kr" + input.EOL);
                System.out.println("The Game has now been returned.");
                return userBill;
            } else {
                System.out.println("Invalid operation. Upon returning an item, the number of days rented must be positive." + input.EOL);
            }
       return 0;
    }
/*
    public void rentAlbum(List<Inventory> albums) {
        String rental = input.getInput(input.EOL + "Rent" + input.EOL + "Album ID: ");
        for (Inventory album : albums) {
            if (album.getID().equals(rental) && album.getRentStatus().equalsIgnoreCase("available")) {
                if (album.getRentStatus().equalsIgnoreCase("\033[31mRented\033[0m")) {
                    System.out.println("Item is unavailable");
                    rentAlbum(albums);
                } else {
                    album.setRentStatus(true);
                    album.setRentedDate(LocalDate.now());
                    System.out.println(">> " + album.getTitle() + " by " + album.getArtist() + " - \033[31mRented\033[0m");
                }
            }
        }
    }
*/


    public Rental returnAlbum(int numberCredits, String membershipType, String customerId, ArrayList<Album> albums) {
        Rating customerRating = null;
        Rental rentTransaction = null;
        double userBill = 0;
        String rental = input.getInput("Return" + input.EOL + "Album ID: ");
        // int days = helper.getInt("Number of days rented: "); for hard day entry to calculate cost
        for (Album album : albums) {
            if (album.getID().equals(rental)) {
                double daysRented = DAYS.between(album.getRentedDate(), LocalDate.of(2020, 10, 31));
                if (daysRented > 1) {
                    if (album.getRentStatus().equals("\033[31mRented\033[0m")) {
                        album.setRentStatus(false);
                        album.setRentedDate(null);
                        if (numberCredits == 5) {
                            userBill = 0;
                        }
                        rentalIncome = rentalIncome + userBill;
                        System.out.println(">> " + album.getTitle() + " by " + album.getArtist() + " - Total Cost: " + userBill + " kr - Returned" + input.EOL);
                        String ratingPrompt = input.getInput("We hope you enjoyed " + album.getTitle() + ". Would you like to rate it? Y/N ");
                        ratingPrompt = ratingPrompt.toLowerCase();
                        if (ratingPrompt.equalsIgnoreCase("n")) {
                            rentTransaction = new Rental(customerId, rental, userBill);
                        } else if (ratingPrompt.equalsIgnoreCase("y")) {
                            int rating = input.getInt("How would you rate it on a scale of 0-5? ");
                            if (rating > 5) {
                                rating = 5;
                            }
                            if (rating < 0) {
                                rating = 0;
                            }
                            String review = input.getInput("Would you like to leave a written review? Y/N ");
                            if (review.equalsIgnoreCase("y")) {
                                String feedback = input.getInput("Did you enjoy listening to " + album.getTitle() + "? Do you have any advice for other listeners? Or were you too stoned to remember...");
                                System.out.println("Thank you for your feedback!");
                                customerRating = new Rating(rating, feedback);
                            } else {
                                System.out.println("Thank you for your feedback!");
                                customerRating = new Rating(rating);
                            }
                            rentTransaction = new Rental(customerId, rental, userBill, customerRating);
                        }
                        album.getRatingSet().add(customerRating);
                    } else {
                        System.out.println("That Album has not been rented or it does not exist");
                        returnAlbum(numberCredits, membershipType, customerId, albums);
                    }
                } else
                    System.out.println("Invalid operation. Upon returning an item, the number of days rented must be positive." + input.EOL);
            }
        }
        return rentTransaction;
    }

    public void showRentalIncome() {
        System.out.println("Rental income to-date is: " + getRentalIncome() + " kr" + input.EOL);

    }

    public String toString() {
        return "Customer ID: " + getCustomerId() + input.EOL + "Rental Item: " + getItemId() + input.EOL + "Transaction Cost: " + getRentExpense() + input.EOL + "Rating: " + getRating() + Input.EOL;
    }
}
