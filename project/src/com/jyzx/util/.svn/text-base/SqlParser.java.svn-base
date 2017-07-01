package com.jyzx.util;


public class SqlParser {
	// public SqlParser()
	// {
	// }
	// select_stmt = SELECT [ ALL | DISTINCT ] select_item { "," select_item }
	// FROM table_ref { "," table_ref }
	// [ WHERE expr ]
	// [ GROUP BY column_ref { "," column_ref } ]
	// [ HAVING expr ]
	// [ ORDER BY order_item { "," order_item } ]
	final static private int indexOfEnd(char a[], int j, String s[]) {
		next: for (int i = 0; i < s.length; i++) {
			int l = s[i].length();
			if (j < l)
				continue;
			int j0 = j - l;
			if (j0 > 0 && Character.isJavaIdentifierPart(a[j0 - 1]))
				continue;
			for (int p = 0; p < l; p++)
				if (s[i].charAt(p) != a[j0 + p])
					continue next;
			return i;
		}
		return -1;
	}

	final static private void addSqlItem(String items[][], int inItem,
			int jSelect, String s) {
		if (items == null || inItem < 0)
			return;
		if (inItem == 6 && items.length < 7) // union...
		{
			return;
		}
		if (inItem >= 7)
			throw new java.lang.IllegalArgumentException();
		if (inItem == 5) {
			// if( items[5]==null ) items[5] = new String[1];
			jSelect = 0;
		}
		for (int j = 0; j < items.length; j++) {
			if (items[j] == null)
				items[j] = new String[jSelect + 1];
			else if (items[j].length < jSelect + 1) {
				String[] old = items[j];
				items[j] = new String[jSelect + 1];
				System.arraycopy(old, 0, items[j], 0, old.length);
			}
		}
		items[inItem][jSelect] = s;
	}

	final public static String sqlNormalize(String sql) // ,java.util.Vector
														// elements)
	{
		if (sql == null)
			return null;
		char[] a, src;
		src = sql.toCharArray();
		a = new char[src.length + 256];
		// System.arraycopy(src,0,a,0,src.length); a[src.length] = ';';
		int j = 0;
		boolean inStr = false;
		boolean spaceNeeded = false;
		@SuppressWarnings("unused")
		int deepBrackets = 0;
		boolean inSqlMacro = false;
		for (int i = 0; i < src.length; i++) {
			char c = src[i];
			if (c == '\'')
				inStr = !inStr;
			else if (c <= ' ' && !inStr) {
				if (j > 0 && a[j - 1] > ' ')// Character.isJavaIdentifierPart(a[j-1])
											// )
				{
					// a[j++]=' '; // continue;
					spaceNeeded = true;
				}
				inSqlMacro = false;
				// j==0 || a[j-1]==' '
				continue;
			}
			if (!inStr && c == ')')
				deepBrackets--;
			if (c >= 'A' && c <= 'Z') {
				if (!inStr && !inSqlMacro)
					c = (char) (c - 'A' + 'a');
			} else {
				inSqlMacro = c == '%' && !inStr && sql.startsWith("%SQL", i);
			}
			if (spaceNeeded && c > ' ') // Character.isJavaIdentifierPart(c)
				a[j++] = ' ';
			spaceNeeded = false;
			a[j++] = c;
			if (!inStr && c == '(')
				deepBrackets++;
		}
		return new String(a, 0, j);
	}

	final public static String getTableNameForUpdateSql(String sql) {
		return getTableNameForUpdateSql(sql, null);
	}

	final public static String getTableNameForUpdateSql(String sql,
			int pSqlType[]) {
		sql = SqlParser.sqlNormalize(sql);
		int p = -1;
		String tags[] = { "update ", "delete ", "insert into ", "delete from " };
		// 1 2 3 4
		for (int i = 0; i < tags.length; i++) {
			if (sql.startsWith(tags[i])) {
				p = tags[i].length();
				if (pSqlType != null)
					pSqlType[0] = i + 1;
			}
		}
		if (pSqlType != null && pSqlType[0] == 4)
			pSqlType[0] = 2;
		if (p > 0) {
			int p0 = p;
			for (; p < sql.length(); p++) {
				char c = sql.charAt(p);
				if (!java.lang.Character.isJavaIdentifierPart(c))
					break;
			}
			if (p > p0) {
				return sql.substring(p0, p);
			}
		}
		return null;
	}

