package my.example.PosLajuMalaysia;

import java.util.Scanner;

public class Menu1_NextDayDelivery implements Runnable {

    @Override
    public void run() {
        String format1 = "%35s%50s%27s%s%n";
        String format2 = "%20s%25s%25s%25s%17s%s%n";
        String format3 = "%-10s%7s%23s%26s%22s%24s%s%n";
        String format4 = "%95s%n";

        System.out.println("1. NEXT-DAY DELIVERY");
        System.out.println("The service promises the delivery of goods or mails the next day. \nThe table below shows the payment rates by zone, weight and package type.\n");
        System.out.printf("%64s\n", "Menu of Next-Day Delivery");
        System.out.printf("%123s\n", "| Thread Name");
        System.out.printf(format1, "Document(below 2kg)", "Parcel(above 2kg)", "| ", Thread.currentThread().getName());
        System.out.printf(format2, "First 500gm", "Subsequent 250gm", "2.001-2.5kg", "Subsequent 500gm", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Zone 1", "4.90", "0.80", "10.50", "0.50", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Zone 2", "5.40", "1.00", "16.00", "2.00", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Zone 3", "6.90", "1.50", "21.00", "3.00", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Zone 4", "7.40", "1.50", "26.00", "3.50", "| ", Thread.currentThread().getName());
        System.out.printf(format3, "Zone 5", "7.90", "2.00", "31.00", "4.00", "| ", Thread.currentThread().getName());
        System.out.printf(format4, "All charges in RM");

    }

    public static double grand_total = 0;
    static Pos pos = new Pos();
    static double[] total_price = new double[30];
    static double[] each_weight = new double[30];
    static int i = 0;
    static String[] goods = new String[30];


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int menu, number, zone, quantity;
        double weight, lastestPrice = 0;
        int[] each_zone = new int[30];


        // Create threads
        (new Thread(new Menu1_NextDayDelivery())).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        do {
            System.out.println("\nDo you want to place your order?\nIf YES, press number 1.\nIf NO, press number 2.");
            number = sc.nextInt();
        } while (number != 1 & number != 2);

        while (number != 2) {
            do {
                System.out.println("\nCategory each zone:");
                System.out.println("Zone 1: City");
                System.out.println("Zone 2: In Peninsular Malaysia, Sarawak and Sabah");
                System.out.println("Zone 3: Between Sabah and Sarawak");
                System.out.println("Zone 4: Between Peninsular Malaysia and Sarawak");
                System.out.println("Zone 5: Between Peninsular Malaysia and Sabah");
                System.out.printf("\nPlease enter zone = ");
                zone = sc.nextInt();
            } while (zone != 1 & zone != 2 & zone != 3 & zone != 4 & zone != 5);

            do {
                System.out.printf("\nPlease enter the weight of goods(in gm) = ");
                weight = sc.nextDouble();

                if (weight < 0) {
                    System.out.println("Invalid input, please enter the weight again.");
                }
            } while (weight < 0);

            /*Call Method*/
            goods[i] = typeOfGoods(weight);

            System.out.println("Your Type of Goods Recommended is " + goods[i] + ".");

            System.out.printf("\nPlease enter the quantity = ");
            quantity = sc.nextInt();
            System.out.println("");

            pos = new NextDayDelivery(weight, quantity, zone);

            double price = priceCalculation(lastestPrice);

            each_zone[i] = ((NextDayDelivery) pos).getZone();
            each_weight[i] = pos.getWeight();

            do {
                System.out.println("\nDo you want continue to place your order?\nIf YES, press number 1.\nIf NO, press number 2.");
                number = sc.nextInt();
            } while (number != 1 & number != 2);

            i++;
        }

        /*Display grand total*/
        String format5 = "%-32s%-10s\n",
                format6 = "%-3d%-13s%-4s%-2d%-9s%-10.2f\n",
                format7 = "%-32s%-10.2f";

        System.out.printf("%n%n%26s%n", "Receipt");
        System.out.printf(format5, "Next-Day Delivery", "Total Price(RM)");
        i = 0;
        int j = 1;

        while (total_price[i] != 0) {
            if (each_weight[i] < 2000) {
                System.out.printf(format6, j, "Document for ", "Zone ", each_zone[i], "-", total_price[i]);
            } else {
                System.out.printf(format6, j, "Parcel for ", "Zone ", each_zone[i], "-", total_price[i]);
            }

            grand_total += total_price[i];
            i++;
            j++;
        }

        pos = new NextDayDelivery(pos.getWeight(), pos.getQuantity(), grand_total, ((NextDayDelivery) pos).getZone());
        System.out.printf(format7, "Grand Total Price is", pos.getPrice());

        /*Prompt the user to make seletion*/
        System.out.println("\n\n1.\t\t\tBack to Main Menu.");
        System.out.println("2.\t\t\tExit.");
        do {
            System.out.printf("Enter your chose you want\t\t\t:");
            menu = sc.nextInt();
        } while (menu != 1 & menu != 2);

