package com.jbslade.lang.mashup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbslade.lang.mashup.R;
import com.jbslade.lang.mashup.interfaces.ListBasicAdapterPopulateFields;

import java.util.ArrayList;

/**
 * Created by Justin on 10/9/13.
 */
public class ListBasicAdapter<T extends ListBasicAdapterPopulateFields> extends BaseAdapter
{
    private ArrayList<T> m_listElems;

    public ListBasicAdapter()
    {
        m_listElems = new ArrayList<T>();
    }

    public boolean addElemsToList(ArrayList<T> listElems)
    {
        boolean added = m_listElems.addAll(listElems);
        this.notifyDataSetChanged();
        return added;
    }


    @Override
    public int getCount()
    {
        return m_listElems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return m_listElems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        /*
         * A ViewHolder keeps references to children views to avoid unnecessary calls to
         * findViewById() on each row.
         */
        ViewHolder holder;

        /* When convertView is not null, we can reuse it directly, there is no need
         to reinflate it. We only inflate a new View when the convertView supplied
         by ListView is null.*/
        if (convertView == null)
        {
            holder = new ViewHolder();
            Context context = parent.getContext();
            convertView = reinflateViewHolder(context, holder);
        }
        else
        {
            // Get the ViewHolder back to get fast access to the TextView and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        //Populate the row fields from element Object
        T elemToShow = this.m_listElems.get(position);
        holder.m_header1.setText(elemToShow.getHeader1Text());
        holder.m_header2a.setText(elemToShow.getHeader2aText());
        holder.m_header2b.setText(elemToShow.getHeader2bText());

        return convertView;
    }

    private View reinflateViewHolder(Context context, ViewHolder holder)
    {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View reinflatedRowView = inflater.inflate(R.layout.list_base_adapter_row, null);
        // Creates a ViewHolder and store references to the two children views
        // we want to bind data to.
        holder.m_icon =
                (ImageView) reinflatedRowView.findViewById(R.id.ImageView_ListBasicRow_Icon);
        View linearLayout = reinflatedRowView.findViewById(R.id.LinearLayout_ListBasicRow);
        holder.m_header1 =
                (TextView) linearLayout.findViewById(R.id.TextView_ListBasicRow_Header1);
        holder.m_header2a =
                (TextView) linearLayout.findViewById(R.id.TextView_ListBasicRow_Header2_a);
        holder.m_header2b =
                (TextView) linearLayout.findViewById(R.id.TextView_ListBasicRow_Header2_b);

        // Set ViewHolder to tag of View to retrieve later.
        reinflatedRowView.setTag(holder);
        return reinflatedRowView;
    }

    /**
     * @Description Used for efficiency to hold views. A ViewHolder
     * keeps references to children views to avoid unnecessary calls
     * to findViewById() on each row.
     */
    static class ViewHolder
    {
        public ImageView m_icon;
        public TextView m_header1;
        public TextView m_header2a;
        public TextView m_header2b;
    }
}
