public class Pion extends Piece
{

  public Pion(String Couleur)
  {
    super(Couleur);
  }

  public int mouvement( int xDepart, int yDepart, int xDestination,  int yDestination){
  	int sens = 0 ;

  	if(this.Couleur == "Blanc")
  		sens = 1;
 	else 
 		sens = -1 ; 
 	

 	if ( xDepart == xDestination && yDestination - yDepart == sens ) 
 		
 			return 2; //mouvement en avant qui requiert qu'il n'y ai pas de pion sur la case de destination
 	

 	else if ( xDepart == xDestination && yDestination - yDepart == 2*sens)  
 	
 		return 3; //mouvement qui requiert que le pion n'ai pas deja effectué un mouvement et qu'il n'y ai pas de pion sur le chemin 
 	

 	else if ( ( xDestination == xDepart + sens && yDestination == yDepart + sens ) //Premiere diagonale
 		|| ( xDestination == xDepart - sens && yDestination == yDepart + sens ) )  //Seconde diagonale
 	
 		return 4; //mouvement qui requiert qu'il y ai une piece ennemi sur la case d'arrivé
 	

 	return 0;
 	
  }//fin de la methode mouvement

  public String toString(){
  	String s = "";
    s += "|Pion" + Couleur + "|";
    return s;
  }




}//fin de la class pion
