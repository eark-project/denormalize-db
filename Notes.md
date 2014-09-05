Implementation Notes
--------------------

* aggregations following each dimension
* dimension auto-detect by variation of column values
* dimension auto-detect by typical data, e.g. time-stamp, geo-coordinates, city/county name? (need dictionary)

* start with a single table
  * [done] keep columns with meta data, from load/format
  * [done] keep content of columns
  * derive meta data from content (date or id etc)
  * know some facts about columns (number of unique values), which is sort of statistical data
  * find columns that always change together e.g. "zip and city" or "city and country"
  * predict fact or dimension and which dimension - tbd
  * sort columns of dimensions
  * [done] aggregate fact/HBase primary column name idea, use `<table>_<smallest dimension>_<next>` and so on.

* have a table with a foreign key
  * [done] expand columns of foreign table into table
  * [done] mark new columns as meta data from original table

* table with more (simple) foreign keys
  * recursively

* m2n
  * tbd

* how to export the de-normalised data?
  This is the goal. needs to be pre-aggregated and sorted by different aggregators counts descending
  if any groups are found, then use each group
