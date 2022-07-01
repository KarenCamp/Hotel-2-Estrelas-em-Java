package juntoEMisturado;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public  class WindowCxDialog extends JDialog {

   private final JPanel contentPanel = new JPanel();
   private JLabel lblMsgConfig;
 
    public static void main(String[] args) {
        
        try {
            WindowCxDialog dialog = new WindowCxDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WindowCxDialog() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(WindowCxDialog.class.getResource("/imagens/aH-40px.png")));
        setBounds(100, 100, 376, 226);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(SystemColor.control);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        contentPanel.setLayout(null);
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(WindowCxDialog.class.getResource("/imagens/Ha-100px.png")));
        lblNewLabel.setBounds(123, 11, 100, 100);
        contentPanel.add(lblNewLabel);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancelar");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand("Cancelar");
                buttonPane.add(cancelButton);
            }
        }
    }        
    
    public JPanel getContentPanel() {
        return contentPanel;
    }
    
    public JLabel setLblMsgConfig(String result, Integer[] foreground, int font, Integer[] bounds) {
        lblMsgConfig = new JLabel(result);
        lblMsgConfig.setForeground(new Color(foreground[0], foreground[1], foreground[2]));
        lblMsgConfig.setFont(new Font("Arial", Font.BOLD, font));
        lblMsgConfig.setBounds(bounds[0], bounds[1], bounds[2], bounds[3]);
        return lblMsgConfig;
    }
}
