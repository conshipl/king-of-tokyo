import java.util.*;
import java.io.*;

public class Game {

  protected Dice[] dice = new Dice[6];
  protected ArrayList<Player> players = new ArrayList<Player>();
  protected ArrayList<String> monsters = new ArrayList<String>();
  protected ArrayList<Player> accounts;

  public static void main(String[] args){
    Game game = new Game();
    game.match();
  } // end main

  public Game(){
    this.populateAccounts();
    this.populateMonsters();
    this.populateDice();
    this.populatePlayers();
    this.populateOpponents();
    this.match();
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

  public void awardWinLoss(Player player){
    player.wins += 1;
    for (Player opp: player.opps){
      opp.losses += 1;
    } // end for
  } // end awardWDL

  public void awardDraw(){
    for (Player player: this.players){
      player.draws += 1;
    } // end for
  } // end awardDraw

  public boolean checkWinner(){
    for (Player player: this.players){
      if (this.victoryByDeath(player)){
	return true;
      } else if (this.victoryByPoints(player)){
        return true;	
      } else if	(this.countAlive() == 0){
	return true;
      } // end if
    } // end for
    return false;
  } // end checkWinner

  public boolean declareWinner(){
    for (Player player: this.players){
      if (this.victoryByDeath(player)){
	this.clearScreen();
	this.awardWinLoss(player);
        System.out.println(player.monster.name + " has won the game by eliminating all other monsters!\n");
	this.incMonsterCount();
	this.serialize();
	this.pressAnyKey();
	return true;
      } else if (this.victoryByPoints(player)){
	this.clearScreen();
	this.awardWinLoss(player);
        System.out.println(player.monster.name + " has won the game by being the first to reach 20 Victory Points!\n");
	this.incMonsterCount();
	this.serialize();
	this.pressAnyKey();
        return true;	
      } else if	(this.countAlive() == 0){
	this.clearScreen();
	this.awardDraw();
        System.out.println("All players have died battling for Tokyo; NO WINNER!\n");
	this.incMonsterCount();
	this.serialize();
	this.pressAnyKey();
	return true;
      } // end if
    } // end for
    return false;
  } // end checkWinner

  public void incMonsterCount(){
    for (Player player: this.players){
      player.monster_count.replace(player.monster.name, player.monster_count.get(player.monster.name) + 1);
    } // end for
  } // end incMonsterCount

  public void match(){
    while (!this.checkWinner()){
      this.round();
    } // end while
    this.declareWinner();
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

  public void pressAnyKey(){
    Scanner input = new Scanner(System.in);
    System.out.print("Press any key to continue.");
    input.nextLine();
    this.clearScreen();
  } // end pressAnyKey

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

  public Player playerMenu(int i){
    boolean keepGoing = true;
    while (keepGoing){
      Scanner input = new Scanner(System.in);
      System.out.print("---Player " + i + "---\n\n1) Login\n2) Create New Account\n\nEnter choice: ");
      String choice = input.nextLine();
      if (choice.equals("1")){
        keepGoing = false;
	Player player = this.login();
	return player;
      } else if (choice.equals("2")){
        keepGoing = false;
	// add player to binary tree (and serialize?)
      } else {
        System.out.println("Sorry, I didn't understand. Please try again!\n");
      } // end if
    } // end while
    Player player = new Player();
    this.accounts.add(player);
    return player;
  } // end playerMenu

  public Player login(){
    boolean keepGoing = true;
    while (keepGoing){
      Scanner input = new Scanner(System.in);
      System.out.print("Enter account name (type EXIT to create new player and quit login): ");
      String inp_acc_name = input.nextLine();
      System.out.print("Enter account password: ");
      String inp_acc_pw = input.nextLine();      
      for (Player account: this.accounts){
        if (account.name.equals(inp_acc_name) && account.password.equals(inp_acc_pw)){
	  keepGoing = false;
	  account.monster = new Monster();
	  account.opps = new ArrayList<>();
	  return account;
        } // end if
      } // end for
      if (inp_acc_name.equals("EXIT")){
        keepGoing = false;
      } else {
	System.out.println("Incorrect name or password; please try again!");      
      } // end if
    } // end while
    Player player = new Player();
    this.accounts.add(player);
    return player;
  } // end login

  public void populateMonsters(){
    this.monsters.add("GIGAZAUR");
    this.monsters.add("CYBERKITTY");
    this.monsters.add("MEKADRAGON");
    this.monsters.add("SPACEPENGUIN");
    this.monsters.add("KRAKEN");
    this.monsters.add("KONG");   
  } // end populateMonsters

  public void populateAccounts(){
    try {
      FileInputStream fileIn = new FileInputStream("accounts.ser");
      ObjectInputStream objIn = new ObjectInputStream(fileIn);
      this.accounts = (ArrayList<Player>)objIn.readObject();
      objIn.close();
      fileIn.close();
    } catch (IOException e){
      System.out.println(e.getMessage());
      this.accounts = new ArrayList<>();
    } catch (ClassNotFoundException c){
      System.out.println(c.getMessage());
    } // end try-catch
  } // end populateAccounts

  public void populatePlayers(){
    int num_players = this.numberOfPlayers();
    for (int i = 0; i < num_players; i++){
      Player player = this.playerMenu(i + 1);
      this.players.add(player);
      this.chooseMonster(player);
      this.pressAnyKey();
    } // end for
    this.sortPlayers();
  } // end populatePlayers

  public void sortPlayers(){
    System.out.println("--ROLLING FOR TURN ORDER--\n");
    for (Player player: this.players){
      System.out.println(player.name + " playing as " + player.monster.name + " rolled a " + player.monster.d_twenty.value + ".");
    } // end for
    Collections.sort(this.players, Collections.reverseOrder());
    System.out.println("\n--FINAL TURN ORDER--\n");
    for (Player player: this.players){
      System.out.println(player.name + " playing as " + player.monster.name);
    } // end for
  } // end sortPlayers

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

  public void serialize(){
    try {
      FileOutputStream fileOut = new FileOutputStream("accounts.ser");
      ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
      objOut.writeObject(this.accounts);
      objOut.close();
      fileOut.close();
    } catch (IOException e){
      System.out.println(e.getMessage());
    } // end try-catch
  } // end serialize

} // end class def
