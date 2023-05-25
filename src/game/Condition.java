package game;

import java.util.HashMap;
import java.util.Map;

public class Condition {
    private int index;
    private String condition;
    private Map<String, Double> variables;

    public Condition(String condition) {
        this.condition = condition;
        this.variables = new HashMap<>();
        for (char c : condition.toCharArray()) {
            if (Character.isLetter(c) && !variables.containsKey(String.valueOf(c))) {
                variables.put(String.valueOf(c), 0.0);
            }
        }
    }

    public void setVariable(String nom, double valeur) {
        variables.put(nom, valeur);
    }

    public int getVariableCount() {
        int nbvariables = 0;
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (Character.isLetter(c) && (i == 0 || !Character.isLetter(condition.charAt(i - 1)))) {
                nbvariables++;
            }
        }
        return nbvariables;
    }

    public boolean eval(double[] valeurs) {
        int nbvariables = getVariableCount();
        if (nbvariables == 1) {
            String NomVariable = variables.keySet().iterator().next();
            setVariable(NomVariable, valeurs[0]);
            index = 0;
            return parseCondition();
        } else if (nbvariables > 1) {
            if (valeurs.length < nbvariables) {
                throw new ArrayIndexOutOfBoundsException("Pas assez de valeurs pour initialiser des variables");
            }
            int i = 0;
            for (Map.Entry<String, Double> entry : variables.entrySet()) {
                entry.setValue(valeurs[i++]);
            }
            index = 0;
            return parseCondition();
        } else {
            return false;
        }
    }

    private boolean parseCondition() {
        boolean valeur1 = parseExpression();
        while (index < condition.length() && (condition.charAt(index) == '&' || condition.charAt(index) == '|')) {
            char operateur = condition.charAt(index);
            index++;
            boolean valeur2 = parseExpression();
            if (operateur == '&') {
                valeur1 = valeur1 && valeur2;
            } else {
                valeur1 = valeur1 || valeur2;
            }
        }
        return valeur1;
    }

    private boolean parseExpression() {
        boolean resultat = false;
        if (index < condition.length() && condition.charAt(index) == '(') {
            index++;
            resultat = parseCondition();
            if (index >= condition.length() || condition.charAt(index) != ')') {
                throw new RuntimeException("Erreur de syntaxe: parenthèse fermante manquante");
            }
            index++;
        } else {
            double valeur1 = parseTerm();
            if (index < condition.length() && (condition.charAt(index) == '>' || condition.charAt(index) == '<' ||
                    condition.charAt(index) == '=')) {
                char operateur = condition.charAt(index);
                index++;
                double valeur2 = parseTerm();
                switch (operateur) {
                    case '>':
                        resultat = (valeur1 > valeur2);
                        break;
                    case '<':
                        resultat = (valeur1 < valeur2);
                        break;
                    case '=':
                        resultat = (valeur1 == valeur2);
                        break;
                    default:
                        resultat = false;
                        break;
                }
            } else {
                resultat = (valeur1 != 0);
            }
        }
        if (index < condition.length() && condition.charAt(index) == '|') {
            index++;
            resultat = resultat || parseExpression();
        }
        if (index < condition.length() && condition.charAt(index) == '&') {
            index++;
            resultat = resultat && parseExpression();
        }
        return resultat;
    }




    private double parseTerm() {
        double valeur = parseFactor();
        while (index < condition.length() && (condition.charAt(index) == '+' || condition.charAt(index) == '-')) {
            char operateur = condition.charAt(index);
            index++;
            double valeurSuivante = parseFactor();
            if (operateur == '+') {
                valeur += valeurSuivante;
            } else {
                valeur -= valeurSuivante;
            }
        }
        return valeur;
    }



    private double parseFactor() {
  double valeur = 0;
  boolean negative = false;
  if (index < condition.length() && (condition.charAt(index) == '+' || condition.charAt(index) == '-')) {
    negative = condition.charAt(index) == '-';
    index++;
  }
  if (index < condition.length() && Character.isLetter(condition.charAt(index))) {
    valeur = variables.get(String.valueOf(condition.charAt(index)));
    index++;
  }
  else if (index < condition.length() && Character.isDigit(condition.charAt(index))) {
    while (index < condition.length() && Character.isDigit(condition.charAt(index))) {
      valeur = valeur * 10 + (condition.charAt(index) - '0');
      index++;
    }
  }
  else if (index < condition.length() && condition.charAt(index) == '(') {
    index++;
    valeur = parseExpression() ? 1 : 0;
    if (index >= condition.length() || condition.charAt(index) != ')') {
      throw new RuntimeException("Erreur de syntaxe: parenthèse fermante manquante");
    }
    index++;
  }

  if (negative) {
    valeur = -valeur;
  }
  return valeur;
}


}
