package defaultPackage;

import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class UseThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame marco = new MarcoRebote();

		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		marco.setVisible(true);

	}

}

class PelotaHilos implements Runnable {

	private Pelota pelota;
	private Component componente;

	public PelotaHilos(Pelota unaPelota, Component unComponente) {

		pelota = unaPelota;
		componente = unComponente;
	}

	public void run() {

		while (!Thread.currentThread().isInterrupted()) {
			pelota.mueve_pelota(componente.getBounds());
			componente.paint(componente.getGraphics());
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				// System.out.println("Hilo bloqueado. Imposible interrumpir");
				// System.exit(0);
				Thread.currentThread().interrupt();
			}
		}
	}

}

// Movimiento de la
// pelota-----------------------------------------------------------------------------------------

class Pelota {

	// Mueve la pelota invirtiendo posición si choca con límites

	public void mueve_pelota(Rectangle2D limites) {

		x += dx;

		y += dy;

		if (x < limites.getMinX()) {

			x = limites.getMinX();

			dx = -dx;
		}

		if (x + TAMX >= limites.getMaxX()) {

			x = limites.getMaxX() - TAMX;

			dx = -dx;
		}

		if (y < limites.getMinY()) {

			y = limites.getMinY();

			dy = -dy;
		}

		if (y + TAMY >= limites.getMaxY()) {

			y = limites.getMaxY() - TAMY;

			dy = -dy;

		}

	}

	// Forma de la pelota en su posición inicial

	public Ellipse2D getShape() {

		return new Ellipse2D.Double(x, y, TAMX, TAMY);

	}

	private static final int TAMX = 15;

	private static final int TAMY = 15;

	private double x = 0;

	private double y = 0;

	private double dx = 1;

	private double dy = 1;

}

// Lámina que dibuja las
// pelotas----------------------------------------------------------------------

class LaminaPelota extends JPanel {

	// Añadimos pelota a la lámina

	public void add(Pelota b) {

		pelotas.add(b);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		for (Pelota b : pelotas) {

			g2.fill(b.getShape());
		}

	}

	private ArrayList<Pelota> pelotas = new ArrayList<Pelota>();
}

// Marco con lámina y
// botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame {

	public MarcoRebote() {

		setBounds(600, 300, 600, 450);

		setTitle("Rebotes");

		lamina = new LaminaPelota();

		add(lamina, BorderLayout.CENTER);

		JPanel laminaBotones = new JPanel();

		inicia1 = new JButton("Hilo1");
		inicia1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comienza_el_juego(e);
			}
		});

		inicia2 = new JButton("Hilo2");
		inicia2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				comienza_el_juego(e);
			}
		});

		inicia3 = new JButton("Hilo3");
		inicia3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				comienza_el_juego(e);
			}
		});

		detener1 = new JButton("Detener1");
		detener1.setEnabled(false);
		detener1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});

		detener2 = new JButton("Detener2");
		detener2.setEnabled(false);
		detener2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});

		detener3 = new JButton("Detener3");
		detener3.setEnabled(false);
		detener3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});

		laminaBotones.add(inicia1);
		laminaBotones.add(inicia2);
		laminaBotones.add(inicia3);
		laminaBotones.add(detener1);
		laminaBotones.add(detener2);
		laminaBotones.add(detener3);

		add(laminaBotones, BorderLayout.SOUTH);
	}

	// Ponemos botones

	public void ponerBoton(Container c, String titulo, ActionListener oyente) {

		JButton boton = new JButton(titulo);

		c.add(boton);

		boton.addActionListener(oyente);

	}

	// Añade pelota y la bota 1000 veces

	public void comienza_el_juego(ActionEvent e) {

		Pelota pelota = new Pelota();

		lamina.add(pelota);

		Runnable r = new PelotaHilos(pelota, lamina);

		if (e.getSource() == inicia1) {
			hilo1 = new Thread(r);
			hilo1.start();
			inicia1.setEnabled(false);
			detener1.setEnabled(true);
		}

		if (e.getSource() == inicia2) {
			hilo2 = new Thread(r);
			hilo2.start();
			inicia2.setEnabled(false);
			detener2.setEnabled(true);
		}

		if (e.getSource() == inicia3) {
			hilo3 = new Thread(r);
			hilo3.start();
			inicia3.setEnabled(false);
			detener3.setEnabled(true);
		}

	}

	public void detener(ActionEvent e) {
		// hilo.stop(); deprecado

		if (e.getSource().equals(detener1)) {
			hilo1.interrupt();
			detener1.setEnabled(false);
			inicia1.setEnabled(true);
		}

		else if (e.getSource().equals(detener2)) {
			hilo2.interrupt();
			detener2.setEnabled(false);
			inicia2.setEnabled(true);
		}

		else {
			hilo3.interrupt();
			detener3.setEnabled(false);
			inicia3.setEnabled(true);
		}

	}

	private LaminaPelota lamina;
	private Thread hilo1, hilo2, hilo3;
	private JButton inicia1, inicia2, inicia3, detener1, detener2, detener3;
}
