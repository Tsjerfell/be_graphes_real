package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.utils.*;
import org.insa.graphs.algorithm.AbstractSolution.Status;



public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    // Initialisation des labels
    protected ArrayList<Label> intizialiseList(ShortestPathData data){
    	ArrayList<Label> toutLabels = new ArrayList<Label>();
    	for(Node node : data.getGraph().getNodes()) {
    		toutLabels.add( new Label(node, false, Double.MAX_VALUE, null));
    	}
    	
    return toutLabels;
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        
        ShortestPathSolution solution = null;
        
        // Declarations
        ArrayList<Label> toutLabels = new ArrayList<Label>(); //Ensemble des labels
        Label labelCourant = null; 
        BinaryHeap<Label> pile = new BinaryHeap<Label>();
        
        boolean trouve = false;
        ArrayList<Arc> solutionArcs= new ArrayList<Arc>();
       
        toutLabels = intizialiseList(data);
        //insertion du premiere label 
        
        	Label label =  toutLabels.get(data.getOrigin().getId()); 
        	label.marque = true;
        	label.cout = 0;
        	pile.insert(label);
        	notifyOriginProcessed(label.getSommet());
               
        while(!trouve && !pile.isEmpty()){
        	labelCourant = pile.deleteMin();
        	labelCourant.marque = true;
        	notifyNodeMarked(labelCourant.getSommet());
        	
        	//Comparer et eventuellement changer le cout de tout les succs de labelCourant
        	for(Arc arc : labelCourant.getSommet().getSuccessors()) {
        		Label suc = toutLabels.get(arc.getDestination().getId());
            	if(!suc.marque && ((data.getCost(arc) + labelCourant.getCost()) < suc.getCost())) {
            		if (suc.pere != null){ 
            			pile.remove(suc);
            		} else { //premiere fois qu'on viste le node
            			notifyNodeReached(suc.getSommet());
            		}
            		suc.cout = data.getCost(arc) + labelCourant.getCost();
            		suc.pere = arc;           		
            		pile.insert(suc);            		
            	}
            }
        	if (labelCourant.sommet == data.getDestination()) {// on test si on est dans le destination, si c'est le cas, on termine
    			trouve = true;
    			notifyDestinationReached(labelCourant.getSommet());
    		}
        }
        
        //Trouve le chemin finale / dit qu'il nexiste pas un chemin        
        if (trouve){ //on a trouve le chemin  
        	while(labelCourant.pere != null) {
        		solutionArcs.add(labelCourant.pere);
        		labelCourant = toutLabels.get(labelCourant.pere.getOrigin().getId());       		      
        	}
        	Collections.reverse(solutionArcs);
            solution = new ShortestPathSolution(data,Status.OPTIMAL, new Path(data.getGraph(), solutionArcs)); 
        } else  { //Il n'existe pas un chemin 
        	solution = new ShortestPathSolution(data,Status.INFEASIBLE, new Path(data.getGraph(), solutionArcs));           
        
        }
        return solution;
    }
}

