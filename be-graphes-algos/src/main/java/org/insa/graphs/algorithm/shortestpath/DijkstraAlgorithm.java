package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.algorithm.AbstractAlgorithm;
import org.insa.graphs.algorithm.AbstractSolution.Status;



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
        Label labelCourant = null;
        float floatBasCout = Float.MAX_VALUE;
        BinaryHeap<Label> pile = new BinaryHeap<Label>();
        boolean trouve = false;
        ArrayList<Arc> solutionArcs= new ArrayList<Arc>(); 
        
        
        // Initialisation des labels
        
        for(Node node : data.getGraph().getNodes()) {
        	toutLabels.add( new Label(node, false, Float.MAX_VALUE, null));
        }
        
        //insertion du premiere label
        pile.insert(toutLabels.get(0));
        
        //
        
        while(!trouve){
        	labelCourant = pile.deleteMin();
        	labelCourant.marque = true;
        	
        	//Comparer et eventuellement changer le cout de tout les succs de labelCourant
        	for(Arc arc : labelCourant.getSommet().getSuccessors()) {
        		Label suc = toutLabels.get(arc.getDestination().getId());
            	if(!suc.marque || ((arc.getLength() + labelCourant.cout) < suc.cout)) {
            		suc.cout = arc.getLength() + labelCourant.getCost();
            		suc.pere = arc;
            		for(Label label : pile.getArray()) { //verification si le suc est deja dans le pil
            			if (label.sommet == suc.sommet) {
            				pile.remove(toutLabels.get(arc.getDestination().getId()));
            				break;
            			}
            		}
            		pile.insert(suc);
            		if (suc.sommet == data.getDestination()) {
            			labelCourant = suc; //Pour trouver le chemin final
            			trouve = true;
            		}
            	}
            }
        	
        }
        
        //Trouver le chemin finale 
        while(labelCourant.pere != null) {
        	System.out.println(labelCourant.sommet.getId());
        	solutionArcs.add(labelCourant.pere);
        	labelCourant = toutLabels.get(labelCourant.pere.getOrigin().getId());
        }
        
        Collections.reverse(solutionArcs);
        solution = new ShortestPathSolution(data,Status.OPTIMAL, new Path(data.getGraph(), solutionArcs));
        return solution;
    }

}
