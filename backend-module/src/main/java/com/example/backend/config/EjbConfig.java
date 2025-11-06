package com.example.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.Optional;

@Data
@Configuration
@EnableConfigurationProperties(EjbConfig.class)
@ConfigurationProperties(prefix = "ejb")
public class EjbConfig {

  private String host;
  private Integer port;
  private String user;
  private String password;

  @Bean
  public InitialContext ejbInitialContext() throws Exception {

    String safeUser = Optional.ofNullable(user).orElse("admin");
    String safePassword = Optional.ofNullable(password).orElse("Admin#123");
    Hashtable<String, Object> props = new Hashtable<>();
    props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
    props.put(Context.PROVIDER_URL, "remote+http://127.0.0.1:8080");
    props.put(Context.SECURITY_PRINCIPAL, safeUser);
    props.put(Context.SECURITY_CREDENTIALS, safePassword);
    props.put("wildfly.naming.client.connect.options.org.xnio.Options.SSL_ENABLED", "false");
    props.put("wildfly.naming.client.connect.options.org.xnio.Options.SASL_MECHANISMS", "DIGEST-MD5");
    props.put("wildfly.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
    props.put("wildfly.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    props.put("org.wildfly.ejb.client.scoped.context", "true");


    return new InitialContext(props);
  }

}
