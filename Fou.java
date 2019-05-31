public class Fou extends Piece{

  public Fou(boolean c){
    super(c);
  }

  public Fou(Fou f){
    super(f);
  }


  public String toString()
  {
    String s = "";
    s += "|Fou" + Couleur + "|";
    return s;
  }


  public int typeMouvement(int xPiece, int yPiece, int xDestination, int yDestination){
    if (xDestination - xPiece == -(yDestination - yPiece)){
      if (xPiece < xDestination - 1)
        //mouvement en diagonale haut droite + check chemin
        return 6;
      else if (xPiece > xDestination + 1)
        //mouvement en diagonale bas gauche + check chemin
        return 7;
      else
        return 1;
    }

    else if (xDestination - xPiece == yDestination - yPiece){
      if (xPiece < xDestination - 1)
        //mouvement en diagonale bas droite + check chemin
        return 8;
      else if (xPiece > xDestination + 1)
        //mouvement en diagonale haut gauche + check chemin
        return 5;
      else
        return 1;
    }

    return 0;
  }
}
