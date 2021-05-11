package org.insa.graphs.model;

public class Label {
	
	public Node sommet; //sommet associé à ce label.
	public boolean marque ; // vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	public float cout; //valeur courante du plus court chemin depuis l'origine vers le sommet.
	public Arc pere; //correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	
	
    public Label(Node sommet, boolean marque, float cout, Arc pere) {
        this.sommet = sommet;
        this.marque = marque;
        this.cout = cout;
        this.pere = pere;
    }

    public float getCost() {
    	return cout;
    }
    
    public Node getSommet() {
    	return sommet;
    }
    
    }