package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.model.Label;
import org.insa.graphs.model.LabelStar;
import org.insa.graphs.model.Node;

import org.insa.graphs.algorithm.*;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected ArrayList<Label> intizialiseList(ShortestPathData data){
    	ArrayList<Label> toutLabels = new ArrayList<Label>();
    	
     	for(int i=0; i < data.getGraph().getNodes().size(); i++) {
    		Node node = data.getGraph().getNodes().get(i);
    		LabelStar newLabel = new LabelStar(node, false, Double.MAX_VALUE, null, Double.MAX_VALUE);	
    		
    		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
    			newLabel.coutestime= Math.abs(newLabel.sommet.getPoint().distanceTo(data.getDestination().getPoint()));
			} else {
				double speed = data.getMaximumSpeed()*3.6;
				newLabel.coutestime = Math.abs(newLabel.sommet.getPoint().distanceTo(data.getDestination().getPoint())) / (data.getMaximumSpeed()*3.6);
			}
    		toutLabels.add(node.getId(), newLabel);
    	}
     	for(Label label : toutLabels) {
     		System.out.println(label.getCoutEstime());
     	}
     	return toutLabels;
    }
}


