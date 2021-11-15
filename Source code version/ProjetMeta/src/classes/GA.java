package classes;

import java.util.Vector;
import java.util.Random;

public class GA {
	public Population GenSol(Vector<Clause> clauses, int nbvar, int sizePop, int nbcl) {
		int[] instTemp;
		int x, j = 1;
		int[] clauseVect = new int[nbcl];
		Vector<SAT> Ouvert = new Vector<SAT>();

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
			SAT satTemp = new SAT(clauseVect, instTemp);
			Ouvert.add(satTemp);
		}

		Population Pop = new Population(Ouvert);
		return Pop;
	}

	public void Fitness(Population pop, Vector<Clause> clVect) {
		
		for (SAT indiv : pop.getIndvVect()) {
			indiv.setNbSat(0);
			for (Clause cl : clVect) {
				indiv.setNbSat(indiv.getNbSat() + SAT.CompareClauses(indiv.getInstVect(), cl));
			}
			}
	}

	/*
	 * CrossOver function, takes 2 individuals (2 SAT objects) chooses randomly a
	 * position to divise each indiv cross the 2 indiv between them example : A= [0
	 * 1 0 1] pos =2 B= [1 0 1 1] pos=3 result = AB[0 1 1 1], BA[1 0 1 1]
	 */
	public void CrossOver(SAT indiv1, SAT indiv2) {
		// sat object : child of crossover
		int pos, nbvar;
	
		nbvar = indiv1.getInstVect().length;
		SAT child = new SAT(indiv1.getClauseVect().clone(), indiv1.getInstVect().clone());

		// choosing a position to do the cut on indiv1
		Random random = new Random();
		
		pos = (random.nextInt(nbvar + 1) + 1) % (nbvar-1);
		while (pos == 0) {
			pos = (random.nextInt(nbvar + 1) + 1) % (nbvar-1);
		}

		// construcing child 1
		for (int i = 1; i <= pos; i++) {
			child.getInstVect()[i] = indiv1.getInstVect()[i];
		}
		for (int i = pos + 1; i < nbvar; i++) {
			child.getInstVect()[i] = indiv2.getInstVect()[i];
		}

		
		// choosing a position to do the cut on indiv2
		pos = (random.nextInt(nbvar + 1) + 1) % (nbvar-1);
		while (pos == 0) {
			pos = (random.nextInt(nbvar + 1) + 1)  % (nbvar-1);
		}
		
		SAT child2 = new SAT(indiv2.getClauseVect().clone(), indiv2.getInstVect().clone());
		// construcing child2
		for (int i = 1; i <= pos; i++) {
			child2.getInstVect()[i] = indiv2.getInstVect()[i];
		}
		for (int i = pos + 1; i < nbvar; i++) {
			child2.getInstVect()[i] = indiv1.getInstVect()[i];
		}
	
		indiv1.setInstVect(child.getInstVect().clone());
		indiv2.setInstVect(child2.getInstVect().clone());
		return;
	}

	/*
	 * Mutate function, takes an individual (SAT object) and chooses randomly a
	 * position to invert its value
	 */
	public SAT Mutate(SAT individ, int number) {
		// choosing the point of mutation
		int pos, temp, nbvar;
		// getting number of variables
		nbvar = individ.getInstVect().length;
		Random random = new Random();
		for (int i = 1; i < number; i++) {
			pos = (random.nextInt(nbvar + 1) + 1)%nbvar;
			pos = Math.abs(pos);

			// putting the concerned element in a temporary variable
			temp = individ.getInstVect()[pos];

			// if the value is negative : mutation makes it positive, else negative
			if (temp < 0)
				temp = Math.abs(temp);
			else
				temp = -temp;
		}
		// return individ
		return individ;
	}

	public SAT Selection(Population pop) {
		float totalNbSat = 0;
		float currentSat = 0;
		SAT chosenOne;
		for (SAT satTemp : pop.getIndvVect()) {
			totalNbSat = totalNbSat + satTemp.getNbSat();
		}

		float rand = (float) (Math.random() * totalNbSat);

		for (SAT satTemp : pop.getIndvVect()) {
			if (rand >= currentSat && rand <= currentSat + satTemp.getNbSat())
				{
				chosenOne= new SAT(satTemp.getClauseVect().clone(),satTemp.getInstVect().clone());
				return chosenOne;
				}

			currentSat = currentSat + satTemp.getNbSat();
		}

		return null;
	}

}
