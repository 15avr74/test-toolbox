package be.etnic.qa.selenium.accessibility.samples;

import org.flywaydb.core.Flyway;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class Deployments {

    // private static final String WEBAPP_SRC = "src/main/webapp";

    public static WebArchive createDeployment() {

        processSqlScript();

        WebArchive archive = createArchive();
        return archive;

    }

    private static void processSqlScript() {

        // Flyway flyway =
        // Flyway.configure().dataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
        // "sa", "sa").load();
        Flyway flyway = Flyway.configure().dataSource("jdbc:db2://127.0.0.1:50000/REFA:currentSchema=REFA;", "db2admin", "Eb5HzMTGEOwtHPrTkwSq").load();

        flyway.info();
        flyway.clean();
        flyway.info();
        flyway.migrate();

    }

    private static WebArchive createArchive() {

        WebArchive archive = null;

        archive = ShrinkWrap.create(WebArchive.class, "archive.war").addAsResource("src/test/resources/refa.war");

        return archive;

    }

}