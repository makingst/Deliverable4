package ser322;
import java.util.Scanner;

public class ElectronicsStoreApp {

    public static void main(String[] args){
        String query;
        Scanner in = new Scanner(System.in);  // Create a Scanner object
        do{
            System.out.println("Available queries");
            query = in.nextLine();  // Read user input
            System.out.println("My input is: " + query);  // Output user input
        }while(!query.equals("exit"));
        System.exit(0);
    }
}