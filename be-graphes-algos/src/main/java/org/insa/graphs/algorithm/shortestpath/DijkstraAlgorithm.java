package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.algorithm.AbstractAlgorithm;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        
        ShortestPathSolution solution = null;
        
        // Declarations
        ArrayList<Label> toutLabels = new ArrayList<Label>(); //Ensemble des labels
        int nombreLabels = 0;
        Label labelCourant = null;
        float floatBasCout = Float.MAX_VALUE;
        
        // Initialisation des labels, deja structure de la facon nickel
        
        for(Node node : data.getGraph().getNodes()) {
        	toutLabels.add( new Label(node, false, Float.MAX_VALUE, null));
        }
        
        //
        for(int j = 0; j < nombreLabels - 1 ;j++){
        	
        	//Trouver le min des cout, husk  si at det ville att lengre tid  bruke en liste hvor man m sjekke om den er der
        	for (Label label : toutLabels) {
        		if (!(label.marque) || label.getCost() < floatBasCout) {
        			labelCourant = label;
        			floatBasCout = label.getCost();
        		}
        	}
        	labelCourant.marque = true;
        	//Comparer et eventuellement changer le cout de tout les succs de labelCourant
        	for(Arc arc : labelCourant.getSommet().getSuccessors()) {
        		Label suc = toutLabels.get(arc.getDestination().getId());
            	if(!suc.marque || (arc.getLength() + labelCourant.cout < suc.cout)) {
            		toutLabels.get(arc.getDestination().getId()).cout = arc.getLength();
            		toutLabels.get(arc.getDestination().getId()).pere = arc;
            	}
            }
        	
        }
        
        //Trouver le chemin finale 
        
        
        
        
        return solution;
    }

}
