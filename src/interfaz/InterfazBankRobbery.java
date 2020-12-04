package interfaz;

import javax.swing.JDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Ciudad;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Ciudad;
import model.Mapa;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class InterfazBankRobbery extends JFrame implements ActionListener,  ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID1 = 1L;

	// Constantes
	// --------------------------------------------------------------------------

	/**
	 * Representa la opci�n de aceptar.
	 */
	public final static String CARGAR = "Cargar";

	// Atributos
	// ----------------------------------------------------------------------------

	private JTextArea areaCaso;

	private JList<Ciudad> listaCasos;

	private JButton botonCargar;

	/**
	 * Representa la relaci�n con la ventana principal.
	 */
	private InterfazBankRobbery principal;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constantes
	// ---------------------------------------------------------------------------

	/**
	 * Representa la opci�n de aceptar.
	 */
	public final static String ACEPTAR = "Aceptar";

	/**
	 * Representa la opci�n de examinar.
	 */
	public final static String EXAMINAR = "Examinar";

	// Atributos
	// ----------------------------------------------------------------------------

	private JTextField campoPath;

	private JButton botonExaminar;

	private JButton botonAceptar;

	private JLabel lblCargar;




	/**
	 * Representa la relaci�n con el dialogo que permite visualizar las ciudades.
	 */
	private DialogoVisualizacion dialogoVisualizacion;

	/**
	 * Representa la relaci�n con el mapa que tiene diferentes ciudades.
	 */
	private Mapa mapa;

	public InterfazBankRobbery() {
		setTitle("Bank Robbery");
		setIconImage(new ImageIcon("./img/ic.png").getImage());
		setSize(500, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);

		// Componentes Casos de prueba

		JPanel casos = new JPanel();
		casos.setLayout(new BorderLayout());

		JPanel panelInputYListas = new JPanel();
		panelInputYListas.setLayout(new FlowLayout());

		areaCaso = new JTextArea(9, 6);
		areaCaso.setEditable(false);
		areaCaso.setFont(new Font("Consolas", Font.PLAIN, 14));

		listaCasos = new JList<>();
		listaCasos.addListSelectionListener(this);

		JLabel lAux2 = new JLabel(new ImageIcon("./img/test.png"));

		JScrollPane scroll1 = new JScrollPane(listaCasos);
		scroll1.setPreferredSize(new Dimension(143, 185));

		JScrollPane scroll2 = new JScrollPane(areaCaso);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setPreferredSize(new Dimension(142, 185));

		botonCargar = new JButton("Visualizar las ciudades");
		botonCargar.setActionCommand(ACEPTAR);
		botonCargar.addActionListener(this);

		JLabel banner = new JLabel(new ImageIcon("./img/ladron.png"));

		panelInputYListas.add(scroll2);
		panelInputYListas.add(scroll1);

		casos.add(panelInputYListas, BorderLayout.CENTER);
		casos.add(banner, BorderLayout.NORTH);
		casos.add(botonCargar, BorderLayout.SOUTH);

		// add(panelInputYListas, BorderLayout.CENTER);
		// add(lAux2, BorderLayout.NORTH);
		// add(botonAceptar,BorderLayout.SOUTH);

		// Componentes
		JPanel aux = new JPanel();
		aux.setLayout(new FlowLayout());
		JLabel lAux = new JLabel(new ImageIcon("./img/banco.png"));
		botonExaminar = new JButton(EXAMINAR, new ImageIcon("./img/ex.png"));
		botonExaminar.setBorderPainted(false);
		botonExaminar.setBorder(new LineBorder(Color.BLACK));
		botonExaminar.setActionCommand(EXAMINAR);
		botonExaminar.addActionListener(this);
		botonAceptar = new JButton(CARGAR, new ImageIcon("./img/ac.png"));
		botonAceptar.setActionCommand(CARGAR);
		botonAceptar.setBorderPainted(false);
		botonAceptar.setBorder(new LineBorder(Color.BLACK));
		botonAceptar.addActionListener(this);
		botonAceptar.setSize(new Dimension(100, 30));
		campoPath = new JTextField();
		campoPath.setPreferredSize(new Dimension(180, 20));
		campoPath.setEditable(false);
		campoPath.setBackground(Color.WHITE);
		lblCargar = new JLabel();
		lblCargar.setText("Cargar Ciudades");
		JLabel vacio = new JLabel();
		aux.add(botonExaminar);
		aux.add(campoPath);
		aux.add(botonAceptar);

		add(lAux, BorderLayout.NORTH);
		add(aux, BorderLayout.CENTER);
		add(casos, BorderLayout.SOUTH);
	}

	// M�todos fundamentales
	// -------------------------------------------------------------------

	/**
	 * M�todo que se encarga de dar la ciudad seleccionada por el usuario en la
	 * lista gr�fica.
	 * 
	 * @return Una ciudad que es la que se ha seleccionado de la lista gr�fica.
	 */
	public Ciudad darCiudadSeleccionada() {
		return (Ciudad) listaCasos.getSelectedValue();
	}

	// M�todos y servicios
	// ---------------------------------------------------------------------

	/**
	 * M�todo que se encarga de actualizar la lista gr�fica que expone las ciudades
	 * a seleccionar.
	 * 
	 * @param lista - Es la lista de ciudades que ya se han creado.
	 */
	public void actualizarLista(ArrayList<Ciudad> lista) {
		Ciudad[] ciudades = new Ciudad[lista.size()];
		lista.toArray(ciudades);
		listaCasos.setListData(ciudades);
		listaCasos.setSelectedIndex(0);
	}

	/**
	 * M�todo que se encarga de actualizar el �rea de texto que expone la
	 * descripci�n de la ciudad que se ha seleccionado en la lista gr�fica.
	 * 
	 * @param texto - Es la descripci�n de la ciudad que se quiere exponer.
	 */
	public void actualizarAreaTexto(String texto) {
		areaCaso.setText(texto);
	}

	/**
	 * Se encarga de dar la ruta que hay en el campo de texto.
	 * 
	 * @return Una cadena que representa la ruta del archivo.
	 */
	public String darPath() {
		return campoPath.getText();
	}

	/**
	 * M�todo que se encarga de modificar la ruta que se expondr� en el campo de
	 * texto.
	 * 
	 * @param path - Es la nueva ruta que se quiere exponer.
	 */
	public void modificarPath(String path) {
		campoPath.setText(path);
	}

	// M�todos y servicios
	// ---------------------------------------------------------------------

	/**
	 * Se encarga de abrir el selector de archivos para elegir el caso de prueba a
	 * visualizar. Luego actualiza el campo de texto con la ruta del archivo
	 * seleccionado.
	 */
	private void examinar() {
		JFileChooser selector = new JFileChooser("./casos/");
		int respuesta = selector.showOpenDialog(this);
		if (respuesta == JFileChooser.APPROVE_OPTION) {
			File archivoSeleccionado = selector.getSelectedFile();
			String ruta = archivoSeleccionado.getPath();
			modificarPath(ruta);
		}
	}

	/**
	 * Se encarga de validar que en el campo de texto que contiene la ruta del
	 * archivo no est� vac�o.
	 * 
	 * @return Verdadero si el campo de texto tiene una ruta, falso de lo contrario.
	 */
	private boolean verificarPath() {
		boolean respuesta = true;
		String path = darPath();
		if (path.equals("")) {
			respuesta = false;
		}
		return respuesta;
	}

	/**
	 * M�todo que se encarga de hacer el llamado al mundo para que se lea el archivo
	 * cuya ruta es la que ha dado el dialogo de carga. Muestra ventanas emergentes
	 * en caso de que la operaci�n haya salido exitosa o haya salido mal.
	 */
	public void leerArchivo() {
		mapa = new Mapa();
		String rutaArchivo = darPath();
		File archivo = new File(rutaArchivo);
		try {
			mapa.cargarDatos(archivo);
			JOptionPane.showMessageDialog(this, "El archivo se carg� exitosamente", "Cargar Archivo",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Hubo problemas al cargar el archivo:\n" + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Se encarga de crear el di�logo que permite visualizar los casos de prueba.
	 */
	public void crearDialogoCasoPrueba() {
		ArrayList<Ciudad> ciudades = mapa.darCiudades();
		actualizarLista(ciudades);
	}

	/**
	 * M�todo que se encarga de crear el di�logo que permite visualizar la ciudad.
	 */
	public void crearDialogoVisualizacion() {
		Ciudad ciudadSeleccionada = darCiudadSeleccionada();
		dialogoVisualizacion = new DialogoVisualizacion(this, ciudadSeleccionada);
		dialogoVisualizacion.setLocationRelativeTo(null);
		dialogoVisualizacion.setVisible(true);
	}

	/**
	 * M�todo que se encarga de hacer los llamados al mundo para que se solucione la
	 * ciudad que trae el di�logo de casos de prueba.
	 */
	public void solucionarCiudad() {
		Ciudad seleccionada = darCiudadSeleccionada();
		String solucion = seleccionada.darSolucion();
		System.out.println(solucion);
		dialogoVisualizacion.actualizarAreaTexto(solucion);
	}
	
	
	public void solucionarCiudadFloyd() {
		Ciudad seleccionada = darCiudadSeleccionada();
		String solucion = seleccionada.darSolucion();
		System.out.println(solucion);
		dialogoVisualizacion.actualizarAreaTexto(solucion);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals(CARGAR)) {
			boolean esValido = verificarPath();
			if (esValido) {
				leerArchivo();
				crearDialogoCasoPrueba();
			} else {
				JOptionPane.showMessageDialog(this, "Aseg�rese de seleccionar un archivo primero", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (comando.equals(EXAMINAR)) {
			examinar();
		} else if (comando.equals(ACEPTAR)) {
			crearDialogoVisualizacion();
		}
	}


	public static void main(String[] args) {
		InterfazBankRobbery ventana = new InterfazBankRobbery();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		Ciudad ciudadSeleccionada = (Ciudad) listaCasos.getSelectedValue();
		if(ciudadSeleccionada != null) {
			String descripcionCiudad = ciudadSeleccionada.darFormatoTexto();
			actualizarAreaTexto(descripcionCiudad);
		}
		
	}
	


	
}
