import javax.swing.*;
import java.sql.PreparedStatement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class Agenda {
    //PreparedStatement ps;
    private JTextField textID;
    private JTextField textNOMBRE;
    private JTextField textCELULAR;
    private JTextField textEMAIL;
    private JButton buscar;
    private JButton actualizar;
    private JPanel Panel;
    public Agenda(){
        textNOMBRE.setEnabled(false);
        textCELULAR.setEnabled(false);
        textEMAIL.setEnabled(false);
        actualizar.setEnabled(false);
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection conexion = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/Agencia", "root", "3001a"
                    );
                    Statement s = conexion.createStatement();
                    textID.setText(textID.getText());
                    ResultSet rs = s.executeQuery("SELECT * FROM Ciudadanos WHERE id =" +textID.getText() );

                    while(rs.next()){
                        if(textID.getText().equals(rs.getString(1))){
                            textNOMBRE.setText(rs.getString(2));
                            textEMAIL.setText(rs.getString(4));
                            textCELULAR.setText(rs.getString(3));
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"DATOS NO ENCONTRADOS");
                        }
                    }
                    conexion.close();
                    rs.close();
                    s.close();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                textNOMBRE.setEnabled(true);
                textCELULAR.setEnabled(true);
                textEMAIL.setEnabled(true);
                actualizar.setEnabled(true);
                textID.setEnabled(false);
                buscar.setEnabled(false);
            }
        });

        actualizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement ps;
                try{

                    Class.forName("com.mysql.cj.jdbc.Driver");

                    Connection conexion = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/Agencia", "root", "3001a"
                    );
                    ps = conexion.prepareStatement("UPDATE Ciudadanos SET id = ?,nombre = ?, celular= ?, emagil =? WHERE id = " + textID.getText());
                    ps.setString(1, textID.getText());
                    ps.setString(2, textNOMBRE.getText());
                    ps.setString(3, textCELULAR.getText());
                    ps.setString(4, textEMAIL.getText());

                    int res = ps.executeUpdate();
                    if(res >0){
                        JOptionPane.showMessageDialog(null,"CIUDADANO " + textID.getText() + " ACTUALIZADO");
                    }else{
                        JOptionPane.showMessageDialog(null,"NO GUARDADO");
                    }
                    //limpiartxt();
                    //textID.setText("");
                    //textNOMBRE.setText("");
                    //textCELULAR.setText("");
                    //textCORREO.setText("");
                    conexion.close();//importante!!!!
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args){
        JFrame frame=new JFrame("BUSCAR Y ACTUALIZAR DATOS");
        frame.setContentPane(new Agenda().Panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setBounds(0,0,400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
