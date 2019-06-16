import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;
public class Partie  implements Serializable {
  private Piece[] plateau;
  private boolean joueur;
  private ArrayList<String> historique;
  private int nbrMouvement;


  public Partie(){
    this.plateau = new Piece[64];
    this.initPlateau();
    this.joueur = true;
    this.historique = new ArrayList<String>();
    this.nbrMouvement = 0;
  }


  public Partie(Partie p){
    this.plateau = new Piece[64];
    Piece[] pPlateau = p.getPlateau();
    for (int i=0; i<64; i++){
      Piece pPiece = pPlateau[i];
      if (pPiece == null)
        this.plateau[i] = null;
      else if (pPiece.getClass() == Pion.class)
        this.plateau[i] = new Pion((Pion) pPiece);
      else if (pPiece.getClass() == Tour.class)
        this.plateau[i] = new Tour((Tour) pPiece);
      else if (pPiece.getClass() == Cavalier.class)
        this.plateau[i] = new Cavalier((Cavalier) pPiece);
      else if (pPiece.getClass() == Fou.class)
        this.plateau[i] = new Fou((Fou) pPiece);
      else if (pPiece.getClass() == Reine.class)
        this.plateau[i] = new Reine((Reine) pPiece);
      else
        this.plateau[i] = new Roi((Roi) pPiece);
    }
    this.joueur = p.getJoueur();
    this.historique = p.getHistorique();
    this.nbrMouvement = p.getNbrMouvement();
  }


  public Partie(Piece[] plateau){
    this.plateau = plateau;
    this.joueur = true;
    this.historique = new ArrayList<String>();
    this.nbrMouvement = 0;
  }


  private void initPlateau(){
    //1ere rangée noir
    this.plateau[0] = new Tour(false);
    this.plateau[1] = new Cavalier(false);
    this.plateau[2] = new Fou(false);
    this.plateau[3] = new Reine(false);
    this.plateau[4] = new Roi(false);
    this.plateau[5] = new Fou(false);
    this.plateau[6] = new Cavalier(false);
    this.plateau[7] = new Tour(false);

    //2e rangée noir
    for(int i=8; i<16; i++)
      this.plateau[i] = new Pion(false);

    //2e rangée blanc
    for(int i=48; i<56; i++)
      this.plateau[i] = new Pion(true);

    //1ere rangée blanc
    this.plateau[56] = new Tour(true);
    this.plateau[57] = new Cavalier(true);
    this.plateau[58] = new Fou(true);
    this.plateau[59] = new Reine(true);
    this.plateau[60] = new Roi(true);
    this.plateau[61] = new Fou(true);
    this.plateau[62] = new Cavalier(true);
    this.plateau[63] = new Tour(true);
  }


  public Piece[] getPlateau(){
    return this.plateau;
  }


  public boolean getJoueur(){
    return this.joueur;
  }


  public ArrayList<String> getHistorique(){
    return this.historique;
  }


  public void setJoueur(boolean j){
    this.joueur = j;
  }


  public void nextJoueur(){
    this.joueur = !this.joueur;
  }

  public int getNbrMouvement(){
    return this.nbrMouvement;
  }


