package gui;

import classes.*;


import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Panel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;

public class fenetre extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public fenetre(String title) {
    	setForeground(Color.WHITE);
    	setBackground(Color.BLACK);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 760, 549);
        setResizable(false);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ignored) {
        }

        contentPane = new JPanel();
        contentPane.setBackground(Color.decode("#515658"));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 11, 765, 519);
        contentPane.add(tabbedPane);


        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(Color.DARK_GRAY);
        tabbedPane.addTab("Data definition and search method", null, dataPanel, null);
        dataPanel.setLayout(null);

        ClausePanel clausesPanel = new ClausePanel();
        dataPanel.add(clausesPanel);

        JButton importFileButton = new JButton("Import CNF file");
        importFileButton.setBounds(85, 282, 151, 38);
        dataPanel.add(importFileButton);

        JButton startResButton = new JButton("Start resolution");
        startResButton.setEnabled(false);
        startResButton.setBounds(85, 344, 151, 38);
        dataPanel.add(startResButton);

        JLabel selectMethodLabel = new JLabel("Select a SAT resolution method :");
        selectMethodLabel.setForeground(Color.WHITE);
        selectMethodLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        selectMethodLabel.setBounds(370, 11, 271, 16);
        dataPanel.add(selectMethodLabel);

        JComboBox<String> resMethodComboBox = new JComboBox<String>();

        resMethodComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Depth-First Search (DFS)",
                "Breadth-First Search (BFS)", "Heuristic Search (A*) First Heuristic",
                "Heuristic Search (A*) Second Heuristic", "Genetic Algorithm","PSO" }));
        resMethodComboBox.setBounds(370, 38, 237, 25);
        dataPanel.add(resMethodComboBox);

        JLabel informationLabel = new JLabel("Welcome to the SAT solver. Add a CNF Benchmark file");
        informationLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        informationLabel.setForeground(Color.decode("#404040"));
        informationLabel.setBounds(0, 477, 667, 25);
        dataPanel.add(informationLabel);
        JLabel numAttemptsLabel = new JLabel("Number of attempts :");
        numAttemptsLabel.setForeground(Color.WHITE);
        numAttemptsLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        numAttemptsLabel.setBounds(370, 74, 130, 25);
        dataPanel.add(numAttemptsLabel);

        JLabel timeAttemptLabel = new JLabel("Time per attempt (minutes) :");
        timeAttemptLabel.setForeground(Color.WHITE);
        timeAttemptLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timeAttemptLabel.setBounds(370, 110, 182, 25);
        dataPanel.add(timeAttemptLabel);

        JSpinner numAttemptsSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 10000, 10));
        numAttemptsSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        numAttemptsSpinner.setBounds(562, 74, 45, 23);
        dataPanel.add(numAttemptsSpinner);

        JSpinner timeAttemptSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3600, 1));
        timeAttemptSpinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        timeAttemptSpinner.setBounds(562, 108, 45, 25);
        dataPanel.add(timeAttemptSpinner);

        Panel panel_1 = new Panel();
        panel_1.setBounds(370, 141, 378, 129);
        dataPanel.add(panel_1);
        panel_1.setLayout(null);

        JLabel initpopsizeLabel = new JLabel("Initialise Population Size :");
        initpopsizeLabel.setForeground(Color.WHITE);
        initpopsizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        initpopsizeLabel.setBounds(0, 11, 151, 18);
        panel_1.add(initpopsizeLabel);

        JSpinner initpopsizespinner = new JSpinner(new SpinnerNumberModel(500, 50, 3600, 20));
        initpopsizespinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        initpopsizespinner.setBounds(193, 9, 44, 20);
        panel_1.add(initpopsizespinner);

        JLabel lblNewLabel_1 = new JLabel("Percentage of success (%) :");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_1.setBounds(0, 40, 192, 19);
        panel_1.add(lblNewLabel_1);

        JSpinner Percentagespinner_1 = new JSpinner(new SpinnerNumberModel(100, 1, 100, 2));
        Percentagespinner_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Percentagespinner_1.setBounds(193, 40, 44, 25);
        panel_1.add(Percentagespinner_1);

        JLabel lblNewLabel = new JLabel("Number of Bits to mutate :");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel.setBounds(0, 73, 172, 19);
        panel_1.add(lblNewLabel);

        

        JSpinner NbBitsspinner = new JSpinner(new SpinnerNumberModel(2, 1, 500, 2));
        NbBitsspinner.setFont(new Font("Tahoma", Font.PLAIN, 14));
        NbBitsspinner.setBounds(193, 70, 44, 25);
        panel_1.add(NbBitsspinner);
          
          JLayeredPane layeredPane = new JLayeredPane();
          layeredPane.setBounds(0, 0, 378, 129);
          panel_1.add(layeredPane);
          
          JLabel lblNewLabel_2 = new JLabel("Intialize Swarm size: ");
          lblNewLabel_2.setForeground(Color.WHITE);
          lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
          lblNewLabel_2.setBounds(0, 11, 131, 14);
          layeredPane.add(lblNewLabel_2);
          
          JLabel lblNewLabel_3 = new JLabel("Intialize c1: ");
          lblNewLabel_3.setForeground(Color.WHITE);
          lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
          lblNewLabel_3.setBounds(0, 37, 113, 14);
          layeredPane.add(lblNewLabel_3);
          
          JLabel lblNewLabel_4 = new JLabel("Initialize c2: ");
          lblNewLabel_4.setForeground(Color.WHITE);
          lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
          lblNewLabel_4.setBounds(0, 64, 78, 14);
          layeredPane.add(lblNewLabel_4);
          
          JLabel lblNewLabel_5 = new JLabel("Initialize weight: ");
          lblNewLabel_5.setForeground(Color.WHITE);
          lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
          lblNewLabel_5.setBounds(0, 89, 131, 17);
          layeredPane.add(lblNewLabel_5);
          
          JSpinner swarmsize = new JSpinner(new SpinnerNumberModel(1000, 100, 1000000, 500));
          swarmsize.setFont(new Font("Tahoma", Font.PLAIN, 14));
          swarmsize.setBounds(175, 8, 63, 20);
          layeredPane.add(swarmsize);
           
           JSpinner const2 = new JSpinner(new SpinnerNumberModel(2, 0, 2, 1));
           const2.setFont(new Font("Tahoma", Font.PLAIN, 14));
           const2.setBounds(188, 60, 50, 23);
           layeredPane.add(const2);
           
           JSpinner weight = new JSpinner(new SpinnerNumberModel(200, 1, 3600, 10));
           weight.setFont(new Font("Tahoma", Font.PLAIN, 14));
           weight.setBounds(188, 87, 50, 20);
           layeredPane.add(weight);
           
            
            JSpinner const1 = new JSpinner(new SpinnerNumberModel(2, 0, 2, 1));
            const1.setBounds(188, 33, 50, 24);
            layeredPane.add(const1);
            const1.setFont(new Font("Tahoma", Font.PLAIN, 14));
            const1.setVisible(false);
        
        layeredPane.setVisible(false);
        swarmsize.setVisible(false);
        weight.setVisible(false);
        const2.setVisible(false);
        lblNewLabel_2.setVisible(false);
        lblNewLabel_4.setVisible(false);
        lblNewLabel_4.setVisible(false);
        lblNewLabel_5.setVisible(false);

        NbBitsspinner.setVisible(false);
        lblNewLabel.setVisible(false);
        Percentagespinner_1.setVisible(false);
        lblNewLabel_1.setVisible(false);
        initpopsizespinner.setVisible(false);
        initpopsizeLabel.setVisible(false);

        ResultPanel resultPanel = new ResultPanel();
        tabbedPane.addTab("Results and statistics", null, resultPanel, null);
        tabbedPane.setEnabledAt(1, false);

        importFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Conjunctive Normal Form (.cnf)", "cnf"));
            fileChooser.showOpenDialog(null);

            try {
                informationLabel.setText(clausesPanel.loadClausesSet(fileChooser.getSelectedFile().getAbsolutePath()));
                startResButton.setEnabled(true);
            } catch (NullPointerException ignore) {
            }
        });

        resMethodComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch (resMethodComboBox.getSelectedIndex()) {
               
                case 0 & 1 & 2:
                panel_1.setVisible(false);
                    NbBitsspinner.setVisible(false);
                    lblNewLabel.setVisible(false);
                    Percentagespinner_1.setVisible(false);
                    lblNewLabel_1.setVisible(false);
                    initpopsizespinner.setVisible(false);
                    initpopsizeLabel.setVisible(false);
                    
                	layeredPane.setVisible(false);
                    swarmsize.setVisible(false);
                    weight.setVisible(false);
                    const1.setVisible(false);
                    const2.setVisible(false);
                    lblNewLabel_2.setVisible(false);
                    lblNewLabel_4.setVisible(false);
                    lblNewLabel_4.setVisible(false);
                    lblNewLabel_5.setVisible(false);
                    
                    panel_1.setVisible(false);
                    NbBitsspinner.setVisible(false);
                    lblNewLabel.setVisible(false);
                    Percentagespinner_1.setVisible(false);
                    lblNewLabel_1.setVisible(false);
                    initpopsizespinner.setVisible(false);
                    initpopsizeLabel.setVisible(false);
                    break;
                case 4:
                        NbBitsspinner.setVisible(true);
                        lblNewLabel.setVisible(true);
                        Percentagespinner_1.setVisible(true);
                        lblNewLabel_1.setVisible(true);
                        initpopsizespinner.setVisible(true);
                        initpopsizeLabel.setVisible(true);
                        
                        layeredPane.setVisible(false);
                        swarmsize.setVisible(false);
                        weight.setVisible(false);
                        const1.setVisible(false);
                        const2.setVisible(false);
                        lblNewLabel_2.setVisible(false);
                        lblNewLabel_4.setVisible(false);
                        lblNewLabel_4.setVisible(false);
                        lblNewLabel_5.setVisible(false);
                        break;
                    case 5:
                    	NbBitsspinner.setVisible(false);
                        lblNewLabel.setVisible(false);
                        Percentagespinner_1.setVisible(false);
                        lblNewLabel_1.setVisible(false);
                        initpopsizespinner.setVisible(false);
                        initpopsizeLabel.setVisible(false);
                    	 
                        layeredPane.setVisible(true);
                         swarmsize.setVisible(true);
                         weight.setVisible(true);
                         const1.setVisible(true);
                         const2.setVisible(true);
                         lblNewLabel_2.setVisible(true);
                         lblNewLabel_4.setVisible(true);
                         lblNewLabel_4.setVisible(true);
                         lblNewLabel_5.setVisible(true);
                         break;
                        
                }
            }
        });
        startResButton.addActionListener(e -> {
            Vector<Clause> clset = clausesPanel.getClauses();

            
            switch (resMethodComboBox.getSelectedIndex()) {
                case 0:
                    informationLabel.setText("SAT instance resolved using \"Depth-First Search (DFS)\"");
                    DFS dfs = new DFS();
                    long timeAttempt = Long.parseLong(timeAttemptSpinner.getValue().toString());

                    int[] clauseVect = new int[clausesPanel.getNbClauses()];
                    for (int i = 0; i < clausesPanel.getNbClauses(); i++) {
                        clauseVect[i] = 0;
                    }

                    // intialisation vecteur instance
                    int[] instVect = new int[clausesPanel.getnbvar() + 1];
                    for (int i = 1; i < clausesPanel.getnbvar() + 1; i++) {
                        instVect[i] = 0;
                    }

                    Vector<SAT> ouvert = new Vector<SAT>();

                    SAT sat = new SAT(clauseVect, instVect);
                    int[] nbsat = new int[1];
                    timeAttempt = TimeUnit.MINUTES.toMillis(timeAttempt);

                    Node root = new Node(sat, null, null);
                    for (int i = 0; i < Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
                        dfs.search(root, clset, ouvert, nbsat, timeAttempt, System.currentTimeMillis());
                        System.out.print(root.getContent().getNbSat());
                    }
                    resultPanel.loadSolution(nbsat[0], clausesPanel.getNbClauses());
                    break;

                case 1:
                    timeAttempt = Long.parseLong(timeAttemptSpinner.getValue().toString());
                    timeAttempt = TimeUnit.MINUTES.toMillis(timeAttempt);
                    clauseVect = new int[clausesPanel.getNbClauses()];
                    for (int i = 0; i < clausesPanel.getNbClauses(); i++) {
                        clauseVect[i] = 0;
                    }
                    instVect = new int[clausesPanel.getnbvar() + 1];
                    for (int i = 1; i < clausesPanel.getnbvar() + 1; i++) {
                        instVect[i] = 0;
                    }
                    SAT satt = new SAT(clauseVect, instVect);

                    // creating openQ queue with root on it
                    Node rooot = new Node(satt, null, null);
                    Vector<Node> openQ = new Vector<>();
                    openQ.add(rooot);

                    // bfs algorithm
                    BFS bfs = new BFS();
                    int maxSat = 0;
                    for (int i = 0; i < Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
                        maxSat = bfs.searchBFS(openQ, clset, timeAttempt);
                    }
                    resultPanel.loadSolution(maxSat, clausesPanel.getNbClauses());

                    break;
                case 2:
                    informationLabel.setText("SAT instance resolved using A Star Method");
                    NodeA Sol = Astar.star(2, clausesPanel.getnbvar(), clset);
                    for (int i = 0; i < Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
                        Sol = Astar.star(1, clausesPanel.getnbvar(), clset);
                    }
                    resultPanel.loadSolutionA(Sol, clausesPanel.getNumberClausespar(), clausesPanel.getNbClauses());

                    break;
                case 3:

                    informationLabel.setText("SAT instance resolved using A Star Method");
                    Sol = Astar.star(2, clausesPanel.getnbvar(), clset);
                    for (int i = 0; i < Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
                        Sol = Astar.star(2, clausesPanel.getnbvar() - 1, clset);
                    }
                    resultPanel.loadSolutionA(Sol, clausesPanel.getNumberClausespar(), clausesPanel.getNbClauses());

                    break;
                case 4:

                    int SizePop = Integer.parseInt(initpopsizespinner.getValue().toString());
                    int NumberAttempts = Integer.parseInt(numAttemptsSpinner.getValue().toString());
                    int Percentage = Integer.parseInt(Percentagespinner_1.getValue().toString());
                    int nbMut = Integer.parseInt(NbBitsspinner.getValue().toString());
                    GA ga = new GA();
                    int genNum = 1, k, staticGB = 500;
                    Population pop, temppop = new Population(new Vector<>()), childs = new Population(new Vector<>());

                    pop = ga.GenSol(clset, clausesPanel.getnbvar(), SizePop, clausesPanel.getNbClauses());
                    int goalSat = (int) (clausesPanel.getNbClauses() * (float) (Percentage / 100));
                    ga.Fitness(pop, clset);
                    Collections.sort(pop.getIndvVect(), Collections.reverseOrder());
                    SAT bestSolPop = pop.getIndvVect().lastElement();
                    int bestSolC = bestSolPop.getNbSat();

                    // Startinng gen Algorithm
                    while (bestSolPop.getNbSat() < goalSat && genNum < NumberAttempts) {
                        genNum++;

                        k = SizePop;
                        while (k > (SizePop / 2)) {
                            SAT Parent1 = ga.Selection(pop);
                            SAT Parent2 = ga.Selection(pop);
                            k -= 2;

                            ga.CrossOver(Parent1, Parent2);

                            ga.Mutate(Parent1, nbMut);
                            ga.Mutate(Parent2, nbMut);
                            childs.getIndvVect().add(Parent1);
                            childs.getIndvVect().add(Parent2);
                        }

                        ga.Fitness(childs, clset);
                        Collections.sort(childs.getIndvVect(), Collections.reverseOrder());
                        temppop.getIndvVect()
                                .addAll(pop.getIndvVect().subList(0, (SizePop) / 2 + (((SizePop) / 2) % 2)));// removing
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
                    ;
                    resultPanel.loadSolutionSAT(bestSolPop, clausesPanel.getNumberClausespar(),
                            clausesPanel.getNbClauses());

                    break;
                case 5:
                PSO pso = new PSO();
                int c1, c2, n, vMax,maxIter;
                double w;
                SAT gBest;
                vMax = clausesPanel.getnbvar();
                Vector<Particle> swarm= new Vector<>();
                c1 = Integer.parseInt(const1.getValue().toString());
                c2 = Integer.parseInt(const2.getValue().toString());
                maxIter= Integer.parseInt(numAttemptsSpinner.getValue().toString());
                n = Integer.parseInt(swarmsize.getValue().toString());
                w = Integer.parseInt(weight.getValue().toString());
                swarm = pso.GenSwarm(clset, clausesPanel.getnbvar(), n, clausesPanel.getNbClauses());
                
                //calculatitng fitness for each position of the particle
                for (Particle part : swarm) {
                    pso.FitnessCal(part, clset);
                }
                pso.initpbest(swarm);
                
                //initialing Global best
                gBest=pso.calculation(swarm);
                System.out.println("initial best solution is : ");
                gBest.Affichage();
                System.out.println("its initial nbsat is : "+gBest.getNbSat());
                if(gBest.getNbSat()==clausesPanel.getNbClauses()){
                    System.out.print("On peut pas avoir de meilleure solution , le Gbest actuel est le meilleur");
                }
                int j=0;
                for(j=0;j<maxIter;j++) {
                    //update currentposition, velocity, bestposition of each particle
                    for(Particle part: swarm){
                        pso.updateVelocity(part,w,c1,c2,gBest,vMax);
                        pso.updatePosition(part);
                        pso.FitnessCal(part, clset);
                        pso.updatePbest(part);
                    }
                    //updating gbest : sort swarm, compare swarm[0] with oldGbest
                    if (pso.calculation(swarm).getNbSat()> gBest.getNbSat()) 
                    {
                        gBest.setInstVect(pso.calculation(swarm).getInstVect());
                        gBest.setNbSat(pso.calculation(swarm).getNbSat());
                    }
                    if(gBest.getNbSat()==clausesPanel.getNbClauses()){
                        break;
                    }
                    
                }
                resultPanel.loadSolutionSAT(gBest, clausesPanel.getNumberClausespar(),
                            clausesPanel.getNbClauses());
                	
                	break;
                	
            }
            tabbedPane.setEnabledAt(1, true);

        });

    }
}