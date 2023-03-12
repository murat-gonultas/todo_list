import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ToDoItems {
	private String aufgabe;
	private String beschreibung;
	private Date beginDatum;
	private Date endDatum;
	private boolean erinnerung;
	private boolean erledigt;
	private boolean gesperrt;
	private int id;

	public String getAufgabe() {
		return aufgabe;
	}

	public void setAufgabe(String aufgabe) {
		this.aufgabe = aufgabe;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Date getBeginDatum() {
		return beginDatum;
	}

	public void setBeginDatum(Date beginDatum) {
		this.beginDatum = beginDatum;
	}

	public Date getEndDatum() {
		return endDatum;
	}

	public void setEndDatum(Date endDatum) {
		this.endDatum = endDatum;
	}

	public boolean isErinnerung() {
		return erinnerung;
	}

	public void setErinnerung(boolean erinnerung) {
		this.erinnerung = erinnerung;
	}

	public boolean isErledigt() {
		return erledigt;
	}

	public void setErledigt(boolean erledigt) {
		this.erledigt = erledigt;
	}

	public boolean isGesperrt() {
		return gesperrt;
	}

	public void setGesperrt(boolean gesperrt) {
		this.gesperrt = gesperrt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static ToDoItems itemErstellen() throws Exception {
		Scanner sc = new Scanner(System.in);
		ToDoItems item = new ToDoItems();

		System.out.println("Aufgabe: ");
		item.setAufgabe(sc.nextLine());

		System.out.println("Beschreibung: ");
		item.setBeschreibung(sc.nextLine());

		try {
			System.out.println("Start Date and Time (yyyy.MM.dd HH:mm:ss : ");
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			item.setBeginDatum(df.parse(sc.nextLine()));

			System.out.println("End Date and Time (yyyy.MM.dd HH:mm:ss : ");
			item.setEndDatum(df.parse(sc.nextLine()));
		}
		catch (Exception e) {
			System.out.println("Unerwartet oder falsche date format:" + e.toString());
			throw new Exception("ungültiges Datum überschritten");
		}

		System.out.println("Wollen Sie eine Erinnerung einstellen (y/n): ");
		item.setErinnerung( sc.nextLine().toLowerCase().startsWith("y") );

		System.out.println("Wollen Sie dieses Zeitintervall blockieren? (y/n): ");
		item.setGesperrt( sc.nextLine().toLowerCase().startsWith("y") );

		return item;
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		return "Id: " + Integer.toString(id) + "\n"
				+ "Aufgabe: " + aufgabe + "\n"
				+ "Beschreibung: " + beschreibung + "\n"
				+ "Start: " + df.format(beginDatum) + "\n"
				+ "End: " + df.format(endDatum) + "\n"
				+ "Erinnerung: " + (erinnerung ? "Yes" : "No") + "\n"
				+ "Gesperrter Kalendar: " + (gesperrt ? "Yes" : "No") + "\n";
	}

	public String toFile() {
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		return aufgabe + "\n"
				+ beschreibung + "\n"
				+ df.format(beginDatum) + "\n"
				+ df.format(endDatum) + "\n"
				+ (erinnerung ? "Yes" : "No") + "\n"
				+ (gesperrt ? "Yes" : "No") + "\n";
	}
}
