package com.github.srcmaxim;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;
import static org.jooq.impl.DSL.select;
import static org.jooq.impl.DSL.using;

import com.github.srcmaxim.jooq.Tables;
import java.sql.Connection;
import java.sql.DriverManager;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.codegen.GenerationTool;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;

public class Main {

  public static void main(String[] args) throws Exception {
    String url = "jdbc:postgresql://localhost:5432/jooq_db";
    String user = "jooq";
    String password = "jooq";

    generateJooqMeta(url, user, password);

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      Settings settings = SettingsTools.defaultSettings()
          .withExecuteLogging(true);
      DSLContext create = using(conn, SQLDialect.POSTGRES, settings);
      Result<Record> selectFromCities = create.select().from(Tables.CITY).fetch();

      for (Record r : selectFromCities) {
        Long id = r.getValue(Tables.CITY.CITY_ID);
        String name = r.getValue(Tables.CITY.NAME);
        String state = r.getValue(Tables.CITY.STATE);

        System.out.println("ID: " + id + " name: " + name + " state: " + state);
      }

      var cityIds = name("city_ids").as(
          select(field(Tables.CITY.CITY_ID.as("id")))
              .from(Tables.CITY)
              .where(Tables.CITY.STATE.eq("NC")));

      Result<Record> selectFromCityWhereStateInNC = create.with(cityIds)
          .select()
          .from(Tables.CITY)
          .where(
              Tables.CITY.CITY_ID.in(select(field("id")).from(cityIds).asField()))
          .fetch();

      for (Record r : selectFromCityWhereStateInNC) {
        Long id = r.getValue(Tables.CITY.CITY_ID);
        String name = r.getValue(Tables.CITY.NAME);
        String state = r.getValue(Tables.CITY.STATE);

        System.out.println("ID: " + id + " name: " + name + " state: " + state);
      }
    }

  }

  private static void generateJooqMeta(String url, String user, String password) throws Exception {
    Configuration configuration = new Configuration()
        .withJdbc(new Jdbc()
            .withDriver("org.postgresql.Driver")
            .withUrl(url)
            .withUser(user)
            .withPassword(password)
        )
        .withGenerator(new Generator()
            .withDatabase(new Database()
                .withName("org.jooq.meta.postgres.PostgresDatabase")
                .withIncludes(".*")
                .withInputSchema("public")
            )
            .withTarget(new Target()
                .withPackageName("com.github.srcmaxim.jooq")
                .withDirectory("/src/main/java")
            )
        );
    GenerationTool.generate(configuration);
  }

}