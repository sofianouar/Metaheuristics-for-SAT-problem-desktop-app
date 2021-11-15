package classes;

import java.util.Vector;



public class DFS   {
	public void search(Node Racine,Vector<Clause> clVect,Vector<SAT> Ouvert,int[] maxSat,long limit, long startTime) {
		int inst;
		
		if ((System.currentTimeMillis()-startTime)<limit) {

		//Recherche de la valeur non instensier
		inst = Racine.getContent().Randinst();
		//Verifier si toute les variables non pas �t� toute instensier
		if(inst>0) { 
		SAT satFG= new SAT(Racine.getContent().getClauseVect().clone(),Racine.getContent().getInstVect().clone());
		Racine.setFG(new Node(satFG,null,null));
		Racine.getFG().getContent().getInstVect()[inst]=inst;
		if (Racine.getFG().getContent().numberClauseSAT(inst, clVect)==clVect.size())
		{
			Ouvert.add(new SAT(Racine.getFG().getContent().getClauseVect().clone(),Racine.getFG().getContent().getInstVect().clone()));
			for(int i=1;i<Ouvert.lastElement().getInstVect().length;i++){
	            System.out.println(Ouvert.lastElement().getInstVect()[i]+ "\t");
	        }
		}
		if (Racine.getFG().getContent().getNbSat()>maxSat[0]) {
			maxSat[0] = Racine.getFG().getContent().getNbSat();
		}
	    search(Racine.getFG(),clVect,Ouvert,maxSat,limit,startTime);
		SAT satFD= new SAT(Racine.getContent().getClauseVect().clone(),Racine.getContent().getInstVect().clone());
		Racine.setFD(new Node(satFD,null,null));
		Racine.getFD().getContent().getInstVect()[inst]=-inst;
		if (Racine.getFD().getContent().numberClauseSAT(-inst, clVect)==clVect.size())
		{
			Ouvert.add(new SAT(Racine.getFD().getContent().getClauseVect().clone(),Racine.getFD().getContent().getInstVect().clone()));
			for(int i=1;i<Ouvert.lastElement().getInstVect().length;i++){
	            System.out.println(Ouvert.lastElement().getInstVect()[i]+ "\t");
	        }
		}
		if (Racine.getFD().getContent().getNbSat()>maxSat[0]) {
			maxSat[0] = Racine.getFD().getContent().getNbSat();
		}
		search(Racine.getFD(),clVect,Ouvert,maxSat,limit,startTime);
		}
		Racine=null;
		System.gc();
		}
	}
}
