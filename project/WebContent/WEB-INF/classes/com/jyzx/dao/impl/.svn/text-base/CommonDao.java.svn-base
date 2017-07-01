package com.jyzx.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jyzx.dao.ICommonDao;
import com.jyzx.model.AbstractModel;
import com.jyzx.util.FilterParam;
import com.jyzx.util.Obj2JsonUtil;
import com.jyzx.util.SqlParser;


/**
 * @author LinDongHai
 * 
 * @version 1.0, 2012-12-29
 * 
 * <p>Dao类的基类(支持hibernate和sql方式)</p>
 */
@Component("CommonDao")
public class CommonDao implements ICommonDao {

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessFactory() {
		return sessionFactory;
	}

	public <T extends AbstractModel> T save(T model) {
		getSession().save(model);
		return model;
	}

	public <T extends AbstractModel> void saveOrUpdate(T model) {
		getSession().saveOrUpdate(model);
	}

	public <T extends AbstractModel> void update(T model) {
		getSession().update(model);
	}

	public <T extends AbstractModel> void merge(T model) {
		getSession().merge(model);
	}

	public <T extends AbstractModel, PK extends Serializable> void delete(
			Class<T> entityClass, PK id) {
		//getSession().delete(get(entityClass, id));
		getSession().delete(load(entityClass, id));
	}

	public <T extends AbstractModel> void deleteObject(T model) {
		getSession().delete(model);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel, PK extends Serializable> T get(
			Class<T> entityClass, PK id) {
		return (T) getSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel, PK extends Serializable> T load(
			Class<T> entityClass, PK id) {
		return (T) getSession().load(entityClass, id);
	}

	public <T extends AbstractModel> int countAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractModel> List<T> listAll(Class<T> entityClass) {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria.list();
	}

	
	//执行hql更新语句
	public <T extends AbstractModel> int executeUpdate(String hql, FilterParam... parms) {
		return executeUpdate(createQuery(hql, HQL), parms);
	}
	public <T extends AbstractModel> int executeUpdate(Query query, FilterParam... parms) {
		setQueryParameter(query, parms);
		int updatedEntities = query.executeUpdate();
		
		return updatedEntities;
	}
	
	//查询hql或sql，将所有数据作为list返回
	@SuppressWarnings("unchecked")
	public List<?> listAll(String hql, int type, FilterParam... parms) {
		return listAll(createQuery(hql, (type & 1)), type, parms);
	}
	@SuppressWarnings("unchecked")
	public List<?> listAll(Query query, int type, FilterParam... parms) {
		//结果默认转换为Map
		if ((type & 3) == SQL_MAP) {
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		}
		
		//设置sql或hql参数
		setQueryParameter(query, parms);

		List<?> list = query.list();
		if (type == SQL) {
			Obj2JsonUtil.formatKeyToLowerCase(list);	//转换字段名为小写
		}
		
		return list;
	}
	
	
	// ==sql查询==
	//count参数中sql并返回
	public int countAll(String sql, FilterParam... parms) {
		String sqlCount = null;
		
		//sql server去掉子查询的order by
		String items[][] = new String[7][];
		SqlParser.sqlNormalize(sql, items);
		items[5] = null;
		
		if (items[0].length > 1 || !Obj2JsonUtil.isNull(items[3][0])) {	//是union、group by类的sql 
			sqlCount = "select count(1) as cnt from (" + SqlParser.toSql(items) + ") aa_2";
		} else {
			items[0][0] = "count(1) as cnt";
			sqlCount = SqlParser.toSql(items);
		}
		items = null;
	
		Query query = getSession().createSQLQuery(sqlCount).addScalar("cnt", LongType.INSTANCE);
		return countAll(query, parms);
	}
	public int countAll(Query query, FilterParam... parms) {		
		//设置查询sql参数
		setQueryParameter(query, parms);
		
		Long cnt = (Long)query.uniqueResult();
		
		return cnt.intValue();
	}
	
	//sql查询，返回所有数据(List<Map>或List<数组>)
	public List<?> listAll(String sql, FilterParam... parms) {
		return listAll(sql, SQL, parms);
	}
	public List<?> listAll(Query query, FilterParam... parms) {
		return listAll(query, SQL, parms);
	}



	
	//执行sql更新语句
	public int executeUpdateBySql(String sql, FilterParam... parms) {
		return executeUpdateBySql(createQuery(sql, SQL), parms);
	}
	public int executeUpdateBySql(Query query, FilterParam... parms) {
		//设置查询sql参数
		setQueryParameter(query, parms);
		
		return query.executeUpdate();
	}
	
	//查询返回单个值
	public Object querySql(String sql, FilterParam... parms) {
		return querySql(createQuery(sql, SQL), parms);
	}
	public Object querySql(Query query, FilterParam... parms) {		
		//设置查询sql参数
		setQueryParameter(query, parms);
		
		return query.uniqueResult();
	}
	
	//设置查询sql参数
	protected void setQueryParameter(Query query, FilterParam... parms) {
		if (query == null || parms == null || parms.length == 0)
			return;
		
		for (FilterParam para : parms) {
			//System.out.println(para.getName() + ":" + para.getValue() + "," + para.getType());
			if ("date".equalsIgnoreCase(para.getType()))
				query.setParameter(para.getName(), para.getValue(), TimestampType.INSTANCE);//DateType.INSTANCE
			else if("list".equalsIgnoreCase(para.getType()))
				query.setParameterList(para.getName(), para.getVaflues());
			else
				query.setParameter(para.getName(), para.getValue(), StringType.INSTANCE);
		}
	}
	
	//取得数据库产品名
	private String databaseProductName = "";
	public String getDatabaseProductName() {
		try {
			if (databaseProductName.equals("") && 
				sessionFactory instanceof SessionFactoryImpl) {
				java.sql.DatabaseMetaData dmd = ((SessionFactoryImpl)sessionFactory).getConnectionProvider().getConnection().getMetaData();
				databaseProductName = dmd.getDatabaseProductName() + " " + dmd.getDatabaseProductVersion();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return databaseProductName;
	}
	
	
	//创建查询
	public Query createQuery(String sql, int type) {
		return (type & 1) == HQL ? 
			getSession().createQuery(sql) : 
			getSession().createSQLQuery(sql);
	}
}
