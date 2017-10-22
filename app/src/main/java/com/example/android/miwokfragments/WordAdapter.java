package com.example.android.miwokfragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Steven on 18/10/2017.
 */

public class WordAdapter extends ArrayAdapter<Words> {

    // int id holding a reference to the background color for the list item views
    private int mCategoryColor;
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param word A List of Words objects to display in a list
     */
    public WordAdapter(@NonNull Context context,  ArrayList<Words> word, int categoryColor) {
        // call the superclass's constructor, with second parameter being 0, since
        // we are not going to use a single TextView as a layout.
        super(context, 0, word);
        mCategoryColor = categoryColor;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        // if there are not recycled Views available, inflate a new one
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Words} object located at this position in the list
        final Words currentWord = getItem(position);

        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.linear_layout);
        linearLayout.setBackgroundResource(mCategoryColor);
        // Find the TextView in the list_item.xml layout with the ID english_word
        TextView englishTranslationTextView = (TextView) listItemView.findViewById(
                R.id.english_word);
        // Get the English Translation from the current Words object and
        // set this text on the TextView
        englishTranslationTextView.setText(currentWord.getEnglishTranslation());

        // Find the TextView in the list_item.xml layout with the ID miwok_word
        TextView miwokTranslationTextView = (TextView) listItemView.findViewById(
                R.id.miwok_word);
        // Get the miwokfragments Translation from the current Words object and
        // set this text on the TextView
        miwokTranslationTextView.setText(currentWord.getmMiwokTranslation());

        // find the imageView for displaying the image in each list item
        ImageView wordImage = (ImageView) listItemView.findViewById(R.id.image_view);
        // check whether the Word has an image associated with it
        if(currentWord.hasImage()){
            // set the image to the ImageView
            wordImage.setImageResource(currentWord.getImageResource());
            // make sure the View is visible
            wordImage.setVisibility(View.VISIBLE);
        } else {
            // no image, so hide the View
            wordImage.setVisibility(View.GONE);
        }

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
