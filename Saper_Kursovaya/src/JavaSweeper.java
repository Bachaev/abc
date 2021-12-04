import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.*;
import sweeper.Box;

public class JavaSweeper extends JFrame {

    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;
    public static void main(String [] args){
        new JavaSweeper().setVisible(true);
    }

    private JavaSweeper(){
        game = new Game (COLS, ROWS, BOMBS);
        game.start();
        setImage();
        initLabel();
        initPanel();
        initButton();
        initFrame();
    }
    private void initButton() {
        JPanel myBottomPanel = new JPanel();
        JButton resetButton = new JButton("Заново");
        JButton tableWinners = new JButton("Статистика");
        myBottomPanel.add(tableWinners);
        myBottomPanel.add(resetButton);
        add(myBottomPanel, BorderLayout.SOUTH);
        resetButton.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.start();
                    panel.repaint();
                }
            }
        });

        tableWinners.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.initTable();
                }
            }
        });
    }
    private void initLabel () {
        label = new JLabel ("Welcome!");
        add (label, BorderLayout.SOUTH);
    }
    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x*IMAGE_SIZE,coord.y*IMAGE_SIZE,this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,
                Ranges.getSize().y*IMAGE_SIZE));
        add(panel);
    }

    private String getMessage(){
        switch (game.getState()){
            case PLAYED : return "Okeeey let's go";
            case BOMBED : return "Boom. You Lose";
            case WINNER : return "Congratulations. You Win";
            default : return "Welcome!";
        }
    }

    private void initFrame (){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Saper Kursovaya");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }
    private void setImage ()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name());
    }
    private Image getImage(String name){
        String filename = "img/"+name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon (getClass().getResource(filename));
        return icon.getImage();
    }
}
