package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.junit.BeforeClass;
import org.junit.Test; 


public class DijkstraTest{
	
	//On utilise le meme graph que pour le Path
    // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;
    
    // Une chemin existant et un inexsistant
    private static ShortestPathData shortData ;
    private static ShortestPathData invalidData;
    
    //le resultat de Dijkstra
    private static Path shortSol;
    
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes, ou rajoute un node qui va rester tout seul
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        
        shortData = new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
        invalidData = new ShortestPathData(graph, nodes[0], nodes[5], ArcInspectorFactory.getAllFilters().get(0));
    
        //Le resultat de Bellman-Ford
        shortSol = new BellmanFordAlgorithm(shortData).run().getPath();
    }
    
    public void test(Graph graph,  int algo,int mode, int originID, int destinationID) { 
    	//mode = 0 si on teste le temps, ou 1 si on teste la distance
    	//algo = 0 si on teste Dijkstra, ou 1 si on tes A*
    	Node origin = graph.get(originID);
		Node destination = graph.get(destinationID);
		ArcInspector inspector;
		
		if (mode == 0) { //on regarde le temps
			inspector = ArcInspectorFactory.getAllFilters().get(2);	
		} else { // on regarde la direction
			inspector = ArcInspectorFactory.getAllFilters().get(0);	
		}
		ShortestPathData data = new ShortestPathData(graph, origin, destination, inspector);
		
		ShortestPathSolution solution = null;
		ShortestPathSolution expected = null;
		if (algo == 0) { //On teste Djikstra
			BellmanFordAlgorithm E = new BellmanFordAlgorithm(data);
			DijkstraAlgorithm S = new DijkstraAlgorithm(data);
			solution = S.run();
			expected = E.run();
		} else { //On teste A*
			BellmanFordAlgorithm E = new BellmanFordAlgorithm(data);
			AStarAlgorithm S = new AStarAlgorithm(data);
			solution = S.run();
			expected = E.run();
		}
		double solutionCost;
		double expectedCost;
		if (mode == 0) { // on teste en temps
			if (solution.getStatus() == Status.INFEASIBLE && expected.getStatus() == Status.INFEASIBLE) { //il n'y a pas de chemin etre l'origin et la destination)
				solutionCost = 0;
				expectedCost = 0;
			} else { //il y a une solution
				solutionCost = solution.getPath().getMinimumTravelTime();
				expectedCost = expected.getPath().getMinimumTravelTime();
			}
		} else { //on test en distance
			if (solution.getStatus() == Status.INFEASIBLE && expected.getStatus() == Status.INFEASIBLE) { //il n'y a pas de chemin etre l'origin et la destination)
				solutionCost = 0;
				expectedCost = 0;
			} else { //il y a une solution
			solutionCost = solution.getPath().getLength();
			expectedCost = expected.getPath().getLength();
			}
		}
		System.out.println("on teste!");
		assertEquals(expectedCost, solutionCost, 0.001);
	
    }
    
    @Test
    public void testCheminExiste() {
        test(graph,0,0,0,3); //test de Dijkstra en temps
        test(graph,0,1,0,3); //test de Dijkstra en distance
        //test(graph,1,0,0,3); //test de A* en temps
        //test(graph,1,1,0,3); //test de A* en distance
    }
    
    @Test
    public void testCheminExistePas() {
        test(graph,0,0,2,5); //test de Dijkstra en temps
        test(graph,0,1,2,5); //test de Dijkstra en distance
        //test(graph,1,0,0,3); //test de A* en temps
        //test(graph,1,1,0,3); //test de A* en distance
    }
    
}
    
    
    