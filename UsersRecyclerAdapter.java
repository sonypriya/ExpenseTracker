package com.squareandcube.expensetracker;

/**
 * Created by SRI on 02/12/2017.
 */

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PAVAN KUMAR on 10/26/2017.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position)
    {
        holder.textViewFirstName.setText(listUsers.get(position).getFirstname());
        holder.textViewLastName.setText(listUsers.get(position).getLastname());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        //  holder.textViewMobileNumber.setText(listUsers.get(position).getMobileNumber());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
        holder.textViewDateOfBirth.setText(listUsers.get(position).getDateofbirth());
        holder.textViewGender.setText(listUsers.get(position).getGender());
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewFirstName;
        public AppCompatTextView textViewLastName;
        public AppCompatTextView textViewDateOfBirth;
        public AppCompatTextView textViewGender;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;
        // public AppCompatTextView textViewMobileNumber;

        public UserViewHolder(View view) {
            super(view);
            textViewFirstName = (AppCompatTextView) view.findViewById(R.id.textViewFirstName);
            textViewLastName = (AppCompatTextView) view.findViewById(R.id.textViewLastName);
            textViewDateOfBirth = (AppCompatTextView) view.findViewById(R.id.textViewDateOfBirth);
             textViewGender = (AppCompatTextView) view.findViewById(R.id.textViewGender);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
            // textViewMobileNumber= (AppCompatTextView) view.findViewById(R.id.textViewMobileNumber);
        }
    }


}
