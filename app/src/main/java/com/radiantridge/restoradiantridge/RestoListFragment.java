package com.radiantridge.restoradiantridge;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * ListFragment for the Restaurant list.
 *
 * @author Erika Bourque
 * @version 01/12/2016
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
//        int id = resto.getDatabaseId();
//
//        // Send Database Id
//        Intent intent = new Intent();
//        // Class could change (not AddRestoActivity, to see with Rafia)
//        intent.setClass(getActivity(), AddRestoActivity.class);
//        intent.putExtra("databaseId", id);
//        startActivity(intent);
    }

    /**
     * This method sets the list to the adapter for the ListFragment to display.
     *
     * @param list      The list to be displayed
     */
    public void setList(Restaurant[] list)
    {
        // TODO: use custom adapter to display only name of resto
        Log.i(TAG, "New ListAdapter");
        this.list = list;
        setListAdapter(new ArrayAdapter<Restaurant>(getActivity(), android.R.layout.simple_list_item_1, list));
    }

    // TODO: long click launches phone with tel # of resto or dialog box saying no phone number
}
