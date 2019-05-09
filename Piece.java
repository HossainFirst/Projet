public abstract class Piece
{
  public String Couleur ;


  public Piece(String couleur){
    this.Couleur = couleur;
  }

  public String getCouleur(){
    return this.Couleur;
  }

  public void setCouleur(String couleur){
    this.Couleur = couleur;
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

 public abstract int mouvement( int xDepart, int yDepart, int xDestination,  int yDestination);


}
