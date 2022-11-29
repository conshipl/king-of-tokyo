import java.util.*;
import java.io.*;

public class Player implements Serializable, Comparable<Player> {

  protected String name;
  protected String password;
  protected transient Monster monster = new Monster();
  protected transient ArrayList<Player> opps = new ArrayList<>();
  protected int wins;
  protected int draws;
  protected int losses;
  protected Hashtable<String, Integer> monster_count;

  public static void main(String[] args){
    Player player = new Player();
    player.showPlayer();
    player.showStats();
  } // end main

  public Player(){
    this.accountName();
    this.accountPassword();
    this.wins = 0;
    this.draws = 0;
    this.losses = 0;
    this.monster_count = new Hashtable<String, Integer>();
    this.populateMonsterCounter();
  } // end default constructor

  // override compareTo
  public int compareTo(Player opponent){
    if (this.monster.d_twenty.value == opponent.monster.d_twenty.value){
      return 0;
    } else if (this.monster.d_twenty.value > opponent.monster.d_twenty.value){
      return 1;
    } else {
      return -1;
    } // end if
  } // end compareTo

  public void populateMonsterCounter(){
    this.monster_count.put("GIGAZAUR", 0);
    this.monster_count.put("CYBERKITTY", 0);
    this.monster_count.put("MEKADRAGON", 0);
    this.monster_count.put("SPACEPENGUIN", 0);
    this.monster_count.put("KRAKEN", 0);
    this.monster_count.put("KONG", 0);
  } // end populateMonsterCounter

  public String leastPlayedMonster(){
    String monster = "NONE";
    int count = 0;
    Enumeration<String> monsters = monster_count.keys();
    while (monsters.hasMoreElements()){
      String key = monsters.nextElement();
      if (monster.equals("NONE") || monster_count.get(key) < count){
        monster = key;
	count = monster_count.get(key);
      } // end if
    } // end while
    return (monster + ", " + count + " plays");  
  } // end leastPlayedMonster

  public String mostPlayedMonster(){
    String monster = "NONE";
    int count = 0;
    Enumeration<String> monsters = monster_count.keys();
    while (monsters.hasMoreElements()){
      String key = monsters.nextElement();
      if (monster.equals("NONE") || monster_count.get(key) > count){
        monster = key;
	count = monster_count.get(key);
      } // end if
    } // end while
    return (monster + ", " + count + " plays");  
  } // end leastPlayedMonster

  public void accountName(){
    Scanner input = new Scanner(System.in);
    System.out.print("Enter new account name: ");
    this.name = input.nextLine();
  } // end accountName

  public void accountPassword(){
    Scanner input = new Scanner(System.in);
    System.out.print("Enter new account password: ");
    this.password = input.nextLine();
  } // end accountPassword

  public void showStats(){
    System.out.println("Player: " + this.name + "\nWins: " + this.wins + "\nDraws: " + this.draws + "\nLosses: " + this.losses + "\nLeast Played Monster: " + this.leastPlayedMonster() + "\nMost Played Monster: " + this.mostPlayedMonster());
  } // end showStats

  public void showPlayer(){
    System.out.println("\nPlayer: " + this.name);
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
      if (!opponent.monster.location.equals(this.monster.location) && opponent.monster.checkHealth()){
	System.out.println(this.monster.name + " attacks " + opponent.monster.name + " for " + count + " damage!");
        opponent.monster.health -= count;
      } // end if
      if (opponent.monster.location.equals("Tokyo")){
	if (!opponent.monster.checkHealth()){
          this.monster.enterTokyo();
        } else {
          current_king = opponent;
	} // end if
      } // end if
    } // end for
    return current_king;   
  } // end applySmash

} // end class def
