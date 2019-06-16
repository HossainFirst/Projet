import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Menu {

  private static int JOUER = 1;
  private static int REPRENDRE = 2;
  private static int SAUVEGARDER = 3;
  private static int CHARGER = 4;
  private static int QUITTER = 5;

  private Partie partie;
  private Scanner scan = new Scanner(System.in);





  public Menu() {
  }





  public Menu(Partie partie) {
    this.partie = partie;
  }





  public void run() {
    while (true) {
      boolean partieFinie = false;
      this.clearTerminal();
      this.afficher();
      System.out.println("\nVeuillez effectuer une action:");
      String action = "";
      if (this.scan.hasNextLine())
        action = this.scan.nextLine().toLowerCase();
      int typeAction = typeAction(action);
      while (typeAction == 0) {
        this.clearTerminal();
        this.afficher();
        System.out.println("\nAction invalide ou impossible, veuilllez réessayer:");
        if (this.scan.hasNextLine())
          action =this.scan.nextLine().toLowerCase();
        typeAction = typeAction(action);
      }

      if (typeAction == JOUER) {
        this.partie = new Partie();
         this.partie.jouer();
      }

      else if (typeAction == REPRENDRE) {
         this.partie.jouer();
      }

      else if (typeAction == SAUVEGARDER) {
        this.sauvegarder(this.partie);
      }

      else if (typeAction == CHARGER) {
        this.charger();
         this.partie.jouer();
      }

      else if (typeAction == QUITTER) {
        this.clearTerminal();
        //if (this.partie != null)
          //this.partie.closeAffichage();
        this.scan.close();
        return;
      }

      if (partieFinie) {
        //this.partie.closeScanner();
        //this.partie.closeAffichage();
        this.partie = null;
      }
    }
  }





  public void clearTerminal() {
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
      try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    else {
      System.out.print("\033[H\033[2J");
      System.out.flush();
    }
  }





  public void afficher() {
    System.out.println("Menu :\n");
    System.out.println("\'jouer\' pour commencer une nouvelle partie,");
    System.out.println("\'reprendre\' pour reprendre un partie en cours,");
    System.out.println("\'sauvegarder\' pour sauvegarder une partie en cours,");
    System.out.println("\'charger\' pour charger une partie sauvegardée,");
    //System.out.println("\'quitter\' pour quitter le jeu.");
  }





  public int typeAction(String action) {
    if (action.equals("jouer") || action.equals("j"))
      return JOUER;

    else if (action.equals("reprendre") || action.equals("r")) {
      if (this.partie != null)
        return REPRENDRE;
    }

    else if (action.equals("sauvegarder") || action.equals("s")) {
      if (this.partie != null)
        return SAUVEGARDER;
    }

    else if (action.equals("charger") || action.equals("c")) {
      File temp = new File("save.txt");
      if (temp.exists() && !temp.isDirectory())
          return CHARGER;
    }

    else if (action.equals("quitter") || action.equals("q"))
      return QUITTER;

    return 0;
  }





  public void sauvegarder(Object partie) {

    try {
      File save = new File("save.txt");
      if (!save.exists())
        save.createNewFile();

      FileOutputStream fileOut = new FileOutputStream("save.txt");
      ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
      objectOut.writeObject(partie);
      fileOut.close();
      objectOut.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }





  public void charger() {

    try {

      FileInputStream fileIn = new FileInputStream("save.txt");
      ObjectInputStream objectIn = new ObjectInputStream(fileIn);
      this.partie = (Partie)objectIn.readObject();
      fileIn.close();
      objectIn.close();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


}
