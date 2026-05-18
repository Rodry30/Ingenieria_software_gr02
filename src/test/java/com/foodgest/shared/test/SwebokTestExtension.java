package com.foodgest.shared.test;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extension JUnit 5 para cumplir con el Plan de Gestion de Calidad SWEBOK.
 * Registra defectos en logs (simulando Jira) cuando un test falla.
 */
public class SwebokTestExtension implements TestWatcher {

    private static final Logger log = LoggerFactory.getLogger(SwebokTestExtension.class);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        log.error("\n=======================================================");
        log.error("[JIRA-DEFECT-SIMULATOR] TEST FALLIDO: {}", testName);
        log.error("TAXONOMIA: Defecto introducido en fase de Implementacion.");
        log.error("TIPO: Error en validacion / Logica de negocio.");
        log.error("CAUSA RAIZ: {}", cause.getMessage());
        log.error("ACCION: Defecto registrado para calculo de densidad de defectos por KLOC.");
        log.error("=======================================================\n");
    }
}
