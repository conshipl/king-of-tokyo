import java.util.*;

public class MainMenu {

  protected Game game = new Game();
  
  public static void main(String[] args){
    MainMenu main_menu = new MainMenu();
  } // end main

  public MainMenu(){
    ;
  } // end default constructor

  /* Should be able to visualize binary tree (print in console) per Jovian

  /* Should be able to search and view the stats for an individual player (W/D/L and Most/Least Played Monsters)*/
  /* You can deserialize the same object twice into two different (in memory location) instances with the same data*/

  public void menu(){
    Scanner input = new Scanner(System.in);
    System.out.println("---KING OF TOKYO---\n");
    System.out.print("1) Start New Game\n2) Load Saved Game\n3) View Player Tree\n\nEnter Choice: ");
    String choice = input.nextLine();

    if (choice.equals("1")){
      ;
    } else if (choice.equals("2")){
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
    } else if (choice.equals("3")){
      ;
    } // end if
	
  } // end menu

} // end class def
