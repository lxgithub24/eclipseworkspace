package repostApi;

public class Repost {

	public static void main(String args[]) {
		String name = "liuxiang";
		int age = 24;
		int xi = 9;
		ConDB conndb = new ConDB();
		conndb.addToDB(name,age,xi);
	}
}