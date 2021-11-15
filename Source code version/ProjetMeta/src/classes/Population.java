package classes;

import java.util.Vector;

public class Population {
    public Vector<SAT> IndvVect = new Vector<SAT>();
    
    public Population(Vector<SAT> vct){
        this.IndvVect=vct;
    }
    public Population() {
		// TODO Auto-generated constructor stub
	}
	public Vector<SAT> getIndvVect() {
        return IndvVect;
    }

    public void setIndvVect(Vector<SAT> IndvVect) {
        this.IndvVect = IndvVect;
    }

    public void Affichage(){
        for (SAT node:IndvVect){
            node.Affichage();
            System.out.print("\t");
        }
    } 
    
    
}
