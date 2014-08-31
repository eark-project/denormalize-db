Implementation Notes
====================

Implementation Steps
--------------------

* aggregations following each dimension
* dimension auto-detect by variation of column values
* dimension auto-detect by typical data, e.g. timestamp, geo-coord, city/county-name? (need dictionary)

* start with a single table
    ** [done] keep columns with meta data, from load/format
    ** [done] keep content of columns
    ** derive meta data from content (date or id etc)
    ** know some facts about columns (number of unique values), which is sort of statistical data
    ** find columns that always change together e.g. "zip and city" or "city and country"
    ** predict fact or dimension and which dimension - tbd
    ** sort columns of dimensions
    ** [done] aggregate fact/HBase primary column name idea, use <table>_<smallest dimension>_<next> and so on.
    
* have a table with a foreign key
    ** [done] expand columns of foreign table into table
    ** [done] mark new columns as meta data from original table
    
* table with more (simple) foreign keys 
    ** recursively

* m2n 
    ** tbd

- how to export the de-normalised data?
  This is the goal. needs to be pre-aggregated and sorted by different aggregators counts descending
  if any groups are found, then use each group


Design
------

A model of the tables and their relations is created during load.
The model contains classes like `Table`, `Column` and so on. Often there is extra
data or meta-data for the classes as well, e.g. `TableData` or `ColumnMetaData`. In case of
(simple) analytical functions there is an extra class for that, e.g. `ColumnAnalytics`.
After the model has been loaded, the references are defined and the chosen table is
exploded/flattened into a single table.


Sakila
------

To start exploring de-normalising relations I need a simple schema with a few relations.
The schema needs to have some data and should be freely accessible. The Sakila example 
database was originally developed for MySQL as part of the documentation. 
See https://code.google.com/p/sakila-sample-database-ports/

Simple Table: County only 1 data column. 
Foreign Keys: Address -> City -> Country
m2n: film_category

Resources
---------

* http://hstack.org/hbasecon-low-latency-olap-with-hbase/
* http://msdn.microsoft.com/en-us/library/cc505841.aspx
