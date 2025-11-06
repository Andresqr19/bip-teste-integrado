package com.example.backend.config;

import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import static org.junit.jupiter.api.Assertions.*;

class EjbConfigTest {

  @Test
  void deveCriarInitialContextComParametrosPadrao() throws Exception {
    EjbConfig config = new EjbConfig();
    config.setUser(null);
    config.setPassword(null);

    InitialContext ctx = config.ejbInitialContext();
    assertNotNull(ctx);
  }

  @Test
  void deveUsarCredenciaisInformadas() throws Exception {
    EjbConfig config = new EjbConfig();
    config.setUser("meuUser");
    config.setPassword("meuPass");

    InitialContext ctx = config.ejbInitialContext();
    assertNotNull(ctx);
    assertEquals("meuUser", ctx.getEnvironment().get(Context.SECURITY_PRINCIPAL));
    assertEquals("meuPass", ctx.getEnvironment().get(Context.SECURITY_CREDENTIALS));
  }
}
