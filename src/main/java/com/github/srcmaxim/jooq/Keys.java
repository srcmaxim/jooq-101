/*
 * This file is generated by jOOQ.
 */
package com.github.srcmaxim.jooq;


import com.github.srcmaxim.jooq.tables.City;
import com.github.srcmaxim.jooq.tables.records.CityRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CityRecord> CITY_PKEY = Internal.createUniqueKey(City.CITY, DSL.name("city_pkey"), new TableField[] { City.CITY.CITY_ID }, true);
}
