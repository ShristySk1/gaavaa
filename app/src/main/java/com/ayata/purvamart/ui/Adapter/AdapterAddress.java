package com.ayata.purvamart.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayata.purvamart.R;
import com.ayata.purvamart.data.Model.ModelAddress;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.MyViewHolder> {

    private Context context;
    private List<ModelAddress> listitem;
    private static OnItemClickListener onItemClickListener;

    public AdapterAddress(Context context, List<ModelAddress> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_shipping_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelAddress modelAddress = listitem.get(position);
        String streetAddress = modelAddress.getStreetName();
        String city = modelAddress.getCity();
        String country = modelAddress.getCountry();
        String postalCode = modelAddress.getPostalCode();

        //required in view
        String address = "Address: " + streetAddress + "," + city + "," + country;
        String phone = modelAddress.getContactNumber();
        String name = modelAddress.getName();
        holder.address.setText(address);
        holder.name.setText(name);
        holder.phone.setText(phone);

    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, address;
        ImageView ivEdit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onEditClick(getAdapterPosition(), listitem.get(getAdapterPosition()));

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onAddressClick(listitem.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onEditClick(int position, ModelAddress modelAddress);

        void onAddressClick(ModelAddress modelAddress);
    }

    public static void setListener(OnItemClickListener listeners) {
        onItemClickListener = listeners;
    }
}
