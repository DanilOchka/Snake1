import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320; //розмір ігрового поля
    private final int DOT_SIZE = 16; //розмір однієї частини змії або розмір яблучка
    private final int ALL_DOTS = 400; // загальна кількість можливих яблучок (тобто кількість клітинок поля з розмірами 16х16)
    private Image dot; // зображення змії
    private Image dot0; // зображення голови змії
    private Image apple; //зображення яблука
    private int appleX; //місцезнаходження яблука Х
    private int appleY; //місцезнаходження яблука Y
    private int[] x = new int[ALL_DOTS]; // положення змійки Х
    private int[] y = new int[ALL_DOTS]; // полодення змійки Y
    private int dots; //розмір змійки в даний момент часу
    private Timer timer; //таймер
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new Klaviatyra());
        setFocusable(true);
    }

    public void initGame(){
        dots= 3; //початкова довжина змійки
        for (int i = 0; i < dots; i++){
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20)*DOT_SIZE;
        appleY = new Random().nextInt(20)*DOT_SIZE;
    }

    public void loadImages(){
        //відображення яблука
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        //відображення тіла змії
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        //відображення гголови змії
        ImageIcon iid0 = new ImageIcon("dot0.png");
        dot0 = iid0.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                if (i == 0){
                    g.drawImage(dot0, x[i], y[i], this);
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            for (int x = 0; x < 351; x+=DOT_SIZE) {
                g.setColor(Color.gray);
                g.drawLine(x, 0, x, SIZE*SIZE);
            }
            for (int y = 0; y < 351; y+=DOT_SIZE) {
                g.setColor(Color.gray);
                g.drawLine(0, y, SIZE*SIZE, y);
            }
        }
        else {
            String str =  "Game Over "; //не знаю як створити перенос на наступну строку
            String points = String.format("points: %d", (dots -3));
            Font f = new Font("Comic Sans MS", Font.BOLD, 18 );
            g.setColor(Color.GREEN);
            g.setFont(f);
            g.drawString(str, SIZE/2-40,SIZE/2);
            g.drawString(points, SIZE/2-40,SIZE/2+20);
        }
    }

    public void move(){
        for (int i = dots; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkBoards(){
        for (int i = dots; i >0 ; i--) {
            if (i>4 && x[0] ==  x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if (x[0]>SIZE){
            inGame = false;
        }
        if (x[0]<0){
            inGame = false;
        }
        if (y[0]>SIZE){
            inGame = false;
        }
        if (y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkBoards();
            move();
        }
        repaint();
    }
    //клас для налаштування керування клавіш
    class Klaviatyra extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left= true;
                up= false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                right = false;
                left = false;
                down = true;
            }
        }
    }
}
