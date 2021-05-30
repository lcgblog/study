package com.lcgblog.study.ignite.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;

public class MyLifecycleBean implements LifecycleBean {

  private Log log = LogFactory.getLog(MyLifecycleBean.class);

  @Override
  public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
    log.info("-------------------- received an event " + evt);
  }
}
