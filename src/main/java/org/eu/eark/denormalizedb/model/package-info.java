/**
 * The model is the structure which holds the data and relations of an archived database in memory.
 * It is the intermediate format between load and dump. 
 * <p>
 * A model of the tables and their relations is created during load. The model contains classes 
 * like `Table`, `Column` and so on. Often there is extra data or meta-data for the classes as 
 * well, e.g. `TableData` or `ColumnMetaData`. In case of (simple) analytical functions there is 
 * an extra class for that, e.g. `ColumnAnalytics`. After the model has been loaded, the 
 * references are defined and the chosen table is exploded/flattened into a single table.
 */
package org.eu.eark.denormalizedb.model;
