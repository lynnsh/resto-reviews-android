package com.radiantridge.restoradiantridge.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.radiantridge.restoradiantridge.R;
import com.radiantridge.restoradiantridge.listadapters.RestaurantAdapter;
import com.radiantridge.restoradiantridge.objects.Restaurant;
import com.radiantridge.restoradiantridge.activities.ShowRestoActivity;

/**
 * ListFragment for the Restaurant list.
 *
 * @author Erika Bourque
 * @version 03/12/2016
 */
public class RestoListFragment extends ListFragment {
    private static final String TAG = "ListFrag";
    private final int SHOW_RESTO = 1;
    Restaurant[] list;

    /**
     * Overriden lifecycle method.  Sets up the fragment.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setLongClickable(true);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "Item long clicked at pos: " + position);
                callRestaurant((Restaurant) getListView().getItemAtPosition(position));
                return true;
            }
        });
    }

    /**
     * Event handler for the ListFragment.  Sends the clicked restaurant for
     * display.
     *
     * @param l         The list that was clicked
     * @param v         The view that was clicked within the list
     * @param position  The position of the clicked view
     * @param id        The id of the row clicked
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "Item clicked at pos: " + position);
        displayRestoDetails((Restaurant) getListView().getItemAtPosition(position));
    }

    /**
     * This method launches the AddRestoActivity with the database
     * id of the restaurant that needs to be displayed.
     *
     * @param resto     The restaurant to be displayed
     */
    private void displayRestoDetails(Restaurant resto)
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShowRestoActivity.class);

        // Add resto to be displayed
        intent.putExtra("resto", resto);

        startActivityForResult(intent, SHOW_RESTO);
    }

    /**
     * This method sets the list to the adapter for the ListFragment to display.
     *
     * @param newList      The list to be displayed
     */
    public void addToList(Restaurant[] newList, boolean isNewList)
    {
        if (newList != null)
        {
            // Check if original list is null
            if (list == null || isNewList)
            {
                // No need to add, only replace
                list = newList;
            }
            else
            {
                mergeList(newList);
            }

            Log.i(TAG, "New ListAdapter");
            setListAdapter(new RestaurantAdapter(getActivity(), list));
            getListView().setDivider(new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.grey)));
            getListView().setDividerHeight(1);
        }
    }

    /**
     * This method dials a restaurant's phone number if it exists, and
     * shows a dialog if does not.
     *
     * @param resto     The restaurant to dial
     */
    private void callRestaurant(Restaurant resto)
    {
        // Check if the phone number exists
        if (resto.getPhone() != null)
        {
            try
            {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + resto.getPhone())));
            }
            catch(ActivityNotFoundException e)
            {
                Log.i(TAG, "Device phone not found");
                Toast.makeText(getActivity(), R.string.toast_no_phone, Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.dialog_phone_title).setMessage(R.string.dialog_phone_text);
            builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Cancel the dialog
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    /**
     * This method adds the new list to the end of the current list.
     *
     * @param newList   The new list to be added
     */
    private void mergeList(Restaurant[] newList)
    {
        int size = list.length + newList.length;
        Restaurant[] temp = new Restaurant[size];

        // Add current list to new array
        for(int i = 0; i < list.length; i++)
        {
            temp[i] = list[i];
        }

        // Add new list to new array
        for (int i = 0; i < newList.length; i++)
        {
            temp[i + list.length] = newList[i];
        }

        list = temp;
    }

    
}
