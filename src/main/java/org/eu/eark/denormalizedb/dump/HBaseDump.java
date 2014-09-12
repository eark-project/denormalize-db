package org.eu.eark.denormalizedb.dump;

public class HBaseDump {

    // add HBASE dependency to pom in this project, version is coming from super pom
    // put into hbase. use getUniqueColumnName as name and value(i) as value.
    // for row key use ID Column value(i).

    // create a junit test which loads a table to test and run the dump
    // load a database table, best use city or addresses from sakila examples, should not be too complex
    // so running the unit test will dump the table into hbase then.
    // the dumper class SHOULD NOT contain load or parse logic.
    
}
