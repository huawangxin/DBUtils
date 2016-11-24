package huawangxin.utils;

import java.util.ArrayList;
import java.util.List;

public class Condtion {
	private String condtion;

	private Convert convert;

	private List<String> where = new ArrayList<String>();

	public Condtion() {
		super();
	}

	public Condtion(String condtion, Convert convert) {
		super();
		this.condtion = condtion;
		this.convert = convert;
	}

	@Deprecated
	public static Condtion create(String condtion, Convert convert) {
		return new Condtion(condtion, convert);
	}

	public static Condtion create(Convert convert) {
		return new Condtion(null, convert);
	}

	public String getWhereSql() {
		if (where.size() == 0)
			return "";

		StringBuilder builder = new StringBuilder();
		builder.append(" WHERE");
		for (int i = 0; i < where.size(); i++) {
			builder.append(" ").append(where.get(i));
			if (i != where.size() - 1)
				builder.append(" AND ");
		}

		return builder.toString();
	}

	public String getCondtion() {
		return condtion;
	}

	public void setCondtion(String condtion) {
		this.condtion = condtion;
	}

	public Convert getConvert() {
		return convert;
	}

	public void setConvert(Convert convert) {
		this.convert = convert;
	}

	public List<String> getWhere() {
		return where;
	}

	public void setWhere(List<String> where) {
		this.where = where;
	}

	public Condtion addWhere(String where) {
		this.where.add(where);
		return this;
	}
}