  public boolean deplacementPossible(int xPiece, int yPiece, int xDestination, int yDestination, boolean joueur){
    //verifie qu'il y a bel et bien un déplacement
    if (xPiece == xDestination && yPiece == yDestination){
      //System.out.println("aucun deplacement effectue");
      return false;
    }

    //verifie que la case contenant la piece existe
    if (!(xPiece <= 7 && xPiece >= 0 ) || !(yPiece <= 7 && yPiece >= 0 )){
      //System.out.println("la case indiquée n'existe pas ");
      return false;
    }

    //verifie que la case de destination existe
    if (!(xDestination <= 7 && xDestination >= 0 ) || !(yDestination <= 7 && yDestination >= 0 )){
      //System.out.println("la case indiquée n'existe pas ");
      return false;
    }

    Piece pieceDepart = this.plateau[xPiece + 8*yPiece];
    Piece pieceArrivee = this.plateau[xDestination + 8*yDestination];

    //verifie qu'il y a bien une piece alliée sur la case de depart
    if (pieceDepart == null || pieceDepart.getCouleur() != joueur){
      //System.out.println("il n'y a aucune piece sur cette case");
      return false;
    }

    //verifie qu'il n'y a pas de piece allié sur la case de destination
    if (pieceArrivee != null && pieceArrivee.getCouleur() == joueur){
      //System.out.println("la case d'arivée contient un pion allié");
      return false;
    }

    /*
    Regarde quel type de mouvement est effectué:
    0 -> mouvement impossible
    1 -> mouvement qui ne requiert pas de check les pieces sur le chemin
    2 -> mouvement en avant qui requiert qu'il n'y ai pas d'ennemi sur la case de destination
    3 -> mouvement qui requiert que le pion n'ai pas deja effectué un mouvement et qu'il n'y ai pas d'enemi sur le chemin
    4 -> mouvement qui requiert qu'il y ai une piece ennemi sur la case d'arrivee
    5 -> mouvement en diagonale haut gauche + check chemin
    6 -> mouvement en diagonale haut droite + check chemin
    7 -> mouvement en diagonale bas gauche + check chemin
    8 -> mouvement en diagonale bas droite + check chemin
    9 -> mouvement en ligne haut + check chemin
    10 -> mouvement en ligne bas + check chemin
    11 -> mouvement en ligne gauche + check chemin
    12 -> mouvement en ligne droite + check chemin
    13 -> grand roque
    14 -> petit roque
    */

    int typeMouvement = pieceDepart.typeMouvement(xPiece, yPiece, xDestination, yDestination);
    int avant = pieceDepart.getAvant();


    //mouvement impossible si typeMouvement = 0
    if (typeMouvement == 0){
      //System.out.println("le mouvement indiquée est impossible");
      return false;
    }

    else if(typeMouvement == 1){
      return true;
    }

    //On applique differents tests en fonction des requierement specifiques du move (ex: qu'il n'ai pas de piece entre la case de depart et la case d'arrivee)
    else if (typeMouvement == 2){
      if (pieceArrivee != null && pieceArrivee.getCouleur() != joueur)
        return false;
    }

    else if (typeMouvement == 3){
      if (pieceDepart.getAEffectueUnMouvement() || pieceArrivee != null || this.plateau[xPiece + 8*(yPiece+avant)] != null)
        return false;
    }

    else if (typeMouvement == 4){
      if (pieceArrivee == null || pieceArrivee.getCouleur() == joueur)
        return false;
    }

    else if (typeMouvement == 5 || typeMouvement == 6 || typeMouvement == 7 || typeMouvement == 8){
      int xDirection;
      int yDirection;
      if (typeMouvement == 5){
        xDirection = -1;
        yDirection = -1;
      }
      else if (typeMouvement == 6){
        xDirection = 1;
        yDirection = -1;
      }
      else if (typeMouvement == 7){

        xDirection = -1;
        yDirection = 1;
      }
      else{
        xDirection = 1;
        yDirection = 1;
      }

      for(int i=1; i<Math.abs(xDestination - xPiece); i++){
        if (this.plateau[(xPiece+xDirection*i) + 8*(yPiece+yDirection*i)] != null){
          return false;
        }
      }
    }

    else if(typeMouvement == 9 || typeMouvement == 10 || typeMouvement == 11 || typeMouvement == 12){
      if (typeMouvement == 9){
        for(int i = 1; i < yPiece-yDestination; i++){
          if (this.plateau[xPiece + 8*(yPiece-i)] != null)
            return false;
        }
      }
      else if (typeMouvement == 10){
        for(int i = 1; i < yDestination-yPiece; i++){
          if (this.plateau[xPiece + 8*(yPiece+i)] != null)
            return false;
        }
      }
      else if (typeMouvement == 11){
        for(int i = 1; i < xPiece-xDestination; i++){
          if (this.plateau[xPiece-i + 8*yPiece] != null)
            return false;
        }
      }
      else{
        for(int i = 1; i < xDestination-xPiece; i++){
          if (this.plateau[xPiece+i + 8*yPiece] != null)
            return false;
        }
      }
    }

    else if (typeMouvement == 13){
      if (!grandRoquePossible())
        return false;
    }

    else{
      if (!petitRoquePossible())
        return false;
    }

    return true;
  }


