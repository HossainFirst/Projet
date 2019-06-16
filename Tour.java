public class Tour extends Piece{

  public Tour(boolean c){
    super(c);
  }

  public Tour(Tour t){
    super(t);
  }

  // public String toString(){
  //   return "   T   ";
  // }


  public String toString(){
   String s = "";

   if (couleur == true) {
     s = "|   ♜   |";
   }

   else
     s = "|   ♖   |";

   return s;
 }


  public int typeMouvement(int xPiece, int yPiece, int xDestination, int yDestination){
    if (xDestination == xPiece){
      if (yDestination - yPiece < -1)
        //mouvement en ligne haut + check chemin
        return 9;
      else if (yDestination -yPiece > 1)
        //mouvement en ligne bas + check chemin
        return 10;
      else
        //mouvement qui ne requiert pas de check les pieces sur le chemin (mouvement d'un seule case)
        return 1;
    }

    else if (yDestination == yPiece){
      if (xDestination - xPiece < -1)
        //mouvement en ligne gauche + check chemin
        return 11;
      else if (xDestination - xPiece > 1)
        //mouvement en ligne droite + check chemin
        return 12;
      else
        //mouvement qui ne requiert pas de check les pieces sur le chemin (mouvement d'un seule case)
        return 1;
    }

    return 0;
  }
}
