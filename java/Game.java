import java.util.*;

public class Game {

  protected Dice[] dice = new Dice[6];
  protected ArrayList<Player> players = new ArrayList<Player>();

  public static void main(String[] args){
    Game game = new Game();
    game.match();
  } // end main

  public Game(){
    this.populateDice();
    this.populatePlayers();
    this.populateOpponents();
  } // end default constructor

  public void clearScreen(){  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  } // end clearScreen

  public boolean isTokyoEmpty(){
    for (Player player: this.players){
      if (player.monster.location.equals("Tokyo")){
        return false;
      } // end if
    } // end for
    return true;
  } // end checkTokyo

  public int countAlive(){
    int alive = 0;
    for (Player player: this.players){
      if (player.monster.checkHealth()){
        alive += 1;
      }
    } // end for
    return alive;
  } // end countAlive

  public boolean victoryByDeath(Player player){
    if (this.countAlive() == 1 && player.monster.checkHealth()){
      return true;
    } // end if
    return false;
  } // end victoryByDeath

  public boolean victoryByPoints(Player player){
    if (player.monster.checkHealth() && player.monster.checkVictoryPoints()){
      return true;
    } // end if
    return false;
  } // end victoryByPoints

  public boolean checkWinner(){
    for (Player player: this.players){
      if (this.victoryByDeath(player) || this.victoryByPoints(player)){
        System.out.println(player.name + " has won the game!");
	return true;
      } else if (this.countAlive() == 0){
        System.out.println("All players have died battling for Tokyo; NO WINNER!");
	return true;
      } // end if
    } // end for
    return false;
  } // end checkWinner

  public void match(){
    while (!this.checkWinner()){
      this.round();
    } // end while
  } // end match

  public void round(){
    for (Player player: this.players){
      if (player.monster.checkHealth() && !this.checkWinner()){
        this.turn(player);
      } // end if
    } // end for
  } // end round

  public void turn(Player player){
    this.startTurn(player);
    player.monster.inTokyo();
    this.showTable(player);
    this.chooseDice();
    System.out.println("\nRESOLVE TURN:\n");
    player.resolveDice(this.dice);
    if (this.isTokyoEmpty()){
      player.monster.enterTokyo();
    } // end if
    System.out.println("\n---FINAL RESULTS---\n");
    this.showTable(player);
  } // end turn

  public void startTurn(Player player){
    Scanner input = new Scanner(System.in);
    System.out.print("\nIt is now " + player.monster.name + "'s turn! Press any key to continue.");
    input.nextLine();
    this.clearScreen();
  } // end startTurn

  public void showTable(Player player){
    System.out.println("OPPONENTS:");
    for (Player opponent: player.opps){
      opponent.showPlayer();
    } // end for
    System.out.println("\nPLAYER:"); 
    player.showPlayer();
  } // end showTable

  public int numberOfPlayers(){
    int num_players = 0;
    Scanner input = new Scanner(System.in);
    while (num_players < 2 || num_players > 4){
      System.out.print("Enter number of players (min 2, max 4): ");
      num_players = Integer.parseInt(input.nextLine());
    } // end while
    this.clearScreen();
    return num_players;
  } // end numberOfPlayers

  public void populatePlayers(){
    int num_players = this.numberOfPlayers();
    for (int i = 0; i < num_players; i++){
      Player player = new Player();
      this.players.add(player);
    } // end for
  } // end populatePlayers

  public void populateOpponents(){
    for (Player player: this.players){
      for (Player opponent: this.players){
        if (player != opponent){
          player.opps.add(opponent);
        } // end if
      } // end for
    } // end for
  } // end populateOpponents

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
    System.out.println();
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
    if (choices[0].equals("")){
      return true;	    
    } else if (choices[0].equals("7")){
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
    this.resetDice();
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
    if (!keepGoing){
      System.out.print("[Final Roll]: ");
      this.showDice();
    } else {
      this.rollDice();
      System.out.print("[Final Roll]: ");
      this.showDice();
    } // end if
    this.resetDice();
  } // end chooseDice

  public void resetDice(){
    for (Dice die: this.dice){
      die.keep_status = false;
    } // end for
  } // end resetDice

} // end class def