  public boolean grandRoquePossible(){
    int xRoi = 4;
    int yRoi;
    int xTour = 0;
    int yTour;


    if (this.joueur){
      yRoi = 7;
      yTour = 7;
    }
    else{
      yRoi = 0;
      yTour = 7;
    }

    Piece caseRoi = this.plateau[xRoi + yRoi*8];
    Piece caseTour = this.plateau[xTour + yTour*8];

    if (caseRoi == null || caseRoi.getClass() != Roi.class || caseRoi.getCouleur() != this.getJoueur() || caseRoi.getAEffectueUnMouvement())
      return false;

    else if (caseTour == null || caseTour.getClass() != Tour.class || caseTour.getCouleur() != this.getJoueur() || caseTour.getAEffectueUnMouvement())
      return false;

    else if (this.plateau[xRoi-1 + yRoi*8] != null || this.plateau[xRoi-2 + yRoi*8] != null || this.plateau[xRoi-3 + yRoi*8] != null)
      return false;

    else if (enEchec() || enEchecApresMouvemement(xRoi, yRoi, xRoi-1, yRoi) || enEchecApresMouvemement(xRoi, yRoi, xRoi-2, yRoi))
      return false;

    else
      return true;
  }



  public boolean petitRoquePossible() {
    int xRoi = 4;
    int yRoi;
    int xTour = 7;
    int yTour;

    if (this.joueur) {
      yRoi = 7;
      yTour = 7;
    }
    else{
      yRoi = 0;
      yTour = 0;
    }

    Piece caseRoi = this.plateau[xRoi + yRoi*8];
    Piece caseTour = this.plateau[xTour + yTour*8];

    if (caseRoi == null || caseRoi.getClass() != Roi.class || caseRoi.getCouleur() != this.getJoueur() || caseRoi.getAEffectueUnMouvement()){
      //System.out.println("1");
      return false;
    }

    else if (caseTour == null || caseTour.getClass() != Tour.class || caseTour.getCouleur() != this.getJoueur() || caseTour.getAEffectueUnMouvement()){
      //System.out.println("2");
      return false;
    }

    else if (this.plateau[xRoi+1 + yRoi*8] != null || this.plateau[xRoi+2 + yRoi*8] != null){
      //System.out.println("3");
      return false;
    }

    else if (enEchec() || enEchecApresMouvemement(xRoi, yRoi, xRoi+1, yRoi) || enEchecApresMouvemement(xRoi, yRoi, xRoi+2, yRoi)){
      //System.out.println("4");
      return false;
    }

    else
      return true;
  }



  public void grandRoque(){
    int y;
    if (this.joueur) {
      y = 7;
    }
    else{
      y = 0;
    }

    Piece roi = this.plateau[4+y*8];
    Piece tour = this.plateau[0+y*8];
    this.plateau[4+y*8] = null;
    this.plateau[0+y*8] = null;
    this.plateau[2+y*8] = roi;
    this.plateau[3+y*8] = tour;
  }



  public void petitRoque(){
    int y;
    if (this.joueur) {
      y = 7;
    }
    else{
      y = 0;
    }

    Piece roi = this.plateau[4+y*8];
    Piece tour = this.plateau[0+y*8];
    this.plateau[4+y*8] = null;
    this.plateau[7+y*8] = null;
    this.plateau[6+y*8] = roi;
    this.plateau[5+y*8] = tour;
  }



  public void deplacerPiece(int xPiece, int yPiece, int xDestination, int yDestination){

    Piece pieceDepart = this.plateau[xPiece + 8*yPiece];
    Piece pieceArrivee = this.plateau[xDestination + 8*yDestination];

    //on prend en compte le mouvement
    pieceDepart.effectueUnMouvement();

    //enregistrement du coup dans l'historique
    this.historique.add(pieceDepart+","+xPiece+","+yPiece+","+pieceArrivee+","+xDestination+","+yDestination+","+this.joueur);

    //le mouvement est effectué
    if (pieceDepart.typeMouvement(xPiece, yPiece, xDestination, yDestination) == 13)
      this.grandRoque();

    else if (pieceDepart.typeMouvement(xPiece, yPiece, xDestination, yDestination) == 14)
      this.petitRoque();

    else{
      this.plateau[xPiece + 8*yPiece] = null;
      this.plateau[xDestination + 8*yDestination] = pieceDepart;
    }
  }



  public int[] getPosRoi(){
    int xRoi = -1;
    int yRoi = -1;
    for(int i = 0; i<64 && xRoi == -1; i++){
      if(this.plateau[i] != null && this.plateau[i].getClass() == Roi.class && this.plateau[i].getCouleur() == this.joueur){
        xRoi = i%8;
        yRoi = i/8;
      }
    }
    return new int[] {xRoi, yRoi};
  }



