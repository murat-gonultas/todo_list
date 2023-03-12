import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.lang.*;

public class Main {

	private static int getMenuAuswahl() {
		Scanner sc = new Scanner(System.in);

		System.out.println("1. Addieren Sie TODO item");
		System.out.println("2. Suchen Sie TODO items nach Datum");
		System.out.println("3. Suchen Sie TODO items nach Aufgaben und Beschreibungen");
		System.out.println("4. Löschen Sie TODO items");
		System.out.println("5. Ändern Sie TODO item");
		System.out.println("6. Listen Sie TODO items auf");
		System.out.println("7. Änderungen in Datei speichern");
		System.out.print("8. Exit\n\nAuswahl: ");

		return sc.nextInt();
	}

	private static String getSearchStr() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Search term(s): ");

		return sc.nextLine();
	}

	private static int getSearchId() {
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter ID: ");

		return sc.nextInt();
	}

	private static Date getSearchDatum() throws Exception {
		Scanner sc = new Scanner(System.in);
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		try {
			System.out.print("Enter date: ");
			String wert = sc.nextLine();
			return df.parse(wert);
		}
		catch (Exception e) {
			throw new Exception("Ungültiges Datum eingegeben: ");
		}
	}

	public static void inDateienSpeichern(String filename, ArrayList<ToDoItems> items) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			writer.print("");

			for (ToDoItems item : items) {
				writer.write(item.toFile());
			}

			writer.close();
		}
		catch (Exception ex) {
			System.out.println("Exception aufgetreten: " + ex);
		}
	}

	public static void main(String[] args) {
		ItemManager meineToDoList = new ItemManager();
		String filename = args[0];

		try {
			ArrayList<ToDoItems> meineItems = ItemManager.readFromFile(filename);

			for (ToDoItems i : meineItems) {
				if (!meineToDoList.addToDoItem(i)) {
					System.out.println("Ihr Kalender ist für diese Zeit voll.");
				}
			}
		}
		catch (Exception e) {
			System.out.println("Datei kann nicht geöffnet werden oder unbehandelte Ausnahme.  e:" +e);
			return;
		}
		
		ArrayList<ToDoItems> befund;
		while (true) {
			switch (getMenuAuswahl()) {
				case 1 -> {
					try {
						if (!meineToDoList.addToDoItem(ToDoItems.itemErstellen())) {
							System.out.println("Ihr Kalender ist für diese Zeit voll.");
						}
					} catch (Exception ex) {
						System.out.println("Aufgabenelement kann nicht hinzugefügt werden.");
					}
				}
				case 2 -> {
					try {
						befund = meineToDoList.searchTodoItem(getSearchDatum(), getSearchDatum());
						for (ToDoItems i : befund) {
							System.out.println(i);
						}
					} catch (Exception ex) {
						System.out.println("Ungültiges Datum");
					}
				}
				case 3 -> {
					befund = meineToDoList.searchTodoItem(getSearchStr());
					for (ToDoItems i : befund) {
						System.out.println(i);
					}
				}
				case 4 -> {
					if (!meineToDoList.deleteItem(getSearchId())) {
						System.out.println("Element kann nicht gelöscht werden!");
					}
				}
				case 5 -> {
					int si = getSearchId();
					befund = meineToDoList.searchTodoItem(si);
					if (befund.size() > 0) {
						meineToDoList.deleteItem(si);
						try {
							if (!meineToDoList.addToDoItem(ToDoItems.itemErstellen())) {
								System.out.println("Ihr Kalender ist für diese Zeit voll.");
							}
						} catch (Exception ex) {
							System.out.println("Aufgabenelement kann nicht hinzugefügt werden.");
						}
					} else {
						System.out.println("Keine solche TODO!");
					}
				}
				case 6 -> meineToDoList.druckItems();
				case 7 -> inDateienSpeichern(filename, meineToDoList.getItems());
				case 8 -> System.exit(0);
				default -> System.out.println("Ungültige Auswahl!");
			}
		}

	}


}
