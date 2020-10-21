package people.features.membership;

// Dark Arts :D
public interface Membership {
    String membershipType();
    double discount(double userBill);
    boolean maxRentals(int rentals);
    int applyCredit(int credits);
}




