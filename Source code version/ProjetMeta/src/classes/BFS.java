package classes;

import java.util.Vector;
/*
 * this algorithm inserts a root in openQ queue,
 * creates two sons (nodes), gives each node instance of 1 || -1
 * remove root from openQ,
 * at each node, the algorithm verifies number of clauses sat depending on instance vector
 * and returns max number of clauses sat
 */

public class BFS {
	//creating the tree to explore
	/*
	 * 					root(variable 1)
	 * 			     1					       -1
	 * var2:   2          -2			  2         -2	
	 * var3: 3   -3      3   -3			3   -3      3   -3
	 * 					....
	 */
	

	public int searchBFS(Vector<Node> openQ, Vector<Clause> clauses, long limit) {
		int maxSat=0; 
		int nbvar=openQ.firstElement().getContent().getInstVect().length-1; //initialisation of vector of instance : variables values at the current node
        Vector<int[]> closeQ = new Vector<>(); //creating closed queue  
		int[] solution;//variable not instantiated yet
		Node rout; 
		int inst;
		
		long startTime = System.currentTimeMillis();
         //RESOLVING THE PROBLEM
 		/*
 		 * looking for number of vars not instantiated yet,
 		 *  value of uninstVar returned == var and its index in instVect vector
 		 */

 		while(!openQ.isEmpty() && (System.currentTimeMillis()-startTime)<limit){	
 			rout=openQ.get(0);	
 			inst = rout.getContent().Randinst();

 			if( inst>0) {
 				createSonG(rout);
 				openQ.add(rout.getFG());
		        //FG Start
 				rout.getFG().getContent().getInstVect()[inst]=inst;
 				solution=treatSonG(rout.getFG(),clauses,inst,nbvar);
 				if(rout.getFG().getContent().getNbSat()>maxSat)
 				{
 					maxSat=rout.getFG().getContent().getNbSat();
 				}
		           	if(solution!=null) { //solution found 
		           		closeQ.add(solution);		           		
		           	}
		           	//FG end
		           	else {  	
		           		createSonD(rout); 
		           		openQ.add(rout.getFD());
					    //FD Start
		           		rout.getFD().getContent().getInstVect()[inst]=-inst;
		           		solution=treatSonD(rout.getFD(),clauses,-inst,nbvar);

		           		if(rout.getFD().getContent().getNbSat()>maxSat)
		           		{
		           			maxSat=rout.getFD().getContent().getNbSat();
		           		}
					        if(solution!=null ) { //solution found     
					        	closeQ.add(solution);
					        }	
					    }
					    //FD END 	  
					    openQ.remove(rout); 
					    rout=null;
					    System.gc();

					} 
					else {		
						openQ.remove(rout);
						rout=null;
						System.gc();
					}		
				} 	
				return maxSat;
			}

     				//create FG
			public void createSonG(Node rout) {  
				SAT satFG= new SAT(rout.getContent().getClauseVect().clone(),rout.getContent().getInstVect().clone());    	 
				rout.setFG(new Node(satFG,null,null));
				return;
			}

			public void createSonD(Node rout) { 	 
				SAT satFD= new SAT(rout.getContent().getClauseVect().clone(),rout.getContent().getInstVect().clone());
				rout.setFD(new Node(satFD,null,null));
				return;
			}

			public int[] treatSonG(Node root, Vector<Clause> clauses,int uninstVar,int nbvar)
			{ 
				int nbsat=root.getContent().numberClauseSAT(uninstVar, clauses);

				if (nbsat==clauses.size()){ 
					int[] solution;
					for(int i=1;i<=nbvar;i++) {
						System.out.println(root.getContent().getInstVect()[i]);
					}
					System.out.println("end sol");
					solution=root.getContent().getInstVect();	    	 	

					return solution;
				} 
				else {	    	
					return null;
				}
			}


			public int[] treatSonD(Node root, Vector<Clause> clauses,int uninstVar,int nbvar)
			{   
				int nbsat=root.getContent().numberClauseSAT(uninstVar, clauses);
				if (nbsat==clauses.size()){ 
					int[] solution;
					for(int i=1;i<=nbvar;i++) {
						System.out.println(root.getContent().getInstVect()[i]);
					}
					System.out.println("end sol");
					solution=root.getContent().getInstVect();
					return solution;
				} 
				else {	    		
					return null;	
				}
			}  
		}