package PosLajuMalaysia;

import java.util.Scanner;

public class Menu4_PosEkspres implements Runnable {
    @Override
    public void run() {
        String format1 = "%-15s%-20s%-20s%-25s%-22s%s%-27s\n";

        /*Display Menu of Pos Ekspress*/
        System.out.println("4. POS EKSPRES");
        System.out.printf("%65s\n", "Menu of Pos Ekspress");
        System.out.printf("%115s\n", "| Thread Name");
        System.out.printf(format1, "Jenis", "LG", "LE(C5)", "LD(B4)", "LK", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Saiz", "220mm x 110mm", "229mm x 162 mm", "353mm x 250mm", "340mm x 250mm", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Berat Max", "0 - 100gm", "101 - 250gm", "251 - 500gm", "501 - 1000gm", "| ", Thread.currentThread().getName());
        System.out.printf(format1, "Harga", "RM 3.18", "RM 3.71", "RM 4.77", "RM 7.42", "| ", Thread.currentThread().getName(), "\n\n");
    }

    static Scanner scan = new Scanner(System.in);
    static double total = 0;
    static Pos pos = new Pos();

    public static void main(String[] args) {
        (new Thread(new Menu4_PosEkspres())).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int choice, menu, count = 0, row = 0, num = 1;
        float total = 0;

        String[][] arr = new String[100][2];
        int[][] arr2 = new int[100][4];
        double[][] arr3 = new double[100][2];

        do {
            menu();
            System.out.print("Your choice: ");
            choice = scan.nextInt();
            System.out.println("");
            if (choice == 0) {
                System.out.println("Thank you and Bye.");
                break;
            } else
                count = processChoice(arr, arr2, arr3, total, row, choice, count, num);
        } while (choice != 0);

        /*Prompt the user to make seletion*/
        System.out.println("\n\n1.\t\t\tBack to Main Menu.");
        System.out.println("2.\t\t\tExit.");
        do {
            System.out.printf("Enter the choice you want\t\t\t:");
            menu = scan.nextInt();
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

    static void menu() {
        System.out.println("\n\n:::::::: MENU :::::::::\n"
                + "1. Add Item\n"
                + "2. Display\n"
                + "0. EXIT Pos Ekspres");
    }

    static int processChoice(String[][] arr, int[][] arr2, double[][] arr3, double total, int row, int choice, int count, int num) {
        switch (choice) {
            case 1:
                count = addItem(arr, arr2, arr3, row, count, num);
                break;
            case 2:
                display(arr, arr2, arr3, total, row, count);
                break;
            default:
                System.out.println("Sorry your choice is not in the list. Please choose again.");
        }//switch
        return count;
    }//processChoice

    static int addItem(String[][] arr, int[][] arr2, double[][] arr3, int row, int count, int num) {
        if (count < arr.length) {
            System.out.print("Please enter the number of pos you want to make: ");
            int number_of_row = scan.nextInt();
            System.out.println("");

            for (row = 0; row < number_of_row; row++) {
                System.out.println("Pos " + (count + 1) + ": ");
                arr2[count][3] = count + 1;
                System.out.print("Please enter the type of pos(LG/LE/LD/LK) :");
                String type = scan.next() + scan.nextLine();
                pos = new PosEkspres(type);
                arr[count][0] = (((PosEkspres) pos).getType());
                arr[count][0] = arr[count][0].toUpperCase();
                while (!arr[count][0].equals("LG") && !arr[count][0].equals("LE") && !arr[count][0].equals("LD") && !arr[count][0].equals("LK")) {
                    System.out.println("Please enter the correct type.");
                    System.out.print("Type(LG/LE/LD/LK) :");
                    type = scan.next();
                    pos = new PosEkspres(type);
                    arr[count][0] = (((PosEkspres) pos).getType());
                    arr[count][0] = arr[count][0].toUpperCase();
                }

                switch (arr[count][0]) {
                    case "LG":
                        arr[count][0] = "LG";
                        arr[count][1] = "220mmx110mm";
                        arr2[count][0] = 100;
                        arr2[count][1] = 3;
                        arr3[count][0] = 3.18;
                        System.out.print("Please enter the quantity for this type: ");
                        arr2[count][2] = scan.nextInt();
                        arr3[count][1] = arr3[count][0] * arr2[count][2];
                        break;

                    case "LE":
                        arr[count][0] = "LE(C5)";
                        arr[count][1] = "229mmx162mm";
                        arr2[count][0] = 250;
                        arr2[count][1] = 5;
                        arr3[count][0] = 3.71;
                        System.out.print("Please enter the quantity for this type: ");
                        arr2[count][2] = scan.nextInt();
                        arr3[count][1] = arr3[count][0] * arr2[count][2];
                        break;

                    case "LD":
                        arr[count][0] = "LD(B4)";
                        arr[count][1] = "353mmx250mm";
                        arr2[count][0] = 500;
                        arr2[count][1] = 10;
                        arr3[count][0] = 4.77;
                        System.out.print("Please enter the quantity for this type: ");
                        arr2[count][2] = scan.nextInt();
                        arr3[count][1] = arr3[count][0] * arr2[count][2];
                        break;

                    case "LK":
                        arr[count][0] = "LK";
                        arr[count][1] = "340mmx250mm";
                        arr2[count][0] = 1000;
                        arr2[count][1] = 25;
                        arr3[count][0] = 7.42;
                        System.out.print("Please enter the quantity for this type: ");
                        arr2[count][2] = scan.nextInt();
                        arr3[count][1] = arr3[count][0] * arr2[count][2];
                }

                total += arr3[count][1];
                System.out.println();
                count++;
            }// end for
        } else
            System.out.println("List is FULL.");
        return count;
    }//addItem

    static void display(String[][] arr, int[][] arr2, double[][] arr3, double total, int row, int count) {
        if (count == 0)
            System.out.println("Sorry list is empty.\n");
        else
            System.out.printf("%n%n```````````````%n");
        System.out.println("Pos Express");
        System.out.printf("```````````````%n%n");
        System.out.printf("%-15s%-15s%-15s%-15s%-20s%-15s%-15s%-15s", "NO", "TYPE ", "SIZE ", "WEIGHT(GM) ", "THICKNESS (MAX)", "UNIT PRICE(RM)", "Quantity", "AMOUNT");
        System.out.printf("%n------------------------------------------------------------------------------------------------------------------------%n");
        for (row = 0; row < count; row++) {
            System.out.printf("%-15d%-15s%-15s%-15d%-20d%-15.2f%-15d%-15.2f%n", arr2[row][3], arr[row][0], arr[row][1], arr2[row][0], arr2[row][1], arr3[row][0], arr2[row][2], arr3[row][1]);
            total += arr3[row][1];
            pos.setPrice(total);
        }
        System.out.println("");
        System.out.printf("%n%n%110s%-1.2f%n", "TOTAL DUE: ", pos.getPrice());
    }//end display

    public static double grand() {
        return total;
    }
}
