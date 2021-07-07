package com.lcgblog.study.springboot.plugins;

import com.lcgblog.study.springboot.plugins.impl.SecurityPlugin;
import java.io.Serializable;
import java.util.UUID;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.internal.IgniteKernal;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.plugin.CachePluginContext;
import org.apache.ignite.plugin.CachePluginProvider;
import org.apache.ignite.plugin.ExtensionRegistry;
import org.apache.ignite.plugin.IgnitePlugin;
import org.apache.ignite.plugin.PluginContext;
import org.apache.ignite.plugin.PluginProvider;
import org.apache.ignite.plugin.PluginValidationException;
import org.springframework.lang.Nullable;

/**
 * This is implementation of {@link PluginProvider} which creates instance of {@link SecurityPlugin}
 */
public class SecurityPluginProvider implements PluginProvider<SecurityPluginConfiguration> {

  private IgnitePlugin authenticationPlugin;

  public String name() {
    return "Security Plugin";
  }

  public String version() {
    return "1.0.1";
  }

  public String copyright() {
    return "BugDbug ©2019";
  }

  public <T extends IgnitePlugin> T plugin() {
    return (T) authenticationPlugin;
  }

  public void initExtensions(PluginContext ctx, ExtensionRegistry registry) throws IgniteCheckedException {
    authenticationPlugin = new SecurityPlugin();
  }

  @Nullable
  public GridSecurityProcessor createComponent(PluginContext pluginContext, Class cls) {
    if (cls.equals(GridSecurityProcessor.class)) {
      return new SecurityProcessor(((IgniteKernal) pluginContext.grid()).context());
    }
    return null;
  }

  public CachePluginProvider createCacheProvider(CachePluginContext ctx) {
    return null;
  }

  public void start(PluginContext ctx) throws IgniteCheckedException {
    System.out.println("Plugin start");
  }

  public void stop(boolean cancel) throws IgniteCheckedException {
    System.out.println("Plugin cancel");
  }

  public void onIgniteStart() throws IgniteCheckedException {
    System.out.println("onIgniteStart");
  }

  public void onIgniteStop(boolean cancel) {
    System.out.println("onIgniteStop");
  }

  @Nullable
  public Serializable provideDiscoveryData(UUID nodeId) {
    return null;
  }

  public void receiveDiscoveryData(UUID nodeId, Serializable data) {
    System.out.println("receiveDiscoveryData");
  }

  public void validateNewNode(ClusterNode node) throws PluginValidationException {
    System.out.println("validateNewNode");
  }
}
