package gui;


import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import classes.Clause;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class ClausePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable clausesTable;
    private int nbvariables, nbclauses;
    Vector<Clause> clausevect = new Vector<Clause>();
    int nbclausespar;


    public ClausePanel() {
        setBounds(10, 11, 321, 260);
        setLayout(new BorderLayout(0, 0));

        clausesTable = new JTable();
        clausesTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        clausesTable.setEnabled(false);
        clausesTable.setRowSelectionAllowed(false);
        add(clausesTable, BorderLayout.CENTER);
        add(new JScrollPane(clausesTable));
    }


    public String loadClausesSet(String path) {
        clausevect = LireFichier(path);
        nbclausespar = clausevect.firstElement().getVarVect().length;

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Clause");

        for (int i = 0; i < nbclausespar; i++)
            tableModel.addColumn("Literal " + (i + 1));

        String[] tableRow = new String[nbclausespar + 1];

        for (int i = 0; i < nbclauses; i++) {
            tableRow[0] = String.valueOf(i);

            for (int j = 1; j <= nbclausespar; j++)
                tableRow[j] = String.valueOf(clausevect.elementAt(i).getVarVect()[j - 1]);

            tableModel.addRow(tableRow);
        }

        clausesTable.setModel(tableModel);

        return "SAT instance loaded :  " + nbclauses + "  clauses,  " + nbvariables + "  variables,  " + clausevect.size() + "  variables/clause";
    }


    public Vector<Clause> LireFichier(String path) {
        BufferedReader objReader;
        Vector<Clause> clauses = new Vector<>();

        //Lecture du fichier CNF
        try {
            objReader = new BufferedReader(new FileReader(path));
            String ligne = objReader.readLine();
            String[] ligne1 = ligne.split(" ");
            nbvariables = Integer.parseInt(ligne1[2]);
            nbclauses = Integer.parseInt(ligne1[3]);
            int sizecl;

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
        return clauses;

    }

    public int getNbClauses() {
        return nbclauses;
    }

    public int getnbvar() {
        return nbvariables;
    }

    public int getNumberClausespar() {
        return nbclausespar;
    }

    public static void stringToInt(String[] tab, Clause cl) {
        for (int i = 0; i < tab.length - 1; i++) {
            cl.getVarVect()[i] = Integer.parseInt(tab[i]);
        }
    }

    public Vector<Clause> getClauses() {
        return clausevect;
    }

}
