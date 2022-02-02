package Loja;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FLogin {
    private JTextField textFieldUsername;
    private JButton cancelarButton;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel JPanelLogin;
    public static void main(String[] args)
    {
        JFrame frame=new JFrame("Gestao da Loja");
        frame.setContentPane(new FLogin().JPanelLogin);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public FLogin() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txtUser=textFieldUsername.getText();
                String txtPass=String.valueOf(passwordField.getPassword());
                try
                {
                    Connection con=coneçao.CreateConnection();
                    String sql="Select nome  ,telefone, nomeUtilizador , palavraPasse FROM funcionarios";
                    PreparedStatement ps=con.prepareStatement(sql);
                    ResultSet rs=ps.executeQuery();
                    boolean b =false;
                    while (rs.next())
                    {
                        String nome=rs.getString(1);
                        String nomeUtilizador=rs.getString("nomeUtilizador");
                        String pass=rs.getString("palavraPasse");
                        //JOptionPane.showMessageDialog(null,nome+":"+nomeUtilizador+"."+cc+"."+pass);
                        System.out.printf("%s - %s -%s ",nome ,nomeUtilizador,pass);
                        if(txtUser.equals(nomeUtilizador) &&txtPass.equals(pass))
                        {

                            new FLoja().setVisible(true);

                            b=true;
                        }
                    }
                    if(!b)
                    {
                        JOptionPane.showMessageDialog(null,"Erro! Login incorreto! " +
                                "Password/Username errados");

                    }
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Erro" +ex.getMessage());
                }

            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }
}
