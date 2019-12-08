package database.dao;

import java.util.List;

public interface DAO<T> {
    void add(T item);
    void update(T item);
    void delete(T item);
    T get(int id);
    List<T> getAll();
}
