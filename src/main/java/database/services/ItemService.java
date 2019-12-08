package database.services;

import java.util.List;

public interface ItemService<T> {
    public void add(T t);
    public void update(T t);
    public void delete(T t);
    public T get(int id);
    public List<T> getAll();
}
