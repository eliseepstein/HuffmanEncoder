public class HuffmanNode implements Comparable<HuffmanNode>{
  
  //the character of the HuffmanNode
  private Character character; 
  
  //the frequency of the character of the HuffmanNode
  private int frequency;
  
  //the left node of the HuffmanNode
  private HuffmanNode left;
  
  //the right node of the HuffmanNode
  private HuffmanNode right;
  
  //the encoding of the HuffmanNode
  private String encoding;
  
  public HuffmanNode(Character character, int frequency, HuffmanNode left, HuffmanNode right, String encoding) {
    this.character= character;
    this.frequency = frequency;
    this.left = left;
    this.right=right;
    this.encoding = encoding;
  }
  
  //gets the character of the HuffmanNode
  public Character getCharacter() {
    return character;
  }
  
  //sets the character of the HuffmanNode
  public void setCharacter(Character character) {
    this.character = character;
  }
  
  //gets the frequency of the character of the HuffmanNode
  public int getFrequency() {
    return frequency;
  }
  
  //sets the frequency of the character of the HuffmanNode
  public void setFrequency(int frequency) {
    this.frequency = frequency;
  }
  
//gets the encoding of the HuffmanNode
  public String getEncoding() {
    return encoding;
  }
  
  //sets the encoding of the HuffmanNode
  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }
  
  //gets the left node of the HuffmanNode
  public HuffmanNode getLeft() {
    return left;
  }
  
  //sets the left node of the HuffmanNode
  public void setLeft(HuffmanNode left) {
    this.left = left;
  }
  
  //gets the right node of the HuffmanNode
  public HuffmanNode getRight() {
    return right;
  }
  
  //sets the right node of the HuffmanNode
  public void setRight(HuffmanNode right) {
    this.right = right;
  }
  
//compares HuffmanNodes and returns a negative number if the object is less than node, 0 if equal, and a positive number if the object is greater than the node
  @Override
  public int compareTo(HuffmanNode node) {
    return ((Integer)getFrequency()).compareTo(node.getFrequency());
  }
  
  
}



