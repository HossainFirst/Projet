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

 public abstract int mouvement( int xDepart, int yDepart,
                            int xDestination,  int yDestination);


}
