package com.jyzx.common;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jyzx.dao.ICommonDao;
import com.jyzx.model.AbstractModel;




@Service("CommonService")
public class CommonService   {
	
	protected  Logger logger = Logger.getLogger(getClass());  
	@Autowired
	@Qualifier("CommonDao")
	protected ICommonDao commonDao;
	
	
	
	protected void flush() {
		commonDao.getSession().flush();
	}
	

	
	//保存操作前处理，可通过throw new RuntimeException阻止保存
	protected void beforeSaveEntityBean() throws Exception {
	}
	
	//保存后的其他处理，可通过throw new RuntimeException阻止保存
	protected void afterSaveEntityBean(AbstractModel bean) throws Exception {
	}
	
	//设置bean数据前处理，可通过throw new RuntimeException阻止保存
	protected void beforeSetBeanData(AbstractModel bean) throws Exception {
	}
	
	//设置bean数据后处理，可通过throw new RuntimeException阻止保存
	protected void afterSetBeanData(AbstractModel bean) throws Exception {
	}
	
	//保存grid行数据前处理，可通过throw new RuntimeException阻止保存
	protected void beforeSaveGridRowData(Map<String,Object> map, String setTypeName, AbstractModel parentBean, Serializable parentPkValue, String setName) throws Exception {
	}
	
	//保存grid行数据前处理，可通过throw new RuntimeException阻止保存
	protected void afterSaveGridRowData(Map<String,Object> map, String setTypeName, AbstractModel parentBean, Serializable parentPkValue, String setName) throws Exception {
	}
	
	
	
	
	protected boolean fieldToken = false;
	protected void setFieldToken(boolean fieldToken) {
		this.fieldToken = fieldToken;
	}
	


}
