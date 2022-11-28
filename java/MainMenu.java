import java.util.*;

public class MainMenu {

  protected Game game;
  
  public static void main(String[] args){
    MainMenu main_menu = new MainMenu();
    main_menu.menu();
  } // end main

  public MainMenu(){
    ;
  } // end default constructor

  /* Should be able to visualize binary tree (print in console) per Jovian

  /* Should be able to search and view the stats for an individual player (W/D/L and Most/Least Played Monsters)*/
  /* You can deserialize the same object twice into two different (in memory location) instances with the same data*/

  public void clearScreen(){  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  } // end clearScreen

  public void menu(){
    boolean keepGoing = true;
    while (keepGoing){
      Scanner input = new Scanner(System.in);
      System.out.println("---KING OF TOKYO---\n");
      System.out.print("1) Start New Game\n2) View Player Tree\n3) View Player Stats\n4) Exit\n\nEnter Choice: ");
      String choice = input.nextLine();

      if (choice.equals("1")){
	this.clearScreen();
        this.game = new Game();
      } /*else if (choice.equals("2")){
        try {
          FileInputStream fileIn = new FileInputStream("game_data.ser");
	  ObjectInputStream objIn = new ObjectInputStream(fileIn);
	  this.game = (Game)objIn.readObject();
	  objIn.close();
	  fileIn.close();
        } catch (IOException e){
	  System.out.println(e.getMessage());
	  System.out.println("Game could not be loaded; new game has been created.");
        } catch (ClassNotFoundException c){
            System.out.println(c.getMessage());
        } // end try-catch
      }*/ else if (choice.equals("2")){
        ;
      } else if (choice.equals("3")){
        ;
      } else if (choice.equals("4")){
        keepGoing = false;
      } // end if
    } // end while
  } // end menu

} // end class def
