
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class AgenteHeuristica extends JPanel implements Runnable{

    public class PosicaoInimigo {
        private int x;
        private int y;

        public PosicaoInimigo(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    // objetos
    private JFrame tela;
    private Thread t;
    private Node root;

    // constantes
    private static final int X = 0;
    private static final int Y = 1;
    private static final int MAX_X = 800;
    private static final int MIN_X = 0;
    private static final int MAX_Y = 600;
    private static final int MIN_Y = 0;
    private static final int DIAMETRO = 50;


    //variaveis
    private List<PosicaoInimigo> posicaoInimigos = new ArrayList<>();
    private int x = 0;
    private int y = 0;
    private int[][] inimigos;

    public static void main(String[] args){
        new Agente();
    }

    public AgenteHeuristica()
    {
        tela = new JFrame("Agente Heuristica");

        this.setDoubleBuffered(true);
        this.setLayout(null);

        tela.getContentPane().add(this);

        tela.setSize(MAX_X, MAX_Y);
        tela.setVisible(true);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setResizable(false);


        inimigos = new int[MAX_X][MAX_Y];
        t = new Thread(this);
        t.start();

        Random ran = new Random();

        for (int i = 0; i < inimigos.length; i = i + DIAMETRO) {
            for (int j = 0; j < inimigos[i].length; j = j + DIAMETRO) {

                if(ran.nextInt() % 10 == 0) {
                    inimigos[i][j] = 1;
                    posicaoInimigos.add(new PosicaoInimigo(i, j));
                }
                else
                    inimigos[i][j] = 0;
            }
        }

        // acoes 1 esq 2 dir 3 cima 4 baixo
        // Chama busca com Heuristica
    }

    private double distancia(PosicaoInimigo agente, PosicaoInimigo inimigo){
        return Math.sqrt(Math.pow(Math.abs(inimigo.x - agente.x), 2) + Math.pow(Math.abs(inimigo.y - agente.y), 2));
    }

    public void paint(Graphics g){

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getForeground());
        g.setColor(Color.RED);

        g.fillOval(x, y, DIAMETRO, DIAMETRO);
        inimigos[x][y] = 0;

        System.out.println(" x" +x+ " y " +y);

        for (int i = 0; i < inimigos.length; i = i + DIAMETRO) {
            for (int j = 0; j < inimigos[i].length; j = j + DIAMETRO) {
                if(inimigos[i][j] == 1)
                {
                    g.setColor(Color.BLUE);
                    g.fillOval(i, j, DIAMETRO, DIAMETRO);
                }
            }
        }
    }

    public void traversePreOrder(Node node) {
        if (node != null) {
            boolean canWalk = true;
            System.out.println(" " + node.value);
            switch (node.value)
            {
                case 1:
                    if((x - DIAMETRO) < MIN_X) {
                        canWalk = false;
                    }
                    break;
                case 2:
                    if((x + DIAMETRO) == MAX_X) {
                        canWalk = false;
                    }
                    break;
                case 3:
                    if((y - DIAMETRO) < MIN_Y) {
                        canWalk = false;
                    }
                    break;
                case 4:
                    if((y + DIAMETRO) == MAX_Y) {
                        canWalk = false;
                    }
                    break;
            }

            if(canWalk)
                controleAnda(node.value);

            traversePreOrder(node.ac1);
            traversePreOrder(node.ac2);
        }
    }

    void andaDireita()
    {
        x = x + DIAMETRO;
        repaint();

    }

    void andaEsquerda()
    {
        x = x - DIAMETRO;
        repaint();

    }

    void andaCima()
    {
        y = y - DIAMETRO;
        repaint();

    }


    void andaBaixo()
    {
        y = y + DIAMETRO;
        repaint();

    }
    // acoes 1 esq 2 dir 3 cima 4 baixo
    void controleAnda(int op)
    {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (op)
        {
            case 1:
                andaEsquerda();
                break;
            case 2:
                andaDireita();
                break;
            case 3:
                andaCima();
                break;
            case 4:
                andaBaixo();
                break;
        }
    }

    @Override
    public void run() {
        while(true)
        {
            traversePreOrder(root);
        }
    }

}