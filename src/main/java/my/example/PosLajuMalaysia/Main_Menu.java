package my.example.PosLajuMalaysia;

import java.util.Scanner;

public class Main_Menu {
    public static void main(String[] args) {
        Pos pos = new Pos();
        Scanner sc = new Scanner(System.in);
        int posMenu;

        /*Display Menu*/
        System.out.println("1.\t\t\tNext-Day Delivery Menu");
        System.out.println("2.\t\t\tSame-Day Delivery Menu");
        System.out.println("3.\t\t\tPrepaid Box & Envelope Menu");
        System.out.println("4.\t\t\tPos Ekspres Menu");
        System.out.println("5.\t\t\tExit.");

        /*Input the number for each menu*/
        do {
            System.out.printf("Enter the menu you want\t\t\t:");
            posMenu = sc.nextInt();
        } while (posMenu != 1 & posMenu != 2 & posMenu != 3 & posMenu != 4 & posMenu != 5);

        /*Call Method*/
        if (posMenu == 1) {
            System.out.println("\n");
            Menu1_NextDayDelivery.main(args);
        } else if (posMenu == 2) {
            System.out.println("\n");
            Menu2_SameDayDelivery.main(args);
        } else if (posMenu == 3) {
            System.out.println("\n");
            Menu3_PrepaidBoxNEnvelope.main(args);
        } else if (posMenu == 4) {
            System.out.println("\n");
            Menu4_PosEkspres.main(args);
        } else {
            System.out.println("\n");
            pos.displayReceipt();
        }
    }
}
