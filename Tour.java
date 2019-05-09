public class Tour extends Piece
{

	public Tour(String couleur){
		super(couleur);
	}

	public int mouvement( int xDepart, int yDepart, int xDestination, int yDestination){

		if (xDepart == xDestination){
			if (yDestination - yDepart < -1 )//mouvement vertical vers le haut sur plusieurs cases 	
				return 9; 		
  		
			else if (yDestination - yDepart > 1) //mouvement vertical vers le bas sur plusieurs cases
  				return 10;
  			 		
			return 1;

		}

		else if (yDepart == yDestination){
			if ( xDestination - xDepart < -1){//mouvement horizontale vers la gauche sur plusieurs cases 	
				return 11;
			}

			else if (xDestination - xDepart > 1){//mouvement horizontale vers la droite 	sur plusieurs cases
				return 12;
			}

  			return 1;
		}

		return 0;

 	}//fin de la methode mouvement

  public String toString()
  {
    String s = "";
    s += "|Tour" + Couleur + "|";
    return s;
  }

}//find dela class
