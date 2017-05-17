package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;

public class GUIKontroler {
	
	private static MenjacnicaInterface sistem;
	private static MenjacnicaGUI mGUI;
	private static DodajKursGUI dkGUI;
	private static IzvrsiZamenuGUI izGUI;
	private static ObrisiKursGUI okGUI;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					sistem = new Menjacnica();
					mGUI = new MenjacnicaGUI();
					mGUI.setVisible(true);
					mGUI.setLocationRelativeTo(null);
					mGUI.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							ugasiAplikaciju();
						}

					});
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
		});
		
	}
	

	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(mGUI,
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(mGUI,
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void otvoriDodajKursProzor() {
		dkGUI = new DodajKursGUI(mGUI);
		dkGUI.setLocationRelativeTo(mGUI);
		dkGUI.setVisible(true);
	}
	
	public static void otvoriIzvrsiZamenuProzor(Valuta valuta) {
		izGUI = new IzvrsiZamenuGUI(mGUI, valuta);
		izGUI.setLocationRelativeTo(mGUI);
		izGUI.setVisible(true);
	}
	
	public static void otvoriObrisiKursProzor(Valuta valuta) {
		okGUI = new ObrisiKursGUI(mGUI, valuta);
		okGUI.setLocationRelativeTo(mGUI);
		okGUI.setVisible(true);
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(mGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mGUI, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(mGUI);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				mGUI.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mGUI, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(Valuta valuta) {

		// Dodavanje valute u kursnu listu
		sistem.dodajValutu(valuta);

		// Osvezavanje glavnog prozora
		mGUI.prikaziSveValute();

		
	}
	
	public static double izvrsiZamenu(Valuta valuta, boolean selected, double iznos){
		return sistem.izvrsiTransakciju(valuta, selected, iznos);
	}
	
	public static void obrisiValutu(Valuta valuta) {
		sistem.obrisiValutu(valuta);
		mGUI.prikaziSveValute();
	}
	
	public static LinkedList<Valuta> vratiKursnuListu() {
		return sistem.vratiKursnuListu();
	}

}