        if (menu == 1) {
            System.out.println("\n\n");
            Main_Menu.main(args);
        } else {
            System.out.println("\n\n");
            pos.displayReceipt();
        }
    }

    public static String typeOfGoods(double weight) {

        String goods;

        if (weight < 2000) {
            goods = "Document";
        } else {
            goods = "Parcel";
        }

        return goods;
    }

    public static double grand() {

        return grand_total;
    }

    static double priceCalculation(double lastestPrice) {
        double additional_weight = 0, total_price_zone_1 = 0, total_price_zone_2 = 0, total_price_zone_3 = 0, total_price_zone_4 = 0, total_price_zone_5 = 0;
        double weight_rate, weight_remainder, total_additional_price;

        /*Calculation Price For Zone 1*/
        double weight_first = 500;
        double subsequent_weight = 250;
        double additional_price = 0.80;
        double price = 4.90;


        if (((NextDayDelivery) pos).getZone() == 1 && pos.getWeight() <= 500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));

            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 1 && pos.getWeight() > 500 && pos.getWeight() < 2000) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_1 = (total_additional_price + weight_remainder + price);
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_1);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_1 * pos.getQuantity()));
            total_price[i] = total_price_zone_1 * pos.getQuantity();
        }

        weight_first = 2500;
        subsequent_weight = 500;
        additional_price = 0.50;
        price = 10.50;

        if (((NextDayDelivery) pos).getZone() == 1 && pos.getWeight() >= 2000 && pos.getWeight() <= 2500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 1 && pos.getWeight() > 2500) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_1 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_1);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_1 * pos.getQuantity()));
            total_price[i] = total_price_zone_1 * pos.getQuantity();
        }

        /*Calculation Price For Zone 2*/
        weight_first = 500;
        subsequent_weight = 250;
        additional_price = 1.00;
        price = 5.40;

        if (((NextDayDelivery) pos).getZone() == 2 && pos.getWeight() <= 500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 2 && pos.getWeight() > 500 && pos.getWeight() < 2000) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_2 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_2);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_2 * pos.getQuantity()));
            total_price[i] = total_price_zone_2 * pos.getQuantity();
        }

        weight_first = 2500;
        subsequent_weight = 500;
        additional_price = 2.00;
        price = 16.00;

        if (((NextDayDelivery) pos).getZone() == 2 && pos.getWeight() >= 2000 && pos.getWeight() <= 2500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 2 && pos.getWeight() > 2500) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_2 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_2);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_2 * pos.getQuantity()));
            total_price[i] = total_price_zone_2 * pos.getQuantity();
        }

        /*Calculation Price For Zone 3*/
        weight_first = 500;
        subsequent_weight = 250;
        additional_price = 1.50;
        price = 6.90;

        if (((NextDayDelivery) pos).getZone() == 3 && pos.getWeight() <= 500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 3 && pos.getWeight() > 500 && pos.getWeight() < 2000) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_3 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_3);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_3 * pos.getQuantity()));
            total_price[i] = total_price_zone_3 * pos.getQuantity();
        }

        weight_first = 2500;
        subsequent_weight = 500;
        additional_price = 3.00;
        price = 21.00;

        if (((NextDayDelivery) pos).getZone() == 3 && pos.getWeight() >= 2000 && pos.getWeight() <= 2500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 3 && pos.getWeight() > 2500) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_3 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_3);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_3 * pos.getQuantity()));
            total_price[i] = total_price_zone_3 * pos.getQuantity();
        }

        /*Calculation Price For Zone 4*/
        weight_first = 500;
        subsequent_weight = 250;
        additional_price = 1.50;
        price = 7.40;

        if (((NextDayDelivery) pos).getZone() == 4 && pos.getWeight() <= 500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 4 && pos.getWeight() > 500 && pos.getWeight() < 2000) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_4 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_4);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_4 * (price * pos.getQuantity())));
            total_price[i] = total_price_zone_4 * price * pos.getQuantity();
        }

        weight_first = 2500;
        subsequent_weight = 500;
        additional_price = 3.50;
        price = 26.00;

        if (((NextDayDelivery) pos).getZone() == 4 && pos.getWeight() >= 2000 && pos.getWeight() <= 2500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 4 && (pos.getWeight() > 2500)) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_4 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_4);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_4 * pos.getQuantity()));
            total_price[i] = total_price_zone_4 * pos.getQuantity();
        }

        /*Calculation Price For Zone 5*/
        weight_first = 500;
        subsequent_weight = 250;
        additional_price = 2.00;
        price = 7.90;

        if (((NextDayDelivery) pos).getZone() == 5 && (pos.getWeight() <= 500)) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 5 && pos.getWeight() > 500 && pos.getWeight() < 2000) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_5 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_5);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_5 * pos.getQuantity()));
            total_price[i] = total_price_zone_5 * pos.getQuantity();
        }

        weight_first = 2500;
        subsequent_weight = 500;
        additional_price = 4.00;
        price = 31.00;

        if (((NextDayDelivery) pos).getZone() == 5 && pos.getWeight() >= 2000 && pos.getWeight() <= 2500) {
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", price);
            System.out.printf("Total Price           = RM%.2f%n", (price * pos.getQuantity()));
            total_price[i] = price * pos.getQuantity();
        } else if (((NextDayDelivery) pos).getZone() == 5 && pos.getWeight() > 2500) {
            additional_weight = pos.getWeight() - weight_first;
            weight_rate = (additional_weight / subsequent_weight);
            weight_remainder = (additional_weight % subsequent_weight);

            if (weight_remainder > 0) {
                weight_remainder = additional_price;
            }
            total_additional_price = Math.round((int) weight_rate) * additional_price;
            total_price_zone_5 = total_additional_price + weight_remainder + price;
            System.out.println("Type of goods         = " + goods[i]);
            System.out.printf("Quantity              = %s%n", pos.getQuantity());
            System.out.printf("Price of each goods   = RM%.2f%n", total_price_zone_5);
            System.out.printf("Total Price           = RM%.2f%n", (total_price_zone_5 * pos.getQuantity()));
            total_price[i] = total_price_zone_5 * pos.getQuantity();
        }

        return price;
    }

}
