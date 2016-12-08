package com.radiantridge.restoradiantridge.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        int id = resto.getDatabaseId();
        Intent intent = new Intent();
        intent.setClass(getActivity(), ShowRestoActivity.class);

        intent.putExtra("resto", resto);

        // Check if the restaurant already exists in database
//        if (id == -1)
//        {
//            intent.putExtra("isZomato", true);
//            addRestoFields(intent, resto);
//        }
//        else
//        {
//            intent.putExtra("isZomato", false);
//            intent.putExtra("databaseId", id);
//        }

        startActivity(intent);
    }

    /**
     * This method sets the list to the adapter for the ListFragment to display.
     *
     * @param list      The list to be displayed
     */
    public void setNewList(Restaurant[] list)
    {
        if (list != null)
        {
            Log.i(TAG, "New ListAdapter");
            this.list = list;
            setListAdapter(new RestaurantAdapter(getActivity(), list));
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
     * This method adds the fields of a restaurant to an intent.
     *
     * @param intent    The intent to add the fields to
     * @param resto     The restaurant whose fields are being added
     */
    private void addRestoFields(Intent intent, Restaurant resto)
    {
        // Zomato does not have fields for notes, createdTime, modifiedTime or dbId
        intent.putExtra("name", resto.getName());
//        intent.putExtra("addNum", resto.getAddNum());
//        intent.putExtra("addStreet", resto.getAddStreet());
//        intent.putExtra("addCity", resto.getAddCity());
//        intent.putExtra("addPostalCode", resto.getAddPostalCode());
        intent.putExtra("genre", resto.getGenre());
        intent.putExtra("priceRange", resto.getPriceRange());
        intent.putExtra("starRating", resto.getStarRating());
        Log.i(TAG, "zomato rating " +resto.getStarRating());
        intent.putExtra("latitude", resto.getLatitude());
        intent.putExtra("longitude", resto.getLongitude());
        intent.putExtra("phone", resto.getPhone());
    }

    public void addToList(Restaurant[] newList)
    {
        // Make sure it is not null
        if (newList != null)
        {
            // Check if original list is null
            if (list == null)
            {
                // No need to add, only replace
                list = newList;
            }
            else
            {
                mergeList(newList);
            }

            setListAdapter(new RestaurantAdapter(getActivity(), list));
        }
    }

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
