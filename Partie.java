public class Partie{
  private Piece[] plateau;
  private String joueur;


  public Partie()
  {
    this.plateau = new Piece[64];
    this.initPlateau(); //initialise le tableau de piece l(8)
    this.joueur = "Blanc";
  }

  //Mutateurs et Accesseurs :
  public Piece[] getPlateau()
  {
    return this.plateau;
  }

  public String getJoueur()
  {
    return this.joueur;
  }

  public void setJoueur(String j)
  {
    this.joueur = j ;
  }

  public void joueursuiv()
  {
    if(this.joueur.equals("Blanc"))
    {
      this.joueur = "Noir";
    }

    else
    {
      this.joueur = "Blanc";
    }
  } 

  //Initialisateur de L'echiquier :
  public void initPlateau()
  {
    //Les pieces du joueur blanc
    this.plateau[0] = new Tour("Blanc");
    this.plateau[1] = new Cavalier("Blanc");
    this.plateau[2] = new Fou("Blanc");
    this.plateau[3] = new Roi("Blanc");
    this.plateau[4] = new Reine("Blanc");
    this.plateau[5] = new Fou("Blanc");
    this.plateau[6] = new Cavalier("Blanc");
    this.plateau[7] = new Tour("Blanc");

    /*this.plateau[42] = new Cavalier("Blanc"); //POUR UN TESSSSSSSSST ***************************************************************************************************************
    this.plateau[36] = new Pion("Noir"); // LA MMM                **************************************************************************************************************
    this.plateau[35] = new Pion("Noir"); //LA MMM                **************************************************************************************************************
    this.plateau[28] = new Pion("Noir"); //LA MMM                **************************************************************************************************************
    this.plateau[44] = new Pion("Noir"); //LA MMM                **************************************************************************************************************
    this.plateau[37] = new Pion("Noir"); //LA MMM                **************************************************************************************************************
    */

    //Rangée des pions Blanc
    for (int i = 8 ; i <= 15  ; i++ )
      {
        this.plateau[i] = new Pion("Blanc");
      }

      
    
    //Rangée de pions Noir
    for (int i = 48 ; i < 56  ; i++ )
      {
        this.plateau[i] = new Pion("Noir");
      }

    //Les pieces du joueur Noir
    this.plateau[56] = new Tour("Noir");
    this.plateau[57] = new Cavalier("Noir");
    this.plateau[58] = new Fou("Noir");
    this.plateau[59] = new Roi("Noir");
    this.plateau[60] = new Reine("Noir");
    this.plateau[61] = new Fou("Noir");
    this.plateau[62] = new Cavalier("Noir");
    this.plateau[63] = new Tour("Noir");
  }

  public boolean mvtpossible(int xDepart, int yDepart, int xDestination, int yDestination, String joueur)
  {
    

    if( xDepart>7 || xDepart<0 && yDepart > 7 || yDepart < 0) 
      {
        System.out.println("error : La case de depart n'existe pas.");

        return false;
      }

    if(xDepart == xDestination && yDepart == yDestination)
      {
        System.out.println("error : La case de destination selection est celle de depart.");

        return false;
      }  

    
    if( xDestination>7 || xDestination<0 && yDestination > 7 || yDestination < 0) 
      {
        System.out.println("error : La case de d'arriver n'existe pas.");

        return false;
      }

    //methodes teste sur les pieces Depart/Arrivé
    Piece PieceDepart = this.plateau[xDepart + 8 * yDepart];
    Piece PieceArrive = this.plateau[xDestination + 8 * yDestination];
    
    if(PieceDepart == null || !(PieceDepart.Couleur.equals(joueur)) )
    {
      System.out.println("error : La case de depart est vide ou pion sur celle-ci ne vous appartient pas. Le contenu de la case : " + PieceDepart);

      return false;
    }

    if(PieceArrive != null && PieceArrive.getCouleur() == joueur)
    {
      System.out.println("error : La case de d'arrivee est vide ou elle contient un pion allie. Le contenu de la case : " + PieceArrive);

      return false;
    }
    
    /*
    Les types de mouvements:
    0 -> mouvement impossible
    1 -> mouvement qui ne requiert pas de check les pieces sur le chemin
    2 -> mouvement en avant qui requiert qu'il n'y ai pas de piece sur la case de destination
    3 -> mouvement qui requiert que le pion n'ai pas deja effectué un mouvement et qu'il n'y ai pas de pion sur le chemin
    4 -> mouvement qui requiert qu'il y ai une piece ennemi sur la case d'arrivee
    5 -> mouvement en diagonale haut gauche + check chemin
    6 -> mouvement en diagonale haut droite + check chemin
    7 -> mouvement en diagonale bas gauche + check chemin
    8 -> mouvement en diagonale bas droite + check chemin
    9 -> mouvement en ligne haut + check chemin
    10 -> mouvement en ligne bas + check chemin
    11 -> mouvement en ligne gauche + check chemin
    12 -> mouvement en ligne droite + check chemin
    */

    System.out.println("piece depart : " + PieceDepart);
    int typemouv = PieceDepart.mouvement(xDepart, yDepart, xDestination, yDestination);

    System.out.println("type de mouvement : " + typemouv);
    System.out.println("etat de piece arrivee : " + PieceArrive);
    
    if (typemouv == 0 ){
      System.out.println("error : Votre piece ne peut pas faire ce mouvement.");
      return false;
    }

    else if (typemouv == 1  && PieceArrive != null && PieceArrive.Couleur == joueur){ 
        System.out.println("error : Une piece alliee se trouve la case d'arrivee. Le contenu de la case : " + PieceArrive);
        return false;
    }
    
    else if (typemouv == 2 && PieceArrive != null){
      System.out.println("error : La case d arrivee est occupee par la piece " + PieceArrive);
      return false;       
    }

    else if (typemouv == 3 &&  (PieceArrive != null || this.plateau[xDestination + 8 * ( yDestination - 1 )]  !=null ) ){
      System.out.println("error : Votre pion a deja effectue un mouvement ou unne piece est sur le chemin ou sur la case d'arrivee.");
      return false;
    }


    else if (typemouv == 4 && (PieceArrive == null || PieceArrive.Couleur == this.joueur)){
      System.out.println("error : Il y'a une piece alliee sur la case d'arrivee ou sois elle est vide. Le contenu de la case arrivee : " + PieceArrive);
      return false;
    }

    else if (typemouv == 9){ //mouvement en ligne haut + check chemin
      for (int i = yDepart - 1  ; i > yDestination ; i--){

        if (this.plateau[xDepart + i * 8] != null){
          System.out.println("error : La piece " + this.plateau[xDepart + i * 8] + " est sur le chemin." );
          return false;
        }  
      }
    }

    else if (typemouv == 10 ) {//mouvement en ligne bas + check chemin
      for (int i = yDepart + 1 ; i < yDestination  ; i++){
 
        if (this.plateau[xDepart + i * 8] != null){
          System.out.println("error : La piece " + this.plateau[xDepart + i * 8] + " est sur le chemin." );
          return false;
        }  
      }
      
    }

    else if (typemouv == 11){//mouvement en ligne gauche + check chemin
      for (int i = xDepart - 1 ; i > xDestination   ; i--){
        if (this.plateau[i + yDestination * 8] != null){
          System.out.println("error : La piece " + this.plateau[i + yDestination * 8] + " est sur le chemin." );
          return false;
        }
      }
    }

    else if (typemouv == 12){//mouvement en ligne droite + check chemin
      for (int i = xDepart + 1 ; i < xDestination   ; i++){
        if (this.plateau[i + yDestination * 8] != null){
          System.out.println("error : La piece " + this.plateau[i + yDestination * 8] + " est sur le chemin." );
          return false;
        }
      }

    }
    
    return true;

  }

  public boolean deplacementPiece(int xDepart, int yDepart,
                                   int xDestination, int yDestination)
  {
    Piece PieceDepart = this.plateau[xDepart + 8 * yDepart];
    Piece PieceArrive = this.plateau[xDestination + 8 * yDestination];
     
    if(mvtpossible(xDepart, yDepart, xDestination, yDestination, this.joueur)){
      this.plateau[xDestination + 8 * yDestination] = this.plateau[xDepart + 8 * yDepart];
      this.plateau[xDepart + 8 * yDepart] = null;
      //this.joueursuiv(); //changement de joueur *mis en commentaire pour facilité les tests*
      return true;
    }

    return false;
  }







  public void afficherplateau(){
    
    for (int i = 0 ; i < 8 ; i++ ){ // les lignes du plateau
    
        String s = ""; //creer la ligne du plateau
        
        for ( int j = 0 ; j < 8 ; j++ ){ //les colonnes du plateau        
          if(this.plateau[i * 8 + j] == null){//si la case du plateau ne contient pas de piece
            
            s = s + "|   0   |";
          }

          else
            s = s + this.plateau[i * 8 + j];
          
        }//fin boucle for avec les colonnes

       System.out.println(s);
    }//fin boucle for pour les lignes

  }//fin de la methode afficherplateau()

}//fin de la class
