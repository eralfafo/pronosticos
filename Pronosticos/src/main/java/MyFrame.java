// Java program to implement
// a Simple Registration Form
// using Java Swing

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class MyFrame
        extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel mno;
    private JTextField tmno;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox date;
    private JComboBox month;
    private JComboBox year;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;

    private JButton importarBtn;
    private JButton imprimirBtn;
    private JButton guardarBtn;
    private JButton salirBtn;
    private JButton pronosticarBtn;

    private JTextField filenameField;

    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;

    private ExcelProcessing excelProcessing = new ExcelProcessing();

    private String actions[]
            = { "Promedio Movil", "Promedio Movil doble" };

    // constructor, to initialize the components
    // with default values.
    public MyFrame()
    {
        setTitle("[Nombre de la empresa]");
        setBounds(300, 90, 600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("[Nombre de la empresa]");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(200, 30);
        c.add(title);

        importarBtn = new JButton("Importar Datos");
        importarBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        importarBtn.setSize(150, 20);
        importarBtn.setLocation(50, 100);
        importarBtn.addActionListener(this);
        c.add(importarBtn);

        filenameField = new JTextField("");
        filenameField.setFont(new Font("Arial", Font.PLAIN, 15));
        filenameField.setSize(150, 20);
        filenameField.setLocation(50, 140);
        filenameField.addActionListener(this);
        filenameField.setEditable(false);
        c.add(filenameField);

        dob = new JLabel("Metodo");
        dob.setFont(new Font("Arial", Font.PLAIN, 20));
        dob.setSize(200, 20);
        dob.setLocation(290, 100);
        c.add(dob);

        date = new JComboBox(actions);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(200, 20);
        date.setLocation(370, 100);
        c.add(date);

        pronosticarBtn = new JButton("Pronosticar");
        pronosticarBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        pronosticarBtn.setSize(150, 20);
        pronosticarBtn.setLocation(290, 140);
        pronosticarBtn.addActionListener(this);
        c.add(pronosticarBtn);

        imprimirBtn = new JButton("Imprimir");
        imprimirBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        imprimirBtn.setSize(100, 20);
        imprimirBtn.setLocation(150, 450);
        imprimirBtn.addActionListener(this);
        c.add(imprimirBtn);

        guardarBtn = new JButton("Guardar");
        guardarBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        guardarBtn.setSize(100, 20);
        guardarBtn.setLocation(270, 450);
        guardarBtn.addActionListener(this);
        c.add(guardarBtn);

        salirBtn = new JButton("Salir");
        salirBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        salirBtn.setSize(100, 20);
        salirBtn.setLocation(390, 450);
        salirBtn.addActionListener(this);
        c.add(salirBtn );

        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == pronosticarBtn) {
            try {
                Integer status = excelProcessing.processData();
                if(status == -1){
                    JOptionPane.showMessageDialog (null, "Necesitas seleccionar un archivo antes de ejecutar el pronostico"
                            , "Archivo no seleccionado", JOptionPane.ERROR_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog (null, "Pronostico realizado, ya puede revisar su excel con los resultados"
                            , "Pronostico finalizado", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog (null, "Ocurrio un error con la aplicacion", "Error", JOptionPane.ERROR_MESSAGE);
                try {
                    wait(1000);
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
                System.exit(0);
            }
        }

        else if (e.getSource() == importarBtn) {
            String filename = excelProcessing.searchFile();
            filenameField.setText(filename);
        }

        else if (e.getSource() == salirBtn) {
            System.exit(0);
        }
    }
}
