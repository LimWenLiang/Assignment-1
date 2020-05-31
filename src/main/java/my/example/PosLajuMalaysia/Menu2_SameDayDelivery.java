package my.example.PosLajuMalaysia;

import java.util.Scanner;

public class Menu2_SameDayDelivery implements Runnable {
    @Override
    public void run() {
        String format1 = "%-10s%35s%50s%35s%s%n";
        String format2 = "%30s%14s%16s%25s%16s%14s%15s%s%n";
        String format3 = "%-10s%18s%10s%20s%20s%18s%17s%15s%s%n";
        String format4 = "%100s%n";
        System.out.println("2. SAME-DAY DELIVERY");
        System.out.println("For this service, your shipment will arrive on the same day in the same town area. The table below shows the payment rates by weight and town-type.\n");
        System.out.printf("%70s\n", "Menu of Same-Day Delivery");
        System.out.printf("%141s\n", "| Thread Name");
        System.out.printf(format1, "Weight", "Local Town", "Cross Town", "| ", Thread.currentThread().getName());
        System.out.printf(format2, "Domestic Charge", "Surcharge", "Total", "Domestic Charge", "Surcharge", "Total", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Below  500gm", "4.90", "6.00", "10.90", "5.40", "7.50", "12.90", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "500gm- 750gm", "5.70", "6.00", "11.70", "6.40", "7.50", "13.90", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "750gm-1000gm", "6.50", "6.00", "12.50", "7.40", "7.50", "14.90", "| ", Thread.currentThread().getName());
        System.out.printf(format4, "All charges in RM");
    }

    static double grandLocal = 0;
    static double grandCross = 0;
    static double grandBoth;

    public static void main(String[] args) {
        Pos pos = new Pos();

        // Create threads
        (new Thread(new Menu2_SameDayDelivery())).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double weight = 0;
        int townType = 0;

        do {
            weight = calculateweight();
            if (weight > 0) {
                System.out.println("\nPlease enter your choice:\n1 for Local Town\n2 for Cross Town");

                Scanner sc = new Scanner(System.in);
                townType = sc.nextInt();
                System.out.println("The entered integer is " + townType + ".\n\n");

                pos = new SameDayDelivery(weight, townType);

                if (((SameDayDelivery) pos).getTownType() == 1) {
                    System.out.println("Call calculate local town:\n");
                    calculateLocalTownCharge(pos.getWeight());
                    printLocalTownReceipt(pos.getWeight());
                } else if (((SameDayDelivery) pos).getTownType() == 2) {
                    System.out.println("Call calculate cross town:\n");
                    calculateCrossTownCharge(pos.getWeight());
                    printCrossTownReceipt(pos.getWeight());
                } else
                    System.out.println("Invalid try again");
                break;
            } else {
                System.out.println("Invalid input, please enter the weight again.");
            }
        } while (weight <= 0);

        /*Calculate Grand Total of both town*/
        grandBoth = grandLocal + grandCross;
        grandBoth = Math.round(grandBoth * 100.0) / 100.0;

        pos = new SameDayDelivery(weight, grandBoth, townType);

        /*Display Grand Total for Both town*/
        System.out.printf("\n%25s\n", "Receipt");
        System.out.printf("Grand Total of Local Town\t: RM%.2f%n", grandLocal);
        System.out.printf("Grand Total of Cross Town\t: RM%.2f%n", grandCross);
        System.out.printf("Grand Total of both town\t: RM%.2f%n", pos.getPrice());

        System.out.println("\n\n1.\t\t\tBack to Main Menu.");
        System.out.println("2.\t\t\tExit.");
        int menu;
        Scanner input = new Scanner(System.in);

        do {
            System.out.printf("Enter your choice.\t\t\t:");
            menu = input.nextInt();
        } while (menu != 1 & menu != 2);

        //input.close();
        if (menu == 1) {
            System.out.println("\n\n");
            Main_Menu.main(args);
        } else {
            System.out.println("\n\n");
            pos.displayReceipt();
        }
    }

    static double calculateweight() {
        System.out.printf("\nPlease enter number amount of weight (in gm) = ");
        Scanner sc = new Scanner(System.in);
        double weight = sc.nextDouble();
        System.out.printf("\nWeight : %.2fgm%n", weight);
        return weight;
    }

    static void calculateLocalTownCharge(double weight) {
        double charge = 0;

        if ((weight >= 0) && (weight < 500)) {
            double domesticCharge = 4.90;
            double surcharge = 6.90;
        } else if ((weight >= 500) && (weight <= 750)) {
            double domesticCharge = 5.70;
            double surcharge = 6.00;
        } else if ((weight >= 750) && (weight <= 1000)) {
            double domesticCharge = 6.50;
            double surcharge = 6.00;
        } else {
            System.out.println("No rates available");
        }
    }

    private static void printLocalTownReceipt(double weightL) {
        System.out.printf("Local Town Deliver Charge\n");
        System.out.printf("Local Town weight is %.0fgm%n", weightL);


        double domesticCharge = 0;
        double surcharge = 0;
        double total = 0;
        if ((weightL >= 0) && (weightL < 500)) {
            domesticCharge = 4.90;
            surcharge = 6.90;
            total = 10.90;
        } else if ((weightL >= 500) && (weightL <= 750)) {
            domesticCharge = 5.70;
            surcharge = 6.00;
            total = 11.70;
        } else if ((weightL >= 750) && (weightL <= 1000)) {
            domesticCharge = 6.50;
            surcharge = 6.00;
            total = 12.50;
        } else {
            System.out.println("No rates available");

        }
        System.out.printf("Local Town domestic charge is RM%.2f%n", domesticCharge);
        System.out.printf("Local Town surcharge is RM%.2f%n", surcharge);
        System.out.printf("Local Town total is RM%.2f%n", total);
        grandLocal += total;
        System.out.printf("Grant Total Price is RM%.2f%n", grandLocal);
    }

    static void calculateCrossTownCharge(double weightC) {
        double charge = 0;

        if ((weightC >= 0) && (weightC < 500)) {
            double domesticCharge = 5.40;
            double surcharge = 7.50;
        } else if ((weightC >= 500) && (weightC <= 750)) {
            double domesticCharge = 6.40;
            double surcharge = 7.50;
        } else if ((weightC >= 750) && (weightC <= 1000)) {
            double domesticCharge = 7.40;
            double surcharge = 7.50;
        } else {
            System.out.println("No rates available");
        }
    }

    private static void printCrossTownReceipt(double weightC) {
        System.out.printf("Cross Town Deliver Charge\n");
        System.out.printf("Cross Town weight is %.0fgm%n", weightC);


        double domesticCharge = 0;
        double surcharge = 0;
        double total = 0;
        if ((weightC >= 0) && (weightC < 500)) {
            domesticCharge = 5.40;
            surcharge = 7.50;
            total = 12.90;
        } else if ((weightC >= 500) && (weightC <= 750)) {
            domesticCharge = 6.40;
            surcharge = 7.50;
            total = 13.90;
        } else if ((weightC >= 750) && (weightC <= 1000)) {
            domesticCharge = 7.40;
            surcharge = 7.50;
            total = 14.90;
        } else {
            System.out.println("No rates available");

        }
        System.out.printf("Cross Town domestic charge is RM%.2f%n", domesticCharge);
        System.out.printf("Cross Town surcharge is RM%.2f%n", surcharge);
        System.out.printf("Cross Town total is RM%.2f%n", total);
        grandCross += total;
        System.out.printf("Grant Total Price is RM%.2f%n", grandCross);
    }

    public static double grand() {
        return grandBoth;
    }
}


