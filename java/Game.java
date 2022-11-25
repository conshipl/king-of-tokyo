import java.util.*;

public class Game {

  protected Dice[] dice = new Dice[6];
  protected ArrayList<Player> players = new ArrayList<Player>();
  protected ArrayList<String> monsters = new ArrayList<String>();

  public static void main(String[] args){
    Game game = new Game();
    game.match();
  } // end main

  public Game(){
    this.populateMonsters();
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
      if (this.victoryByDeath(player)){
	this.clearScreen();
        System.out.println(player.monster.name + " has won the game by eliminating all other monsters!");
	return true;
      } else if (this.victoryByPoints(player)){
	this.clearScreen();
        System.out.println(player.monster.name + " has won the game by being the first to reach 20 Victory Points!");
        return true;	
      } else if	(this.countAlive() == 0){
	this.clearScreen();
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

  public void chooseMonster(Player player){
    Scanner input = new Scanner(System.in);
    String choice;
    System.out.println(player.name + ", please select which monster you'd like to play as:\n");
    for (int i = 0; i < this.monsters.size(); i++){
      int number = i + 1;
      System.out.println(number + ") " + this.monsters.get(i));
    } // end for
    System.out.print("\nEnter choice: ");
    choice = input.nextLine();
    int number_choice = Integer.parseInt(choice) - 1;
    player.monster.name = this.monsters.get(number_choice);
    System.out.println(player.name + " is playing as " + player.monster.name + "!\n");
    this.monsters.remove(number_choice);
  } // end chooseMonster

  /*public Player login(){
    // ask player if they'd like to login or create new player
    // if they select login, ask user to input login and password
    // try to load the binary tree from .ser file
    //   if unsuccessful, return new Player()
    //   if successful, traverse tree and find player and compare inputs
    //     if unsuccessful, ask user to login again
    //     if successful, return saved Player instance
    // if they select create new player, create new Player() and ask them to enter new login credentials
    //   add player to binary tree and serialize
    //   return new Player() instance
  } // end login*/

  public void populateMonsters(){
    this.monsters.add("GIGAZAUR");
    this.monsters.add("CYBERKITTY");
    this.monsters.add("MEKADRAGON");
    this.monsters.add("SPACEPENGUIN");
    this.monsters.add("KRAKEN");
    this.monsters.add("KONG");   
  } // end populateMonsters

  public void populatePlayers(){
    int num_players = this.numberOfPlayers();
    for (int i = 0; i < num_players; i++){
      Player player = new Player();
      this.players.add(player);
      this.chooseMonster(player);
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
      System.out.print("\n[Final Roll]: ");
      this.showDice();
    } else {
      this.rollDice();
      System.out.print("\n[Final Roll]: ");
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
