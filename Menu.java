package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    private Game game;
    private int number;
    private Menu t2;
    public JTextArea textArea;
    private JPanel jPanel,jPanel2,jPanel3,jPanel4;
    private JButton jButton,jButton2,jButton3;
    private JTextField jTextField;
    private JLabel jLabel,jLabel2,jLabel3;
    private TextField textField,textField2;
    //private Test2 t2;
    private Room r;
    //private int number;

    public Menu(){
        //this.setSize(600,600);
        this.setVisible(true);
        this.setBounds(300,200,400,400);
        this.setLayout(new GridLayout(1,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();

        container.setLocation(30,30);

        JButton jButton = new JButton("Singel Player");
        JButton jButton1 = new JButton("Multi Player");

        //jButton.setBounds(30,30,50,50);
        //jButton1.setBounds(50,50,100,100);

        container.add(jButton);
        container.add(jButton1);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Game game =new Game();
                    game.setTitle("Monster Game");
                    game.setSize(700, 700);
                    game.setLocationRelativeTo(null);  // center the frame
                    game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    game.setVisible(true);
                    //game.play();
                    //getContentPane().add(game);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String limitNumber = JOptionPane.showInputDialog("Input number of players:");
                //int number = Integer.parseInt(limitNumber);
                //t2.setNumber(number);
                //new Room();\
            }
        });
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }



    public static void main(String[] args) {
        new Menu();
    }
}
