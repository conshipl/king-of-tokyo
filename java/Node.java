public class Node {

  protected String key;
  protected Player value;
  protected Node left = null;
  protected Node right = null;
  protected Node parent = null;

  public static void main(String[] args){
    Node root = new Node();
  } // end main
  
  public Node(){
    this.key = null;
    this.value = null;
  } // end default constructor

  public Node(String key, Player player){
    this.key = key;
    this.value = player;
  } // end parameterized constructor

} // end class def
