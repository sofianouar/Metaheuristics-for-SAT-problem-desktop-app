package classes;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class PSO {
	
	// generate population(swarm)
	public Vector<Particle> GenSwarm(Vector<Clause> clauses, int nbvar, int sizePop, int nbcl) {
		int[] instTemp;
		int x, j = 1;
		int[] clauseVect = new int[nbcl];
		Vector<Particle> Swarm = new Vector<Particle>();

		for (int i = 0; i < sizePop; i++) {
			instTemp = new int[nbvar + 1];
			while (j < nbvar + 1) {
				Random random = new Random();
				x = (random.nextInt(nbvar + 1 + nbvar) - nbvar);
				while (x == 0) {
					x = (random.nextInt(nbvar + 1 + nbvar) - nbvar);
				}

				if (instTemp[Math.abs(x)] == 0) {
					instTemp[Math.abs(x)] = x;
					j++;
				}
			}
			j = 1;
			int y=0;
			Random random = new Random();
			y = random.nextInt(nbvar-1)+1;
			SAT satTemp = new SAT(clauseVect, instTemp);
			SAT sat2 = new SAT(clauseVect,instTemp);
			Particle part = new Particle(satTemp,sat2,y);
			
			Swarm.add(part);
		}
		return Swarm;
	}
	public void initpbest(Vector<Particle> part) {	
		for(Particle parti:part){
			parti.getBestPos().setNbSat(parti.getCurrentPos().getNbSat());

		}
	}
	

	public void FitnessCal(Particle part, Vector<Clause> clVect) {	
			part.getCurrentPos().setNbSat(0);
			for (Clause cl : clVect) {
				part.getCurrentPos().setNbSat(part.getCurrentPos().getNbSat() + SAT.CompareClauses(part.getCurrentPos().getInstVect(), cl));
			}
	}
	
	public SAT calculation(Vector<Particle> vect){
		Collections.sort(vect);
		return vect.firstElement().getBestPos();
	}

	public void updateVelocity(Particle p, double  w,int c1, int c2, SAT gBest,int nbvar) {
		double vel,r1,r2;
		r1 = Math.random();
		r2 = Math.random();
		SAT pBest, x; //x=currentPos
		vel=p.getVelocity();
		x=p.getCurrentPos();
		pBest=p.getBestPos();
		int distPbest=distance(pBest,x);
		int distGbest=distance(gBest,x);
		vel=w*vel + c1*r1*distPbest +c2*r2*distGbest;
		p.setVelocity(vel%nbvar);
	}
	public void updatePosition(Particle p) {
		Vector<Integer> randPos= new Vector<Integer>();
		Random rand= new Random();
		int posR= rand.nextInt(p.getCurrentPos().getInstVect().length);
		randPos.add(posR);
		for (int i = 0; i < p.getVelocity()-1; i++) {
			while(randPos.contains(posR)) {
				posR= rand.nextInt(p.getCurrentPos().getInstVect().length-1)+1;
			}
			randPos.add(posR);
		}
		for (Integer pos : randPos) {
			p.getCurrentPos().getInstVect()[pos]=p.getCurrentPos().getInstVect()[pos]*-1;
		}
	}
	
	public int distance(SAT point1, SAT point2) {
		int distance=0, size=point1.getInstVect().length;
		
		for(int i=0;i<size;i++) {
			if(point1.getInstVect()[i]!=point2.getInstVect()[i])  			
				distance+=1;			
		}	
		return distance;
	}
	
	public void updatePbest(Particle p) {
		SAT  best;
		best=p.getBestPos();
		if(p.getCurrentPos().getNbSat()>best.getNbSat()) 	p.setBestPos(p.getCurrentPos());
	}


}