	/**
	 * @param sql
	 * @return items : String[][] {
	 *         {"select_item1,select_item2,...","select_item1,select_item2,...",
	 *         {"table_ref1,table_ref2,...","table_ref1,table_ref2,...",
	 *         {whereExpr} {groupBy1, groupBy2,... }, {havingExpr},
	 *         {OrderBy1,OrderBy2,... } }
	 */
	final public static String sqlNormalize(final String sql,
			final String items[][]) {
		// System.out.println("SqlParser.sqlNormalize:sql="+sql);
		int jSelect = 0;
		if (sql == null)
			return null;
		int inItem = -1; // 0:select,1:from,2:where,3:group by,4:having
		int itemFrom = -1;
		String itemIds[] = { "select", "from", "where", "group by", "having",
				"order by" };
		String unionIds[] = { "union", "union all" };
		char[] a, src;
		src = sql.toCharArray();
		int lenSrc = src.length;
		for (; lenSrc > 0 && (src[lenSrc - 1] <= ' ' || src[lenSrc - 1] == ';'); lenSrc--)
			;
		a = new char[lenSrc + 256];
		// System.arraycopy(src,0,a,0,src.length); a[src.length] = ';';
		int j = 0;
		boolean inStr = false;
		boolean spaceNeeded = false;
		boolean inSqlMacro = false;
		int deepBrackets = 0;
		for (int i = 0; i < lenSrc; i++) {
			char c = src[i];
			if (c == '\'')
				inStr = !inStr;
			else if (c <= ' ' && !inStr) {
				if (j > 0 && a[j - 1] > ' ')// Character.isJavaIdentifierPart(a[j-1])
											// )
				{
					// a[j++]=' '; // continue;
					spaceNeeded = true;
				}
				// j==0 || a[j-1]==' '
				inSqlMacro = false;
				continue;
			}
			if (!inStr && c == ')')
				deepBrackets--;
			if (inSqlMacro && (inStr || !Character.isJavaIdentifierPart(c)))
				inSqlMacro = false;
			if (c >= 'A' && c <= 'Z' && !inStr && !inSqlMacro) {
				c = (char) (c - 'A' + 'a');
			} else if (c == '%' && !inStr && sql.startsWith("%SQL", i)) {
				inSqlMacro = true;
			}
			if (!Character.isJavaIdentifierPart(c) || spaceNeeded) {
				int idx;
				if (j >= 0 && (idx = indexOfEnd(a, j, unionIds)) >= 0) {
					int l = unionIds[idx].length();
					if (deepBrackets == 0) {
						if (inItem >= 0 && items != null) // j-l-itemFrom>=0
						{
							// items[inItem] = new
							// String(a,itemFrom,j-itemFrom).trim();
							addSqlItem(items, inItem, jSelect, new String(a,
									itemFrom, j - l - itemFrom).trim());
						}
						if (idx == 0) {
							jSelect++;
						}
						addSqlItem(items, 6, jSelect, unionIds[idx]);
						inItem = -1;
					}
					if (j > l && a[j - l - 1] != ' ') {
						a[j - l] = ' ';
						j++;
						for (int p = (idx == 0 ? 0 : 6); p < l; p++)
							a[j - l + p] = unionIds[idx].charAt(p);
					}
					a[j++] = ' ';
					spaceNeeded = false;
				} else if (j > 0 && (idx = indexOfEnd(a, j, itemIds)) >= 0) // a[j-1]>='a'
																			// &&
																			// a[j-1]<='z'
																			// &&
				{
					int l = itemIds[idx].length();
					if (deepBrackets == 0 && inItem >= 0 && items != null) {
						addSqlItem(items, inItem, jSelect, new String(a,
								itemFrom, j - l - itemFrom).trim());
					} else if (deepBrackets != 0) {
					}
					/*
					 * if( deepBrackets==0 && inItem>=0 && items!=null ) //
					 * j-l-itemFrom>=0 { items[inItem] = new
					 * String(a,itemFrom,j-l-itemFrom).trim(); }
					 */
					if (j > l && a[j - l - 1] != ' ') {
						a[j - l] = ' ';
						j++;
						for (int p = 0; p < l; p++)
							a[j - l + p] = itemIds[idx].charAt(p);
					}
					a[j++] = ' ';
					spaceNeeded = false;
					if (deepBrackets == 0) {
						inItem = idx;
						itemFrom = j;
					}
				} // else if( j>=0 &&
			}
			if (spaceNeeded && c > ' ') // Character.isJavaIdentifierPart(c)
				a[j++] = ' ';
			spaceNeeded = false;
			a[j++] = c;
			if (!inStr && c == '(')
				deepBrackets++;
		}
		if (deepBrackets == 0 && inItem >= 0 && items != null) // j-l-itemFrom>=0
		{
			// items[inItem] = new String(a,itemFrom,j-itemFrom).trim();
			addSqlItem(items, inItem, jSelect, new String(a, itemFrom, j
					- itemFrom).trim());
		}
		// if( j>0 && a[j-1]==' ' ) j--;
		return new String(a, 0, j);
	}

