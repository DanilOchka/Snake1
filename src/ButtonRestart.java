import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//ЩОСЬ НІФІГА НЕ ПАШУТЬ КНОПКИ, ТРЕБА РОЗІБРАТИСЬ

public class ButtonRestart extends JFrame implements ActionListener {
    JButton restart;
    public ButtonRestart(){
        restart = new JButton();
        restart.setBounds(200, 200, 50, 50);
        restart.addActionListener(this);
        this.add(restart);
    }

слава украине
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restart){
            System.out.println("пашет");
        }
    }
}
