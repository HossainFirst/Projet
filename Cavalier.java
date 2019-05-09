public class Cavalier extends Piece
{
  public Cavalier(String Couleur)
  {
    super(Couleur);
  }

  public int mouvement( int xDepart, int yDepart,
                             int xDestination, int yDestination)
  {
    if((xDestination == xDepart - 2 && (yDestination == yDepart - 1 || yDestination == yDepart + 1) )
    ||  xDestination == xDepart - 1 && (yDestination == yDepart - 2 || yDestination == yDepart + 2) 
    ||  xDestination == xDepart + 1 && (yDestination == yDepart - 2 || yDestination == yDepart + 2) 
    ||  xDestination == xDepart + 2 && (yDestination == yDepart - 1 || yDestination == yDepart + 1)){
      return 1;
    }
    else
      return 0;
  }

  public String toString()
  {
  	String s = "";
    s += "|Cavalier" + Couleur + "|";
    return s;
  }
  
}
