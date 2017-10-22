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

public class ColorsActivity extends AppCompatActivity {

    //mediaplayer object to play the audio files
    private MediaPlayer mMediaPlayer;

    // listener for when the audio has finished playing
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
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
        words.add(new Words("red","wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Words("mustard yellow","chiwiitə",R.drawable.color_mustard_yellow,
                R.raw.color_mustard_yellow));
        words.add(new Words("dusty yellow","topiisə", R.drawable.color_dusty_yellow,
                R.raw.color_dusty_yellow));
        words.add(new Words("green","chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Words("brown","takaakki",R.drawable.color_brown, R.raw.color_brown));
        words.add(new Words("gray","topoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Words("black","kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Words("white","kelelli", R.drawable.color_white, R.raw.color_white));


        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter =
                new WordAdapter(this, words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in
        // the word_list layout file.
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

                if (requestResponse == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this,
                            word.getAudioResource());
                    // start the audio file
                    mMediaPlayer.start();

                    // set up an onCompletionListener, which is called when the audio file is finished
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
