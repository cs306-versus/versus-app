package com.github.versus.db;

import java.util.List;
import java.util.concurrent.Future;

/**
 * This interface abstracts all the entities used to manage the databases we will be using in the project.
 *
 * @param <D> the stored data type
 */
public interface DataBaseManager<D> {

    /**
     * Inserts to entry the database.
     *
     * @param data
     */
    Future<Boolean> insert(D data);

    /**
     * Retrieves the entry with the given id.
     *
     * @param id
     * @return
     */
    Future<D> fetch(String id);

    /**
     * Deletes the entry with the given id from the database.
     *
     * @param id
     * @return
     */
    Future<Boolean> delete(String id);


}

