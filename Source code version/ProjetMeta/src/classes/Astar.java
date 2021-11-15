package classes;

import java.util.*;

public class Astar {

    public static NodeA star(int heurMode, int nbvar, Vector<Clause> clauses) {
        int dpth = 1;
        int[] instVect = new int[nbvar + 1];
        int[] heurVal = calHeur(clauses, nbvar);
        int[] clauseVect = new int[clauses.size()];
        NodeA ouverttemp;
        Vector<NodeA> Ouvert = new Vector<>();
        Ouvert.add(new NodeA(new SAT(clauseVect, instVect), 0, 0));

        //Intialisation de ouvert pour depth=0
        createSons(Ouvert, clauses, 0, heurMode, heurVal);

        //Boucle principale de recherche
        while ((bestNode(Ouvert).getContent().getNbSat() != clauses.size()) && dpth < nbvar) {
            ouverttemp = bestNode(Ouvert); //Sauvegarder le meilleurs noeud des fils
            Ouvert.clear();
            Ouvert.add(ouverttemp);
            createSons(Ouvert, clauses, dpth, heurMode, heurVal);
            dpth++;
        }

        //Test si la solution � �t� trouv� 
        ouverttemp = bestNode(Ouvert);
        return ouverttemp;
    }

    public static NodeA bestNode(Vector<NodeA> nodeVect) { //Trouve le meilleur noeud d'un vecteur de noeud
    Vector<NodeA> nodeVectTemp = new Vector<NodeA>();
    for (NodeA temp : nodeVect) {
        if (temp.getContent().getNbSat() == maxNbSat(nodeVect)) {
            if (temp.getHeur() == maxHeur(nodeVect)) {
                nodeVectTemp.add(temp);
            }
        }
    }
    if (nodeVectTemp.size() != 0) {
        Random rd = new Random();
        return nodeVectTemp.get(rd.nextInt(nodeVectTemp.size()));

    } else for (NodeA temp : nodeVect) {
        if (temp.getContent().getNbSat() == maxNbSat(nodeVect)) {
            return temp;
        }
    }


    return null;
}

    public static int[] calHeur(Vector<Clause> clauses, int nbvar) { //G�nere le tableau des heuristiques
        int[] heurVal = new int[nbvar + 1];
        for (int i = 1; i < heurVal.length; i++) {
            heurVal[i] = 0;
        }
        for (Clause temp : clauses) {
            for (int i = 0; i < temp.getVarVect().length; i++) {
                heurVal[Math.abs(temp.getVarVect()[i])]++;
            }
        }
        return heurVal;
    }

    public static int maxHeur(Vector<NodeA> nodeVect) { //Trouve l'heuristique la plus grande des noeuds
    int valHeur = 0;
    for (NodeA temp : nodeVect) {
        if (temp.getHeur() > valHeur) {
            valHeur = temp.getHeur();
        }
    }
    return valHeur;
}

    public static int maxNbSat(Vector<NodeA> tab) { //Rend le plus grand nombre de SAT r�aliser des noeuds
        int maxSat = 0;
        for (NodeA temp : tab) {
            if (temp.getContent().getNbSat() > maxSat) {
                maxSat = temp.getContent().getNbSat();
            }
        }
        return maxSat;
    }

    public static void createSons(Vector<NodeA> Ouvert, Vector<Clause> clauses, int dpth, int heurMode, int[] heurVal) {
        //G�nere les fils d'une racine donn�e

        int inst = 1;
        int nbvar = Ouvert.get(0).getContent().getInstVect().length - 1;
        NodeA temp = new NodeA(new SAT(Ouvert.lastElement().getContent().getClauseVect().clone(), Ouvert.lastElement().getContent().getInstVect().clone()), 0, 0);
        NodeA root = Ouvert.lastElement(); //Sauvegarder le p�re
        Ouvert.clear();//Supprimer le p�re car on en a pas besoin
        int[] bestInst = root.getContent().getInstVect().clone(); //Sauvegarder le meilleurs vecteur d'instance

        //Choix de l'heuristique
        if (heurMode == 1) {//Heuristics = nombre d'occuence de la variable
            while (inst < nbvar + 1) { //Remplisage du vecteur ouvert par les noeuds des variables positifs
                temp.getContent().setInstVect(bestInst.clone());
                if (temp.getContent().getInstVect()[inst] == 0) {
                    temp.getContent().getInstVect()[inst] = inst;
                    Ouvert.add(new NodeA(new SAT(root.getContent().getClauseVect().clone(), root.getContent().getInstVect().clone()), 0, 0));
                    Ouvert.lastElement().getContent().setInstVect(temp.getContent().getInstVect().clone());
                    Ouvert.lastElement().setDpth(dpth);
                    Ouvert.lastElement().getContent().numberClauseSAT(inst, clauses);
                    Ouvert.lastElement().setHeur(heurVal[inst]);
                    inst++;
                } else inst++;
            }
            inst = 1;
            while (inst < nbvar + 1) { //Remplisage du vecteur ouvert par les noeuds des variables n�gatifs
                temp.getContent().setInstVect(bestInst.clone());
                if (temp.getContent().getInstVect()[inst] == 0) {
                    temp.getContent().getInstVect()[inst] = -inst;
                    Ouvert.add(new NodeA(new SAT(root.getContent().getClauseVect().clone(), root.getContent().getInstVect().clone()), 0, 0));
                    Ouvert.lastElement().getContent().setInstVect(temp.getContent().getInstVect().clone());
                    Ouvert.lastElement().setDpth(dpth);
                    Ouvert.lastElement().getContent().numberClauseSAT(-inst, clauses);
                    Ouvert.lastElement().setHeur(heurVal[inst]);
                    inst++;
                } else inst++;
            }
        } else if (heurMode == 2) {
            while (inst < nbvar + 1) { //Remplisage du vecteur ouvert par les noeuds des variables positifs
                temp.getContent().setInstVect(bestInst.clone());
                if (temp.getContent().getInstVect()[inst] == 0) {
                    temp.getContent().getInstVect()[inst] = inst;
                    Ouvert.add(new NodeA(new SAT(root.getContent().getClauseVect().clone(), root.getContent().getInstVect().clone()), 0, 0));
                    Ouvert.lastElement().getContent().setInstVect(temp.getContent().getInstVect().clone());
                    Ouvert.lastElement().setDpth(dpth);
                    Ouvert.lastElement().getContent().numberClauseSAT(inst, clauses);
                    Ouvert.lastElement().setHeur(heurVal[inst] + dpth);
                    inst++;
                } else inst++;
            }
            inst = 1;
            while (inst < nbvar + 1) { //Remplisage du vecteur ouvert par les noeuds des variables n�gatifs
                temp.getContent().setInstVect(bestInst.clone());
                if (temp.getContent().getInstVect()[inst] == 0) {
                    temp.getContent().getInstVect()[inst] = -inst;
                    Ouvert.add(new NodeA(new SAT(root.getContent().getClauseVect().clone(), root.getContent().getInstVect().clone()), 0, 0));
                    Ouvert.lastElement().getContent().setInstVect(temp.getContent().getInstVect().clone());
                    Ouvert.lastElement().setDpth(dpth);
                    Ouvert.lastElement().getContent().numberClauseSAT(-inst, clauses);
                    Ouvert.lastElement().setHeur(heurVal[inst] + dpth);
                    inst++;
                } else inst++;
            }
        }
    }
}