  public boolean enEchec(){
    int[] posRoi = this.getPosRoi();
    int xRoi = posRoi[0];
    int yRoi = posRoi[1];
    boolean ennemi = !this.joueur;

    for(int i = 0; i<64; i++){
      if (this.plateau[i] != null && this.plateau[i].getCouleur() == ennemi && this.deplacementPossible(i%8, i/8, xRoi, yRoi, ennemi))
        return true;
    }
    return false;
  }



  public boolean enEchecApresMouvemement(int xPiece, int yPiece, int xDestination, int yDestination){
    //on cree une copie de la partie
    Partie copiePartie = new Partie(this);
    //on simule le mouvement
    Piece pieceDepart = copiePartie.plateau[xPiece + 8*yPiece];
    copiePartie.plateau[xPiece + 8*yPiece] = null;
    copiePartie.plateau[xDestination + 8*yDestination] = pieceDepart;
    //on regarde si le move met le joueur en echec
    return copiePartie.enEchec();
  }



  public boolean echecEtMat(){
    if (enEchec()){
      for (int i = 0; i < 64; i++) {
        if (this.plateau[i] != null && this.plateau[i].getCouleur() == this.joueur) {
          for (int j = 0; j < 64; j++) {
            if (this.deplacementPossible(i % 8, i / 8, j % 8, j / 8, this.joueur)
                && !this.enEchecApresMouvemement(i % 8, i / 8, j % 8, j / 8))
              return false;
          }
        }
      }
      return true;
    }
    return false;
  }



  public boolean pat(){
    for (int i = 0; i < 64; i++) {
      if (this.plateau[i] != null && this.plateau[i].getCouleur() == this.joueur) {
        for (int j = 0; j < 64; j++) {
          if (this.deplacementPossible(i % 8, i / 8, j % 8, j / 8, this.joueur)
              && !this.enEchecApresMouvemement(i % 8, i / 8, j % 8, j / 8))
            return false;
        }
      }
    }
    return true;
  }



  public int promotionPossible(){
    if (this.joueur){
      for (int i=0; i<8; i++){
        if (this.plateau[i] != null && this.plateau[i].getCouleur() && this.plateau[i].getClass() == Pion.class)
          return i;
      }
    }

    else{
      for (int i=0; i<8; i++){
        if (this.plateau[i] != null && !this.plateau[i].getCouleur() && this.plateau[i].getClass() == Pion.class)
          return i;
      }
    }

    return -1;
  }



  public void promotion(Scanner scan, String os, int nJoueur, int position){
    this.clearTerminal(os);
    this.afficherPlateau();
    System.out.println("Au tour du joueur " + nJoueur + "\n\n");
    System.out.println("En quelle pièce voulez vous promouvoir votre pion : (Entrez \"reine\", \"fou\", \"tour\" ou \"cavalier\")");
    String piece = scan.nextLine().toLowerCase();

    while (piece != "reine" || piece != "fou" || piece != "tour" || piece != "cavalier"){
      this.clearTerminal(os);
      this.afficherPlateau();
      System.out.println("Au tour du joueur " + nJoueur + "\n\n");
      System.out.println("Saisie incorecte, veuillez reesayer : (Entrez \"reine\", \"fou\", \"tour\" ou \"cavalier\")");
      piece = scan.nextLine().toLowerCase();
    }

    if (piece.equals("reine"))
      this.plateau[position] = new Reine(this.joueur);

    else if (piece.equals("fou"))
      this.plateau[position] = new Fou(this.joueur);

    else if (piece.equals("tour"))
      this.plateau[position] = new Tour(this.joueur);

    else
      this.plateau[position] = new Cavalier(this.joueur);

    this.plateau[position].effectueUnMouvement();
  }


  public void afficherPlateau(){
    System.out.println("|   a   ||   b   ||   c   ||   d   ||   e   ||   f   ||   g   ||   h   |");
    System.out.println("------------------------------------------------------------------------");
    for(int i=0; i<8; i++){
      String row = "";
      for(int j=0; j<8; j++){
        if (this.plateau[8*i+j] == null)
          row += "|       |";
        else
          row += this.plateau[8*i+j];
      }
        System.out.println(row);
        System.out.println("------------------------------------------------------------------------");

    }
  }


