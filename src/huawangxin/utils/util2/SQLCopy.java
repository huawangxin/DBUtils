package huawangxin.utils.util2;

public class SQLCopy {
	public static void main(String[] args) {
		String[] tables = new String[] { "CONTRADICTION_DISPUTE","IMPORTPLACE","SCHOOL" };

		for (String table : tables) {
			System.out.println("alter table "+table+" add TQ_ID NUMBER(9);");
		}
		System.out.println("------------------------------------------");
		for (String table : tables) {
			System.out.println("truncate table "+table+";");
		}
		System.out.println("------------------------------------------");
		for (String table : tables) {
			System.out.println(getTigger(table, "mp_pid"));
		}
		
		System.out.println("------------------------------------------");
		for (String table : tables) {
			System.out.println(getSequence(table, "mp_pid"));
		}
		
		
		System.out.println("------------------------------------------");
		for (String table : tables) {
			System.out.println(getMaxPID(table, "mp_pid"));
		}

		System.out.println((int)'A');
		System.out.println((int)'b');
	}

	public static String getTigger(String tableName, String uid) {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE OR REPLACE TRIGGER tg_").append(tableName);
		builder.append("\n");
		builder.append("BEFORE INSERT ON ").append(tableName)
				.append(" FOR EACH ROW ");
		builder.append("\n");
		builder.append("when (new.").append(uid).append("  is null)");
		builder.append("\n");
		builder.append("begin");
		builder.append("\n");
		builder.append("select seq_").append(tableName)
				.append(".nextval into:new.").append(uid).append(" from dual;");
		builder.append("\n");
		builder.append("end;");
		builder.append("\n");

		return builder.toString();
	}
	
	public static String getSequence(String tableName, String uid) {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE SEQUENCE seq_")
				.append(tableName)
				.append(" MINVALUE 0 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;");
		builder.append("\n");
		return builder.toString();
	}

	public static String getMaxPID(String tableName, String uid) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT MAX(").append(uid).append(") FROM ")
				.append(tableName);
		return builder.toString();
	}

	
}
