package Loja;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
public class FLoja {
    private JTextField textFieldNome;
    private JTextField textFieldQuantidade;
    private JButton salvarButton;
    private JButton eliminarButton;
    private JButton atualizarButton;
    private JTextField textFieldId;
    private JButton pesquisarButton;
    private JTextField textFieldPreço;
    private JTextArea textAreaCategoria;
    private JPanel JPanelLoja;
    private JTextField textFieldcategoria;
    private JTextArea textAreaProdutos;
    private JButton listarButton;
    private JTextField textFieldAddCat;
    private JButton adicionarCategoriaButton;
    private JTextField textFieldIdCategoria;
    private JButton atualizarCategoriaButton;
    private JButton buttonListarCategoria;
    private JButton eliminarCategoriaButton;
    private JButton pesquisarCatButton;
    private JButton pesquisarProdutoPorCategoriaButton;
    private JButton consultaProdutosButton;
    private JButton procurarImagemButton;
    private JLabel Label1;
    private JButton limparButton;
    Connection con;
    PreparedStatement pst;
    private String path=null;
    private byte[] UserImage;
    private ResultSet rs;
    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdlojamarco", "root", "1234");
            System.out.println("Success");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void setVisible(boolean b) {
        JFrame frame = new JFrame("Gestao dos Produtos");
        frame.setContentPane(new FLoja().JPanelLoja);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    public FLoja() {
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String nome, preço, quantidade,categoria;
                nome = textFieldNome.getText();
                preço = textFieldPreço.getText();
                quantidade = textFieldQuantidade.getText();
                categoria =textFieldcategoria.getText();
                try {
                    pst = con.prepareStatement("insert into produtos(NomeProdutos,Preco,Quantidade,idCategorias,Foto)" +
                            "values(?,?,?,?,?)");
                    pst.setString(1, nome);
                    pst.setString(2, preço);
                    pst.setString(3, quantidade);
                    pst.setString(4,categoria);
                    pst.setBytes(5,UserImage);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Addedddddd!!!!");
                    textFieldNome.setText("");
                    textFieldPreço.setText("");
                    textFieldQuantidade.setText("");
                    textFieldNome.requestFocus();
                    textAreaProdutos.setText("");
                    textFieldcategoria.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String id,nome,preço,quantidade,categoria;
                nome = textFieldNome.getText();
                preço = textFieldPreço.getText();
                quantidade = textFieldQuantidade.getText();
                categoria=textFieldcategoria.getText();
                id = textFieldId.getText();
                try {
                    pst = con.prepareStatement("update produtos set nomeProdutos = ?,Preco = ?,Quantidade = ?," +
                            "idCategorias = ?,Foto=? where idProdutos = ?");
                    pst.setString(1, nome);
                    pst.setString(2, preço);
                    pst.setString(3, quantidade);
                    pst.setString(4,categoria);
                    pst.setBytes(5,UserImage);
                    pst.setString(6, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    textFieldNome.setText("");
                    textFieldQuantidade.setText("");
                    textFieldPreço.setText("");
                    textFieldNome.requestFocus();
                    textFieldId.setText("");
                    textFieldcategoria.setText("");
                    textAreaProdutos.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        pesquisarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    String pid = textFieldId.getText();
                    pst = con.prepareStatement("select nomeProdutos,Preco,Quantidade,idCategorias,Foto from produtos " +
                            "where idProdutos = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String nome = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String categoria = rs.getString(4);
                        Blob blob = rs.getBlob(5);
                        byte[] imageBytes = blob.getBytes(1, (int)
                                blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).
                                getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
                        Label1.setIcon(imgIcon);
                        textFieldNome.setText(nome);
                        textFieldPreço.setText(preço);
                        textFieldQuantidade.setText(quantidade);
                        textFieldcategoria.setText(categoria);

                    }
                    else
                    {
                        textFieldQuantidade.setText("");
                        textFieldNome.setText("");
                        textFieldPreço.setText("");
                        textFieldcategoria.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String bid;
                bid = textFieldId.getText();
                try {
                    pst = con.prepareStatement("delete from produtos where idProdutos = ?");
                    pst.setString(1, bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    textFieldNome.setText("");
                    textFieldPreço.setText("");
                    textFieldQuantidade.setText("");
                    textFieldcategoria.setText("");
                    textFieldNome.requestFocus();
                    textFieldId.setText("");
                    textAreaProdutos.setText("");

                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    Connection con=coneçao.CreateConnection();
                    String sql="select nomeProdutos,Preco,Quantidade,idCategorias from produtos ";
                    PreparedStatement ps=con.prepareStatement(sql);
                    ResultSet rs=ps.executeQuery();
                    while (rs.next())
                    {
                        String nome = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String categoria = rs.getString(4);
                        textAreaProdutos.append("Nome : "+nome+" Preço :"+preço+" Quantidade : "+quantidade+" Categoria :"+categoria +"\n");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        adicionarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String nomeCat;
                nomeCat = textFieldAddCat.getText();
                try {
                    pst = con.prepareStatement("INSERT INTO categorias (NomeCategorias) VALUES (?)");
                    pst.setString(1, nomeCat);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Record Addedddddd!!!!");
                    textFieldAddCat.setText("");
                    textAreaCategoria.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        atualizarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String nome,id;
                nome = textFieldAddCat.getText();
                id= textFieldIdCategoria.getText();
                try {
                    pst = con.prepareStatement("update categorias set NomeCategorias = ? where idCategorias = ?");
                    pst.setString(1, nome);
                    pst.setString(2, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updateee!!!!!");
                    textFieldAddCat.setText("");
                    textFieldIdCategoria.setText("");
                    textAreaCategoria.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        eliminarCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String bid;
                bid = textFieldIdCategoria.getText();
                try {
                    pst = con.prepareStatement("delete from categorias where idCategorias = ?");
                    pst.setString(1, bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    textAreaCategoria.setText("");
                    textFieldIdCategoria.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        pesquisarCatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    Connect();
                    String pid = textFieldIdCategoria.getText();
                    pst = con.prepareStatement("select NomeCategorias from categorias where idCategorias = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()==true)
                    {
                        String nome = rs.getString(1);
                        textFieldAddCat.setText(nome);
                    }
                    else
                    {
                        textFieldIdCategoria.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        buttonListarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                Connect();
                Connection con=coneçao.CreateConnection();
                String sql="select idCategorias, NomeCategorias from categorias ";
                PreparedStatement ps=con.prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                while (rs.next())
                {
                    String nome = rs.getString(2);
                    String id = rs.getString(1);
                    textAreaCategoria.append("Id: "+id+" Nome :"+nome+"\n");
                }
            }
                catch (SQLException ex)
            {
                ex.printStackTrace();
            }
            }
        });
        pesquisarProdutoPorCategoriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connect();
                    Connection con=coneçao.CreateConnection();
                    String pid = textFieldIdCategoria.getText();
                    String sql="select nomeProdutos,Preco,Quantidade,idCategorias from produtos " +
                            "where idCategorias = ?";
                    PreparedStatement ps=con.prepareStatement(sql);
                    ps.setString(1, pid);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next())
                    {
                        String nome = rs.getString(1);
                        String preço = rs.getString(2);
                        String quantidade = rs.getString(3);
                        String categoria = rs.getString(4);
                        textAreaProdutos.append("Nome : "+nome+" Preço :"+preço+" Quantidade : "
                                +quantidade+" Categoria :"+categoria +"\n");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        procurarImagemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChooser=new JFileChooser();
                imgChooser.showOpenDialog(null);
                File img=imgChooser.getSelectedFile();
                path=img.getAbsolutePath();
                BufferedImage image;
                try
                {
                    image= ImageIO.read(imgChooser.getSelectedFile());
                    ImageIcon imgIcon=new ImageIcon(new ImageIcon(image).getImage().
                            getScaledInstance(250,250, Image.SCALE_DEFAULT));
                    Label1.setIcon(imgIcon);
                    File imgg=new File(path);
                    FileInputStream fs=new FileInputStream(imgg);
                    ByteArrayOutputStream bos=new ByteArrayOutputStream();
                    byte[] buff=new byte[1024];
                    int nBytes_read=0;
                    while ((nBytes_read=fs.read(buff))!=-1) {
                        bos.write(buff, 0, nBytes_read);
                    }
                    UserImage=bos.toByteArray();

                }
                catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        consultaProdutosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FConsulta().setVisible(true);
            }
        });
        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldId.setText("");
                textFieldNome.setText("");
                textAreaCategoria.setText("");
                textAreaProdutos.setText("");
                textFieldIdCategoria.setText("");
                textFieldAddCat.setText("");
                textFieldcategoria.setText("");
                textFieldPreço.setText("");
                textFieldQuantidade.setText("");
                Label1.setIcon(null);


            }
        });
    }
}
