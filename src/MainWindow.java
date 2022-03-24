//ідеї які можна додати
// 1) кількість балів зароблених за гру (довжина кніцевої змії - 3 = кількість з'їдених яблук)
// 2) + поставити аватрку на програму
// 3) стоврити екзе файл (щоб запускати його без ІнтеледжІдея)
// 4) додати кнопку рестарт вгрі
// 5) збільшити розширення гри (1000х1000 [наприклад])
// 6) додати декілка інших правил гри (червоне яблуко додає змію, зелене забирає змію (або навпаки))
// 7) зробити збільшення швидкості гри (підвищення скалдності)
// 8) перед запуском гри створити кнопки з рівнем скалдності
// 9) перша клітинка змії іншого кольору
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    public static void main (String[] args){
        MainWindow wm = new MainWindow();
        /*
        String [] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String s: fonts){
            System.out.println(s);
        }
         */

    }
    //Image apl;
    public MainWindow(){
        setIconImage(new ImageIcon("apple.png").getImage());
        setVisible(true);
        setResizable(false);
        setTitle("Змея Дани");
        //ImageIcon iiia = new ImageIcon("apple1.png");
        //apl = iiia.getImage();
        //setIconImage(apl);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        //setSize(320, 345);
        //setLocation();
        setBounds(dimension.width/2 - 168, dimension.height/2-179, 351, 374);
        add(new GameField());
    }
}

