package com.example.android.miwokfragments;

/**
 * Created by Steven on 18/10/2017.
 */

public class Words {

    // English translation of the word
    private String mEnglishTranslation;
    // Miwok translation of the word
    private String mMiwokTranslation;
    // holds the image resource id
    private int mImageResource = NO_IMAGE_PROVIDED;
    // constant holding a value for when no image was provided
    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResource;

    /**
     * create a new Word object
     * @param englishTranslation is the English translation of the word
     * @param miwokTranslation is the Miwok translation of the word
     * @param audioResource id of the audio resource of the word
     */
    public Words(String englishTranslation, String miwokTranslation, int audioResource){
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResource = audioResource;
    }

    /**
     * create a new Word object
     * @param englishTranslation is the English translation of the word
     * @param miwokTranslation is the Miwok translation of the word
     * @param imageResource is the image resource id of the word
     * @param audioResource id of the audio resource of the word
     */
    public Words(String englishTranslation, String miwokTranslation, int imageResource,int audioResource ){
        mEnglishTranslation = englishTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResource = imageResource;
        mAudioResource = audioResource;
    }

    /**
     * get the English translation of the word
     * @return English translation
     */
    public String getEnglishTranslation(){
        return mEnglishTranslation;
    }

    /**
     * get the Miwok translation
     * @return Miwok translation
     */
    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    /**
     * get the image resource id for the Word
     * @return int holding the image resource id
     */
    public int getImageResource() {
        return mImageResource;
    }

    /**
     * checks the Word object to see whether the image resource id is the
     * int representing no image provided.
     * @return boolean whether the Word has an image or not
     */
    public boolean hasImage(){
        return mImageResource != NO_IMAGE_PROVIDED;
    }

    /**
     * get the audio resource id of the current word
     * @return id of the audio resource
     */
    public int getAudioResource() {
        return mAudioResource;
    }
}
