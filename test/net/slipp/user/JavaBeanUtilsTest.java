package net.slipp.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaBeanUtilsTest {
	static final Logger logger = LoggerFactory.getLogger(JavaBeanUtilsTest.class);
	
	@Test
	public void populate() throws Exception {
		final Map<String, String[]> params = new HashMap<>();
	    params.put("userName", new String[]{"userA"});
	    params.put("password", new String[]{"secrect"});
	    params.put("id", new String[]{"10"});
	    final JavaBean javaBean = new JavaBean();
	    BeanUtilsBean.getInstance().populate(javaBean, params);
	    logger.debug(javaBean.getUserName());
	    logger.debug(javaBean.getPassword());
	    logger.debug(javaBean.getId() + "");
	    
	}
}
