import java.util.*;

public class Game {

  protected Dice[] dice = new Dice[6];

  public static void main(String[] args){
    Game game = new Game();
    game.populateDice();
    game.chooseDice();
  } // end main

  public Game(){
    ;
  } // end default constructor

  public void populateDice(){
    for (int i = 0; i < dice.length; i++){
      dice[i] = new Dice();
    } // end for
  } // end populateDice

  public void rollDice(){
    for (Dice die: this.dice){
      if (!die.keep_status){   
        die.roll();
      } // end if
    } // end for
  } // end rollDice

  public void showDice(){
    for (int i = 0; i < (dice.length - 1); i++){
      System.out.print(dice[i].getValue() + ", ");
    } // end for
    System.out.println(dice[dice.length - 1].getValue());
  } // end showDice

  public String[] whichDice(){
    String keepers;
    Scanner input = new Scanner(System.in);
    System.out.print("Which dice would you like to keep? Enter as CSV, i.e. 1,3,5 will keep first, third, and fifth die; 7 will keep all dice: ");
    keepers = input.nextLine();
    return keepers.split(",");   
  } // end keepDice

  public boolean keepDice(String[] choices){
    if (choices[0] == "7"){
      return false;
    } else {
      for (int i = 0; i < choices.length; i++){
        int choice = Integer.parseInt(choices[i]) - 1;
	this.dice[choice].keep_status = true;
      } // end for
      return true;
    } // end if
  }

  public void chooseDice(){
    int i = 0;
    boolean keepGoing = true;
    String[] choices;
    
    while (i < 2 & keepGoing){
      this.rollDice();
      this.showDice();
      this.resetDice();
      choices = this.whichDice();
      keepGoing = this.keepDice(choices); 
      i++;
    } // end while
    this.rollDice();
    this.showDice();
  } // end chooseDice

  public void resetDice(){
    for (Dice die: this.dice){
      die.keep_status = false;
    } // end for
  } // end resetDice

} // end class def
