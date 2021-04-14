package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;
import java.util.ArrayList;


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
        ArrayList<Label> nodesVisite = new ArrayList<Label>(); //Vide au debut
        ArrayList<Label> nodesNonVisite = data.getGraph().getNodes(); //Tout les nodes
        boolean pasFaitQqc = false;
        Label labelCourant;
        
        // Initialisation des labels
        int i = 0;
        for(Node node : data.getGraph().getNodes()) {
        	tout_labels[i] = new Label(node, false, Float.MAX_VALUE, null);
        	i++;		
        }
        public Label plusBasCout() {
        	float floatBasCout = Float.MAX_VALUE;
        	Label labelBasCout = null;
        	for (Label label : toutLabels) {
        		if (label.getCout() < floatBasCout) {
        			labelBasCout = label;
        			floatBasCout = label.getCout();
        		}
        	}
        	return labelBasCout;
        }
        
        while (!pasFaitQqc) {
        	pasFaitQqc = true;
        	labelCourant = plusBasCout(); 
        }
        
        return solution;
    }

}
