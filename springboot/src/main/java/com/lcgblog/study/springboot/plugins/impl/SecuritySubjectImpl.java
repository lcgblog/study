package com.lcgblog.study.springboot.plugins.impl;

import java.net.InetSocketAddress;
import java.util.UUID;
import org.apache.ignite.plugin.security.SecurityPermissionSet;
import org.apache.ignite.plugin.security.SecuritySubject;
import org.apache.ignite.plugin.security.SecuritySubjectType;

public class SecuritySubjectImpl implements SecuritySubject {

  private UUID id;
  private SecuritySubjectType type;
  private Object login;
  private InetSocketAddress address;
  private SecurityPermissionSet permissions;

  public SecuritySubjectImpl(UUID id, SecuritySubjectType type, Object login,
      InetSocketAddress address, SecurityPermissionSet permissions) {
    this.id = id;
    this.type = type;
    this.login = login;
    this.address = address;
    this.permissions = permissions;
  }

  @Override
  public UUID id() {
    System.out.println("return id " + id);
    return id;
  }

  @Override
  public SecuritySubjectType type() {
    System.out.println("return type");
    return this.type;
  }

  @Override
  public Object login() {
    System.out.println("return login object");
    return this.login;
  }

  @Override
  public InetSocketAddress address() {
    System.out.println("return address");
    return this.address;
  }

  @Override
  public SecurityPermissionSet permissions() {
    System.out.println("return permissions");
    return this.permissions;
  }

  @Override
  public String toString() {
    return "SecuritySubjectImpl{" +
        "id=" + id +
        ", type=" + type +
        ", login=" + login +
        ", address=" + address +
        ", permissions=" + permissions +
        '}';
  }
}
