import java.util.*;

public class Monster {
  
  protected String name;
  protected int health = 10;
  protected int victory_points = 0;
  protected int energy = 0;
  protected String location = "Outside";

  public static void main(String[] args){
    Monster monster = new Monster();
    monster.showMonster();
  } // end main

  public Monster(){
    ;
  } // end default constructor

  public void showMonster(){
    System.out.println("Monster: " + this.name + "\nLocation: " + this.location + "\nHealth: " + this.health + "\nEnergy: " + this.energy + "\nVictory Points: " + this.victory_points);
  } // end showMonster

  public boolean checkHealth(){
    if (this.health > 0){
      return true;
    } // end if
    return false;
  } // end checkHealth

  public boolean checkVictoryPoints(){
    if (this.victory_points > 19){
      return true;
    } // end if
    return false;
  } // end checkVictoryPoints
 
  public void inTokyo(){
    if (this.location.equals("Tokyo")){
      System.out.println(this.name + " starts their turn in Tokyo and is awarded 2 Victory Points!\n");
      this.victory_points += 2;
    } // end if
  } // end inTokyo

  public void enterTokyo(){
    System.out.println(this.name + " enters Tokyo and is awarded 1 Victory Point!");
    this.victory_points += 1;
    this.location = "Tokyo";
  } // end enterTokyo

  public boolean yieldTokyo(){
    if (this.location.equals("Tokyo")){
      Scanner input = new Scanner(System.in);
      System.out.print("Would " + this.name + " like to yield Tokyo (Y/N)? ");
      if (input.nextLine().equals("Y")){
	System.out.println(this.name + " has yielded Tokyo!");
        this.location = "Outside";
        return true;
      } // end if
    } // end if
    return false;
  } // end yieldTokyo

} // end class def
