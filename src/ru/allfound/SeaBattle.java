package ru.allfound;

//import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by vvv on 30.10.15.
 */

public class SeaBattle {
    final static int SIZEGOFRAMEX = 1100;
    final static int SIZEGOFRAMEY = 550;

    final static int SIZESTARTFRAMEX = 600;
    final static int SIZESTARTFRAMEY = 400;

    final static int maxNumShip = 10;
    final static int minNumShip = 3;
    final static int curNumShip = 5;

    static String nameGamer;
    static int numShip = curNumShip;

    private static int counterPC = 0;
    private static int counterGamer = 0;

    static Dialog startDialog = null;

    public static void main(String[] args) {

        startSeaBattle();
        goSeaBattle();

    }


    //Стартовая форма
    static void startSeaBattle(){

        Frame frame = new Frame();
        // Create a modal dialog
        startDialog = new Dialog(frame, "Игра 'Морской бой'", true);
        BoxLayout boxLayout = new BoxLayout(startDialog, BoxLayout.Y_AXIS);
        startDialog.setLayout(boxLayout);
        startDialog.setLocationByPlatform(true);

        startDialog.add(new Label("Доброго времени суток, уважаемый Игрок!"));

        final JTextArea textRule = new JTextArea();
        textRule.setText("Правила игры:\n" +
                        "1) Корабли расстанавливаются случайным образом\n" +
                        "2) Количество кораблей одинаково для всех сторон\n" +
                        "3) Размеры кораблей назначаются случайным образом\n" +
                        "4) Игру начинает пользователь (Игрок)");
        textRule.setEditable(false);
        startDialog.add(textRule);

        //dialog.add( new Label ("Введите ваше имя для удобства общения:"));
        //final JTextField textField = new JTextField();
        //textField.setText("Игрок");
        //dialog.add(textField);

        startDialog.add(new Label("Укажите количество кораблей:"));
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, minNumShip, maxNumShip, curNumShip);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        startDialog.add(slider);

        // Create an OK button
        Button ok = new Button ("OK");
        ok.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                //nameGamer = textField.getText().toString();
                //System.out.println("Имя игрока: " + nameGamer);
                numShip = slider.getValue();
                System.out.println("Количество кораблей: " + numShip);
                // Hide dialog
                startDialog.setVisible(false);
            }
        });
        startDialog.add(ok);

        Button cansel = new Button ("Выход");
        cansel.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                System.exit(0);
            }
        });
        startDialog.add(cansel);

        // Show dialog
        startDialog.pack();
        startDialog.setVisible(true);
    }

    static void goSeaBattle(){

        boolean endGame = true;
        while (endGame) {

            JFrame goFrame = new JFrame("Игра 'Морской бой'");
            JFrame.setDefaultLookAndFeelDecorated(true);
            goFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Insets insets = goFrame.getInsets();
            goFrame.setSize(SIZEGOFRAMEX + insets.left + insets.right,
                SIZEGOFRAMEY + insets.top + insets.bottom);
            goFrame.setLocationByPlatform(true);
            goFrame.setVisible(true);
            final Container pane = goFrame.getContentPane();

            JLabel labelGamer = new JLabel();
            labelGamer.setText("Игровое поле 1 (Игрок):");
            pane.add(labelGamer);
            Dimension size = labelGamer.getPreferredSize();
            labelGamer.setBounds(40, 5, size.width, size.height + 15);

            JLabel labelMessageGamer = new JLabel();
            labelMessageGamer.setText("Сообщения: все корабли расставлены, начинайте игру");
            pane.add(labelMessageGamer);
            size = labelMessageGamer.getPreferredSize();
            labelMessageGamer.setBounds(40, 450, size.width, size.height + 15);

            JLabel labelMessagePC = new JLabel();
            labelMessagePC.setText("Сообщения: корабли расставлены, жду первого удара!");
            pane.add(labelMessagePC);
            size = labelMessagePC.getPreferredSize();
            labelMessagePC.setBounds(600, 450, size.width, size.height + 15);

            JLabel labelGamerShip = new JLabel();
            labelGamerShip.setText("Корабли:");
            pane.add(labelGamerShip);
            size = labelGamerShip.getPreferredSize();
            labelGamerShip.setBounds(450, 25, size.width, size.height + 15);

            JLabel labelPC = new JLabel();
            labelPC.setText("Игровое поле 2 (ПК):");
            pane.add(labelPC);
            size = labelPC.getPreferredSize();
            labelPC.setBounds(580, 5, size.width, size.height + 15);

            JLabel labelPCShip = new JLabel();
            labelPCShip.setText("Корабли:");
            pane.add(labelPCShip);
            size = labelPCShip.getPreferredSize();
            labelPCShip.setBounds(990, 25, size.width, size.height + 15);

            JLabel labelCounter = new JLabel();
            labelGamerShip.setText("Счет: " + counterGamer + ":" + counterPC);
            pane.add(labelGamerShip);
            size = labelGamerShip.getPreferredSize();
            labelGamerShip.setBounds(450, 5, size.width, size.height + 15);

            JLabel labelTemp1 = new JLabel();
            labelTemp1.setText(" ");
            labelTemp1.setVisible(false);
            pane.add(labelTemp1);
            size = labelTemp1.getPreferredSize();
            labelTemp1.setBounds(0, SIZEGOFRAMEY - 50, size.width, size.height + 15);

            //
            SeaMapGUI seaMapGUI_Gamer = new SeaMapGUI(labelMessageGamer, numShip, false);
            seaMapGUI_Gamer.setShipToSeaMap();
            seaMapGUI_Gamer.showMap(pane, 30, 50, true);
            seaMapGUI_Gamer.showShips(pane, 450, 50);

            SeaMapGUI seaMapGUI_PC = new SeaMapGUI(labelMessagePC, numShip, true);
            seaMapGUI_PC.setShipToSeaMap();
            seaMapGUI_PC.showMap(pane, 570, 50, false);
            seaMapGUI_PC.showShips(pane, 990, 50);

            boolean endBattle = true;
            while (endBattle) {
                if (seaMapGUI_PC.toAttack()) {
                    endDialog("Поздравляем, Вы выиграли!");
                    counterPC++;
                    break;
                }

                goFrame.setEnabled(false);
                if (seaMapGUI_Gamer.toAttack()) {
                    endDialog("К сожалению Вы проиграли...");
                    counterGamer++;
                    break;
                }
                goFrame.setEnabled(true);
            }
        //
            startDialog.setVisible(true);

            goFrame.dispose();
        }


    }


    private static void endDialog(String string) {
        Frame frame = new Frame();
        // Create a modal dialog
        final Dialog dialog = new Dialog(frame, "Все...", true);
        BoxLayout boxLayout = new BoxLayout(dialog, BoxLayout.Y_AXIS);
        dialog.setLayout(boxLayout);
        dialog.setLocationByPlatform(true);

        dialog.add( new Label (string));
        dialog.add( new Label ("Хотите продолжить игру?"));

        // Create an OK button
        Button ok = new Button ("Продолжить");
        ok.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {

                // Hide dialog
                //dialog.setVisible(false);
                //close dialog
                dialog.dispose();
            }
        });
        dialog.add( ok );

        Button cansel = new Button ("Выход");
        cansel.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                System.exit(0);
            }
        });
        dialog.add( cansel );

        // Show dialog
        dialog.pack();
        dialog.setVisible(true);
    }

}