	final public static String toSql(final String items[][]) {
		String itemIds[] = { "select", "from", "where", "group by", "having",
				"order by" };
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < items[0].length; j++) {
			// if( items[0]==null || items[0][j]==null )
			// break;
			if (j > 0)
				sb.append(items.length <= 6 || items[6].length <= j || items[6][j] == null || items[6][j].trim().length() == 0 ? " union " : " " + items[6][j] + " ");
			for (int i = 0; i < 6; i++) {
				if (items[i] != null && j < items[i].length
						&& items[i][j] != null) {
					sb.append(" ");
					sb.append(itemIds[i]);
					sb.append(" ");
					sb.append(items[i][j]);
				}
			}
		}
		return sb.toString().trim();
	}

	final static public String tableNameForSql(String sql) {
		String items[][] = new String[6][];
		SqlParser.sqlNormalize(sql, items);
		if (items[1] == null)
			return null;
		for (int i = 0; i < items[1].length; i++) {
			String nm = firstTableName(items[1][i]);
			if (nm != null)
				return nm;
		}
		return null;
	}

	final static public String getSqlWhere(String sql) {
		String items[][] = new String[6][];
		SqlParser.sqlNormalize(sql, items);
		return items[2] != null ? items[2][0] : null;
	}

	/*
	 * SqlParser.addSqlFilter(event.sql,"xxx like xxx") end proc
	 */
	final static public String addSqlFilter(String sql, String filter) {
		if (filter == null)
			return sql;
		String items[][] = new String[7][];
		SqlParser.sqlNormalize(sql, items);
		for (int i = 0; i < items[0].length; i++) {
			if (items[2][i] == null)
				items[2][i] = filter;
			else
				items[2][i] = "(" + items[2][i] + ") and (" + filter + ")";
		}
		return toSql(items);
	}

	final static public String[] tableNamesForSql(String sql) {
		String items[][] = new String[6][];
		SqlParser.sqlNormalize(sql, items);
		if (items[1] == null || items[0] == null)
			return null;
		if (items[1].length == 1) {
			String a[] = splitString(items[1][0], ',');
			if (a != null)
				for (int i = 0; i < a.length; i++)
					a[i] = toTableName(a[i]);
			return a;
		} else {
			final java.util.List<String> v = new java.util.ArrayList<String>();
			for (int j = 0; j < items[1].length; j++) {
				String a[] = splitString(items[1][j], ',');
				if (a != null)
					for (int i = 0; i < a.length; i++)
						v.add(toTableName(a[i]));
			}
			if (v.size() == 0)
				return null;
			return v.toArray(new String[v.size()]);
		}
	}

	final static String toTableName(String name) {
		int p;
		if (name == null
				|| (p = (name = name.trim().toLowerCase()).indexOf(' ')) < 0)
			return name;
		return name.substring(0, p);
	}

	// tableNamesForSql("select * from a,b,c where xx",'[',']') : [a][b][c]
	final static public String tableNamesForSql(String sql, char prefix,
			char suffix) {
		return tableNamesForSql(sql, prefix, suffix, null);
	}

	// final static public String tableNamesForSql(String sql,char prefix,char
	// suffix,java.util.Vector to)
	// {
	// return tableNamesForSql(sql,prefix)
	// }
	final static public String tableNamesForSql(String sql, char prefix,
			char suffix, java.util.List<String> to) {
		String items[][] = new String[6][];
		SqlParser.sqlNormalize(sql, items);
		if (items[1] == null)
			return null;
		// String s = items[1][0];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < items[1].length; i++) {
			toTableNames(items[1][i], prefix, suffix, sb, to);
		}
		return sb.toString();
	}

	final static public void toTableNames(String name, char prefix,
			char suffix, StringBuffer sb, java.util.List<String> to) {
		if (name == null)
			return;
		// if(s==null) return null;
		String t;
		if (name.indexOf(',') < 0) {
			sb.append(prefix);
			sb.append(t = toTableName(name));
			sb.append(suffix);
			if (to != null)
				to.add(t);
			// return prefix+toTableName(s)+suffix;
			return;
		}
		String a[] = splitString(name, ',');
		// StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++) {
			sb.append(prefix);
			sb.append(t = toTableName(a[i]));
			if (to != null)
				to.add(t);
			sb.append(suffix);
		}
		// return sb.toString();
	}

	static public String firstTableName(String tableName) {
		if (tableName != null) {
			int p = (tableName = tableName.trim()).indexOf(',');
			if (p > 0)
				tableName = tableName.substring(0, p).trim(); // first
			p = tableName.indexOf(' ');
			if (p > 0)
				tableName = tableName.substring(0, p);
			// tableName = tableName.trim();
			for (int i = 0; i < tableName.length(); i++) {
				if (!Character.isJavaIdentifierPart(tableName.charAt(i))) {
					return null;
				}
			}
		}
		return tableName;
	}

	/**
	 * exprList = expr1,expr2,... new String[]{ expr1,expr2,... }
	 */
	static public String[] splitSqlExpr(String exprList) {
		if (exprList == null)
			return null;
		boolean inStr = false; // boolean spaceNeeded = false;
		int deepBrackets = 0;
		java.util.List<String> v = new java.util.ArrayList<String>();
		int p0 = 0;
		for (int i = 0; i < exprList.length(); i++) {
			char c = exprList.charAt(i);
			if (c == '\'') {
				inStr = !inStr;
				continue;
			}
			if (inStr)
				continue;
			// !inStr
			if (c == ')')
				deepBrackets--;
			else if (c == '(')
				deepBrackets++;
			else if (c == ',' && deepBrackets == 0) {
				v.add(exprList.substring(p0, i).trim());
				p0 = i + 1;
			}
		}
		v.add(exprList.substring(p0).trim());
		return v.toArray(new String[v.size()]);
	}

	/*
	 * @return bit 1 : sql1<>sql2 bit 2 : has macro {...} bit 4 : none sql
	 */
	public static int sameSqlColumns(String sql1, String sql2) {
		String sqlParts1[][] = new String[6][], sqlParts2[][] = new String[6][];
		if (sql1 == sql2) {
			if (sql1 == null)
				return 0;
			sql1 = sqlNormalize(sql1, sqlParts1);
			if (sqlParts1[0] == null)
				return 4;
			for (int j = 0; j < sqlParts1[0].length; j++)
				if (sqlParts1[0][j].indexOf('{') >= 0)
					return 2;
			return 0;
		}
		if (sql1 == null || sql2 == null)
			return 1;
		// if( sql1.equals(sql2) )
		// return true;
		sql1 = sqlNormalize(sql1, sqlParts1);
		sql2 = sqlNormalize(sql2, sqlParts2);
		// {
		// int n1 = sqlParts1[1]==null ? 0 : sqlParts1[1].length;
		// int n2 = sqlParts2[1]==null ? 0 : sqlParts2[1].length;
		// if( n1!=n2 ) return 1;
		// }
		{
			int n1 = sqlParts1[0] == null ? 0 : sqlParts1[0].length;
			int n2 = sqlParts2[0] == null ? 0 : sqlParts2[0].length;
			if (n1 != n2)
				return 1;
			if (n1 == 0)
				return 4;
			for (int i = 0; i < n1; i++) {
				try {
					if (sqlParts1[0][i].equals(sqlParts2[0][i])
							&& sqlParts1[1][i].equals(sqlParts2[1][i])) {
						if (sqlParts1[0][i].indexOf('{') >= 0
								|| sqlParts2[0][i].indexOf('{') >= 0)
							return 2;
					} else
						return 1;
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
		return 0;
	}

	public static final String[] splitString(String str, int istart,
			char delimiter) {
		if (str == null)
			return null;
		int sl = str.length();
		int n = 0;
		for (int i = istart; i < sl; i++)
			if (str.charAt(i) == delimiter)
				n++;
		String[] sa = new String[n + 1];
		int i = istart, j = 0;
		for (; i < sl;) {
			int iend = str.indexOf(delimiter, i);
			if (iend < 0)
				break;
			sa[j++] = str.substring(i, iend);
			i = iend + 1;
		}
		sa[j++] = str.substring(i);
		return sa;
	}

	public static final String[] splitString(String str, char delimiter) {
		if (delimiter == 0)
			return str == null ? null : new String[] { str };
		return splitString(str, 0, delimiter);
	}
	
	
	// debug
	static public void main(String args[]) throws Exception {
		int sqlType[] = new int[1];
		String tblName = getTableNameForUpdateSql("deletE   from   Xxx",
				sqlType);
		System.out.println("tblName=" + tblName + ":" + sqlType[0]);
		tblName = getTableNameForUpdateSql("deletE      Xxx", sqlType);
		System.out.println("tblName=" + tblName + ":" + sqlType[0]);
		tblName = getTableNameForUpdateSql("insert into     Xxx", sqlType);
		System.out.println("tblName=" + tblName + ":" + sqlType[0]);
		tblName = getTableNameForUpdateSql("update     Xxx", sqlType);
		System.out.println("tblName=" + tblName + ":" + sqlType[0]);

		String sql = "select a,B  ,c  from aaaa %SQLXXxxA";// union all select 2
															// union select 3
															// order by 1,2";
		sql = addSqlFilter(sql, "ddd=fff");
		System.out.println(sql);
	}
}
