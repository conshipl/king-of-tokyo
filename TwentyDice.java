import java.util.*;

public class TwentyDice extends AbstractDice {
  
  public static void main(String[] args){
    TwentyDice die = new TwentyDice();
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

  public TwentyDice(){
    this.sides = 20;
    this.populateDict();
    this.roll();
  } // end default constructor
  
  public void populateDict(){
    this.dict.put(0, "One");
    this.dict.put(1, "Two");
    this.dict.put(2, "Three");
    this.dict.put(3, "Four");
    this.dict.put(4, "Five");
    this.dict.put(5, "Six");
    this.dict.put(6, "Seven");
    this.dict.put(7, "Eight");
    this.dict.put(8, "Nine");
    this.dict.put(9, "Ten");
    this.dict.put(10, "Eleven");
    this.dict.put(11, "Twelve");
    this.dict.put(12, "Thirteen");
    this.dict.put(13, "Fourteen");
    this.dict.put(14, "Fifteen");
    this.dict.put(15, "Sixteen");
    this.dict.put(16, "Seventeen");
    this.dict.put(17, "Eighteen");
    this.dict.put(18, "Nineteen");
    this.dict.put(19, "Twenty");
  } // end populate_dict

} // end class def
