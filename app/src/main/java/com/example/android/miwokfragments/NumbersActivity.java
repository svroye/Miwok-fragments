/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwokfragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    //mediaplayer object to play the audio files
    private MediaPlayer mMediaPlayer;

    // listener for when the audio has finished playing
    private MediaPlayer.OnCompletionListener onCompletionListener = new
            MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    // handles audio focus
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange ==
                            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // lost focus, pause the audio
                        mMediaPlayer.pause();
                        // go back to start of the audio file
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                        mAudioManager.abandonAudioFocus(afChangeListener);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // gain focus, start audio
                        mMediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("one","lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("two","otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("three","tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("four","oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("five","massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("six","temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("seven","kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("nine","wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("ten","na'aacha", R.drawable.number_ten, R.raw.number_ten));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter =
                new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_listyout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words word = words.get(i);

                // create and setup the mediaplayer with the audiofile of the list item that
                // the user clicked
                releaseMediaPlayer();

                //request audio focus for music and for a short period of time
                int requestResponse = mAudioManager.requestAudioFocus(afChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (requestResponse == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this,
                            word.getAudioResource());
                    // start the audio file
                    mMediaPlayer.start();

                    // set up an onCompletionListener, which is called when the audio file is
                    // finished
                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });

    }

    private void releaseMediaPlayer(){
        // if the mediaplayer is not null
        if (mMediaPlayer != null){
            // release the resources
            mMediaPlayer.release();
            // restore the variable
            mMediaPlayer = null;
            // release the audio focus
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}

