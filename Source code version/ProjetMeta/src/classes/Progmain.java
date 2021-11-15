package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;


public class Progmain {
	public static void main(String[] args) {
		int nbvariables = 0, nbclauses = 0;
		int[] maxSat = new int[1];

		int numberTry = 1;
		double pourcentageSatBfs;
		long timeLimit = 1; // minute
		timeLimit = TimeUnit.MINUTES.toMillis(timeLimit);
		BufferedReader objReader;
		Vector<Clause> clauses = new Vector<>();

		// Lecture du fichier CNF
		try {

			objReader = new BufferedReader(new FileReader("src/SAT/uf75-01.cnf"));
			String ligne = objReader.readLine();

			String[] ligne1 = ligne.split(" ");
			nbvariables = Integer.parseInt(ligne1[2]);
			System.out.println("Le nombre de variable est :" + nbvariables);
			nbclauses = Integer.parseInt(ligne1[3]);
			int sizecl;

			// traitement des clauses
			try {
				while ((ligne = objReader.readLine()) != null) {

					if (ligne.compareTo(Arrays.toString(ligne1)) != 0) {
						String[] data = ligne.split(" ");
						sizecl = data.length;
						Clause cl = new Clause(sizecl - 1);

						stringToInt(data, cl);

						clauses.add(cl);
					}
				}

			} catch (NumberFormatException | IOException ignore) {
			}
			objReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner sc = new Scanner(System.in);
		// fin de lecture cnf

		/*
		 * Init dfs
		 */
		DFS dfs = new DFS();

		// intialisation vecteur clause
		int[] clauseVect = new int[nbclauses];
		for (int i = 0; i < nbclauses; i++) {
			clauseVect[i] = 0;
		}
		// intialisation vecteur instance
		int[] instVect = new int[nbvariables + 1];
		for (int i = 1; i < nbvariables + 1; i++) {
			instVect[i] = 0;
		}
		// intialisation ouvert
		Vector<SAT> ouvert = new Vector<SAT>();
		// intialisation SAT
		SAT sat = new SAT(clauseVect, instVect);
		// creation Racine DFS
		Node root = new Node(sat, null, null);
		/*
		 * fin initialisation dfs
		 */

		System.out.print("Choose a searching algorithm : ");

		int choiceSC = sc.nextInt();

		// Choix de l'algorithme de Recherche 1 Etoile, 2 BFS, 3 DFS, 4 GA, 5 PSO
		switch (choiceSC) {
		case 1:

			break;
		case 2:
			Vector<Node> openQ = new Vector<>();
			openQ.add(root);

			// bfs algorithm
			BFS bfs = new BFS();
			System.out.print("BFS ALGORITHM STARTING...\n");

			for (int i = 0; i < numberTry; i++) {
				maxSat[0] = bfs.searchBFS(openQ, clauses, timeLimit);
			}
			pourcentageSatBfs = (double) ((maxSat[0] * 100) / nbclauses);

			System.out.println("max clauses " + maxSat[0]);

			System.out.print("pourcentage of success is " + pourcentageSatBfs + " %.\n");
			// fin BFS
			System.out.print("BFS ENDS");
			break;

		case 3:

			clauseVect = new int[nbclauses];
			for (int i = 0; i < nbclauses; i++) {
				clauseVect[i] = 0;
			}
			// intialisation vecteur instance
			instVect = new int[nbvariables + 1];
			for (int i = 1; i < nbvariables + 1; i++) {
				instVect[i] = 0;
			}
			// intialisation ouvert
			ouvert = new Vector<SAT>();
			// intialisation SAT
			sat = new SAT(clauseVect, instVect);

			// creation Racine DFS
			root = new Node(sat, null, null);
			long startTime = System.currentTimeMillis();

			for (int i = 0; i < numberTry; i++) {
				dfs.search(root, clauses, ouvert, maxSat, timeLimit, startTime);
			}
			if (ouvert.size() == 0) {
				System.out.println("Solution not found");
				System.out.println("Le nombre max de clauses sat est:" + maxSat[0]);
				pourcentageSatBfs = (maxSat[0] * 100) / nbclauses;
				System.out.print("pourcentage of success is " + pourcentageSatBfs + " %.\n");
			} else
				for (SAT sattemp : ouvert) {
					for (int i = 1; i < nbvariables + 1; i++) {
						System.out.println(sattemp.getInstVect()[i] + "\t");
					}
				}
			break;

		case 4: // GA
			GA ga = new GA();
			int genNum = 1, staticGB = 500, k;
			Population pop, temppop = new Population(new Vector<>()), childs = new Population(new Vector<>());

			System.out.print("Choose size of Population: ");
			int sizePop = sc.nextInt();
			pop = ga.GenSol(clauses, nbvariables, sizePop, nbclauses);

			System.out.print("Choose number of Generation: ");
			int genGoal = sc.nextInt();

			System.out.print("Choose the goal of % sat u want to achieve: ");
			int goalSat = (int) (nbclauses * Float.parseFloat(sc.next()));

			ga.Fitness(pop, clauses);
			Collections.sort(pop.getIndvVect(), Collections.reverseOrder());
			SAT bestSolPop = pop.getIndvVect().lastElement();
			int bestSolC = bestSolPop.getNbSat();

			System.out.print("Choose number of bits to mutate:");
			int nbMut = sc.nextInt();
			System.out.println("The best solution from randomization is: " + bestSolPop.getNbSat()
					+ "\nInput something to continue");
			sc.next();
			// Startinng gen Algorithm
			while (bestSolPop.getNbSat() < goalSat && genNum < genGoal) {
				System.out.println("Gen number " + genNum + " Best solution last gen :" + bestSolPop.getNbSat());
				genNum++;

				k = sizePop;
				while (k > (sizePop / 2)) {
					SAT Parent1 = ga.Selection(pop);
					SAT Parent2 = ga.Selection(pop);
					k -= 2;

					ga.CrossOver(Parent1, Parent2);

					ga.Mutate(Parent1, nbMut);
					ga.Mutate(Parent2, nbMut);
					childs.getIndvVect().add(Parent1);
					childs.getIndvVect().add(Parent2);
				}

				ga.Fitness(childs, clauses);
				Collections.sort(childs.getIndvVect(), Collections.reverseOrder());
				System.out
						.println("This gen, the best SAT number is: " + childs.getIndvVect().lastElement().getNbSat());
				temppop.getIndvVect().addAll(pop.getIndvVect().subList(0, (sizePop) / 2 + (((sizePop) / 2) % 2)));// removing
																													// the
																													// 50%
																													// worse
																													// solution
				pop.getIndvVect().removeAll(temppop.getIndvVect());
				pop.getIndvVect().addAll(childs.getIndvVect());
				Collections.sort(pop.getIndvVect(), Collections.reverseOrder());
				bestSolPop = pop.getIndvVect().lastElement();
				childs.getIndvVect().removeAllElements();
				temppop.getIndvVect().removeAllElements();
				if (bestSolPop.getNbSat() == bestSolC) { // code for not getting better after 500 gen
					staticGB--;
					if (staticGB <= 0) {
						break;
					}
				} else if (bestSolPop.getNbSat() > bestSolC) {
					bestSolC = bestSolPop.getNbSat();
					staticGB = 500;
				}
			}

			System.out.print("The best solution I found satisfies :" + bestSolPop.getNbSat() + " clauses ( "
					+ (bestSolPop.getNbSat() * 100) / 325 + "%)\n");
			System.out.print("The solution is : \n");
			bestSolPop.Affichage();
			break;

		case 5: // PSO
			PSO pso = new PSO();
			int c1, c2, n, vMax,maxIter;
			double w;
			SAT gBest;
			vMax = nbvariables;
			Vector<Particle> swarm= new Vector<>();
			
			System.out.print("Enter swarm size : ");
			n = sc.nextInt();
			System.out.print("Choose weight value : ");
			w = sc.nextInt();
			System.out.print("Choose c1 between 0 and 2 : ");
			c1 = sc.nextInt();
			System.out.print("Choose c2 between 0 and 2 : ");
			c2 = sc.nextInt();

			
	
			
			
			System.out.print("Enter max iterations : ");
			maxIter = sc.nextInt();
			
			swarm = pso.GenSwarm(clauses, nbvariables, n, nbclauses);
			
			//calculatitng fitness for each position of the particle
			for (Particle part : swarm) {
				pso.FitnessCal(part, clauses);
			}
			pso.initpbest(swarm);
			
			//initialing Global best
			gBest=pso.calculation(swarm);
			System.out.println("initial best solution is : ");
			gBest.Affichage();
			System.out.println("its initial nbsat is : "+gBest.getNbSat());
			if(gBest.getNbSat()==nbclauses){
				System.out.print("On peut pas avoir de meilleure solution , le Gbest actuel est le meilleur");
			}
			int j=0;
			for(j=0;j<maxIter;j++) {
				//update currentposition, velocity, bestposition of each particle
				for(Particle part: swarm){
					pso.updateVelocity(part,w,c1,c2,gBest,nbvariables);
					pso.updatePosition(part);
					pso.FitnessCal(part, clauses);
					pso.updatePbest(part);
				}
				//updating gbest : sort swarm, compare swarm[0] with oldGbest
			    if (pso.calculation(swarm).getNbSat()> gBest.getNbSat()) 
				{
					gBest.setInstVect(pso.calculation(swarm).getInstVect());
					gBest.setNbSat(pso.calculation(swarm).getNbSat());
				}
				System.out.println(j+"\n");
				if(gBest.getNbSat()==nbclauses){
					break;
				}
				
			}
			
			System.out.print("done");
			System.out.print(gBest.getNbSat());

			break;

		default:
			System.out.println("Wrong choice || heuristics non-existant");
			break;
		}
		sc.close();
	}

	public static void stringToInt(String[] tab, Clause cl) {
		for (int i = 0; i < tab.length - 1; i++) {
			cl.getVarVect()[i] = Integer.parseInt(tab[i]);
		}
	}

}
