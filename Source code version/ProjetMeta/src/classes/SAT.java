package classes;

import java.util.Vector;



public class SAT implements Comparable<SAT> {


	private int[] clauseVect; //1 la clause d'indice i est correct 0 elle ne l'est pas initialement tous a 0
	private int[] instVect; 
	private int nbSat;
	//Constructor
	public SAT(int[] clauseVect, int[] instVect) {
		this.clauseVect = clauseVect;
		this.instVect = instVect;
		for (int i = 0; i < clauseVect.length; i++) {
			if  (clauseVect[i]==1) nbSat++;
		}
	}
	
	public SAT() {
		// TODO Auto-generated constructor stub
	}

	//Getters and Setters
	public int[] getClauseVect() {
		return clauseVect;
	}
	public void setClauseVect(int[] clauseVect) {
		this.clauseVect = clauseVect;
	}

	public int[] getInstVect() {
		return instVect;
	}
	public void setInstVect(int[] instVect) {
		this.instVect = instVect;
	}
	
	public int getNbSat() {
		return nbSat;
	}
	public void setNbSat(int nbSat) {
		this.nbSat = nbSat;
	}
	
	//Methodes 
	public int Randinst(){
		Vector<Integer> tempinst= new Vector<>();
		int randNum=0;
		for (int i = 1; i < instVect.length; i++) {
			if (instVect[i]==0) {
				tempinst.add(i);
			}
		}
		if (tempinst.size()>0) {

		randNum= (int)Math.random() * (tempinst.size());
		
		return tempinst.get(randNum);
		}
		else return 0;
	}
	public int  numberClauseSAT(int inst, Vector<Clause> clVect){ // Donne le nombre de clause SAT
        int i=0;
        for (Clause clause : clVect) {
        	if (clauseVect[i]==0)
        	{
        	if(clause.isTrue(inst)==1) {
        	clauseVect[i]=1; // mettre a jour vecteur clause
            nbSat++; // mettre a jour nbsat
        	}
        	}
            i++;
        }
        return nbSat;
    }
	public static int CompareClauses(int[] tab,Clause cl){
        int x=0;
        int i=1;
        while(i<tab.length && x!=1){
            if(cl.isTrue(tab[i])==1){
                x=1;
            }
            else i++;
        }
        return x;
    }
	public int compareTo(SAT o) {
		return o.getNbSat()-this.getNbSat();
	}
	public void Affichage(){
        for (int i = 1; i < instVect.length; i++) {
            System.out.print(instVect[i] + "\t");
        }
    }

}
