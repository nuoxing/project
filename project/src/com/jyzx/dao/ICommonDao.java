package com.jyzx.dao;


import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.jyzx.model.AbstractModel;
import com.jyzx.util.FilterParam;

public interface ICommonDao {
	public final int HQL       = 0;
	public final int SQL       = 1;
	public final int SQL_ARRAY = SQL | 2;
	public final int SQL_MAP   = SQL | 0;
    
	public Session getSession();
	
	public SessionFactory getSessFactory();
	
	public <T extends AbstractModel> T save(T model);
    
    public <T extends AbstractModel> void saveOrUpdate(T model);
    
    public <T extends AbstractModel> void update(T model);
    
    public <T extends AbstractModel> void merge(T model);
    
    public <T extends AbstractModel, PK extends Serializable> void delete(Class<T> entityClass, PK id);
    
    public <T extends AbstractModel> void deleteObject(T model);
    
    public <T extends AbstractModel, PK extends Serializable> T get(Class<T> entityClass, PK id);
    
    public <T extends AbstractModel, PK extends Serializable> T load(Class<T> entityClass, PK id);
    
    public <T extends AbstractModel> int countAll(Class<T> entityClass);
    
    public <T extends AbstractModel> List<T> listAll(Class<T> entityClass);
    
    
    public List<?> listAll(String hql, int type, FilterParam... parms);
    public List<?> listAll(Query query, int type, FilterParam... parms);
    
    public <T extends AbstractModel> int executeUpdate(String hql, FilterParam... parms);
    public <T extends AbstractModel> int executeUpdate(Query query, FilterParam... parms);
    
    
    // == sql操作 ==
    public int countAll(String sql, FilterParam... parms);
    public int countAll(Query query, FilterParam... parms);
    
    public List<?> listAll(String sql, FilterParam... parms);	//<T extends Map<String, ?>> List<T>
    public List<?> listAll(Query query, FilterParam... parms);
    
    
    public int executeUpdateBySql(String sql, FilterParam... parms);
    public int executeUpdateBySql(Query query, FilterParam... parms);
    
    public Object querySql(String sql, FilterParam... parms);
    public Object querySql(Query query, FilterParam... parms);
    
    public String getDatabaseProductName();
    
    
    //创建查询
    public Query createQuery(String sql, int type);
}
