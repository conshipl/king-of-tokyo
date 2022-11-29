import java.util.*;
import java.io.*;

public class MainMenu extends ScreenHelper {

  protected Game game;
  protected ArrayList<Player> accounts;
  
  public static void main(String[] args){
    MainMenu main_menu = new MainMenu();
  } // end main

  public MainMenu(){
    this.menu();
  } // end default constructor

  /* Should be able to search and view the stats for an individual player (W/D/L and Most/Least Played Monsters)*/
  /* You can deserialize the same object twice into two different (in memory location) instances with the same data*/

  public void menu(){
    this.clearScreen();
    boolean keepGoing = true;
    while (keepGoing){
      Scanner input = new Scanner(System.in);
      System.out.println("---KING OF TOKYO---\n");
      System.out.print("1) Start New Game\n2) View All Players\n3) Search Player Stats\n4) Exit\n\nEnter Choice: ");
      String choice = input.nextLine();

      if (choice.equals("1")){
	this.clearScreen();
        this.game = new Game();
      } else if (choice.equals("2")){
        this.viewAllPlayers();
      } else if (choice.equals("3")){
        this.searchPlayer();
      } else if (choice.equals("4")){
        keepGoing = false;
	this.clearScreen();
      } // end if
    } // end while
  } // end menu

  public void searchPlayer(){
    this.populateAccounts();
    this.clearScreen();
    boolean found = false;
    Scanner input = new Scanner(System.in);
    System.out.print("Enter Player Name: ");
    String target = input.nextLine();
    for (Player account: this.accounts){
      if (account.name.equals(target)){
	System.out.println("");
        account.showStats();
	found = true;
	System.out.println("");
      } // end if
    } // end for
    if (!found){
      System.out.println("\nPlayer Not Found.\n");
    } // end if
    this.pressAnyKey();
  } // end searchPlayer

  public void viewAllPlayers(){
    this.populateAccounts();
    this.clearScreen();
    for (Player account: this.accounts){
      account.showStats();
      System.out.println("");
    } // end for
    this.pressAnyKey();
  } // end viewAllPlayers

  public void populateAccounts(){
    try {
      FileInputStream fileIn = new FileInputStream("accounts.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);
      this.accounts = (ArrayList<Player>)objIn.readObject();
      objIn.close();
      fileIn.close();
    } catch (IOException e){
      System.out.println(e.getMessage());
      System.out.println("\nReturning to Main Menu.");
    } catch (ClassNotFoundException c){
      System.out.println(c.getMessage());
    } // end try-catch
  } // end populateAccounts

} // end class def
