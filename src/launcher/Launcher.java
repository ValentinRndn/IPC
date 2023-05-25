
package launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import game.*;

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean continuer = true;
        while(continuer){
            System.out.print("Entrer une ou plusieurs expression séparées par des virgules (,) : ");
            String expressionStr = scanner.nextLine();
            String[] expressions = expressionStr.split(",");

            System.out.print("Entrer une condition (facultatif): "); //pas obligé de mettre une condition
            String conditionStr = scanner.nextLine();
            Condition condition = null;
            if (!conditionStr.isEmpty()) {
                condition = new Condition(conditionStr);
            }

            //System.out.print("Entrer une liste d'entiers : ");
            System.out.print("Entrer une ou plusieurs listes d'entiers séparées par des points-virgules (;) : ");
            //String listStr = scanner.nextLine();
            String[] listStr = scanner.nextLine().split(";");
            List<List<Double>> allValeurs = new ArrayList<>();

            //List<Double> valeurs = new ArrayList<>();
            //for (String s : listStr.split(",")) {
              //  valurs.add(Double.parseDouble(s));
            //}
            for (String lst : listStr) {
              String[] values = lst.split(",");
              List<Double> valeurs = new ArrayList<>();

              for (String s : values) {
                valeurs.add(Double.parseDouble(s));
              }
              allValeurs.add(valeurs);

            }



            int numeroListe = 1;
            for (List<Double> valeurs : allValeurs) {
            for (String expression : expressions) {
                Parser parser = new Parser(expression);
                int nbvariables = parser.getnbvariables();
                List<Double> listeOriginal = new ArrayList<>(valeurs);
                List<Double> copy = new ArrayList<>(valeurs);
                boolean conditionRes = true;
                int etape = 1;

                if (nbvariables == 1) {
                    boolean conditionSatisfied = false;
                    for (int i = 0; i < valeurs.size(); i++) {
                        double[] sousliste = new double[1];
                        sousliste[0] = valeurs.get(i);
                        if (condition != null) {
                            boolean valid = condition.eval(sousliste);
                            while (valid) {
                                double resultat = parser.eval(sousliste);
                                valeurs.set(i, resultat);
                                sousliste[0] = valeurs.get(i);
                                valid = condition.eval(sousliste);
                                conditionSatisfied = true;
                                System.out.println("Expression " + expression + " -> Etape " + etape + " Liste n°" + numeroListe + " : " + valeurs);
                                etape++;
                            }
                        } else {
                            double resultat = parser.eval(sousliste);
                            valeurs.set(i, resultat);
                            conditionSatisfied = true;
                            System.out.println("Expression " + expression + " -> Etape " + etape + " Liste n°" + numeroListe + " : " + valeurs);
                            etape++;
                        }
                    }

                    if (conditionSatisfied) {
                        System.out.println("Resultat expression " + expression + " de la liste n°" + numeroListe + " -> " + valeurs +"\n");
                    } else {
                        System.out.println("Aucune valeur de la Liste n°" + numeroListe + " ne respecte la condition.");
                    }

                }

                else {
                    copy = new ArrayList<>(valeurs);
                    conditionRes = false;
                    int index = 0;
                    int nbInvalidValues = 0;
                    int tours = 0;
                    int count = 0;  // initialisation du compteur

                    while (index < valeurs.size() - nbvariables+1 && count < (valeurs.size()*nbvariables)) {  // limite de 10 itérations
                        double[] sousliste = new double[nbvariables];

                        for (int i = 0; i < nbvariables; i++) {
                            sousliste[i] = valeurs.get(index + i);
                        }

                        if (condition != null && !condition.eval(sousliste)) {
                            valeurs.add(valeurs.remove(index));
                            nbInvalidValues++;
                            count++;  // incrémentation du compteur
                        } else {
                            double[] variables = new double[nbvariables];

                            for (int i = 0; i < nbvariables; i++) {
                                variables[i] = valeurs.remove(index);
                            }

                            double resultat = parser.eval(variables);
                            valeurs.add(index, resultat);
                            conditionRes = true;
                            System.out.println("Expression " + expression + " -> Etape " + etape + " Liste n°" + numeroListe + " : " + valeurs);
                            etape++;
                        }

                        copy = new ArrayList<>(valeurs);
                        tours++;

                        // Vérifier si la condition est satisfaite pour toutes les variables
                        if (tours >= valeurs.size() && !conditionRes) {
                          //  System.out.println("Aucune variable de la Liste n°" + numeroListe + " ne satisfait la condition.");
                            break;
                        }
                    }

                    if (count >= (valeurs.size()*nbvariables)) {  // condition non satisfaisable après 10 itérations
                        System.out.println("La condition n'est plus satisfaisable pour la Liste n°" + numeroListe + ".");
                    }

                    if (!conditionRes) {
                        System.out.println("Expression " + expression + " -> Aucune variable de la Liste n°" + numeroListe + " ne satisfait la condition.");
                    } else {
                        for (int i = 0; i < nbInvalidValues; i++) {
                            valeurs.add(valeurs.remove(0));
                        }
                        Collections.sort(valeurs);

                        System.out.println("\n Resultat expression " + expression + " de la Liste n°" + numeroListe + " -> " + valeurs);
                    }


                }



                valeurs = new ArrayList<>(listeOriginal);
                }

                numeroListe++;
                System.out.println("--------------------------------------------------------------");

              }


                System.out.print("Voulez-vous relancer le programme ? (oui ou non) : ");
                            String reponse = scanner.nextLine();
                            continuer = reponse.equalsIgnoreCase("oui");
                            System.out.println("  ");

                }
                scanner.close();
                }
                }