  public int actionValide(String action){
    if (action.equals("help") || action.equals("h"))
      return 1;

    else if (action.equals("menu") || action.equals("m"))
      return 2;

    else if (action.equals("abandonner") || action.equals("a"))
      return 3;

    else if (action.length() == 5
    && action.charAt(0) >= 'a' && action.charAt(0) <= 'h'
    && action.charAt(1) >= '1' && action.charAt(1) <= '8'
    && action.charAt(2) == ' '
    && action.charAt(3) >= 'a' && action.charAt(3) <= 'h'
    && action.charAt(4) >= '1' && action.charAt(4) <= '8'
    && this.deplacementPossible(action.charAt(0) - 97, 7-(action.charAt(1)-49), action.charAt(3)-97, 7-(action.charAt(4)-49), this.joueur)
    && !this.enEchecApresMouvemement(action.charAt(0)-97, 7-(action.charAt(1)-49), action.charAt(3)-97, 7-(action.charAt(4)-49)))
        return 4;

    else if ((action.equals("grand roque") || action.equals("gr")) && grandRoquePossible())
      return 5;

    else if ((action.equals("petit roque") || action.equals("pr")) && petitRoquePossible())
      return 6;

    else
      return 0;
  }



  public void clearTerminal(String os){
    if (os.contains("win")){
      try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    else{
      System.out.print("\033[H\033[2J");
      System.out.flush();
    }
  }



  public void jouer(){
    String os = System.getProperty("os.name").toLowerCase();
    Scanner scan = new Scanner(System.in);

    while (!echecEtMat() && !pat()){

      this.clearTerminal(os);
      this.afficherPlateau();
      //this.afficherHistorique();


      int nJoueur;
      if (this.joueur)
        nJoueur = 1;
      else
        nJoueur = 2;

      System.out.println("Au tour du joueur "+nJoueur+".\n\n");
      System.out.println("Effectuez une action:");

      String action = scan.nextLine().toLowerCase();
      int typeAction = actionValide(action);

      while (typeAction == 0){
        this.clearTerminal(os);
        System.out.println("typeAction: " + typeAction + ", action: " + action + ", actionValide: " + actionValide(action) +".\n\n");
        this.afficherPlateau();
        System.out.println("Au tour du joueur " + nJoueur+".\n\n");
        System.out.println("Action ou mouvement invalide, reesayez:");
        action = scan.nextLine().toLowerCase();
        typeAction = actionValide(action);
      }

      if (typeAction == 1){
        this.clearTerminal(os);
        System.out.println("work in progress");
      }

      if (typeAction == 2){
        return;
      }

      if (typeAction == 3){
        //THINGS TO ADD HERE
        return;
      }

      if (typeAction == 4 || typeAction == 5 || typeAction == 6){
        if (typeAction == 4){
          int xPiece = action.charAt(0)-97;
          int yPiece = 7-(action.charAt(1)-49);
          int xDestination = action.charAt(3)-97;
          int yDestination = 7-(action.charAt(4)-49);
          this.deplacerPiece(xPiece, yPiece, xDestination, yDestination);
        }

        else if (typeAction == 5){
          this.grandRoque();
        }

        else{
          this.petitRoque();
        }

        int promotionPossible = this.promotionPossible();
        if (promotionPossible != -1){
          this.promotion(scan, os, nJoueur, promotionPossible);
        }

        this.nbrMouvement ++;
        this.nextJoueur();
      }


    }

    this.clearTerminal(os);
    this.afficherPlateau();

    if (pat()){
      System.out.println("Pat ! Il y a égalité.\n\n");
    }

    else{
      int nJoueur;
      if (this.joueur)
        nJoueur = 2;
      else
        nJoueur = 1;
      System.out.println("Echec et Mat ! Le joueur "+nJoueur+" a gagne.\n\n");
    }

    System.out.println("Entrez \"menu\" pour revenir au menu");
    String action = scan.nextLine().toLowerCase();

    while (!(action.equals("menu") || action.equals("m"))){
      this.clearTerminal(os);
      this.afficherPlateau();
      if (pat()) {
        System.out.println("Pat ! Il y a égalité.\n\n");
      }
      else {
        int nJoueur;
        if (this.joueur)
          nJoueur = 2;
        else
          nJoueur = 1;
        System.out.println("Echec et Mat ! Le joueur " + nJoueur + " a gagne.\n\n");
      }
      System.out.println("Entrez \"menu\" pour revenir au menu");
      action = scan.nextLine().toLowerCase();
    }

  }


}
