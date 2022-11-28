import java.util.*;

public class Dice extends AbstractDice {
  
  public static void main(String[] args){
    Dice die = new Dice();
    die.roll();
    System.out.println(die.getValue());
    die.roll();
    System.out.println(die.getValue());
    die.roll();
    System.out.println(die.getValue());
    die.roll();
    System.out.println(die.getValue());
    die.roll();
    System.out.println(die.getValue());
    die.roll();
    System.out.println(die.getValue());
  } // end main

  public Dice(){
    this.populateDict();
  } // end default constructor
  
  public void populateDict(){
    this.dict.put(0, "1");
    this.dict.put(1, "2");
    this.dict.put(2, "3");
    this.dict.put(3, "Energy");
    this.dict.put(4, "Smash");
    this.dict.put(5, "Heal");
  } // end populate_dict

} // end class def
