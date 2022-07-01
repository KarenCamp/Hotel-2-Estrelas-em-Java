package juntoEMisturado;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import db_nucleo.ConnectionFactory;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import javax.swing.JOptionPane;


public class Reservas extends JFrame {

    private final JPanel contentPane;
    private JTextField Valor;
    private String data_entrada;
    private String data_saida;
    private String forma_pagamento = "Cartão de Crédito";
    private Integer numero_de_reserva;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Reservas frame = new Reservas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Reservas() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Reservas.class.getResource("/imagens/calendario.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 540);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245));
        panel.setBounds(0, 0, 900, 502);
        contentPane.add(panel);
        panel.setLayout(null);

        JDateChooser FechaE = new JDateChooser();
        FechaE.setBounds(88, 166, 235, 33);
        panel.add(FechaE);

        JLabel lblNewLabel_1 = new JLabel("Data de Check In");
        lblNewLabel_1.setBounds(88, 142, 133, 14);
        lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Data de Check Out");
        lblNewLabel_1_1.setBounds(88, 210, 133, 14);
        lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblNewLabel_1_1);

        JDateChooser FechaS = new JDateChooser();
        FechaS.setBounds(88, 234, 235, 33);
        FechaS.getCalendarButton().setBackground(Color.WHITE);
        panel.add(FechaS);

        Valor = new JTextField();
        Valor.setBounds(88, 303, 235, 33);
        Valor.setEnabled(false);
        panel.add(Valor);
        Valor.setColumns(10);
        Valor.setText(" Clique em 'Continuar' para calcularmos :)");

