package com.lcgblog.study.springboot.plugins;

import com.lcgblog.study.springboot.plugins.impl.SecurityContextImpl;
import com.lcgblog.study.springboot.plugins.impl.SecurityPermissionsSetImpl;
import com.lcgblog.study.springboot.plugins.impl.SecuritySubjectImpl;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.GridKernalContext;
import org.apache.ignite.internal.processors.GridProcessorAdapter;
import org.apache.ignite.internal.processors.security.GridSecurityProcessor;
import org.apache.ignite.internal.processors.security.SecurityContext;
import org.apache.ignite.plugin.PluginConfiguration;
import org.apache.ignite.plugin.security.AuthenticationContext;
import org.apache.ignite.plugin.security.SecurityCredentials;
import org.apache.ignite.plugin.security.SecurityPermission;
import org.apache.ignite.plugin.security.SecuritySubject;
import org.apache.ignite.plugin.security.SecuritySubjectType;
import org.springframework.lang.Nullable;

/**
 * This is implementation of {@link GridSecurityProcessor} which  handles cluster security
 */
public class SecurityProcessor extends GridProcessorAdapter implements GridSecurityProcessor {

  private IgniteConfiguration config;
  private SecurityPluginConfiguration securityPluginConfiguration;
  private Map<UUID, SecuritySubject> authenticatedSubjects = new HashMap<>();

  /**
   * @param ctx Kernal context.
   */
  protected SecurityProcessor(GridKernalContext ctx) {
    super(ctx);
    config = ctx.config();
    PluginConfiguration[] pluginConfigurations = this.config.getPluginConfigurations();
    if (pluginConfigurations != null) {
      for (PluginConfiguration pluginConfiguration : pluginConfigurations) {
        if (pluginConfiguration instanceof SecurityPluginConfiguration) {
          securityPluginConfiguration = (SecurityPluginConfiguration) pluginConfiguration;
        }
      }
    }
  }

  /**
   * @param node
   * @param cred
   * @return
   * @throws IgniteCheckedException
   */
  public SecurityContext authenticateNode(ClusterNode node, SecurityCredentials cred) throws IgniteCheckedException {

    SecurityCredentials userSecurityCredentials;
    if (securityPluginConfiguration != null) {
      if ((userSecurityCredentials = securityPluginConfiguration.getSecurityCredentials()) != null) {
        System.out.println("authenticateNode " + node + " " + cred.getLogin());
        SecuritySubjectImpl securitySubjectImpl = new SecuritySubjectImpl(node.id(), null, cred.getLogin(), null, new SecurityPermissionsSetImpl());
        return userSecurityCredentials.equals(cred) ? new SecurityContextImpl(securitySubjectImpl) : null;
      }
    }

    if (cred == null)
      return new SecurityContextImpl(new SecuritySubjectImpl(node.id(), null, null, null, new SecurityPermissionsSetImpl()));

    return null;

  }

  public boolean isGlobalNodeAuthentication() {
    return false;
  }

  public SecurityContext authenticate(AuthenticationContext ctx) throws IgniteCheckedException {
    System.out.println("authenticate" + ctx.credentials().getLogin() + " - " + ctx.credentials().getPassword());
    SecuritySubjectImpl securitySubject = new SecuritySubjectImpl(ctx.subjectId(),
        ctx.subjectType(), ctx.credentials().getLogin(), ctx.address(),
        new SecurityPermissionsSetImpl());
    SecurityCredentials userSecurityCredentials;
    if (securityPluginConfiguration != null) {
      if ((userSecurityCredentials = securityPluginConfiguration.getSecurityCredentials()) != null) {
        System.out.println("authenticate  " + securitySubject);
        return userSecurityCredentials.equals(ctx.credentials()) ? new SecurityContextImpl(securitySubject) : null;
      }
    }

    return new SecurityContextImpl(securitySubject);
  }

  public Collection<SecuritySubject> authenticatedSubjects() throws IgniteCheckedException {
    System.out.println("authenticatedSubject");
    return authenticatedSubjects.values();
  }

  public SecuritySubject authenticatedSubject(UUID subjId) throws IgniteCheckedException {
    System.out.println("authenticatedSubject subjId " + subjId);
    return authenticatedSubjects.get(subjId);
  }

  public void authorize(String name, SecurityPermission perm, @Nullable SecurityContext securityCtx) throws SecurityException {
    //TODO - Here we can check permissions for given cache operation
    System.out.println("authorize " + name + " " + perm + " " + securityCtx.subject());
    if(securityCtx.subject().type() == SecuritySubjectType.REMOTE_CLIENT){
      throw new SecurityException("No permission on " + perm);
    }
  }

  public void onSessionExpired(UUID subjId) {
    System.out.println("one session expired " + subjId);
    authenticatedSubjects.remove(subjId);
  }

  public boolean enabled() {
    return true;
  }

}
