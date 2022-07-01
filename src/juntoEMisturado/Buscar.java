package juntoEMisturado;

import db_nucleo.ConnectionFactory;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class Buscar extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTable tbHospedes;
 
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Buscar frame = new Buscar();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Buscar() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Buscar.class.getResource("/imagens/lupa2.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 516);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(647, 85, 158, 31);
        contentPane.add(txtBuscar);
        txtBuscar.setColumns(10);

        JButton btnBuscar = new JButton("");
        btnBuscar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              Object [] opcao ={"HÓSPEDES","RESERVAS"};
              int escolha = JOptionPane.showOptionDialog(rootPane,"Clique na opção de tabela desejada, um arquivo de texto será \nbaixado para o seu sistema, caso a operação não falhe por \nalgum motivo.","Requisição",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.INFORMATION_MESSAGE,null,opcao,"Aceitar");
              if (escolha == JOptionPane.YES_OPTION) {
	requisicao("nome", "sobrenome", "data_nascimento", "nacionalidade", "telefone", "numero_de_reserva", "hospedes");
                }
              else {
                        requisicao("data_entrada", "data_saida", "valor", "forma_pagamento", "id", "numero_de_reserva", "reservas"); 
              }
          } 
      });

        btnBuscar.setBackground(Color.WHITE);
        btnBuscar.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/lupa2.png")));
        btnBuscar.setBounds(815, 75, 54, 41);
        contentPane.add(btnBuscar);

        JButton btnEditar = new JButton("");
        btnEditar.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/editar-texto.png")));
        btnEditar.setBackground(SystemColor.menu);
        btnEditar.setBounds(587, 416, 54, 41);
        contentPane.add(btnEditar);

        JLabel lblNewLabel_4 = new JLabel("Sistema de Busca");
        lblNewLabel_4.setForeground(new Color(12, 138, 199));
        lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
        lblNewLabel_4.setBounds(155, 42, 212, 42);
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Tabela temporariamente indisponível, clique na lupa para obter a listagem simplificada.");
        lblNewLabel_5.setForeground(new Color(204, 58, 41));
        lblNewLabel_5.setFont(new Font("Arial", Font.BOLD, 16));
        lblNewLabel_5.setBounds(225, 122, 657, 42); //x,y,w,h
        contentPane.add(lblNewLabel_5);

        JButton btnSair = new JButton("");
        btnSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MenuUsuario usuario = new MenuUsuario();
                usuario.setVisible(true);
                dispose();
            }
        });

        btnSair.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/encerrar-sessao-32-px.png")));
        btnSair.setForeground(Color.WHITE);
        btnSair.setBackground(Color.WHITE);
        btnSair.setBounds(815, 416, 54, 41);
        contentPane.add(btnSair);

        JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
        panel.setBounds(10, 127, 874, 265);
        contentPane.add(panel);

        tbHospedes = new JTable();
        tbHospedes.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.addTab("Hóspedes", new ImageIcon(Buscar.class.getResource("/imagens/pessoa.png")), tbHospedes, null);

        JTable tbReservas = new JTable();
        tbReservas.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.addTab("Reservas", new ImageIcon(Buscar.class.getResource("/imagens/calendario.png")), tbReservas, null);

        JButton btnDelete = new JButton("");
        btnDelete.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/deletar.png")));
        btnDelete.setBackground(SystemColor.menu);
        btnDelete.setBounds(651, 416, 54, 41);
        contentPane.add(btnDelete);

        JButton btnCancelar = new JButton("");
        btnCancelar.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/cancelar.png")));
        btnCancelar.setBackground(SystemColor.menu);
        btnCancelar.setBounds(713, 416, 54, 41);
        contentPane.add(btnCancelar);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Buscar.class.getResource("/imagens/Ha-100px.png")));
        lblNewLabel_2.setBounds(25, 10, 104, 107);
        contentPane.add(lblNewLabel_2);
        setResizable(false);
    }
   
    private void requisicao(String c1, String c2, String c3, String c4, String c5, String c6, String t) {
        String cA = c1;
        String cB = c2;
        String cC = c3;
        String cD = c4;
        String cE = c5;
        String cF = c6;
        String cG = t;
         ConnectionFactory connectionFactory = new ConnectionFactory();
                    try ( Connection connection = connectionFactory.recuperarConexao()) {
                        try ( PreparedStatement stm = connection.prepareStatement("SELECT " + c1+ "," + c2+ "," + c3 + "," + c4+ "," + c5 + "," + c6 + " FROM " + t);) {
                            stm.execute();
                            try ( ResultSet rst = stm.getResultSet();) {
                                try (BufferedWriter bw= new BufferedWriter(new FileWriter("Listagem de " + t + ".txt"))) {
                                        while (rst.next()) {
                                            c1 = c1.toUpperCase() + ": " + rst.getString(c1) + "\n";
                                            c2 = c2.toUpperCase() + ": " + rst.getString(c2) + "\n";
                                            c3 = c3.toUpperCase() + ": " + rst.getString(c3) + "\n";
                                            c4 = c4.toUpperCase() + ": " + rst.getString(c4) + "\n";
                                                    if (c5.equals("id")) {
                                                        Integer id = rst.getInt(c5);
                                                        c5 = c5.toUpperCase() + ": " + id.toString() + "\n";
                                                    }
                                                    else {
                                                        c5 = c5.toUpperCase() + ": " + rst.getString(c5) + "\n";
                                                    } 
                                                    
                                            Integer nRes = rst.getInt(c6);
                                            c6 = c6.toUpperCase() + ": " + nRes.toString() + "; ";
                                                    
                                            bw.write(c1);
                                            bw.write(c2);
                                            bw.write(c3);
                                            bw.write(c4);
                                            bw.write(c5);
                                            bw.write(c6);
                                            bw.newLine();
                                            bw.newLine();
                                            c1 = cA;
                                            c2 = cB;
                                            c3 = cC;
                                            c4 = cD;
                                            c5 = cE;
                                            c6 = cF;
                                            t = cG;
                                        } 
                                } 
                            }
                        }
                    } 
                    catch (Exception ex) {
                                WindowCxDialog erroTxtFile = new WindowCxDialog();
                                 erroTxtFile.setVisible(true);
                                 erroTxtFile.getContentPanel().add(erroTxtFile.setLblMsgConfig("Algo errado. Tente mais tarde.", new Integer[]{12, 138, 199}, 17, new Integer[]{63, 122, 300, 24}));
                                 ex.printStackTrace();
                   }                           
    }
}

