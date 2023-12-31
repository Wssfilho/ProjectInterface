import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
//PROJETO FEITO POR ESTHER REZENDE, VITOR SACRAMENTO & WILSON FILHO;
//Programa constantemente atualizado por Wilson Filho
public class Main extends JFrame {
    JButton btnSalvar, btnCancelar,btnBuscar;
    JLabel lbltitulo, lblinstrutor, lblBuscar;
    JLabel lbliniciodat, lblfimdat, lblListaCursos, lblListaBusca;
    JTextField txttitulo, txtintrutor, txtiniciodat, txtfimdat,txtBusca;
    JList jlistodos, jListbusca;
    DefaultListModel lientes, lbusca;
    ArrayList<Cursos> ltscursos = new ArrayList<Cursos>();

    public void NovoCurso() {
        try {
            String titulo = txttitulo.getText().trim();
            String instrutor = txtintrutor.getText().trim();
            String InDat = txtiniciodat.getText().trim();
            String outDat = txtfimdat.getText().trim();

            if (titulo.isEmpty() || instrutor.isEmpty() || InDat.isEmpty() || outDat.isEmpty()) {
                throw new IllegalArgumentException("Digite todos os campos!");
            }

            // Parse as datas de início e fim
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate iniciodat = LocalDate.parse(txtiniciodat.getText().trim(), formatter);
            LocalDate fimdat = LocalDate.parse(txtfimdat.getText().trim(), formatter);

            LocalDate dataAtual = LocalDate.now();
            if (iniciodat.isBefore(dataAtual)) {
                throw new IllegalArgumentException("A data de início do curso não pode ser anterior a data de hoje.!");
            }

            if(fimdat.isBefore(iniciodat)){
                throw new IllegalArgumentException("A data de fim do curso não pode ser anterior à data de início!");
            }

            Cursos csos = new Cursos(titulo, instrutor, iniciodat, fimdat);
            ltscursos.add(csos);

            JOptionPane.showMessageDialog(rootPane, "Novo curso criado: " + csos.titulo);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(rootPane, "Data inválida!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }

        txttitulo.setText("");
        txtintrutor.setText("");
        txtiniciodat.setText("");
        txtfimdat.setText("");

        AtualizarLista();
    }

    public void AtualizarLista() {
        int i;
        lientes.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (Cursos aux : ltscursos) {
            // DefaultListModel atribuído ao JList
            String datinFormatada = aux.getDatin().format(formatter);
            String datfimFormatada = aux.getDatfim().format(formatter);
            lientes.addElement("Titulo: " + aux.getTitulo() + ", " + "Instrutor: " + aux.getIntrutor() + ", " + "Data de início: " + datinFormatada + ", " + "Data de término: " + datfimFormatada);

        }
    }

    public void ConsultarCursoPorNome(String nome) {
        lbusca.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (ltscursos.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Não há cursos cadastrados.");
            return;
        }
        boolean cursoEncontrado = false;
        for (Cursos curso : ltscursos) {
            if (curso.getTitulo().equalsIgnoreCase(nome)) {
                cursoEncontrado = true;
                String datinFormatada = curso.getDatin().format(formatter);
                String datfimFormatada = curso.getDatfim().format(formatter);
                lbusca.addElement("Titulo: " + curso.getTitulo() + ", " +
                        "Instrutor: " + curso.getIntrutor() + ", " +
                        "Data de início: " + datinFormatada + ", " +
                        "Data de término: " + datfimFormatada);
            }
        } if (!cursoEncontrado) {
            JOptionPane.showMessageDialog(rootPane, "Curso não encontrado.");
        }
    }


    Main() {
        lblListaCursos = new JLabel("Lista de cursos");
        lbltitulo = new JLabel("Titulo do Curso");
        lblListaBusca = new JLabel("Lista de busca");
        txttitulo = new JTextField();
        lblinstrutor = new JLabel("Instrutor");
        txtintrutor = new JTextField();
        lbliniciodat = new JLabel("Data de inicio (dd/mm/yyyy)");
        txtiniciodat = new JTextField();
        lblfimdat = new JLabel("Data de fim (dd/mm/yyyy)");
        txtfimdat = new JTextField();
        lblBuscar = new JLabel("Consulta");
        txtBusca = new JTextField();
        lientes = new DefaultListModel();
        jlistodos = new JList(lientes);
        lbusca = new DefaultListModel();
        jListbusca = new JList(lbusca);

        btnBuscar = new JButton("Buscar");
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        JPanel formulario = new JPanel();
        formulario.setBackground(Color.LIGHT_GRAY);
        formulario.setLayout(new GridLayout(7, 2));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formulario.add(lbltitulo);
        formulario.add(txttitulo);
        formulario.add(lblinstrutor);
        formulario.add(txtintrutor);
        formulario.add(lbliniciodat);
        formulario.add(txtiniciodat);
        formulario.add(lblfimdat);
        formulario.add(txtfimdat);
        formulario.add(btnSalvar);
        formulario.add(btnCancelar);
        JScrollPane listScroller = new JScrollPane(jlistodos);
        listScroller.setPreferredSize(new Dimension(300, 80));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        JScrollPane listScrollerBusca = new JScrollPane(jListbusca);
        listScrollerBusca.setPreferredSize(new Dimension(300, 80));
        listScrollerBusca.setAlignmentX(LEFT_ALIGNMENT);
        JPanel listPane = new JPanel();

        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        listPane.add(lblListaCursos);
        listPane.add(Box.createRigidArea(new Dimension(2, 5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel ListBusca = new JPanel();
        ListBusca.setLayout(new BoxLayout(ListBusca, BoxLayout.PAGE_AXIS));

        lblListaBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        ListBusca.add(lblListaBusca);

        ListBusca.add(Box.createRigidArea(new Dimension(2, 5)));

        listScrollerBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        ListBusca.add(listScrollerBusca);

        lblBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        ListBusca.add(lblBuscar);

        txtBusca.setAlignmentX(Component.LEFT_ALIGNMENT);
        ListBusca.add(txtBusca);

        btnBuscar.setAlignmentX(Component.LEFT_ALIGNMENT);
        ListBusca.add(btnBuscar);

        ListBusca.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        Container contentPane = getContentPane();
        contentPane.add(formulario, BorderLayout.NORTH);
        contentPane.add(listPane, BorderLayout.WEST);
        contentPane.add(ListBusca, BorderLayout.EAST);

        EventoHandler handler = new EventoHandler();
        btnSalvar.addActionListener(handler);
        btnCancelar.addActionListener(handler);
        btnBuscar.addActionListener(handler);
    }

    private class EventoHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String string = "";

            if (event.getSource() == btnSalvar) {
                NovoCurso();

            } else if (event.getSource() == btnCancelar) {
                JOptionPane.showMessageDialog(null, "Operação cancelada.");
                a.dispose();

            }else if (event.getSource() == btnBuscar) {
                String nomeBusca = txtBusca.getText().trim();
                if (!nomeBusca.isEmpty()) {
                    ConsultarCursoPorNome(nomeBusca);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Digite um nome para buscar.");
                }
            }

        }
    }

    public static JFrame a;

    public static void main(String[] args) {
        a = new Main();
        a.setTitle("Cadastro de cursos");
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setBounds(300, 300, 870, 400);
        a.setVisible(true);
        a.setResizable(false);

    }

    public class Cursos {
        private String titulo, intrutor;
        private LocalDate datin, datfim;

        Cursos(String ti, String in, LocalDate dain, LocalDate daout) {
            this.titulo = ti;
            this.intrutor = in;
            this.datfim = daout;
            this.datin = dain;
        }

        public LocalDate getDatfim() {
            return datfim;
        }

        public LocalDate getDatin() {
            return datin;
        }

        public String getIntrutor() {
            return intrutor;
        }

        public String getTitulo() {
            return titulo;
        }

    }
}

