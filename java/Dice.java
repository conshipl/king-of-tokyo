import java.util.*;

public class Dice {
  
  protected int value;
  protected boolean keep_status = false;
  protected Hashtable<Integer, String> dict = new Hashtable<Integer, String>();

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

  public void roll(){
    Random rand = new Random();
    this.value = rand.nextInt(6);
  } // end roll

  public String getValue(){
    return dict.get(this.value);
  } // end getValue

} // end class def
