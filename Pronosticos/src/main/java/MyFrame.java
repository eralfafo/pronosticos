// Java program to implement
// a Simple Registration Form
// using Java Swing

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class MyFrame
        extends JFrame
        implements ActionListener {

    final String COMPANY_NAME = "DelProfe Barberia y Salon";
    final int DEFAULT_WIDGET_WIDTH = 200;
    final int DEFAULT_WIDGET_HEIGHT = 20;
    final int DEFAULT_WINDOW_WIDTH = 600;
    final int DEFAULT_WINDOW_HEIGHT = 400;
    final int DEFAULT_TITLE_WIDTH = DEFAULT_WINDOW_WIDTH;
    final int DEFAULT_TITLE_HEIGHT = 30;
    final Font DEFAULT_TITLE_FONT = new Font("Arial", Font.PLAIN, 20);
    final Font DEFAULT_LABEL_FONT = new Font("Arial", Font.PLAIN, 15);
    final Font DEFAULT_BUTTON_FONT = DEFAULT_LABEL_FONT;

    String filename = "";


    Dimension size
            = Toolkit.getDefaultToolkit().getScreenSize();
    int screen_width = (int)size.getWidth();
    int screen_height = (int)size.getHeight();

    // Components of the Form
    private Container main_container;
    GridLayout main_layout = new GridLayout(9, 1);
    JPanel file_sel_p = new JPanel(new GridLayout(1, 3));
    JPanel title_p = new JPanel(new GridLayout(1, 1));
    JPanel method_sel_p = new JPanel(new GridLayout(1, 2));
    JPanel pronosticar_btns_p = new JPanel(new GridLayout(1, 1));
    JPanel save_btns_p = new JPanel(new GridLayout(1, 3));
    JPanel blank_p = new JPanel();
    JPanel blank_p1 = new JPanel();
    JPanel blank_p2 = new JPanel();
    JPanel blank_p3 = new JPanel();
    private JLabel title;

    private JLabel dob;
    private JComboBox date;


    private JButton importarBtn;
    private JButton imprimirBtn;
    private JButton guardarBtn;
    private JButton salirBtn;
    private JButton pronosticarBtn;

    private JTextField filenameField;

    private ExcelProcessing excelProcessing = new ExcelProcessing();

    private String actions[]
            = {"Promedio Movil", "Promedio Movil doble"};

    // constructor, to initialize the components
    // with default values.
    public MyFrame() {
        setTitle(COMPANY_NAME);
        setBounds((screen_width/2) - (DEFAULT_WINDOW_WIDTH /2), (screen_height/2) - (DEFAULT_WINDOW_HEIGHT /2),
                DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        try {
            ImageIcon imageIcon = new ImageIcon("background.png"); // load the image to a imageIcon
            Image image = imageIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newimg);
            setContentPane(new JLabel(imageIcon));
        } catch (Exception e){

        }


        main_container = getContentPane();
        main_container.setLayout(main_layout);
        file_sel_p.setBackground(Color.BLACK);

        title = new JLabel(COMPANY_NAME, SwingConstants.CENTER);
        title.setFont(DEFAULT_TITLE_FONT);
        title.setSize(DEFAULT_TITLE_WIDTH, DEFAULT_TITLE_HEIGHT);
        title_p.add(title);

        importarBtn = new JButton("Importar Datos");
        importarBtn.setFont(DEFAULT_BUTTON_FONT);
        importarBtn.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        importarBtn.addActionListener(this);
        importarBtn.setBackground(Color.WHITE);
        file_sel_p.add(importarBtn);

        filenameField = new JTextField("");
        filenameField.setFont(new Font("Arial", Font.PLAIN, 15));
        filenameField.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        filenameField.addActionListener(this);
        filenameField.setEditable(false);
        file_sel_p.add(filenameField);

        dob = new JLabel("Metodo", SwingConstants.CENTER);
        dob.setFont(DEFAULT_LABEL_FONT);
        dob.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        dob.setBackground(Color.WHITE);
        dob.setOpaque(true);
        method_sel_p.add(dob);

        date = new JComboBox(actions);
        date.setFont(DEFAULT_LABEL_FONT);
        date.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        method_sel_p.add(date);

        pronosticarBtn = new JButton("Pronosticar");
        pronosticarBtn.setFont(DEFAULT_LABEL_FONT);
        pronosticarBtn.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        pronosticarBtn.addActionListener(this);
        pronosticarBtn.setBackground(Color.WHITE);
        pronosticar_btns_p.add(pronosticarBtn);

        imprimirBtn = new JButton("Imprimir");
        pronosticarBtn.setFont(DEFAULT_LABEL_FONT);
        pronosticarBtn.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        imprimirBtn.addActionListener(this);
        imprimirBtn.setBackground(Color.WHITE);
        save_btns_p.add(imprimirBtn);

        guardarBtn = new JButton("Guardar");
        pronosticarBtn.setFont(DEFAULT_LABEL_FONT);
        pronosticarBtn.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        guardarBtn.addActionListener(this);
        guardarBtn.setBackground(Color.WHITE);
        save_btns_p.add(guardarBtn);

        salirBtn = new JButton("Salir");
        pronosticarBtn.setFont(DEFAULT_LABEL_FONT);
        pronosticarBtn.setSize(DEFAULT_WIDGET_WIDTH, DEFAULT_WIDGET_HEIGHT);
        salirBtn.addActionListener(this);
        salirBtn.setBackground(Color.WHITE);
        save_btns_p.add(salirBtn);

        title_p.setOpaque(false);
        file_sel_p.setOpaque(false);
        method_sel_p.setOpaque(false);
        pronosticar_btns_p.setOpaque(false);
        save_btns_p.setOpaque(false);
        blank_p.setOpaque(false);
        blank_p1.setOpaque(false);
        blank_p2.setOpaque(false);
        blank_p3.setOpaque(false);

        main_container.add(title_p);
        main_container.add(blank_p);
        main_container.add(file_sel_p);
        main_container.add(blank_p1);
        main_container.add(method_sel_p);
        main_container.add(blank_p2);
        main_container.add(pronosticar_btns_p);
        main_container.add(blank_p3);
        main_container.add(save_btns_p);

        setVisible(true);
    }

    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pronosticarBtn) {
            try {
                Integer status = excelProcessing.processData();
                if (status == -1) {
                    JOptionPane.showMessageDialog(null, "Necesitas seleccionar un archivo antes de ejecutar el pronostico"
                            , "Archivo no seleccionado", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Pronostico realizado, ya puede revisar su excel con los resultados"
                            , "Pronostico finalizado", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error con la aplicacion", "Error", JOptionPane.ERROR_MESSAGE);
                try {
                    wait(1000);
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
                System.exit(0);
            }
        } else if (e.getSource() == importarBtn) {
            filename = excelProcessing.searchFile();
            filenameField.setText(filename);
        } else if (e.getSource() == imprimirBtn) {
            PrintExcel printExcel = new PrintExcel();
            printExcel.printExcel(filename);
        }else if (e.getSource() == salirBtn) {
            System.exit(0);
        }
    }
}
