package repostApi;

public class Repost {

	public static void main(String args[]) {
		String name = "av111sb";
		int age = 24;
		int xi = 9;
		ConDB conndb = new ConDB();
//		conndb.addToDB(name,age,xi);
		long uid = Long.parseLong("5340825039");
		String content = "hello,world";
		long srcDid = Long.parseLong("3769354850591348");
//		conndb.post(uid,content);
//		conndb.repost(uid, content, srcDid);
//		conndb.comment(uid, srcDid, content);
//		conndb.support(uid, srcDid);
//		conndb.
		System.out.println(conndb.uidToUname(name));
	}
}