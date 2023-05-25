package game;

import java.util.HashMap;
import java.util.Map;

public class Parser {
private int index;
private String expression;
private Map<String, Double> variables;
private int nbvariables;


public Parser(String expression) {
  this.expression = expression;
  this.variables = new HashMap<>();
  this.nbvariables = 0;
  for (char c : expression.toCharArray()) {
    if (Character.isLetter(c) && !variables.containsKey(String.valueOf(c))) {
      variables.put(String.valueOf(c), 0.0);
      this.nbvariables++;
    }
  }
}

public void setVariable(String nom, double valeur) {
  variables.put(nom, valeur);
}

public double eval(double[] valeurs) {
  int nbvariables = getnbvariables();
  if (nbvariables == 1) {
    String NomVariable = variables.keySet().iterator().next();
    setVariable(NomVariable, valeurs[0]);
    index = 0;
    return parseExpression();
  } else if (nbvariables > 1) {

    if (valeurs.length < nbvariables) {
      throw new ArrayIndexOutOfBoundsException("Pas assez de valeurs pour initialiser des variables");
    }
    int i = 0;
    for (Map.Entry<String, Double> entry : variables.entrySet()) {
      entry.setValue(valeurs[i++]);
    }
    index = 0;
    return parseExpression();
  } else {
    return 0.0;
  }
}

public int getnbvariables() {
  return this.nbvariables;
}

private double parseExpression() {
  double valeur = parseTerm();
  while (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
    char operateur = expression.charAt(index);
    index++;
    double valeurSuivante = parseTerm();
    if (operateur == '+') {
      valeur += valeurSuivante;
    } else {
      valeur -= valeurSuivante;
    }
  }
  return valeur;
}

private double parseTerm() {
  double valeur = parseFactor();
  while (index < expression.length() && (expression.charAt(index) == '*' || expression.charAt(index) == '/')) {
    char operateur = expression.charAt(index);
    index++;
    double valeurSuivante = parseFactor();
    if (operateur == '*') {
      valeur *= valeurSuivante;
    } else {
      valeur /= valeurSuivante;
    }
  }
  return valeur;
}

private double parseFactor() {
  double valeur = 0;
  boolean negative = false;
  if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
    negative = expression.charAt(index) == '-';
    index++;
  }
  if (index < expression.length() && Character.isLetter(expression.charAt(index))) {
    valeur = variables.get(String.valueOf(expression.charAt(index)));
    index++;
  }
  else if (index < expression.length() && Character.isDigit(expression.charAt(index))) {
    while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
      valeur = valeur * 10 + (expression.charAt(index) - '0');
      index++;
    }
  }

  else if (index < expression.length() && expression.charAt(index) == '(') {
    index++;
    valeur = parseExpression();
    if (index >= expression.length() || expression.charAt(index) != ')') {
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
