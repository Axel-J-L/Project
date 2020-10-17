package items;

import tools.Input;
import java.time.LocalDate;

public class Album extends Inventory {
    private String artist;


    private final Input input = Input.getInstance();

    public Album(String title, String artist, int year, double dailyRent, boolean rentStatus, LocalDate date) {
        super(title, dailyRent, year, rentStatus, date);
        this.artist = artist;
    }

    public Album(String title, String artist, int year, double dailyRent) {
        super(title, dailyRent, year);
        this.artist = artist;
    }

    public Album() {
        super();
    }

    public String getID() {
        return super.getId();
    }

    public String getTitle() {
        return super.getTitle();
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return super.getYear();
    }

    public double getRating() {
        return super.averageRating();
    }

    public double getDailyRent() {
        return super.getDailyRent();
    }

    public LocalDate getRentedDate() {
        return super.getRentedDate();
    }

    public void setRentedDate(LocalDate rentedDate) {
        super.setRentedDate(rentedDate);
    }

    public String getRentStatus() {
        if (super.isRentStatus() == true) {
            return "\033[31mRented\033[0m";
        }
        return "available";
    }

    public int getRentFrequency() {
        return super.getRentalFrequency();
    }

    public void setRentStatus(boolean rentStatus) {
        super.setRentStatus(rentStatus);
    }
   /* public void addGame() {
        int countArray = games.size();
        String newGameTitle = input.getInput("Title:  ");
        while(newGameTitle.length() < 1){
            newGameTitle = input.getInput("We like our games to have names!" + input.EOL + "Title:  ");
        }
        String newGameGenre = input.getInput("Genre:  ");
        int newGameYear = input.getInt("Year:  ");
        while(newGameYear < 1950 || newGameYear > LocalDate.now().getYear()){
            newGameYear = input.getInt("Nah, that can't be right... Try again. "+ input.EOL+ "Year: ");
        }
        //String newGameDate = input.getInput(" Date: ");
        double newGameRentCost = input.getDouble("Daily Rent Fee:  ");
        while(newGameRentCost < 0){
            newGameRentCost = input.getDouble("Whoa..... we are not that cheap! "+ input.EOL+ "Daily rent Fee: ");
        }
        input.input.nextLine();

        games.add(new Game(newGameTitle, newGameGenre, newGameRentCost, newGameYear ));
        System.out.println("Game Added Successfully : " + games.get(games.size()-1).toString());
    }
*/

    public Album addAlbum() {
        String addTitle = input.getInput("Title: ");
        while(addTitle.length() < 1){
            addTitle=input.getInput("We like our games to have names!" + input.EOL + "Title:  ");
        }
        String addArtist = input.getInput("Artist: ");
        int addYear = input.getInt("Year: ");
        double addDailyRent = input.getDouble("Daily Rent amount: ");
        while(addDailyRent < 0){
            addDailyRent=input.getDouble("Whoa..... we are not that cheap! "+ input.EOL+ "Daily rent Fee: ");
        }
        return new Album(addTitle, addArtist, addYear, addDailyRent);
    }

    public String toString() {
        return input.DIVIDER + input.EOL + "ID: " + this.getID() + input.EOL + "Album: " + this.getTitle()
                + input.EOL + "Artist: " + this.getArtist() + ". " + input.EOL + "Released year: " +
                this.getYear() + ". " + input.EOL + "Daily Price: " + this.getDailyRent() + " SEK."
                + input.EOL + "Status: " + this.getRentStatus() + input.EOL + "Rating: " + this.averageRating();
    }

}
