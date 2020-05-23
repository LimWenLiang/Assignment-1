package PosLajuMalaysia;

import java.util.Scanner;

public class Menu3_PrepaidBoxNEnvelope implements Runnable {
    @Override
    public void run() {
        /*Display Menu of Prepaid Box & Envelope*/
        String format1 = "%-15s%-20s%-20s%-25s%-25s%-27s%s%-25s\n";

        System.out.println("3. PREPAID BOX & ENVELOPE");
        System.out.printf("%80s\n", "Menu of Prepaid Box & Envelope");
        System.out.printf("%145s\n", "| Thread Name");
        System.out.printf(format1, "Jenis", "Envelope S (ES)", "Envelope L (EL)", "Prepaid Box S (PBS)", "Prepaid Box M (PBM)", "Prepaid Box L (PBL)", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Saiz", "280mm x 200mm", "380mm x 320mm", "380mm x 250mm x 80mm", "340mm x 250mm x 150mm", "380mm x 320mm x 200mm", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Berat Max", "0 - 0.50Kg", "0.51 - 1.00Kg", "1.01 - 2.00Kg", "2.01 - 5Kg", "5.01 - 10Kg", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Harga", "RM 7.31", "RM 10.49", "RM 13.78", "RM 21.20", "RM 31.80", "| ", Thread.currentThread().getName());
    }

    public static double grandPrice = 0;

    public static void main(String[] args) {
        Pos pos = new Pos();

        (new Thread(new Menu3_PrepaidBoxNEnvelope())).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String format2 = "%-30s%-10s\n";
        String format3 = "%-4s%-3sRM%-6.2f%-2s%-13d%-10.2f\n";
        String format4 = "%-30s%-10.2f";

        Scanner input = new Scanner(System.in);
        int choice, numType, quantity, menu, i = 0;
        double weight = 0;
        String jenis;
        String[] typeDelivery = new String[30];
        int[] numberDelivery = new int[30];
        double[] price = new double[30];
        double[] totalPrice = new double[30];

        /*Prompt the user to order*/
        do {
            System.out.println("\nDo you want to place your order?\nIf YES, press number 1.\nIf NO, press number 2.");
            choice = input.nextInt();
        } while (choice != 1 & choice != 2);

        while (choice != 2) {
            do {
                /*Input weight for Delivery*/
                if (weight > 10.00 | weight < 0) {
                    System.out.println("Invalid input.");
                }
                System.out.printf("\nEnter the weight of delivery (in KG)\t\t\t\t\t:");
                weight = input.nextDouble();
            } while (weight > 10.00 | weight < 0);
            pos.setWeight(weight);

            /*Call Method*/
            jenis = WeightofDelivery(weight);

            System.out.println("Your Type of Delivery Recommended\t\t\t\t\t\t:" + jenis);

            do {
                System.out.printf("Do you follow it? (1 to Yes / 2 to No)\t\t\t\t\t:");
                choice = input.nextInt();
            } while (choice != 1 & choice != 2);

            if (choice == 2) {
                do {
                    /*Input type of Delivery*/
                    System.out.println("Enter the type of delivery you want");
                    System.out.printf("(1.ES / 2.EL / 3.PBS / 4.PBM / 5.PBL)\t\t\t\t\t:");
                    numType = input.nextInt();
                } while (numType != 1 & numType != 2 & numType != 3 & numType != 4 & numType != 5);
                /*Call Method*/
                jenis = numForJenis(numType);
            }

            /*Input the number of delivery*/
            System.out.printf("Enter the quantity of delivery you want\t\t\t\t\t:");
            quantity = input.nextInt();

            pos = new PrepaidBoxNEnvelope(pos.getWeight(), quantity, jenis);
            typeDelivery[i] = ((PrepaidBoxNEnvelope) pos).getJenis();
            numberDelivery[i] = pos.getQuantity();

            /*Call Method*/
            price[i] = FindPrice(((PrepaidBoxNEnvelope) pos).getJenis());

            /*Calculate the total price*/
            totalPrice[i] = price[i] * numberDelivery[i];
            totalPrice[i] = Math.round(totalPrice[i] * 100.0) / 100.0;

            /*Display Price and Total Price*/
            System.out.printf("The Price of Prepaid Box & Envelope \t\t\t\t\t:RM%.2f%n", price[i]);
            System.out.printf("The Total Price of Prepaid Box & Envelope \t\t\t\t:RM%.2f%n", totalPrice[i]);
            do {
                System.out.printf("\nDo you still want to buy?(1 to order, 2 to close)\t\t:");
                choice = input.nextInt();
            } while (choice != 1 & choice != 2);

            /*Calculate the grand price*/
            grandPrice += totalPrice[i];
            i++;
        }

        /*Display Receipt*/
        System.out.printf("\n%30s\n", "Receipt");
        System.out.printf(format2, "Prepaid Box & Envelope", "Total Price(RM)");
        i = 0;
        while (price[i] != 0) {
            System.out.printf(format3, typeDelivery[i], "-", price[i], "x", numberDelivery[i], totalPrice[i]);
            i++;
        }
        System.out.printf(format4, "Grand Total Price is", grandPrice);

        /*Prompt the user to make seletion*/
        System.out.println("\n\n1.\t\t\tBack to Main Menu.");
        System.out.println("2.\t\t\tExit.");
        do {
            System.out.printf("Enter your chose you want\t\t\t:");
            menu = input.nextInt();
        } while (menu != 1 & menu != 2);

        switch (menu) {

            case 1:
                System.out.println("\n\n");
                Main_Menu.main(args);
                break;

            default:
                System.out.println("\n\n");
                pos.displayReceipt();
                break;
        }
    }

    public static String WeightofDelivery(double weight) {
        String jenis;

        if (weight <= 0.50) {
            jenis = "ES";
        } else if (weight > 0.50 & weight <= 1.00) {
            jenis = "EL";
        } else if (weight > 1.00 & weight <= 2.00) {
            jenis = "PBS";
        } else if (weight > 2.00 & weight <= 5.00) {
            jenis = "PBM";
        } else {
            jenis = "PBL";
        }

        return jenis;
    }

    public static String numForJenis(int num) {
        String jenis;

        if (num == 1) {
            jenis = "ES";
        } else if (num == 2) {
            jenis = "EL";
        } else if (num == 3) {
            jenis = "PBS";
        } else if (num == 4) {
            jenis = "PBM";
        } else {
            jenis = "PBL";
        }

        return jenis;
    }

    public static double FindPrice(String jenis) {
        double harga;

        switch (jenis) {
            case "ES":
                harga = 7.31;
                break;

            case "EL":
                harga = 10.49;
                break;

            case "PBS":
                harga = 13.78;
                break;

            case "PBM":
                harga = 21.20;
                break;

            default:
                harga = 31.80;
                break;
        }
        return harga;
    }

    public static double grand() {
        return grandPrice;
    }
}
