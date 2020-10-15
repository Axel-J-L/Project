package items;

import tools.Input;

import java.time.LocalDate;

public class Game extends Inventory {

    private String genre;
    private Input input = Input.getInstance();

    public Game(String title, String genre, double dailyRent, int year) {
        super(title, dailyRent, year);
        this.genre = genre;
    }

    public Game(String title, String gameGenre, double dailyRent, int year, boolean rentStatus, LocalDate date) {
        super(title, dailyRent, year, rentStatus, date);
        this.genre = gameGenre;
    }

    public Game() {
    }

    public String getId() {
        return super.getId();
    }

    public void setId(String id) {
        super.setId(id);
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getGenre() {
        return genre;
    }

    public double getRentCost() {
        return super.getDailyRent();
    }

    public boolean getRentStatus() {
        return super.isRentStatus();
    }

    public void setRentStatus(boolean isRented) {
        super.setRentStatus(isRented);
    }

    public LocalDate getRentedDate() {
        return super.getRentedDate();
    }

    public void setRentedDate(LocalDate rentedDate) {
        super.setRentedDate(rentedDate);
    }

    public int getYear() {
        return super.getYear();
    }

    public double getRating() {
        return super.averageRating();
    }

    public int getRentFrequency() {
        return super.getRentalFrequency();
    }

    @Override
    public String toString() {
        String outOnRent;
        if (this.getRentStatus()) {
            outOnRent = "\033[31mOut on rent  \033[0m";
        } else outOnRent = "Available";
        return input.DIVIDER + input.EOL + "ID: " + this.getId() + input.EOL + "Game: " + this.getTitle() +
               input.EOL + "Genre: " + this.getGenre() + ". " + input.EOL + "Released year: " +
               super.getYear() + ". " + input.EOL + "Daily Price: " + this.getDailyRent() + "kr." +
               input.EOL + "Status: " + outOnRent  + input.EOL + "Rating: " + this.averageRating();
    }
}