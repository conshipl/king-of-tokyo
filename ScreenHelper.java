import java.util.*;

abstract class ScreenHelper {
  
  public void pressAnyKey(){
    Scanner input = new Scanner(System.in);
    System.out.print("Press any key to continue.");
    input.nextLine();
    this.clearScreen();
  } // end pressAnyKey

  public void clearScreen(){  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  } // end clearScreen

} // end class def
