package ma.tdi.tinghir.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import ma.tdi.tinghir.R;
import ma.tdi.tinghir.models.Todo;

public class TodosAdapter extends BaseAdapter {

    private Context context;
    private int resourceLayout;
    private List<Todo> todos;

    public TodosAdapter(Context context, int resourceLayout, List<Todo> todos) {
        super();
        this.context = context;
        this.resourceLayout = resourceLayout;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ((Todo)getItem(position)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater
                            .from(context)
                            .inflate(resourceLayout, null , false);
        }
        RelativeLayout rlLvRoot = (RelativeLayout)convertView.findViewById(R.id.rlLvRoot);
        TextView tvLvTitre = (TextView) convertView.findViewById(R.id.tvLvTitre);
        TextView tvLvDescription = (TextView) convertView.findViewById(R.id.tvLvDescription);
        TextView tvLvExpiration = (TextView) convertView.findViewById(R.id.tvLvExpiration);

        Todo todo = (Todo)getItem(position);

        tvLvTitre.setText(todo.getTitre());
        tvLvDescription.setText(todo.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvLvExpiration.setText(sdf.format(todo.getDateExpiration()));

        if(todo.getStatus().equals("Nouvelle")) {
            rlLvRoot.setBackgroundColor(context.getResources().getColor(R.color.col_nouvelle));
        } else if(todo.getStatus().equals("En cours")) {
            rlLvRoot.setBackgroundColor(context.getResources().getColor(R.color.col_en_cours));
        } else if(todo.getStatus().equals("Terminée")) {
            rlLvRoot.setBackgroundColor(context.getResources().getColor(R.color.col_terminee));
        }

        convertView.setEnabled(true);
        return convertView;
    }
}
