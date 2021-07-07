package com.lcgblog.study.springboot.plugins.impl;

import java.io.Serializable;
import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.plugin.security.AuthenticationContext;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecuritySubject;

public class SecurityContextImpl implements SecurityContext, Serializable {

  private SecuritySubject securitySubject;

  public SecurityContextImpl(SecuritySubject securitySubject) {
    this.securitySubject = securitySubject;
  }

  public SecuritySubject subject() {
    System.out.println("return subject " + securitySubject.id() + " - " + securitySubject.login());
    return securitySubject;
  }

  public boolean taskOperationAllowed(String taskClsName, SecurityPermission perm) {
    System.out.println("taskOperationAllowed " + taskClsName);
    return true;
  }

  public boolean cacheOperationAllowed(String cacheName, SecurityPermission perm) {
    System.out.println("cacheOperationAllowed " + cacheName);
    return true;
  }

  public boolean serviceOperationAllowed(String srvcName, SecurityPermission perm) {
    System.out.println("serviceOperationAllowed " + srvcName);
    return true;
  }

  public boolean systemOperationAllowed(SecurityPermission perm) {
    System.out.println("systemOperationAllowed " + perm);
    return true;
  }
}
