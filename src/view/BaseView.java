package view;

import controller.Controller;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;

public class BaseView extends JDialog implements VeiwInterface{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox labelComboBox;
    private JLabel jLabel;
    private JTextField phoneNumberTextField;
    private JTextField descriptionTextField;

    private Controller controller;

    public void setTextPanel(String text){
        this.jLabel.setText( "<html>" + text.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
    }

    public void addItem(String text){
        this.labelComboBox.addItem(text);
    }

    public BaseView(Controller controller) {
        this.controller = controller;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() throws SQLException, ParseException {
        this.controller.addNumber((String) labelComboBox.getSelectedItem(), phoneNumberTextField.getText()
            , descriptionTextField.getText()
        );
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void showFinalAction(){
        this.pack();
        this.setVisible(true);
        System.exit(0);
    }

}
