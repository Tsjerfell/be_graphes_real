package org.insa.graphs.model;

import org.insa.graphs.model.*;

public class LabelStar extends Label{
		
		public double coutestime; // le cout estimé

		public LabelStar(Node sommet, boolean marque, double cout, Arc pere, double CoutEstime) {
			super(sommet,marque,cout,pere);
			this.coutestime = CoutEstime;
		}
		
		@Override
		public double getCoutEstime() {
			return this.coutestime;
		}
		
		@Override
		public double getTotalCost() {
			return this.cout + this.coutestime;
		}		
	}

