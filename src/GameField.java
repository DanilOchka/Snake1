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
    private Image dotUP; // зображення голови змії
    private Image dotDOWN; // зображення голови змії
    private Image dotLEFT; // зображення голови змії
    private Image dotRIGHT; // зображення голови змії
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
    private boolean inGame = false;
    private boolean pause = false; //змінна ПАУЗИ
    private boolean startMenu = true; //змінна початкового меню гри

    private int speed; // ствоерння змінної швидкості (складності гри)
    private boolean speed1 = false;
    private boolean speed2 = false;
    private boolean speed3 = false;
    private boolean [] speedB = {speed1, speed2, speed3};



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
        if (speed1){
            speed = 250;
        }
        if (speed2){
            speed = 175;
        }
        if (speed3){
            speed = 100;
        }
        System.out.println(speed);
        timer = new Timer(speed, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
        //перевірка спавну яблука на тілі змії
        for (int i = 0; i < dots; i++){
            if (appleX == x[i] && appleY == y[i]) {
                //System.out.println("яблоко попало");
                appleX = new Random().nextInt(20) * DOT_SIZE;
                appleY = new Random().nextInt(20) * DOT_SIZE;
            }
        }
    }

    public void loadImages(){
        //відображення яблука
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        //відображення тіла змії
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        //відображення гголови змії
        ImageIcon iidUP = new ImageIcon("dotUP.png");
        dotUP = iidUP.getImage();
        ImageIcon iidDOWN = new ImageIcon("dotDOWN.png");
        dotDOWN = iidDOWN.getImage();
        ImageIcon iidLEFT = new ImageIcon("dotLEFT.png");
        dotLEFT = iidLEFT.getImage();
        ImageIcon iidRIGHT = new ImageIcon("dotRIGHT.png");
        dotRIGHT = iidRIGHT.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(startMenu){
            String helloNiga = "Hello Niga";
            String startMen =  "Press «S» to start";
            String [] sp = {"speed 1",  "speed 2", "speed 3"};
            Font k = new Font("Comic Sans MS", Font.BOLD, 18 );
            g.setColor(Color.GREEN);
            g.setFont(k);

            g.drawString(helloNiga, SIZE/2-40,SIZE/2-20);
            g.drawString(startMen, SIZE/2-75,SIZE/2);

            //ПОГАНО З ПОМИЛКОЮ ВІДМАЛЬВУЮТЬСЯ ОБРАНІ ШВИДКОСТІ, ШВИДКОСТІ ОБИРАЮТЬСЯ НЕЗРОЗУМІЛО
            for (int i = 0; i < 3; i++){
                if (speedB[i] == true){
                    g.setColor(Color.RED);
                    g.drawString(sp[i], SIZE/2-75,SIZE/2+((i+1)*20));
                }
                else if (speedB[i] == false){
                    g.setColor(Color.GREEN);
                    g.drawString(sp[i], SIZE/2-75,SIZE/2+((i+1)*20));
                }
            }

        }
        else if(inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
               //логіка зображення голови змії з реагуванням на напрямок (в яку сторону йде змія в ту ж сторону дивляться очі)
               if (i == 0){
                    if (up){
                        g.drawImage(dotUP, x[i], y[i], this);
                    }
                    else if (down){
                        g.drawImage(dotDOWN, x[i], y[i], this);
                    }
                    else if (left){
                        g.drawImage(dotLEFT, x[i], y[i], this);
                    }
                    else {
                        g.drawImage(dotRIGHT, x[i], y[i], this);
                    }
                }
                else{
                    g.drawImage(dot, x[i], y[i], this);
                }
            }
            //зображення стіки поля
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
            String  restart = "to restart, press «R»";
            Font f = new Font("Comic Sans MS", Font.BOLD, 18 );
            g.setColor(Color.GREEN);
            g.setFont(f);
            g.drawString(str, SIZE/2-40,SIZE/2);
            g.drawString(points, SIZE/2-40,SIZE/2+20);
            g.drawString(restart, SIZE/2-85,SIZE/2+40);

        }

        /*
        Попітка отрісовкі Дургого изображения во время паузі
        ТУТ ТРЕБА ВИВЧИТИ МНОГОПОТОЧНОСТЬ
        https://ru.stackoverflow.com/questions/1119495/%D0%A0%D0%B0%D0%B1%D0%BE%D1%82%D0%B0-%D1%81-%D0%BF%D0%BE%D1%82%D0%BE%D0%BA%D0%BE%D0%BC-%D0%B2-swing-java

        System.out.println(pause);

        if (pause == true){
            String paus =  "Pause ";
            Font k = new Font("Comic Sans MS", Font.BOLD, 18 );
            g.setColor(Color.GREEN);
            g.setFont(k);
            g.drawString(paus, SIZE/2-40,SIZE/2);
        }
         */
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
                timer.stop();
            }
        }
        if (x[0]>SIZE){
            inGame = false;
            timer.stop();
        }
        if (x[0]<0){
            inGame = false;
            timer.stop();
        }
        if (y[0]>SIZE){
            inGame = false;
            timer.stop();
        }
        if (y[0]<0){
            inGame = false;
            timer.stop();
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
            //стврення клавіші пауза
            if (key == KeyEvent.VK_SPACE){
                if (pause == false) {
                    timer.stop();
                    pause = true;
                } else {
                    timer.start();
                    pause = false;
                }
            }
            if (key == KeyEvent.VK_S){
                startMenu = false;
                inGame = true;
            }
            if (key == KeyEvent.VK_R){
                startMenu = true;

                //щоб змія не запамятовувала свій минулий напрямок, сидуємого його до початкового
                right = true;
                up = false;
                down = false;
                left =false;

                initGame(); //заново запускаємо гру
            }
            if (key == KeyEvent.VK_Q){
                speed1 = true;
                speed2 = false;
                speed3 = false;
                System.out.println("1"+ speed1);
            }
            if (key == KeyEvent.VK_W){
                speed1 = false;
                speed2 = true;
                speed3 = false;
                System.out.println("2"+ speed2);

            }
            if (key == KeyEvent.VK_E){
                speed1 = false;
                speed2 = false;
                speed3 = true;
                System.out.println("3"+ speed3);
            }

        }
    }
}
