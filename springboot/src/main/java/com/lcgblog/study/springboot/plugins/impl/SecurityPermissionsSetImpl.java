package com.lcgblog.study.springboot.plugins.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecurityPermissionSet;
import org.springframework.lang.Nullable;

public class SecurityPermissionsSetImpl implements SecurityPermissionSet {

  public boolean defaultAllowAll() {
    return true;
  }

  public Map<String, Collection<SecurityPermission>> taskPermissions() {
    /**
     * One can define condition based permissions for the tasks.
     */
    return Collections.emptyMap();
  }

  public Map<String, Collection<SecurityPermission>> cachePermissions() {
    /**
     * One can define cache name based permissions
     */
    return Collections.emptyMap();
  }

  public Map<String, Collection<SecurityPermission>> servicePermissions() {
    /**
     * One can define service name based permissions
     */
    return Collections.emptyMap();
  }

  @Nullable
  public Collection<SecurityPermission> systemPermissions() {
    return Collections.emptyList();
  }
}