denormalize-db
==============

eArk WP6 - reference implementation: generate csv from archived db

TODOs
-----
- get archived db format: Estonian buildings database
- understand given format (XML/CSV) and load
- automatically understand foreign keys, create data model using given sample data
- experiment with ways to automatically follow foreign keys
- automatically denormalize using each table as facts for once
- and/or create star schema
- see what we need to have real star schema
- define requirements for AIP level 2 (Manfred/HKI) to be useful

Future
------
- see if zohmg or Mondrian can help analysing the data
- how to we need to put data into it?
- maybe run a Mondrian on each node using a different table as facts
- send queries to CHX and Solr and HBase parallel and reduce results for user
- understand other formats like SIARD and EPP and HKI defined one.

Implementation Ideas
--------------------
- aggregations following each dimension
- dimension auto-detect by variation of column values
- dimension auto-detect by typical data, e.g. timestamp, geo-coord, city/county-name? (need dictionary)

* start with a single table
    ** keep columns with meta data, from load/format
    ** keep content of columns
    ** derive meta data from content (date or id or sth)
    ** know some facts about columns (number of unique values), which is sort of statistical data
    ** find columns that always change together e.g. "zip and city" or "city and cntry"
    ** predict fact or dimension and which dimension - tbd
    ** sort columns of dimensions
    ** aggregate fact/HBase primary column name idea, use <table>_<smallest dimension>_<next> and so on.
    
* have a table with a foreign key
    ** expand columns of foreign table into table
    ** mark new columns as meta data from original table
    
* table with more (simple) foreign keys recursively

* m2n - tbd

- how to export the denormalized data?
  This is the goal. needs to be preaggregated and sorted by different aggregators counts descending
  if any groups are found, then use each group

Sakila
------
Table: County only 1 column data
FK: Address -> city -> country
m2n: film_category

Resources
---------

* http://hstack.org/hbasecon-low-latency-olap-with-hbase/
* http://msdn.microsoft.com/en-us/library/cc505841.aspx
