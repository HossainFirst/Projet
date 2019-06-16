import java.io.Serializable;
public abstract class Piece  implements Serializable{
  public boolean couleur;
  public boolean aEffectueUnMouvement;

  public Piece(boolean c){
    this.couleur = c;
    this.aEffectueUnMouvement = false;
  }

  public Piece(Piece p){
    this.couleur = p.getCouleur();
    this.aEffectueUnMouvement = p.aEffectueUnMouvement;
  }

  public boolean getAEffectueUnMouvement() {
    return this.aEffectueUnMouvement;
  }

  public void effectueUnMouvement() {
    this.aEffectueUnMouvement = true;
  }

  public boolean getCouleur(){
    return this.couleur;
  }

  public void setCouleur(boolean c){
    this.couleur = c;
  }

  public int getAvant(){
    if (this.couleur)
      return -1;
    else
      return 1;
  }

  public abstract int typeMouvement(int xPiece, int yPiece, int xDestination, int yDestination);

}
