package ma.tdi.tinghir.dao;

import ma.tdi.tinghir.R;
import ma.tdi.tinghir.models.Todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class Data {

    private static List<Todo> todos = new ArrayList<>();
    private static Activity activity;

    public static List<Todo> getTodos() {
        return todos;
    }

    public static void setActivity(Activity activity) {
        Data.activity = activity;
    }

    public static Todo existe(long id) {
        for (Todo todo : todos) {
            if(todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    public static void ajouter(Todo todo) {
        long id = todo.getId();
        if(existe(id) == null) {
            todos.add(todo);
            return;
        }
        throw new IllegalArgumentException(
            activity
                .getResources()
                .getString(R.string.todo_existe)
        );
    }

    public static void supprimer(long id) {
        Todo todo = existe(id);
        if(todo != null) {
            todos.remove(todo);
            return;
        }
        throw new IllegalArgumentException(
                activity
                .getResources()
                .getString(R.string.todo_n_existe_pas)
        );
    }

    public static void modifier(Todo todo) {
        Todo old = existe(todo.getId());
        if(old != null) {
            old.setTitre(todo.getTitre());
            old.setDescription(todo.getDescription());
            old.setDateExpiration(todo.getDateExpiration());
            old.setStatus(todo.getStatus());
            return;
        }
        throw new IllegalArgumentException(
                activity
                .getResources()
                .getString(R.string.todo_n_existe_pas)
        );
    }


}