        JLabel lblNewLabel_1_1_1 = new JLabel("Valor da Reserva");
        lblNewLabel_1_1_1.setBounds(88, 278, 133, 14);
        lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblNewLabel_1_1_1);

        JComboBox FormaPago = new JComboBox();
        FormaPago.setBounds(88, 373, 235, 33);
        FormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
        FormaPago.setModel(new DefaultComboBoxModel(new String[]{"Cartão de Crédito", "Cartão de Débito", "Boleto"}));
        FormaPago.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    forma_pagamento = e.getItem().toString();
                }
            }
        });
        panel.add(FormaPago);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Forma de pagamento");
        lblNewLabel_1_1_1_1.setBounds(88, 347, 151, 24);
        lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(lblNewLabel_1_1_1_1);

        JLabel lblNewLabel_4 = new JLabel("Sistema de Reservas");
        lblNewLabel_4.setBounds(108, 93, 199, 42);
        lblNewLabel_4.setForeground(new Color(65, 105, 225));
        lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblNewLabel_4);

        JButton btnReservar = new JButton("Continuar");
        btnReservar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (FechaE.getDate() == null || FechaS.getDate() == null) {
                        WindowCxDialog emptyField = new WindowCxDialog();
                        emptyField.setVisible(true);
                        emptyField.getContentPanel().add(emptyField.setLblMsgConfig("Por gentileza, informe todas as datas.", new Integer[]{12, 138, 199}, 17, new Integer[]{33, 122, 300, 24}));
                } 
                    else if (FechaE.getDate().after(FechaS.getDate())) {
                        WindowCxDialog invalidFields = new WindowCxDialog();
                        invalidFields.setVisible(true);
                        invalidFields.getContentPanel().add(invalidFields.setLblMsgConfig("Formato de datas incorreto.", new Integer[]{12, 138, 199}, 17, new Integer[]{68, 122, 300, 24}));
                } 
                    else if (FechaE.getDate() != null && FechaS.getDate() != null && FechaE.getDate().before(FechaS.getDate())) {
                        data_entrada = ((JTextField) FechaE.getDateEditor().getUiComponent()).getText();
                        data_saida = ((JTextField) FechaS.getDateEditor().getUiComponent()).getText();
                        LocalDate LDFechaE = FechaE.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate LDFechaS = FechaS.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        Period periodo = Period.between(LDFechaE, LDFechaS);
                        Integer dias = periodo.getDays();
                        dias++;
                        Integer diaria = 45;
                        Integer valorEstadia = dias * diaria;
                    
                    String valorAPagar = valorEstadia.toString();
                    Valor.setText("R$ " + valorAPagar);
                    
                    if (periodo.getMonths() > 0) {
                                    Valor.setText("A combinar.");
                                    valorAPagar = "A combinar";
                                }

                                    if (periodo.getYears() > 0) {
                                        Valor.setText("A combinar.");
                                        valorAPagar = "A combinar";
                                    }
                                    
                    Object[] opcao = {"Prosseguir", "Cancelar"};
                    int escolha = JOptionPane.showOptionDialog(rootPane, "Tempo de estadia: " + dias + " dia(s)" + "\nValor: R$ " + valorAPagar + "\nForma de Pagamento: "
                            + forma_pagamento, "Mensagem de confirmação",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE, null, opcao, "Aceitar");
                   
                    if (escolha == JOptionPane.YES_OPTION) {
                        String valor = "R$ " + valorAPagar + ",00";
                        if(valorAPagar.equals("A combinar")) {
                            valor = valorAPagar;
                        }

                        Double nReservaDbl = Math.floor(Math.random()*100000);
                        numero_de_reserva = nReservaDbl.intValue();
                        System.out.print(numero_de_reserva);
                        
                            ConnectionFactory factory = new ConnectionFactory();
                            try (Connection connection = factory.recuperarConexao()) {
                           
                                try (PreparedStatement stmRes = connection.prepareStatement("INSERT INTO RESERVAS (data_entrada, data_saida, valor, forma_pagamento, numero_de_reserva) VALUES (?, ?, ?, ?, ?)",
                                            Statement.RETURN_GENERATED_KEYS);) {

                                    stmRes.setString(1, data_entrada);
                                    stmRes.setString(2, data_saida);
                                    stmRes.setString(3, valor);
                                    stmRes.setString(4, forma_pagamento);
                                    stmRes.setDouble(5, numero_de_reserva);

                                    stmRes.execute(); 

                                        try (ResultSet rstRes = stmRes.getGeneratedKeys();) {

                                                while (rstRes.next()) {
                                                    Integer id = rstRes.getInt(1);
                                                    System.out.println("O id criado foi: " + id);
                                               }
                                        }
                                }
                                
                                RegistroHospede hospede = new RegistroHospede();
                                hospede.setVisible(true);
                                hospede.getTxtNReserva().setText(numero_de_reserva.toString());
                                dispose();
                                
                           } 
                                catch (SQLException ex) {
                                    WindowCxDialog erro = new WindowCxDialog();
                                    erro.setVisible(true);
                                    erro.getContentPanel().add(erro.setLblMsgConfig("Algo errado. Por favor, tente de novo.", new Integer[]{12, 138, 199}, 17, new Integer[]{33, 122, 300, 24}));
                                    ex.printStackTrace();
                                }
                     }
                  } 
                    else {
               }
            }
        });

        btnReservar.setForeground(Color.WHITE);
        btnReservar.setBounds(190, 436, 133, 33);
        btnReservar.setIcon(new ImageIcon(Reservas.class.getResource("/imagens/calendario.png")));
        btnReservar.setBackground(new Color(65, 105, 225));
        btnReservar.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(btnReservar);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(399, 0, 491, 502);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, -16, 500, 539);
        panel_1.add(lblNewLabel);
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setIcon(new ImageIcon(Reservas.class.getResource("/imagens/reservas-img-2.png")));

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Reservas.class.getResource("/imagens/Ha-100px.png")));
        lblNewLabel_2.setBounds(15, 6, 104, 107);
        panel.add(lblNewLabel_2);
    }

    private static void addPopup(Component component, final JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showMenu(e);
                }
            }

            private void showMenu(MouseEvent e) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }
    public Integer getNReserva() {
        return numero_de_reserva;
    }
}
