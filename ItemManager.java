import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class ItemManager {
	private ArrayList<ToDoItems> items = new ArrayList<>();

	private static int number_of_items = 0;

	public boolean addToDoItem(ToDoItems item) {
		if (item.isGesperrt()) {
			ArrayList<ToDoItems> list = searchTodoItem(item.getBeginDatum(), item.getEndDatum());

			if (list.size() > 0) {
				System.out.println("Kalender ist derzeit nicht verfügbar!");
				return false;
			}
		}

		int platzierung = 0;
		for (ToDoItems i : items) {
			if (i.getBeginDatum().before(item.getBeginDatum())) {
				platzierung++;
			}
			else {
				break;
			}
		}

		int n_items = ItemManager.number_of_items++;
		item.setId(n_items);
		items.add(platzierung, item);

		return true;
	}

	public boolean deleteItem(int id) {
		int idx = 0;
		boolean isGefunden = false;
		for (ToDoItems i : items) {
			if (i.getId() == id) {
				isGefunden = true;
				break;
			}

			idx++;
		}

		if (isGefunden) items.remove(idx);

		return isGefunden;
	}

	public ArrayList<ToDoItems> searchTodoItem(String searchstr) {
		ArrayList<ToDoItems> list = new ArrayList<>();

		for (ToDoItems item : items) {
			if (item.getAufgabe().contains(searchstr) || item.getBeschreibung().contains(searchstr)) {
				list.add(item);
			}
		}

		return list;
	}

	public ArrayList<ToDoItems> searchTodoItem(Date start, Date end) {
		ArrayList<ToDoItems> list = new ArrayList<>();

		for (ToDoItems item : items) {
			if ((start.before(item.getBeginDatum()) && end.after(item.getBeginDatum()))
					|| (start.before(item.getEndDatum()) && end.after(item.getEndDatum()))) {
				list.add(item);
			}
		}

		return list;
	}

	public ArrayList<ToDoItems> searchTodoItem(int id) {
		ArrayList<ToDoItems> list = new ArrayList<>();

		for (ToDoItems i : items) {
			if (i.getId() == id) {
				list.add(i);
				return list;
			}
		}

		return list;
	}

	private class ToDoDatumCompare implements Comparator<ToDoItems>
	{
		@Override
		public int compare(ToDoItems a, ToDoItems b)
		{
			return a.getBeginDatum().compareTo(b.getBeginDatum());
		}
	}

	public ArrayList<ToDoItems> getItems() {
		return items;
	}

	public void druckItems() {
		System.out.println("Nummer von TODO Items: " + Integer.toString(items.size()));
		for (ToDoItems item : items) {
			System.out.println(item.toString());
		}
	}

	public static ArrayList<ToDoItems> readFromFile(String filename) throws Exception {
		ArrayList<ToDoItems> fileitems = new ArrayList<>();
	
		File file = new File(filename);

		Scanner eingabe = new Scanner(file);
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

		while (eingabe.hasNextLine()) {
			ToDoItems item = new ToDoItems();

			for (int feld = 0; feld < 6 && eingabe.hasNextLine(); feld++) {
				String line = eingabe.nextLine();
				switch (feld) {
					case 0:
						item.setAufgabe(line);
						break;
					case 1:
						item.setBeschreibung(line);
						break;
					case 2:
						try {
							item.setBeginDatum(df.parse(line));
						}
						catch (Exception e) {
							System.out.println("StartDatum: Unerwartet oder falsche Datum format:" + e + " " + line);
							throw new Exception("ungültiges Datum überschritten");
						}
						break;
					case 3:
						try {
							item.setEndDatum(df.parse(line));
						}
						catch (Exception e) {
							System.out.println("EndDate: Unerwartet oder falsche Datum format:" + e + " " + line);
							throw new Exception("ungültiges Datum überschritten");
						}
						break;
					case 4:
						item.setErinnerung( line.toLowerCase().startsWith("y") );
						break;
					case 5:
						item.setGesperrt( line.toLowerCase().startsWith("y") );
						break;
				}
			}

			fileitems.add(item);
		}
		eingabe.close();

		return fileitems;
	}




}
