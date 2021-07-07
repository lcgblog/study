package com.lcgblog.study.springboot.plugins;

import java.util.HashMap;
import java.util.Map;
import org.apache.ignite.internal.IgniteNodeAttributes;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;

public class CustomTcpDiscoverySpi extends TcpDiscoverySpi {

  private SecurityCredentials securityCredentials;

  /**
   * This method sets {@link SecurityCredentials}
   *
   * @param securityCredentials - security credentials
   */
  public void setSecurityCredentials(SecurityCredentials securityCredentials) {

    this.securityCredentials = securityCredentials;
  }

  @Override
  protected void initLocalNode(int srvPort, boolean addExtAddrAttr) {
    try {
      super.initLocalNode(srvPort, addExtAddrAttr);
      this.setSecurityCredentials();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void setSecurityCredentials() {
    if (securityCredentials != null) {

      Map<String,Object> attributes = new HashMap<>(locNode.getAttributes());
      attributes.put(IgniteNodeAttributes.ATTR_SECURITY_CREDENTIALS, securityCredentials);
      this.locNode.setAttributes(attributes);
    }
  }
}
