package interfaz;

import javax.swing.JDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mundo.Ciudad;

public class DialogoCasosPrueba extends JDialog implements ActionListener, ListSelectionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Constantes --------------------------------------------------------------------------
	
	/**
	 * Representa la opción de aceptar.
	 */
	public final static String ACEPTAR = "Aceptar";
	
	// Atributos ----------------------------------------------------------------------------
	
	private JTextArea areaCaso; 
	
	private JList<model.Ciudad> listaCasos; 
	
	private JButton botonAceptar;
	
	/**
	 * Representa la relación con la ventana principal.
	 */
	private InterfazBankRobbery principal;
	
	// Constructor --------------------------------------------------------------------------

	public DialogoCasosPrueba(InterfazBankRobbery ventana){
		super(ventana, true);
		principal = ventana;
		setTitle("Bank Robbery");
		setSize(new Dimension(400, 500));
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("./img/ic.png").getImage());
		setLayout(new BorderLayout());
		
		// Creación de componentes------------------------------
		JPanel panelInputYListas = new JPanel();
		panelInputYListas.setLayout(new FlowLayout());
		
		areaCaso = new JTextArea(9, 6);
		areaCaso.setEditable(false);
		areaCaso.setFont(new Font("Consolas", Font.PLAIN, 14));
		
		listaCasos = new JList<>();
		listaCasos.addListSelectionListener(this);
		
		JLabel lAux = new JLabel(new ImageIcon("./img/test.png"));
		
		JScrollPane scroll1 = new JScrollPane(listaCasos);
		scroll1.setPreferredSize(new Dimension(143, 185));
		
		JScrollPane scroll2 = new JScrollPane(areaCaso);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setPreferredSize(new Dimension(142, 185));
		
		botonAceptar = new JButton(ACEPTAR);
		botonAceptar.setActionCommand(ACEPTAR);
		botonAceptar.addActionListener(this);
		
		panelInputYListas.add(scroll2);
		panelInputYListas.add(scroll1);
		
		
		add(panelInputYListas, BorderLayout.CENTER);
		add(lAux, BorderLayout.NORTH);
		add(botonAceptar,BorderLayout.SOUTH);
	}
	
	// Métodos fundamentales ---------------------------------------------------------------
	
	/**
	 * Método que se encarga de dar la ciudad seleccionada por el usuario
	 * en la lista gráfica.
	 * @return Una ciudad que es la que se ha seleccionado de la lista gráfica.
	 */
	public Ciudad darCiudadSeleccionada() {
		return (Ciudad) listaCasos.getSelectedValue();
	}
	
	// Métodos y servicios ---------------------------------------------------------------------

	
	/**
	 * Método que se encarga de actualizar la lista gráfica que expone las ciudades
	 * a seleccionar.
	 * @param lista - Es la lista de ciudades que ya se han creado.
	 */
	public void actualizarLista(ArrayList<Ciudad> lista) {
		Ciudad[] ciudades = new Ciudad[lista.size()];
		lista.toArray(ciudades);
		listaCasos.setListData(ciudades);
		listaCasos.setSelectedIndex(0);
	}
	
	/**
	 * Método que se encarga de actualizar el área de texto que expone la descripción
	 * de la ciudad que se ha seleccionado en la lista gráfica.
	 * @param texto - Es la descripción de la ciudad que se quiere exponer.
	 */
	public void actualizarAreaTexto(String texto) {
		areaCaso.setText(texto);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Ciudad ciudadSeleccionada = (Ciudad) listaCasos.getSelectedValue();
		if(ciudadSeleccionada != null) {
			String descripcionCiudad = ciudadSeleccionada.darFormatoTexto();
			actualizarAreaTexto(descripcionCiudad);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals(ACEPTAR)) {
			principal.crearDialogoVisualizacion();
		}
	}
}
