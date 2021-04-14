package org.insa.graphs.algorithm.shortestpath;

public class Label extends ShortestPathAlgorithm {
	
	public Node sommet; //sommet associé à ce label.
	public boolean marque ; // vrai lorsque le coût min de ce sommet est définitivement connu par l'algorithme
	public float cout; //valeur courante du plus court chemin depuis l'origine vers le sommet.
	public arc pere; //correspond au sommet précédent sur le chemin correspondant au plus court chemin courant.
	
    public Label(int sommet, boolean marque, float cout, arc pere) {
        this.sommet = sommet;
        this.marque = marque;
        this.cout = cout;
        this.pere = pere;
    }

    public float getCost() {
    	return cout;
    }
    
    
    }