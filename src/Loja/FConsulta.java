package Loja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FConsulta {
    private JTextField TextFieldSalario;
    private JTextField textFieldNome;
    private JTextField textFieldId;
    private JButton buttonAnterior;
    private JButton buttonProximo;
    private JButton buttonUltimo;
    private JButton buttonPrimeiro;
    private JLabel labelFoto;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel JpanelConsulta;
    private Statement st;
    private Connection con;
    private String path=null;
    private byte[] UserImage;
    private ResultSet rs;
    public static void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestao dos Produtos");
        frame.setContentPane(new FConsulta().JpanelConsulta);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public FConsulta() {
        Connection();

        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!rs.isFirst()) {
                        rs.previous();

                        textFieldId.setText(String.valueOf(rs.getInt("idProdutos")));

                        textFieldNome.setText(rs.getString("NomeProdutos"));
                        TextFieldSalario.setText(String.valueOf(rs.getInt("Preco")));
                        textField1.setText(String.valueOf(rs.getInt("Quantidade")));
                        textField2.setText(String.valueOf(rs.getInt("idCategorias")));

                        Blob blob = rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int)
                                blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelFoto.setIcon(imgIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.first();

                    textFieldId.setText(String.valueOf(rs.getInt("idProdutos")));

                    textFieldNome.setText(rs.getString("NomeProdutos"));
                    TextFieldSalario.setText(String.valueOf(rs.getInt("Preco")));
                    textField1.setText(String.valueOf(rs.getInt("Quantidade")));
                    textField2.setText(String.valueOf(rs.getInt("idCategorias")));

                    Blob blob = rs.getBlob("Foto");
                    byte[] imageBytes = blob.getBytes(1, (int)
                            blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    labelFoto.setIcon(imgIcon);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }

        });
        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    rs.last();

                    textFieldId.setText(String.valueOf(rs.getInt("idProdutos")));

                    textFieldNome.setText(rs.getString("NomeProdutos"));
                    TextFieldSalario.setText(String.valueOf(rs.getInt("Preco")));
                    textField1.setText(String.valueOf(rs.getInt("Quantidade")));
                    textField2.setText(String.valueOf(rs.getInt("idCategorias")));

                    Blob blob = rs.getBlob("Foto");
                    byte[] imageBytes = blob.getBytes(1, (int)
                            blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                            getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                    labelFoto.setIcon(imgIcon);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        buttonProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!rs.isLast()) {
                        rs.next();

                        textFieldId.setText(String.valueOf(rs.getInt("idProdutos")));

                        textFieldNome.setText(rs.getString("NomeProdutos"));
                        TextFieldSalario.setText(String.valueOf(rs.getInt("Preco")));
                        textField1.setText(String.valueOf(rs.getInt("Quantidade")));
                        textField2.setText(String.valueOf(rs.getInt("idCategorias")));

                        Blob blob = rs.getBlob("Foto");
                        byte[] imageBytes = blob.getBytes(1, (int)
                                blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT));
                        labelFoto.setIcon(imgIcon);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public void Connection() {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/bdlojamarco",
                    "root","1234");
            st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select idProdutos,NomeProdutos,idCategorias,Quantidade,Preco, Foto from produtos ");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

