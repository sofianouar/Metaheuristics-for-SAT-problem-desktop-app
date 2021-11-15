package gui;

import classes.NodeA;
import classes.SAT;

import java.awt.BorderLayout;


import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ResultPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable solutiontable;

    /**
     * Create the panel.
     */
    public ResultPanel() {
        setBounds(10, 11, 321, 260);
        setLayout(new BorderLayout(0, 0));

        solutiontable = new JTable();
        solutiontable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        solutiontable.setEnabled(false);
        solutiontable.setRowSelectionAllowed(false);
        add(solutiontable, BorderLayout.CENTER);
        add(new JScrollPane(solutiontable));
    }

    public void loadSolution(int solution,int nbclauses) {
    	DefaultTableModel tableModel = new DefaultTableModel();


        tableModel.addColumn("Nombre Clauses Satisfaites");
        tableModel.addColumn("Pourcentage de réussite");
        String[] tableRow = new String[2];
        tableRow[0] = String.valueOf(solution);
        tableRow[1] =String.valueOf((solution*100)/nbclauses);
        tableModel.addRow(tableRow);
        solutiontable.setModel(tableModel);

    }

    public void loadSolutionA(NodeA solution, int nbclausespar,int nbclauses) {
        DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.addColumn("    ");

        for (int i = 0; i < nbclausespar; i++)
            tableModel.addColumn("  ");

        String[] tableRow = new String[4];

        /*for (int j = 0; j < solution.getContent().getInstVect().length; j++) {
            tableRow[j] = String.valueOf(solution.getContent().getInstVect()[j]);
        }

        tableModel.addRow(tableRow);*/
        int k = 0;
        int i=0;

        for ( i = 0; i < solution.getContent().getInstVect().length/3; i++) {
            tableRow[0] = String.valueOf(i);

           System.out.println(k);
            for (int j = 1; j <= nbclausespar; j++) {
                tableRow[j] = String.valueOf(solution.getContent().getInstVect()[k+1]);
                k++;
            }
            tableModel.addRow(tableRow);
        }
        System.out.print(solution.getContent().getNbSat());
        System.out.print("\n");
        System.out.print((solution.getContent().getNbSat()*100)/ nbclauses);
    
        solutiontable.setModel(tableModel);
    }
    public void loadSolutionSAT(SAT solution, int nbclausespar,int nbclauses) {
        DefaultTableModel tableModel = new DefaultTableModel();

        

        for (int i = 0; i < nbclausespar; i++)
            tableModel.addColumn("  ");
        String[] tableRow = new String[3];

        /*for (int j = 0; j < solution.getContent().getInstVect().length; j++) {
            tableRow[j] = String.valueOf(solution.getContent().getInstVect()[j]);
        }

        tableModel.addRow(tableRow);*/
        int k = 0;
        int i=0;

        for ( i = 0; i < solution.getInstVect().length/3 ; i++) {

            for (int j =0; j < nbclausespar; j++) {
                tableRow[j] = String.valueOf(solution.getInstVect()[k+1]);
                k++;
            }
            tableModel.addRow(tableRow);
        }
        System.out.print(solution.getNbSat());
        System.out.print("\n");
        System.out.print((solution.getNbSat()*100)/ nbclauses);
    
        solutiontable.setModel(tableModel);
    }
    public void clearData() {
		solutiontable.clearSelection();
	}

}
