import java.util.*;

public class Player {

  protected String monster_name;
  protected int health = 10;
  protected int victory_points = 0;
  protected int energy = 0;
  protected ArrayList<Player> opps = new ArrayList<>();

  public static void main(String[] args){
    ;
  } // end main

  public Player(){
    ;
  } // end default constructor

  public void showPlayer(){
    System.out.println("Name: " + "Gary" + "\nHealth: " + this.health + "\nEnergy: " + this.energy + "\nVictory Points: " + this.victory_points);
  } // end showPlayer

  public void resolveDice(Dice[] dice){
    for (Dice die: dice){
      if (die.keep_status){
        ;
      } else if (die.getValue().equals("1") || die.getValue().equals("2") || die.getValue().equals("3")){
        this.resolveDigits(dice);
      } else if (die.getValue().equals("Energy")){
        this.energy += 1;
      } else if (die.getValue().equals("Heal")){
        this.resolveHeal();
      } else {
        this.resolveSmash();
      } // end if
    } // end for
  } // end resolveDice

  public void resolveDigits(Dice[] dice){
    int count_ones = 0;
    int count_twos = 0;
    int count_threes = 0;

    for (Dice die: dice){
      if (die.getValue().equals("1")){
        count_ones += 1;
	die.keep_status = true;
      } else if (die.getValue().equals("2")){
        count_twos += 1;
	die.keep_status = true;
      } else if (die.getValue().equals("3")){
        count_threes += 1;
	die.keep_status = true;
      } // end if
    } // end for

    this.scoreDigits(count_ones);
    this.scoreDigits(count_twos);
    this.scoreDigits(count_threes);
  } // end resolveDigits

  public void scoreDigits(int count){
    this.victory_points += (count / 3);
    count -= 3;
    if (count > 0){
      this.victory_points += (count % 3);
    } // end if
  } // end scoreDigits

  public void resolveHeal(){
    if (this.health < 10){
      this.health += 1;
    } else {
      System.out.println("Already at max health");
    } // end if
  } // end resolveHeal

  public void resolveSmash(){
    for (Player opponent: this.opps){
      opponent.health -= 1;
    } // end for
  } // end resolveSmash

} // end class def
