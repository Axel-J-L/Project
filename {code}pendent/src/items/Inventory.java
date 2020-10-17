package items;

import items.properties.Rating;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Inventory {
    private String id;
    private String title;
    private int year;
    private double dailyRent;
    private LocalDate rentedDate;
    private boolean rentStatus;
    private int rentalFrequency;
    private ArrayList<Rating> ratings;


    public Inventory(String title, double dailyRent, int year, boolean rentStatus, LocalDate date) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.dailyRent = dailyRent;
        this.year = year;
        this.rentStatus = rentStatus;
        this.rentedDate = date;
        this.rentalFrequency = 0;
        this.ratings = new ArrayList<Rating>();
    }

    public Inventory(String title, double dailyRent, int year) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.dailyRent = dailyRent;
        this.rentStatus = false;
        this.year = year;
        this.rentedDate = null;
        this.rentalFrequency = 0;
        this.ratings = new ArrayList<Rating>();
    }
    public Inventory(String title, double dailyRent, int year, LocalDate date) {
        this.title=title;
        this.dailyRent=dailyRent;
        this.year=year;
        this.rentedDate=date;
    }
    public Inventory(){
    }

    public Inventory(String title, int rentalTimes) {
        this.title = title;
        this.rentalFrequency = rentalTimes;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public double getDailyRent() {
        return dailyRent;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public LocalDate getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(LocalDate rentedDate) {
        this.rentedDate = rentedDate;
    }

    public boolean isRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(boolean rentStatus) {
        this.rentStatus = rentStatus;
    }

    public ArrayList getRatingSet() {
        return ratings;
    }

    public int getRentalFrequency() {
        return rentalFrequency;
    }

    public double averageRating() {
        if (ratings.size() != 0) {
            double sum = 0;
            for (Rating rating : ratings) {
                sum = sum + rating.getRating();
            }
            return Math.round((sum / ratings.size()) * 100) / 100;
        }
        return 0;
    }

}
