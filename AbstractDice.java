import java.util.*;

abstract class AbstractDice {
  
  protected int value;
  protected boolean keep_status = false;
  protected Hashtable<Integer, String> dict = new Hashtable<Integer, String>();
  protected int sides;
  
  public abstract void populateDict();

  public void roll(){
    Random rand = new Random();
    this.value = rand.nextInt(sides);
  } // end roll

  public String getValue(){
    return dict.get(this.value);
  } // end getValue

} // end class def
