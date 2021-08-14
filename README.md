# JOOQ Example

Run Docker Postgres `docker-compose -f src/postgres/docker-compose.yml up`  
Generate JOOQ Meta:

```java
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
```

Run JOOQ queries:

```java
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
}
```