import java.util.*;

public class Player {

  protected String name;
  protected Monster monster = new Monster();
  protected ArrayList<Player> opps = new ArrayList<>();

  public static void main(String[] args){
    Player player = new Player();
    player.showPlayer();
  } // end main

  public Player(){
    ;
  } // end default constructor

  public void showPlayer(){
    System.out.println("\nPlayer: " + name);
    this.monster.showMonster();
  } // end showPlayer

  public void resolveDice(Dice[] dice){
    Player current_king = this;
    for (Dice die: dice){
      if (die.keep_status){
        ;
      } else if (die.getValue().equals("1") || die.getValue().equals("2") || die.getValue().equals("3")){
        this.resolveDigits(dice);
      } else if (die.getValue().equals("Energy")){
        this.monster.energy += 1;
      } else if (die.getValue().equals("Heal")){
        this.resolveHeal();
      } else {
        current_king = this.resolveSmash(dice);
      } // end if
    } // end for
    if (current_king != this){
      if (current_king.monster.yieldTokyo()){
        this.monster.enterTokyo();
      } // end if
    } // end if
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

    this.scoreDigits(count_ones, 1);
    this.scoreDigits(count_twos, 2);
    this.scoreDigits(count_threes, 3);
  } // end resolveDigits

  public void scoreDigits(int count, int factor){
    if (count >= 3){
      int points_scored = (factor + (count - 3));
      System.out.println(this.monster.name + " scores " + points_scored + " Victory Points for rolling " + count + " " + factor + "'s!");
      this.monster.victory_points += points_scored;
    } // end if
  } // end scoreDigits

  public void resolveHeal(){
    if (this.monster.location.equals("Outside") && this.monster.health < 10){
      System.out.println(this.monster.name + " heals for 1 hp!");
      this.monster.health += 1;
    } else {
      System.out.println("Cannot heal: already at max health or location is Tokyo");
    } // end if
  } // end resolveHeal

  public Player resolveSmash(Dice[] dice){
    int count_smash = 0;
    for (Dice die: dice){
      if (die.getValue().equals("Smash")){
        count_smash += 1;
	die.keep_status = true;
      } // end if
    } // end for
	
    return this.applySmash(count_smash);
  } // end resolveSmash

  public Player applySmash(int count){
    Player current_king = this;
    for (Player opponent: this.opps){
      if (!opponent.monster.location.equals(this.monster.location)){
	System.out.println(this.monster.name + " attacks " + opponent.monster.name + " for " + count + " damage!");
        opponent.monster.health -= count;
      } // end if
      if (opponent.monster.location.equals("Tokyo")){
        current_king = opponent;
      } // end if
    } // end for
    return current_king;   
  } // end applySmash

} // end class def
