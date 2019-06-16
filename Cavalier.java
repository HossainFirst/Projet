public class Cavalier extends Piece{

  public Cavalier(boolean c){
    super(c);
  }

  public Cavalier(Cavalier c){
    super(c);
  }

   //
   // public String toString(){
   //   return "   C   ";
   // }

   public String toString(){
      String s = "";

      if (couleur == true) {
        s = "|   ♞   |";
      }

      else
        s = "|   ♘   |";

      return s;
  }


  public int typeMouvement(int xPiece, int yPiece, int xDestination, int yDestination){
    if ((xDestination == xPiece-1 && (yDestination == yPiece-2 || yDestination == yPiece+2))
     || (xDestination == xPiece+1 && (yDestination == yPiece-2 || yDestination == yPiece+2))
     || (xDestination == xPiece-2 && (yDestination == yPiece-1 || yDestination == yPiece+1))
     || (xDestination == xPiece+2 && (yDestination == yPiece-1 || yDestination == yPiece+1)))
      // mouvement qui ne requiert pas de check les pieces sur le chemin
      return 1;
    return 0;
  }
}
