package org.insa.graphs.model;

public class Label implements Comparable<Label>{
	
	public Node sommet; //sommet associé à ce label.
	public boolean marque ; // vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	public double cout; //valeur courante du plus court chemin depuis l'origine vers le sommet.
	public Arc pere; //correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	
	
    public Label(Node sommet, boolean marque, double cout, Arc pere) {
        this.sommet = sommet;
        this.marque = marque;
        this.cout = cout;
        this.pere = pere;
    }

    public double getCost() {
    	return cout;
    }
    
    public Node getSommet() {
    	return sommet;
    }

	@Override
	public int compareTo(Label autre) {
		int returnvalue;
		if (autre.getCost() > this.getCost()){
			returnvalue = -1;
		} else if (autre.getCost() == this.getCost()) {
			returnvalue = 0;
		} else {
			returnvalue = 1;
		}
		return returnvalue;
	}
    
    }