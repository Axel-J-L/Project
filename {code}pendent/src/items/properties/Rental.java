package items.properties;

import items.Album;
import items.Game;
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
    private static final double SILVER_DISCOUNT = .90;
    private static final double GOLD_DISCOUNT = .85;
    private static final double PLATINUM_DISCOUNT = .75;
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

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public double getRentExpense() {
        return rentExpense;
    }

    public void setRentExpense(double rentExpense) {
        this.rentExpense = rentExpense;
    }

    public void totalProfit() {
        System.out.println("Total profit: " + rentalIncome);
    }

    public double getRentalIncome() {
        return rentalIncome;
    }

    public void rentGame(ArrayList<Game> games) {
        boolean contains = false;
        String rentId = input.getInput("ID of your next favourite game :" );
        for (Game rentGame : games) {
            if (rentGame.getId().equals(rentId) && !rentGame.getRentStatus()) {
                contains = true;
                rentGame.setRentStatus(true);
                rentGame.setRentedDate(LocalDate.now());
                System.out.println("Game has been rented. Enjoy!");
            } else if (rentGame.getId().equals(rentId) && rentGame.getRentStatus()) {
                int choice = input.getInt("Sorry, that game is being rented at the moment " + input.EOL + " 1) Try a different game" + input.EOL + " 2) Back to Customer menu"+input.EOL);
                if (choice == 1) {
                    rentGame(games);
                } else if (choice == 2) {
                    return;
                } else {
                    System.out.println("Wrong entry");
                }
            }
        }
        if (!contains) {
            System.out.println("Come on! that's not a real ID, please try again");
            rentGame(games);
        }
    }

   public Rental returnGame(Customer customer, Game rentedGame) {
        long daysRented = 0;
        double userBill = 0;
        boolean contains = false;
                daysRented = DAYS.between(rentedGame.getRentedDate(), LocalDate.now());
                if (daysRented > 0) {
                    contains = true;
                    rentedGame.setRentStatus(false);
                        if (customer.getCredits() == 5) {
                            userBill = 0;
                        } else userBill = customer.memberDiscount(daysRented * rentedGame.getDailyRent());

           /*             switch (membershipType) {
                            case "Silver":
                                userBill = discount(daysRented * rentedGame.getDailyRent());
                                break;
                            case "Gold":
                                userBill = discount(daysRented * rentedGame.getDailyRent());
                                break;
                            case "Platinum":
                                userBill = discount(daysRented * rentedGame.getDailyRent());
                                break;
                            default:
                                userBill = daysRented * rentedGame.getDailyRent();
                                break;
                     }

            */

                        rentalIncome = rentalIncome + userBill;
                        System.out.println(input.EOL + "You rented " + rentedGame.getTitle() + " for " + daysRented + " days. " + input.EOL + "Your total is " + Math.round(userBill * 100.0) / 100.0 + " kr" + input.EOL);
                        System.out.println("The Game has now been returned.");

                        String feedback = null;
                        int rating = 0;
                        Rating customerRating = null;
                        Rental rentTransaction = null;
                        String ratingQuestion = input.getInput("We hope you enjoyed playing this " + rentedGame.getTitle() + " Would you like to rate it? Y/N ");

                        if (ratingQuestion.equalsIgnoreCase("n")) {
                            rentTransaction = new Rental(getCustomerId(), rentedGame.getId(), userBill);
                            return rentTransaction;
                        } else if (ratingQuestion.equalsIgnoreCase("y")) {
                            rating = input.getInt("How would you rate it on a scale of 0-5? ");
                            if (rating > 5) {
                                rating = 5;
                            }
                            if (rating < 0) {
                                rating = 0;
                            }
                            String feedbackQuestion = input.getInput("Would you like to leave a review? Y/N ");
                            if (feedbackQuestion.equalsIgnoreCase("y")) {
                                feedback = input.getInput("How did you experience the " + rentedGame.getTitle() + "?  Do you have any advice for other players? or did you kind of just suck at it...");
                                System.out.println("Thank you for your feedback!");
                                customerRating = new Rating(rating, feedback);
                            } else {
                                System.out.println("Thank you for your feedback!");
                                customerRating = new Rating(rating);
                            }
                            rentTransaction = new Rental(customerId, rentedGame.getId(), userBill, customerRating);
                        }
                        rentedGame.getRatingSet().add(customerRating);
                        return rentTransaction;
                } else {
                    System.out.println("Invalid operation. Upon returning an item, the number of days rented must be positive." + input.EOL);
                }
       return null;
    }

    public void rentAlbum(ArrayList<Album> albums) {
        String rental = input.getInput(input.EOL + "Rent" + input.EOL + "Album ID: ");
        for (Album album : albums) {
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


    public Rental returnAlbum(int numberCredits, String membershipType, String customerId, ArrayList<Album> albums) {
        Rating customerRating = null;
        Rental rentTransaction = null;
        double userBill;
        String rental = input.getInput("Return" + input.EOL + "Album ID: ");
        // int days = helper.getInt("Number of days rented: "); for hard day entry to calculate cost
        for (Album album : albums) {
            if (album.getID().equals(rental)) {
                double daysRented = DAYS.between(album.getRentedDate(), LocalDate.of(2020, 10, 31));
                if (daysRented > 1) {
                    if (album.getRentStatus().equals("\033[31mRented\033[0m")) {
                        album.setRentStatus(false);
                        album.setRentedDate(null);
                        switch (membershipType) {
                            case "Silver":
                                userBill = (daysRented * album.getDailyRent()) * SILVER_DISCOUNT;
                                break;
                            case "Gold":
                                userBill = (daysRented * album.getDailyRent()) * GOLD_DISCOUNT;
                                break;
                            case "Platinum":
                                userBill = (daysRented * album.getDailyRent()) * PLATINUM_DISCOUNT;
                                break;
                            default:
                                userBill = daysRented * album.getDailyRent();
                                break;
                        }
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
